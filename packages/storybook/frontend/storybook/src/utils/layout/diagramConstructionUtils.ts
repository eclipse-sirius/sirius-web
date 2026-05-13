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
  GQLDiagram,
  GQLDiagramDescription,
  GQLNode,
  GQLNodeStyle,
  GQLPalette,
} from '@eclipse-sirius/sirius-components-diagrams';
import { GQLAction } from '../../../../../../diagrams/frontend/sirius-components-diagrams/dist/renderer/actions/useActions.types';

export type EnrichedDescription = GQLDiagramDescription & {
  __typename: string;
  palette: GQLPalette;
  actions: GQLAction;
  childNodeDescriptionIds: string[];
  layoutConfigurations: string[];
};

export type EnrichedGQLDiagram = GQLDiagram & {
  descriptionId: string;
  description: EnrichedDescription;
};

export const createDefaultPalette = (): GQLPalette => ({
  id: 'default-palette',
  paletteEntries: [],
  quickAccessTools: [],
});

export const createDefaultDescription = (rawDesc?: any): EnrichedDescription => {
  const description = rawDesc?.default || rawDesc;

  const palette: GQLPalette = {
    ...createDefaultPalette(),
    ...(description?.palette || {}),
    __typename: 'Palette',
  };
  palette.paletteEntries = palette.paletteEntries || [];
  palette.quickAccessTools = palette.quickAccessTools || [];

  return {
    ...description,
    id: description?.id,
    __typename: 'DiagramDescription',
    debug: description?.debug,
    dropNodeCompatibility: description?.dropNodeCompatibility,
    arrangeLayoutDirection: description?.arrangeLayoutDirection,
    nodeDescriptions: description?.nodeDescriptions,
    actions: description?.actions || [],
    palette: palette,
    layoutConfigurations: description?.layoutConfigurations || [],
    childNodeDescriptionIds: description?.childNodeDescriptionIds || [],
    toolbar: description?.toolbar,
    autoLayout: description?.autoLayout,
    minimapVisible: description?.minimapVisible || false,
  };
};

export const assembleDiagram = (representationJson: any, descriptionJson: any): GQLDiagram => {
  const representation = representationJson?.default || representationJson;
  const rawDesc = descriptionJson?.viewer?.editingContext?.representation?.description;

  const description = createDefaultDescription(rawDesc);
  const getArray = (data: GQLDiagram) => (Array.isArray(data) ? data : Object.values(data || {}));

  const formatNodes = (nodes: GQLNode<GQLNodeStyle>[] = []): GQLNode<GQLNodeStyle>[] =>
    nodes.map((node) => ({
      ...node,
      __typename: 'Node',
      id: node.id,
      state: node.state,
      style: { ...(node.style || {}), __typename: 'RectangularNodeStyle' },
      insideLabel: node.insideLabel
        ? {
            ...node.insideLabel,
            __typename: 'InsideLabel',
            style: {
              ...node.insideLabel.style,
              __typename: 'LabelStyle',
              iconURL: [],
            },
          }
        : undefined,
      childNodes: formatNodes(node.childNodes),
      borderNodes: formatNodes(node.borderNodes),
    }));

  const result: EnrichedGQLDiagram = {
    ...representation,
    __typename: 'Diagram',
    nodes: formatNodes(representation.nodes),
    edges: representation.edges,
    layoutData: {
      __typename: 'DiagramLayoutData',
      nodeLayoutData: getArray(representation.layoutData?.nodeLayoutData).map((data: GQLDiagram) => ({
        ...data,
        __typename: 'NodeLayoutData',
      })),
      edgeLayoutData: getArray(representation.layoutData?.edgeLayoutData).map((data: GQLDiagram) => ({
        ...data,
        __typename: 'EdgeLayoutData',
      })),
      labelLayoutData: getArray(representation.layoutData?.labelLayoutData).map((data: GQLDiagram) => ({
        ...data,
        __typename: 'LabelLayoutData',
      })),
    },
    descriptionId: description.id,
    description: description,
    metadata: {
      ...representation.metadata,
      __typename: 'RepresentationMetadata',
      descriptionId: description.id,
      description: description,
    },
  };
  return result;
};
