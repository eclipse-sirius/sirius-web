/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import { Selection } from '@eclipse-sirius/sirius-components-core';
import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLDiagramEventPayload } from '../graphql/subscription/diagramEventSubscription.types';

export interface DiagramRepresentationState {
  id: string;
  message: string | null;
  toolSelections: Map<string, Selection>;
}

export interface GQLDiagramEventData {
  diagramEvent: GQLDiagramEventPayload;
}

export interface GQLDiagramEventVariables {
  input: GQLDiagramEventInput;
}

export interface GQLDiagramEventInput {
  id: string;
  editingContextId: string;
  diagramId: string;
}

export interface GQLDiagramDescriptionVariables {
  editingContextId: string;
  representationId: string;
}

export interface GQLDiagramDescriptionData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext | null;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata | null;
}

export interface GQLRepresentationMetadata {
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  __typename?: string;
  id: string;
}

export interface GQLDiagramDescription extends GQLRepresentationDescription {
  id: string;
  nodeDescriptions: GQLNodeDescription[];
  toolbar?: GQLDiagramToolbar;
  dropNodeCompatibility: GQLDropNodeCompatibility[];
  debug: boolean;
  arrangeLayoutDirection: GQLArrangeLayoutDirection;
  layoutOption: GQLDiagramLayoutOption;
  minimapVisible: boolean;
}

export type GQLDiagramLayoutOption = 'NONE' | 'AUTO_LAYOUT' | 'AUTO_UNTIL_MANUAL';

export interface GQLDiagramToolbar {
  expandedByDefault: boolean;
}

export type GQLArrangeLayoutDirection = 'UNDEFINED' | 'RIGHT' | 'DOWN' | 'LEFT' | 'UP';

export interface GQLDropNodeCompatibility {
  droppedNodeDescriptionId: string;
  droppableOnDiagram: boolean;
  droppableOnNodeTypes: string[];
}
