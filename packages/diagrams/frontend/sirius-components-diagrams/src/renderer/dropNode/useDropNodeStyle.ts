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

import { useTheme } from '@mui/material/styles';
import { useContext, useMemo } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDropNodeStyleValue } from './useDropNodeStyle.types';

/**
 * Computes the CSS style customization to apply to a given node during a DnD operation.
 *
 * @param isDropNodeTarget whether the node to style is currently targeted by the drop
 *                         (i.e. the mouse is on top of it, and releasing the mousing
 *                         button would trigger the drop on this node)
 * @param isDropNodeCandidate whether the node to style is a compatible drop candidate
 *                            for the dragged element(s)
 * @param isDragging whether the node is (one of) the node(s) being moved/dragged.
 * @returns CSS properties to apply to the node to give feedback to the user.
 */
export const useDropNodeStyle = (
  isDropNodeTarget: boolean,
  isDropNodeCandidate: boolean,
  isDragging: boolean
): useDropNodeStyleValue => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const theme = useTheme();
  const dropPossible = `rgb(from ${theme.palette.success.main} r g b / 0.5)`;
  const dropValid = theme.palette.success.main;
  const dropInvalid = `rgb(from ${theme.palette.error.main} r g b / 0.5)`;

  const style: React.CSSProperties = {};
  if (!readOnly && !isDragging) {
    let highlightColor: string | null = null;
    if (isDropNodeCandidate) {
      highlightColor = isDropNodeTarget ? dropValid : dropPossible;
    } else if (isDropNodeTarget) {
      highlightColor = dropInvalid;
    }
    if (highlightColor) {
      style.boxShadow = `0px 0px 2px 2px ${highlightColor}`;
      style.transition = `box-shadow 0.3s ease-in-out`;
    }
  }

  const memoizedStyle = useMemo(() => style, [isDropNodeTarget, isDropNodeCandidate, isDragging]);

  return { style: memoizedStyle };
};
