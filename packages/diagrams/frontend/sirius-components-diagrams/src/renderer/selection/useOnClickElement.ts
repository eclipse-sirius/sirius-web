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
import { useSelection } from '@eclipse-sirius/sirius-components-core';
import { usePalette } from '@eclipse-sirius/sirius-components-palette';
import { Edge, Node, useStoreApi, XYPosition } from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseOnClickElementValue } from './useOnClickElement.types';

const computePalettePosition = (event: MouseEvent | React.MouseEvent, bounds: DOMRect | undefined): XYPosition => {
  return {
    x: event.clientX - (bounds?.left ?? 0),
    y: event.clientY - (bounds?.top ?? 0),
  };
};

export const useOnClickElement = (selectedElementsIds: string[]): UseOnClickElementValue => {
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { setSelection } = useSelection();
  const { showPalette } = usePalette();

  const openPalette = useCallback((event: React.MouseEvent<Element, MouseEvent> | MouseEvent, elements: string[]) => {
    const { domNode } = store.getState();
    const domElement = domNode?.getBoundingClientRect();

    const palettePosition = computePalettePosition(event, domElement);
    if (!event.altKey && !event.ctrlKey) {
      event.preventDefault();
      showPalette(palettePosition.x, palettePosition.y, elements);
    }
  }, []);

  const onNodeContextMenu = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>, element: Node<NodeData>) => {
      if (event.shiftKey) {
        return;
      }
      const isClickedElementAlreadySelected = selectedElementsIds.find((id) => id === element.id);
      if (!isClickedElementAlreadySelected) {
        store.getState().addSelectedNodes([element.id]);
      }

      const shouldOpenGroupPalette = selectedElementsIds.length > 1 && isClickedElementAlreadySelected;
      if (shouldOpenGroupPalette) {
        openPalette(event, selectedElementsIds);
      } else {
        openPalette(event, [element.id]);
      }
    },
    [selectedElementsIds]
  );

  const onEdgeContextMenu = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>, element: Edge<EdgeData>) => {
      if (event.shiftKey) {
        return;
      }
      const isClickedElementAlreadySelected = selectedElementsIds.find((id) => id === element.id);
      if (!isClickedElementAlreadySelected) {
        store.getState().addSelectedEdges([element.id]);
      }

      const shouldOpenGroupPalette = selectedElementsIds.length > 1 && isClickedElementAlreadySelected;
      if (shouldOpenGroupPalette) {
        openPalette(event, selectedElementsIds);
      } else {
        openPalette(event, [element.id]);
      }
    },
    [selectedElementsIds]
  );

  const onPaneClick = useCallback(() => {
    // Select the diagram itself when the user left-clicks on the background
    store.getState().resetSelectedElements();
    setSelection({ entries: [{ id: diagramId }] });
  }, [diagramId]);

  const onPaneContextMenu = useCallback(
    (event: MouseEvent | React.MouseEvent<Element, MouseEvent>) => {
      if (!event.shiftKey) {
        store.getState().resetSelectedElements();
        setSelection({ entries: [{ id: diagramId }] });
        openPalette(event, []);
      }
    },
    [diagramId]
  );

  const onSelectionContextMenu = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>) => {
      const lastElementSelectedId = selectedElementsIds.at(selectedElementsIds.length - 1) || '';
      const selectedElement =
        store.getState().edgeLookup.get(lastElementSelectedId) ||
        store.getState().nodeLookup.get(lastElementSelectedId);
      if (selectedElement) {
        if (selectedElementsIds.length > 1) {
          openPalette(event, selectedElementsIds);
        } else {
          openPalette(event, [selectedElement[0].id]);
        }
      }
    },
    [selectedElementsIds]
  );

  return {
    onNodeContextMenu,
    onEdgeContextMenu,
    onPaneContextMenu,
    onSelectionContextMenu,
    onPaneClick,
  };
};
