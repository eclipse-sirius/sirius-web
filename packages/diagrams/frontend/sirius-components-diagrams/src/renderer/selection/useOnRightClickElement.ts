/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useGroupPalette } from '../palette/group-tool/useGroupPalette';
import { useDiagramElementPalette } from '../palette/useDiagramElementPalette';
import { useDiagramPalette } from '../palette/useDiagramPalette';
import { UseOnRightClickElementValue } from './useOnRightClickElement.types';

export const useOnRightClickElement = (selectedElementsIds: string[]): UseOnRightClickElementValue => {
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { onDiagramBackgroundContextMenu } = useDiagramPalette();
  const { onDiagramElementContextMenu: elementPaletteOnDiagramElementContextMenu } = useDiagramElementPalette();
  const {
    hideGroupPalette,
    position: groupPalettePosition,
    isOpened: isGroupPaletteOpened,
    refElementId: groupPaletteRefElementId,
    onDiagramElementContextMenu: groupPaletteOnDiagramElementContextMenu,
  } = useGroupPalette();

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
        groupPaletteOnDiagramElementContextMenu(event, element);
      } else {
        elementPaletteOnDiagramElementContextMenu(event, element);
      }
    },
    [selectedElementsIds, elementPaletteOnDiagramElementContextMenu, groupPaletteOnDiagramElementContextMenu]
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
        groupPaletteOnDiagramElementContextMenu(event, element);
      } else {
        elementPaletteOnDiagramElementContextMenu(event, element);
      }
    },
    [selectedElementsIds, elementPaletteOnDiagramElementContextMenu, groupPaletteOnDiagramElementContextMenu]
  );

  const onPaneContextMenu = useCallback((event: MouseEvent | React.MouseEvent<Element, MouseEvent>) => {
    if (!event.shiftKey) {
      store.getState().resetSelectedElements();
      onDiagramBackgroundContextMenu(event);
    }
  }, []);

  const onSelectionContextMenu = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>) => {
      const lastElementSelectedId = selectedElementsIds.at(selectedElementsIds.length - 1) || '';
      const selectedElement =
        store.getState().edgeLookup.get(lastElementSelectedId) ||
        store.getState().nodeLookup.get(lastElementSelectedId);
      if (selectedElement) {
        if (selectedElementsIds.length > 1) {
          groupPaletteOnDiagramElementContextMenu(event, selectedElement);
        } else {
          elementPaletteOnDiagramElementContextMenu(event, selectedElement);
        }
      }
    },
    [groupPaletteOnDiagramElementContextMenu, elementPaletteOnDiagramElementContextMenu, selectedElementsIds]
  );

  return {
    onNodeContextMenu,
    onEdgeContextMenu,
    onPaneContextMenu,
    onSelectionContextMenu,
    groupPalettePosition: groupPalettePosition,
    groupPaletteRefElementId: groupPaletteRefElementId,
    isGroupPaletteOpened: isGroupPaletteOpened,
    hideGroupPalette: hideGroupPalette,
  };
};
