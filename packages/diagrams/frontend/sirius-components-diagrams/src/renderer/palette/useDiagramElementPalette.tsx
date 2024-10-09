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

import { useSelection } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, XYPosition, useStoreApi } from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { DiagramElementPaletteContext } from './DiagramElementPaletteContext';
import { DiagramElementPaletteContextValue } from './DiagramElementPaletteContext.types';
import { UseDiagramElementPaletteValue } from './useDiagramElementPalette.types';

const computePalettePosition = (event: MouseEvent | React.MouseEvent, bounds: DOMRect | undefined): XYPosition => {
  return {
    x: event.clientX - (bounds?.left ?? 0),
    y: event.clientY - (bounds?.top ?? 0),
  };
};

export const useDiagramElementPalette = (): UseDiagramElementPaletteValue => {
  const { x, y, isOpened, hideDiagramElementPalette, showDiagramElementPalette } =
    useContext<DiagramElementPaletteContextValue>(DiagramElementPaletteContext);
  const store = useStoreApi();

  const { selection } = useSelection();

  const onDiagramElementClick = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>, elementClicked: Node<NodeData> | Edge<EdgeData>) => {
      const { domNode } = store.getState();
      const element = domNode?.getBoundingClientRect();
      const palettePosition = computePalettePosition(event, element);
      if (
        !event.altKey &&
        !event.ctrlKey &&
        !(
          !!selection.entries.find((selection) => selection.id === elementClicked.data?.targetObjectId) &&
          selection.entries.length > 1
        )
      ) {
        showDiagramElementPalette(palettePosition.x, palettePosition.y);
      } else {
        hideDiagramElementPalette();
      }
    },
    [showDiagramElementPalette, hideDiagramElementPalette, selection]
  );

  return {
    x,
    y,
    isOpened,
    hideDiagramElementPalette,
    showDiagramElementPalette,
    onDiagramElementClick,
  };
};
