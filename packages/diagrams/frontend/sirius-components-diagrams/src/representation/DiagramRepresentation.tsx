/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { gql, useQuery } from '@apollo/client';
import { RepresentationComponentProps, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { ReactFlowProvider } from '@xyflow/react';
import { memo, useEffect, useState } from 'react';
import { DiagramDescriptionContext } from '../contexts/DiagramDescriptionContext';
import { ConnectorContextProvider } from '../renderer/connector/ConnectorContext';
import { DiagramDirectEditContextProvider } from '../renderer/direct-edit/DiagramDirectEditContext';
import { DropNodeContextProvider } from '../renderer/dropNode/DropNodeContext';
import { MarkerDefinitions } from '../renderer/edge/MarkerDefinitions';
import { FullscreenContextProvider } from '../renderer/fullscreen/FullscreenContext';
import { NodeContextProvider } from '../renderer/node/NodeContext';
import { DiagramElementPaletteContextProvider } from '../renderer/palette/DiagramElementPaletteContext';
import { DiagramPaletteContextProvider } from '../renderer/palette/DiagramPaletteContext';
import {
  DiagramRepresentationState,
  GQLDiagramDescription,
  GQLDiagramDescriptionData,
  GQLDiagramDescriptionVariables,
} from './DiagramRepresentation.types';
import { DiagramSubscriptionProvider } from './DiagramSubscriptionProvider';

export const getDiagramDescription = gql`
  query getDiagramDescription($editingContextId: ID!, $representationId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on DiagramDescription {
              id
              nodeDescriptions {
                id
                userResizable
                keepAspectRatio
                childNodeDescriptionIds
                borderNodeDescriptionIds
              }
              childNodeDescriptionIds
              dropNodeCompatibility {
                droppedNodeDescriptionId
                droppableOnDiagram
                droppableOnNodeTypes
              }
              debug
              arrangeLayoutDirection
            }
          }
        }
      }
    }
  }
`;

export const DiagramRepresentation = memo(
  ({ editingContextId, representationId, readOnly }: RepresentationComponentProps) => {
    const [state, setState] = useState<DiagramRepresentationState>({
      id: crypto.randomUUID(),
      message: null,
    });
    const { addErrorMessage } = useMultiToast();

    const {
      loading: diagramDescriptionLoading,
      data: diagramDescriptionData,
      error: diagramDescriptionError,
    } = useQuery<GQLDiagramDescriptionData, GQLDiagramDescriptionVariables>(getDiagramDescription, {
      variables: {
        editingContextId,
        representationId,
      },
    });

    useEffect(() => {
      if (!diagramDescriptionLoading) {
        setState((prevState) => ({
          ...prevState,
          diagramDescription: diagramDescriptionData?.viewer.editingContext.representation.description,
        }));
      }
      if (diagramDescriptionError) {
        const { message } = diagramDescriptionError;
        addErrorMessage(message);
      }
    }, [diagramDescriptionLoading, diagramDescriptionData, diagramDescriptionError]);

    const diagramDescription: GQLDiagramDescription | undefined =
      diagramDescriptionData?.viewer.editingContext.representation.description;

    if (state.message) {
      return <div>{state.message}</div>;
    }

    if (!diagramDescription) {
      return <div></div>;
    }

    return (
      <ReactFlowProvider>
        <DiagramDirectEditContextProvider>
          <DiagramPaletteContextProvider>
            <DiagramElementPaletteContextProvider>
              <ConnectorContextProvider>
                <DropNodeContextProvider>
                  <NodeContextProvider>
                    <MarkerDefinitions />
                    <FullscreenContextProvider>
                      <DiagramDescriptionContext.Provider value={{ diagramDescription }}>
                        <DiagramSubscriptionProvider
                          diagramId={representationId}
                          editingContextId={editingContextId}
                          readOnly={readOnly}></DiagramSubscriptionProvider>
                      </DiagramDescriptionContext.Provider>
                    </FullscreenContextProvider>
                  </NodeContextProvider>
                </DropNodeContextProvider>
              </ConnectorContextProvider>
            </DiagramElementPaletteContextProvider>
          </DiagramPaletteContextProvider>
        </DiagramDirectEditContextProvider>
      </ReactFlowProvider>
    );
  }
);
