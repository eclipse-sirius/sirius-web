/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { Connection, Edge } from '@xyflow/react';

export interface UseReconnectEdge {
  reconnectEdge: (oldEdge: Edge, newConnection: Connection) => void;
  onReconnectEdgeEnd: (event: MouseEvent | TouchEvent, edge: Edge, handleType: 'source' | 'target') => void;
}

export interface GQLReconnectEdgeVariables {
  input: GQLReconnectEdgeInput;
}

export interface GQLReconnectEdgeInput {
  id: string;
  editingContextId: string;
  representationId: string;
  edgeId: string;
  newEdgeEndId: string;
  reconnectEdgeKind: GQLReconnectKind;
}

export interface Position {
  x: number;
  y: number;
}

export interface GQLReconnectEdgeData {
  reconnectEdge: GQLReconnectEdgePayload;
}

export interface GQLReconnectEdgePayload {
  __typename: string;
}

export enum GQLReconnectKind {
  SOURCE = 'SOURCE',
  TARGET = 'TARGET',
}

export interface GQLErrorPayload extends GQLReconnectEdgePayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLReconnectEdgePayload {
  messages: GQLMessage[];
}
