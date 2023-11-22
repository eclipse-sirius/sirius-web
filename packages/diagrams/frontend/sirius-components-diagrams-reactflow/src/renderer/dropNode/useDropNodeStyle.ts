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

import { useTheme } from '@material-ui/core/styles';
import { useDropNode } from '../dropNode/useDropNode';
import { useDropNodeStyleValue } from './useDropNodeStyle.types';

export const useDropNodeStyle = (nodeId: string): useDropNodeStyleValue => {
  const { compatibleNodeIds, targetNodeId, draggedNode } = useDropNode();
  const theme = useTheme();

  const isCompatibleDropTarget: boolean = compatibleNodeIds.includes(nodeId);
  const isSelectedDropTarget: boolean = nodeId === targetNodeId;
  const style: React.CSSProperties = {};

  if (draggedNode !== null) {
    if (draggedNode.id !== nodeId && !isCompatibleDropTarget) {
      style.opacity = '0.4';
    }
    if (isSelectedDropTarget && isCompatibleDropTarget) {
      style.boxShadow = `0px 0px 2px 2px ${theme.palette.primary.main}`;
    }
  }
  return { style };
};
