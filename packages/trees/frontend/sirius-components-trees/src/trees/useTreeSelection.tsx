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

import { useState } from 'react';
import { GQLTree, GQLTreeItem } from '../views/TreeView.types';
import {
  ComputeSelectedElementsInRangeResult,
  TreeItemClickResult,
  UseTreeSelectionValue,
} from './useTreeSelection.types';

/**
 * Hook to handle the different scenarios of tree item selection: simple click, multi-selection (CTRL), range selection (SHIFT).
 * @returns An object containing the function to handle tree item click events.
 */
export const useTreeSelection = (): UseTreeSelectionValue => {
  const [pivotId, setPivotId] = useState<string | null>(null);

  const computeSelectedElementsInRange = (
    subtree: GQLTree | GQLTreeItem,
    firstSelectionItemId: string,
    secondSelectionItemId: string,
    isSelecting: boolean
  ): ComputeSelectedElementsInRangeResult => {
    const selectedElements: string[] = [];
    for (const element of subtree.children) {
      const isEndpoint = element.id === firstSelectionItemId || element.id === secondSelectionItemId;

      // Toggle selection state when we hit a range endpoint
      if (isEndpoint) {
        isSelecting = !isSelecting;
      }

      // Add current element if we're in selection mode
      if ((isSelecting || isEndpoint) && element.selectable) {
        selectedElements.push(element.id);
      }

      // Early return if we've completed the range
      if (isEndpoint && !isSelecting) {
        return { selectedElements, isSelecting };
      }

      // Recurse into expanded children (DFS)
      if (element.hasChildren && element.expanded) {
        const { selectedElements: recursiveResult, isSelecting: recursiveIsSelecting } = computeSelectedElementsInRange(
          element,
          firstSelectionItemId,
          secondSelectionItemId,
          isSelecting
        );
        selectedElements.push(...recursiveResult);

        // If we are not selecting but the selectedElement array is not empty, that means we finished selecting the range and we can stop the recursion
        if (!recursiveIsSelecting && selectedElements.length > 0) {
          return { selectedElements, isSelecting: recursiveIsSelecting };
        }
        isSelecting = recursiveIsSelecting;
      }
    }

    return { selectedElements, isSelecting };
  };

  const treeItemClick = (
    event: React.MouseEvent<HTMLDivElement, MouseEvent>,
    tree: GQLTree,
    item: GQLTreeItem,
    selectedElements: string[],
    allowMultiSelection: boolean = true
  ): TreeItemClickResult => {
    let newSelectedElements: string[] = [];
    let singleTreeItemSelected: GQLTreeItem | null = null;
    if (allowMultiSelection && event.shiftKey) {
      // Shift or Shift+Ctrl: Range selection
      event.stopPropagation();
      // If the pivot point has not been explicitly defined, set it to the first element of the tree
      let currentPivotId = pivotId;
      if (!currentPivotId) {
        currentPivotId = tree.children[0]?.id ?? null;
        setPivotId(currentPivotId);
      }

      if (currentPivotId) {
        const rangeSelection = computeSelectedElementsInRange(tree, currentPivotId, item.id, false).selectedElements;

        // Shift+Ctrl: Add to existing selection | Shift: Replace selection
        const shouldMerge = event.ctrlKey || event.metaKey;
        newSelectedElements = shouldMerge
          ? [...selectedElements, ...rangeSelection.filter((id) => !selectedElements.includes(id))]
          : rangeSelection;
      }
    } else if (allowMultiSelection && (event.ctrlKey || event.metaKey)) {
      // Ctrl: Toggle single item selection
      event.stopPropagation();
      // Update the selection
      const isItemInSelection = selectedElements.includes(item.id);
      if (isItemInSelection) {
        newSelectedElements = selectedElements.filter((id) => id !== item.id);
      } else {
        newSelectedElements = [...selectedElements, item.id];
        // Ctrl click defines a new pivot point
        setPivotId(item.id);
      }
    } else {
      // Normal click: Select single item
      newSelectedElements = [item.id];
      singleTreeItemSelected = item;
      // Simple click defines a new pivot point
      setPivotId(item.id);
    }
    return { selectedTreeItemIds: newSelectedElements, singleTreeItemSelected };
  };

  return { treeItemClick };
};
