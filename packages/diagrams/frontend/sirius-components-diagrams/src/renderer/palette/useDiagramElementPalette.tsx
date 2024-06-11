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

import { useCallback, useContext, useEffect } from 'react';
import { Edge, Node, XYPosition, useKeyPress, useReactFlow, useStoreApi } from 'reactflow';
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
  const { getNodes, getEdges } = useReactFlow<NodeData, EdgeData>();

  const store = useStoreApi();

  const onDiagramElementClick = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>, elementClicked: Node | Edge) => {
      const { domNode } = store.getState();
      const element = domNode?.getBoundingClientRect();
      const palettePosition = computePalettePosition(event, element);
      const selectedElement = [
        ...getNodes().filter((node) => node.selected),
        ...getEdges().filter((edge) => edge.selected),
      ];
      if (
        !event.altKey &&
        selectedElement.length === 1 &&
        selectedElement[0] &&
        selectedElement[0].id === elementClicked.id
      ) {
        showDiagramElementPalette(palettePosition.x, palettePosition.y);
      } else {
        hideDiagramElementPalette();
      }
    },
    [showDiagramElementPalette, hideDiagramElementPalette]
  );

  const escapePressed = useKeyPress('Escape');
  useEffect(() => {
    if (escapePressed) {
      hideDiagramElementPalette();
    }
  }, [escapePressed, hideDiagramElementPalette]);

  return {
    x,
    y,
    isOpened,
    hideDiagramElementPalette,
    showDiagramElementPalette,
    onDiagramElementClick,
  };
};
