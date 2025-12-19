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

import { GQLMessage } from '@eclipse-sirius/sirius-components-core';
import { Node, OnNodeDrag, XYPosition } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';

export interface UseDropNodesValue {
  onNodesDragStart: OnNodeDrag<Node<NodeData>>;
  onNodesDrag: OnNodeDrag<Node<NodeData>>;
  onNodesDragStop: OnNodeDrag<Node<NodeData>>;
}

export interface GQLDropNodesPayload {
  __typename: string;
}

export interface GQLDropNodesData {
  dropNodes: GQLDropNodesPayload;
}

export interface GQLDropNodesVariables {
  input: GQLDropNodesInput;
}

export interface GQLDropNodesInput {
  id: string;
  editingContextId: string;
  representationId: string;
  droppedElementIds: string[];
  targetElementId: string | null;
  dropPositions: XYPosition[];
}

export interface GQLErrorPayload extends GQLDropNodesPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLDropNodesPayload {
  messages: GQLMessage[];
}
