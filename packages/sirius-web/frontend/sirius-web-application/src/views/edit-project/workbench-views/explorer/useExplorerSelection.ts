/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { Selection, SelectionEntry, useSelection } from '@eclipse-sirius/sirius-components-core';
import {
  GQLGetTreePathVariables,
  GQLTree,
  GQLTreeItem,
  useTreePath,
  useTreeSelection,
} from '@eclipse-sirius/sirius-components-trees';
import { useCallback, useEffect, useState } from 'react';
import { UseExplorerSelectionState, UseExplorerSelectionValue } from './useExplorerSelection.types';

export const useExplorerSelection = (
  editingContextId: string,
  treeId: string | null,
  expanded: string[],
  onExpandedElementChange: (newExpandedIds: string[], newMaxDepth: number) => void
): UseExplorerSelectionValue => {
  const [state, setState] = useState<UseExplorerSelectionState>({
    selectedTreeItemIds: [],
    singleTreeItemSelected: null,
  });

  const { selection, setSelection } = useSelection();
  const { getTreePath, data: treePathData } = useTreePath();
  const { treeItemClick } = useTreeSelection();

  const onTreeItemClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tree: GQLTree, item: GQLTreeItem) => {
    var localSelection = treeItemClick(event, tree, item, state.selectedTreeItemIds, true);
    setState((prevState) => ({
      ...prevState,
      selectedTreeItemIds: localSelection.selectedTreeItemIds,
      singleTreeItemSelected: localSelection.singleTreeItemSelected,
    }));

    var globalSelection = treeItemClick(
      event,
      tree,
      item,
      selection.entries.map((entry) => entry.id),
      true
    );
    setSelection({ entries: globalSelection.selectedTreeItemIds.map<SelectionEntry>((id) => ({ id })) });
  };

  const selectionKey: string = selection?.entries
    .map((entry) => entry.id)
    .sort()
    .join(':');

  const onRevealSelection = useCallback(() => {
    if (treeId && selection.entries.length > 0) {
      const variables: GQLGetTreePathVariables = {
        editingContextId,
        treeId: treeId,
        selectionEntryIds: selection.entries.map((entry) => entry.id),
      };
      getTreePath({ variables });
    }
  }, [editingContextId, selectionKey, treeId, getTreePath]);

  useEffect(() => {
    if (treePathData && treePathData.viewer?.editingContext?.treePath) {
      const expandedIds = treePathData.viewer.editingContext.treePath.treeItemIdsToExpand.filter(
        (id) => !expanded.includes(id)
      );
      const newExpandedIds = [...expanded, ...expandedIds];
      onExpandedElementChange(newExpandedIds, treePathData.viewer.editingContext.treePath.maxDepth);
      setState((prevState) => {
        return {
          ...prevState,
          selectedTreeItemIds: selection.entries.map((entry) => entry.id),
        };
      });
    }
  }, [treePathData]);

  const applySelection = (selection: Selection) => {
    const newSelectedTreeItemIds = selection.entries.map((entry) => entry.id);
    setState((prevState) => ({
      ...prevState,
      selectedTreeItemIds: newSelectedTreeItemIds,
    }));

    if (treeId && newSelectedTreeItemIds.length > 0) {
      const variables: GQLGetTreePathVariables = {
        editingContextId,
        treeId: treeId,
        selectionEntryIds: newSelectedTreeItemIds,
      };
      getTreePath({ variables });
    }
  };

  const setSelectedTreeItemIds = (selectedTreeItemIds: string[]) => {
    setState((prevState) => {
      return { ...prevState, selectedTreeItemIds };
    });
  };

  return {
    selectedTreeItemIds: state.selectedTreeItemIds,
    singleTreeItemSelected: state.singleTreeItemSelected,
    setSelectedTreeItemIds,
    applySelection,
    onRevealSelection,
    onTreeItemClick,
  };
};
