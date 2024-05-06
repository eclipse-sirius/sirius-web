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

import { useTheme } from '@material-ui/core/styles';
import { useContext, useMemo } from 'react';
import { ReactFlowState, useStore } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDropNodeStyleValue } from './useDropNodeStyle.types';

const draggedNodeIdSelector = (state: ReactFlowState) =>
  Array.from(state.nodeInternals.values()).find((n) => n.dragging)?.id || '';

export const useDropNodeStyle = (
  isDropNodeTarget: boolean,
  isDropNodeCandidate,
  isDragging: boolean
): useDropNodeStyleValue => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const draggedNodeId = useStore(draggedNodeIdSelector);

  const theme = useTheme();
  const style: React.CSSProperties = {};

  if (draggedNodeId && !readOnly) {
    if (!isDragging && !isDropNodeCandidate) {
      style.opacity = '0.4';
    }
    if (isDropNodeTarget) {
      style.boxShadow = `0px 0px 2px 2px ${theme.palette.primary.main}`;
    }
  }

  const memoizedStyle = useMemo(() => style, [isDropNodeTarget, isDropNodeCandidate, isDragging, draggedNodeId]);

  return { style: memoizedStyle };
};
