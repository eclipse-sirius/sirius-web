/*******************************************************************************
 * Copyright (c) 2026 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import {
  ApolloClient,
  ApolloLink,
  InMemoryCache,
  Observable,
  type FetchResult,
  type Operation,
  type RequestHandler,
} from "@apollo/client";
import type { GQLLabel } from "@eclipse-sirius/sirius-components-diagrams/graphql/subscription/labelFragment.types";
import type { GQLDiagram } from "@eclipse-sirius/sirius-components-diagrams/index";
import type { GQLPalette } from "@eclipse-sirius/sirius-components-diagrams/renderer/palette/Palette.types";
import {
  createDefaultDescription,
  createDefaultPalette,
} from "./diagramConstructionUtils";

export const useClient = (diagram: GQLDiagram) => {
  const cache = new InMemoryCache({ addTypename: true });

  const paletteData = createDefaultPalette();
  const descriptionData = createDefaultDescription();

  const paletteWithTypename = { ...paletteData, __typename: "Palette" };
  const descriptionWithTypename = {
    ...descriptionData,
    __typename: "DiagramDescription",
    actions: [],
    palette: paletteWithTypename,
  };

  const enrichedDiagram = {
    ...diagram,
    __typename: "Diagram",
    descriptionId: descriptionData.id,
    description: descriptionWithTypename,
    metadata: {
      ...diagram.metadata,
      __typename: "RepresentationMetadata",
      descriptionId: descriptionData.id,
      description: descriptionWithTypename,
    },
    layoutData: {
      ...diagram.layoutData,
      __typename: "DiagramLayoutData",
    },
  };

  interface GQLGenericPayload {
    diagram?: GQLDiagram;
    labels?: GQLLabel[];
    palette?: GQLPalette;
  }

  const getObservable = (op: Operation) =>
    new Observable<FetchResult>((o) => {
      const success = (payloadData: GQLGenericPayload) => ({
        __typename: "SuccessPayloadWrapper",
        payload: { __typename: "SuccessPayload", messages: [], ...payloadData },
      });

      const responseData = {
        viewer: {
          __typename: "Viewer",
          editingContext: {
            __typename: "EditingContext",
            id: "root",
            objects: [],
            representation: enrichedDiagram.metadata,
            representations: {
              __typename: "EditingContextRepresentationsConnection",
              edges: [
                {
                  __typename: "EditingContextRepresentationsEdge",
                  node: enrichedDiagram.metadata,
                },
              ],
            },
            getDiagram: success({ diagram: enrichedDiagram }),
            getDiagramDescription: descriptionWithTypename,
            getObjectsLabels: success({ labels: [] }),
            getPalette: success({ palette: paletteWithTypename }),
          },
        },
        diagramEvent: {
          __typename: "DiagramRefreshedEventPayload",
          id: enrichedDiagram.id,
          diagram: enrichedDiagram,
          cause: "refresh",
        },
        layoutDiagram: success({ diagram: enrichedDiagram }),
        pinDiagramElement: success({ diagram: enrichedDiagram }),
        hideDiagramElement: success({ diagram: enrichedDiagram }),
        fadeDiagramElement: success({ diagram: enrichedDiagram }),
      };

      o.next({ data: responseData });
      if (op.operationName !== "diagramEvent") {
        o.complete();
      }
    });

  const requestHandler: RequestHandler = (op) => getObservable(op);

  const client = new ApolloClient({
    cache,
    link: new ApolloLink(requestHandler),
  });

  return client;
};
