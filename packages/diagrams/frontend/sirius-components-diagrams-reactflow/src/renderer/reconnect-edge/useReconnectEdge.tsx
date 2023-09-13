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
import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useContext, useEffect } from 'react';
import { Connection, Edge } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  GQLErrorPayload,
  GQLReconnectEdgeData,
  GQLReconnectEdgeInput,
  GQLReconnectEdgePayload,
  GQLReconnectEdgeVariables,
  GQLReconnectKind,
  UseReconnectEdge,
} from './useReconnectEdge.types';

export const reconnectEdgeMutation = gql`
  mutation reconnectEdge($input: ReconnectEdgeInput!) {
    reconnectEdge(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLReconnectEdgePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useReconnectEdge = (): UseReconnectEdge => {
  const { addErrorMessage } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const [updateEdgeEnd, { data: reconnectEdgeData, error: reconnectEdgeError }] = useMutation<
    GQLReconnectEdgeData,
    GQLReconnectEdgeVariables
  >(reconnectEdgeMutation);

  const handleReconnectEdge = useCallback(
    (edgeId: string, newEdgeEndId: string, reconnectEdgeKind: GQLReconnectKind): void => {
      const input: GQLReconnectEdgeInput = {
        id: crypto.randomUUID(),
        editingContextId: editingContextId,
        representationId: diagramId,
        edgeId,
        newEdgeEndId,
        reconnectEdgeKind,
        newEdgeEndPosition: { x: 0, y: 0 },
      };
      updateEdgeEnd({ variables: { input } });
    },
    [editingContextId, diagramId, updateEdgeEnd]
  );

  useEffect(() => {
    if (reconnectEdgeError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (reconnectEdgeData) {
      const { reconnectEdge } = reconnectEdgeData;
      if (isErrorPayload(reconnectEdge)) {
        addErrorMessage(reconnectEdge.message);
      }
    }
  }, [reconnectEdgeData, reconnectEdgeError]);

  const reconnectEdge = (oldEdge: Edge, newConnection: Connection) => {
    const edgeId = oldEdge.id;
    let reconnectEdgeKind = GQLReconnectKind.TARGET;
    let newEdgeTargetId = newConnection.target;
    if (oldEdge.target === newConnection.target) {
      reconnectEdgeKind = GQLReconnectKind.SOURCE;
      newEdgeTargetId = newConnection.source;
    }
    if (newEdgeTargetId) {
      handleReconnectEdge(edgeId, newEdgeTargetId, reconnectEdgeKind);
    }
  };

  return { reconnectEdge };
};
