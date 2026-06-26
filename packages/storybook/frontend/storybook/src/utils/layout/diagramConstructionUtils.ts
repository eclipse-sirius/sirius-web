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
import { GQLAction, GQLDiagram, GQLDiagramDescription } from '@eclipse-sirius/sirius-components-diagrams';
import { GQLPalette } from '@eclipse-sirius/sirius-components-palette';

export type EnrichedDescription = GQLDiagramDescription & {
  __typename: string;
  palette: GQLPalette;
  actions: GQLAction[];
  childNodeDescriptionIds: string[];
  autoLayout: boolean;
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

export const createDefaultDescription = (rawDesc?: GQLDiagramDescription): EnrichedDescription => {
  const description = rawDesc as any;
  return {
    ...description,
    id: description?.id || 'default-description-id',
    __typename: 'DiagramDescription',
    debug: description?.debug || false,
    dropNodeCompatibility: description?.dropNodeCompatibility || [],
    arrangeLayoutDirection: description?.arrangeLayoutDirection || 'DOWN',
    nodeDescriptions: description?.nodeDescriptions || [],
    palette: description?.palette || createDefaultPalette(),
    childNodeDescriptionIds: description?.childNodeDescriptionIds || [],
    autoLayout: description?.autoLayout || false,
    actions: description?.actions || [],
    toolbar: description?.toolbar || { expandedByDefault: false },
    layoutOption: description?.layoutOption || 'NONE',
    minimapVisible: description?.minimapVisible || false,
  };
};

export const assembleDiagram = (representationJson: GQLDiagram, descriptionJson: GQLDiagramDescription): GQLDiagram => {
  const representation = representationJson;
  const description = createDefaultDescription(descriptionJson);

  const result: EnrichedGQLDiagram = {
    ...representation,
    nodes: representation.nodes,
    edges: representation.edges,
    layoutData: representation.layoutData,
    descriptionId: description.id,
    description: description,
    metadata: representation.metadata,
  };
  return result;
};
