/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import { useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { ConnectorContext } from '../connector/ConnectorContext';
import { ConnectorContextValue } from '../connector/ConnectorContext.types';
import { useHandles } from '../connector/useHandles';
import { useConnectionCandidatesQuery } from '../handles/useConnectionCandidatesQuery';
import { EdgeCreationHandleProps } from './EdgeCreationHandle.types';

export const EdgeCreationHandle = ({ edgeId, edgePath, isPathDragged }: EdgeCreationHandleProps) => {
  const { mountEdgeHandles, unMountHandles } = useHandles();
  const { editingContextId, diagramId, readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const candidates = useConnectionCandidatesQuery(editingContextId, diagramId, edgeId);
  const shouldRender = candidates !== null && candidates.length > 0 && !readOnly;
  const { setCandidates } = useContext<ConnectorContextValue>(ConnectorContext);

  // Unmount/Mount around the center of the edge while selected
  useEffect(() => {
    if (shouldRender) {
      mountEdgeHandles(edgeId, edgePath);
    }
    return () => {
      unMountHandles();
    };
  }, [shouldRender]);

  // Unmount/Mount while moving bending point or segment
  useEffect(() => {
    if (isPathDragged) {
      unMountHandles();
    } else if (!isPathDragged && shouldRender) {
      mountEdgeHandles(edgeId, edgePath);
    }
  }, [isPathDragged]);

  // Set candidates in the context
  useEffect(() => {
    if (candidates !== null) {
      setCandidates(candidates);
    }
  }, [candidates]);

  return null;
};
