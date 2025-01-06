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
import { GQLMessage } from '../Tool.types';

export interface UseDiagramDeleteMutation {
  deleteElements: (nodeIds: string[], edgesId: string[], deletePocily: GQLDeletionPolicy) => void;
}

export interface GQLDeleteFromDiagramSuccessPayload extends GQLDeleteFromDiagramPayload {
  messages: GQLMessage[];
}

export interface GQLDeleteFromDiagramVariables {
  input: GQLDeleteFromDiagramInput;
}

export interface GQLDeleteFromDiagramInput {
  id: string;
  editingContextId: string;
  representationId: string;
  nodeIds: string[];
  edgeIds: string[];
  deletionPolicy: GQLDeletionPolicy;
}

export interface GQLDeleteFromDiagramData {
  deleteFromDiagram: GQLDeleteFromDiagramPayload;
}

export interface GQLDeleteFromDiagramPayload {
  __typename: string;
}

export enum GQLDeletionPolicy {
  SEMANTIC = 'SEMANTIC',
  GRAPHICAL = 'GRAPHICAL',
}

export interface GQLErrorPayload extends GQLDeleteFromDiagramPayload {
  message: string;
  messages: GQLMessage[];
}
