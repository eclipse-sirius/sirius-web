/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { useTheme } from '@mui/material/styles';
import { useCallback, useContext, useMemo, useState } from 'react';
import { useStore } from '../../representation/useStore';
import { DropNodeContext } from './DropNodeContext';
import { DropNodeContextValue } from './DropNodeContext.types';
import { useDropDiagramStyleValue } from './useDropDiagramStyle.types';

export const useDropDiagramStyle = (): useDropDiagramStyleValue => {
  const { droppableOnDiagram } = useContext<DropNodeContextValue>(DropNodeContext);
  const { getNodes } = useStore();
  const theme = useTheme();
  const [background, setBackground] = useState<string>(theme.palette.background.default);

  const targetNode = getNodes().find((node) => node.data.isDropNodeTarget);
  const draggedNode = getNodes().find((node) => node.dragging);

  const diagramTargeted: boolean =
    !!draggedNode && !targetNode && !!draggedNode?.parentNode && !draggedNode.data.isBorderNode;
  const diagramForbidden: boolean = diagramTargeted && !!draggedNode?.id !== null && !droppableOnDiagram;
  const backgroundColor = diagramForbidden ? theme.palette.action.disabledBackground : background;

  const setBackgroundAfterTransformation = useCallback(
    (newBackground: string) => {
      setBackground(getCSSColor(newBackground, theme));
    },
    [setBackground]
  );

  const memoizedStyle = useMemo(() => {
    return {
      background: backgroundColor,
      smallGridColor: diagramForbidden ? 'transparent' : '#f1f1f1',
      largeGridColor: diagramForbidden ? 'transparent' : '#cccccc',
      setBackground: setBackgroundAfterTransformation,
    };
  }, [diagramForbidden, backgroundColor]);

  return memoizedStyle;
};
