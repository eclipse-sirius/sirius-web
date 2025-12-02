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

import { useTheme } from '@mui/material/styles';
import { useContext, useMemo } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDropNodeStyleValue } from './useDropNodeStyle.types';

export const useDropNodeStyle = (
  isDropNodeTarget: boolean,
  isDropNodeCandidate,
  isDragging: boolean
): useDropNodeStyleValue => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

  const theme = useTheme();
  const style: React.CSSProperties = {};

  if (!readOnly) {
    if (!isDragging && isDropNodeCandidate) {
      style.boxShadow = `0px 0px 2px 2px ${theme.palette.success.main}`;
      style.transition = `box-shadow 0.3s ease-in-out`;
    }
    if (isDropNodeTarget) {
      style.boxShadow = `0px 0px 2px 2px ${theme.palette.primary.main}`;
    }
  }

  const memoizedStyle = useMemo(() => style, [isDropNodeTarget, isDropNodeCandidate, isDragging]);

  return { style: memoizedStyle };
};
