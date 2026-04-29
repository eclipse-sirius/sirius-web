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
import { memo, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { ConnectorContext } from '../connector/ConnectorContext';
import { ConnectorContextValue } from '../connector/ConnectorContext.types';
import { useHandles } from './../connector/useHandles';
import { ConnectionCreationHandlesProps } from './ConnectionCreationHandles.types';
import { useConnectionCandidatesQuery } from './useConnectionCandidatesQuery';

export const ConnectionCreationHandles = memo(
  ({ nodeId, nodePosition, nodeWidth, nodeHeight, isDraggedNode }: ConnectionCreationHandlesProps) => {
    const { editingContextId, diagramId, readOnly } = useContext<DiagramContextValue>(DiagramContext);
    const { mountNodeHandles, updateNodeHandles, unMountHandles } = useHandles();
    const { setCandidates } = useContext<ConnectorContextValue>(ConnectorContext);

    const candidates = useConnectionCandidatesQuery(editingContextId, diagramId, nodeId);
    const shouldRender = candidates !== null && candidates.length > 0 && !readOnly;

    // Unmount/Mount while selected
    useEffect(() => {
      if (shouldRender) {
        mountNodeHandles(nodeId, nodePosition, nodeWidth, nodeHeight);
      }
      return () => {
        unMountHandles();
      };
    }, [shouldRender]);

    // Unmount/Mount while dragging the node
    useEffect(() => {
      if (isDraggedNode) {
        unMountHandles();
      } else if (!isDraggedNode && shouldRender) {
        mountNodeHandles(nodeId, nodePosition, nodeWidth, nodeHeight);
      }
    }, [isDraggedNode]);

    // Update handle position if needed (for resize)
    useEffect(() => {
      updateNodeHandles(nodeId, nodePosition, nodeWidth, nodeHeight);
    }, [nodePosition, nodeWidth, nodeHeight]);

    // Set candidates in the context
    useEffect(() => {
      if (candidates !== null) {
        setCandidates(candidates);
      }
    }, [candidates]);

    return null;
  }
);
