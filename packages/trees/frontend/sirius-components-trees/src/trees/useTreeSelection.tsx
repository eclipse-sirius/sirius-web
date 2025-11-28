import { useCallback, useState } from 'react';
import { GQLTree, GQLTreeItem } from '../views/TreeView.types';
import { TreeItemClickResult } from './useTreeSelection.types';

export const useTreeSelection = () => {
  const [pivotId, setPivotId] = useState<string | null>(null);

  const computeSelectedElementsInRange = useCallback(
    (
      subtree: GQLTree | GQLTreeItem,
      firstSelectionItemId: string,
      secondSelectionItemId: string,
      isSelecting: boolean = false,
      accumulator: string[] = []
    ): string[] => {
      for (const element of subtree.children) {
        const isRangeEndpoint = element.id === firstSelectionItemId || element.id === secondSelectionItemId;

        // Toggle selection state when we hit a range endpoint
        if (isRangeEndpoint) {
          isSelecting = !isSelecting;
        }

        // Add current element if we're in selection mode
        if ((isSelecting || isRangeEndpoint) && element.selectable) {
          accumulator.push(element.id);
        }

        // Early return if we've completed the range
        if (isRangeEndpoint && !isSelecting) {
          return accumulator;
        }

        // Recurse into expanded children (DFS)
        if (element.hasChildren && element.expanded) {
          computeSelectedElementsInRange(
            element,
            firstSelectionItemId,
            secondSelectionItemId,
            isSelecting,
            accumulator
          );
          // Early return if range was completed in children
          if (
            accumulator.length > 0 &&
            (accumulator[accumulator.length - 1] === firstSelectionItemId ||
              accumulator[accumulator.length - 1] === secondSelectionItemId)
          ) {
            return accumulator;
          }
        }
      }

      return accumulator;
    },
    []
  );

  const treeItemClick = useCallback(
    (
      event: React.MouseEvent<HTMLDivElement, MouseEvent>,
      item: GQLTreeItem,
      tree: GQLTree,
      selectedElements: string[],
      allowMultiSelection: boolean = true
    ): TreeItemClickResult => {
      let newSelectedElements: string[] = [];
      let singleTreeItemSelected: GQLTreeItem | null = null;
      if (allowMultiSelection && event.shiftKey && pivotId !== null) {
        // Shift or Shift+Ctrl: Range selection
        event.stopPropagation();
        if (pivotId && tree) {
          const rangeSelection = computeSelectedElementsInRange(tree, pivotId, item.id);

          // Shift+Ctrl: Add to existing selection | Shift: Replace selection
          const shouldMerge = event.ctrlKey || event.metaKey;
          newSelectedElements = shouldMerge
            ? [...selectedElements, ...rangeSelection.filter((id) => !selectedElements.includes(id))]
            : rangeSelection;
        }
      } else if (allowMultiSelection && (event.ctrlKey || event.metaKey)) {
        // Ctrl: Toggle single item selection
        event.stopPropagation();
        // Update the global selection
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
    },
    [pivotId, computeSelectedElementsInRange]
  );

  return { treeItemClick };
};
