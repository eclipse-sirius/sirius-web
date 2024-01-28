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

import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import {
  GQLDiagramEventPayload,
  GQLDiagramRefreshedEventPayload,
} from '../graphql/subscription/diagramEventSubscription.types';

export interface DiagramRepresentationState {
  id: string;
  diagramRefreshedEventPayload: GQLDiagramRefreshedEventPayload | null;
  complete: boolean;
  message: string | null;
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
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentation;
}

export interface GQLRepresentation {
  description: GQLDiagramDescription;
}

export interface GQLDiagramDescription {
  id: string;
  nodeDescriptions: GQLNodeDescription[];
  dropNodeCompatibility: GQLDropNodeCompatibility[];
  debug: boolean;
}

export interface GQLDropNodeCompatibility {
  droppedNodeDescriptionId: string;
  droppableOnDiagram: boolean;
  droppableOnNodeTypes: string[];
}
