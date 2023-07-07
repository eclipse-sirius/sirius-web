/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

export interface UseDropValue {
  onDrop: (event: React.DragEvent, diagramElementId?: string) => void;
  onDragOver: (event: React.DragEvent) => void;
}

export interface GQLDropOnDiagramPayload {
  __typename: string;
}
export interface GQLDropOnDiagramData {
  dropOnDiagram: GQLDropOnDiagramPayload;
}

export interface GQLDropOnDiagramInput {
  id: string;
  editingContextId: string;
  representationId: string;
  objectIds: string[];
  startingPositionX: number;
  startingPositionY: number;
  diagramTargetElementId: string;
}

export interface GQLDropOnDiagramVariables {
  input: GQLDropOnDiagramInput;
}
export interface GQLErrorPayload extends GQLDropOnDiagramPayload {
  messages: string;
}
