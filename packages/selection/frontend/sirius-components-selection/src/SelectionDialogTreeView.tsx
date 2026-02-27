/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { FilterBar } from '@eclipse-sirius/sirius-components-core';
import { DiagramDialogVariable } from '@eclipse-sirius/sirius-components-diagrams';
import {
  GQLGetExpandAllTreePathVariables,
  GQLTreeItem,
  TreeItemActionProps,
  TreeView,
  useExpandAllTreePath,
} from '@eclipse-sirius/sirius-components-trees';
import UnfoldMoreIcon from '@mui/icons-material/UnfoldMore';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import { useEffect, useState } from 'react';
import { SelectionDialogTreeViewProps, SelectionDialogTreeViewState } from './SelectionDialogTreeView.types';
import { useSelectionDialogTreeSubscription } from './useSelectionDialogTreeSubscription';

export const SELECTION_DIALOG_TYPE: string = 'selectionDialogDescription';

const initialState: SelectionDialogTreeViewState = {
  filterBarText: '',
  expanded: [],
  maxDepth: 1,
};

export const SelectionDialogTreeView = ({
  editingContextId,
  treeDescriptionId,
  variables,
  onTreeItemClick,
  selectedTreeItemIds,
}: SelectionDialogTreeViewProps) => {
  const [state, setState] = useState<SelectionDialogTreeViewState>(initialState);

  const treeId = `selection://?treeDescriptionId=${encodeURIComponent(treeDescriptionId)}${encodeVariables(variables)}`;
  const { tree } = useSelectionDialogTreeSubscription(editingContextId, treeId, state.expanded, state.maxDepth);

  const onExpandedElementChange = (newExpandedIds: string[], newMaxDepth: number) => {
    setState((prevState) => ({
      ...prevState,
      expanded: newExpandedIds,
      maxDepth: Math.max(newMaxDepth, prevState.maxDepth),
    }));
  };

  return (
    <Box
      sx={(theme) => ({
        display: 'flex',
        flexDirection: 'column',
        border: '1px solid',
        borderRadius: theme.spacing(0.5),
        borderColor: selectedTreeItemIds.length !== 0 ? theme.palette.primary.main : theme.palette.divider,
        height: '100%',
        overflowY: 'auto',
      })}>
      <Box sx={(theme) => ({ padding: theme.spacing(1) })}>
        <FilterBar
          onTextChange={(event) => setState((prevState) => ({ ...prevState, filterBarText: event.target.value }))}
          onTextClear={() => setState((prevState) => ({ ...prevState, filterBarText: '' }))}
          text={state.filterBarText}
        />
      </Box>
      <Divider />
      {tree ? (
        <TreeView
          editingContextId={editingContextId}
          readOnly={true}
          tree={tree}
          textToFilter={state.filterBarText}
          textToHighlight={state.filterBarText}
          treeItemActionRender={(props) => <SelectionDialogTreeItemAction {...props} />}
          onExpandedElementChange={onExpandedElementChange}
          expanded={state.expanded}
          maxDepth={state.maxDepth}
          onTreeItemClick={onTreeItemClick}
          selectTreeItems={() => {}}
          selectedTreeItemIds={selectedTreeItemIds}
          data-testid={treeId}
        />
      ) : null}
    </Box>
  );
};

const SelectionDialogTreeItemAction = ({
  editingContextId,
  treeId,
  item,
  isHovered,
  expanded,
  onExpandedElementChange,
}: TreeItemActionProps) => {
  const { getExpandAllTreePath, data: expandAllTreePathData } = useExpandAllTreePath();

  useEffect(() => {
    if (expandAllTreePathData && expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
      const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } =
        expandAllTreePathData.viewer.editingContext.expandAllTreePath;
      const newExpanded: string[] = [...expanded];

      treeItemIdsToExpand?.forEach((itemToExpand) => {
        if (!expanded.includes(itemToExpand)) {
          newExpanded.push(itemToExpand);
        }
      });
      onExpandedElementChange(newExpanded, expandedMaxDepth);
    }
  }, [expandAllTreePathData]);

  const onExpandAll = (treeItem: GQLTreeItem) => {
    const variables: GQLGetExpandAllTreePathVariables = {
      editingContextId,
      treeId,
      treeItemId: treeItem.id,
    };
    getExpandAllTreePath({ variables });
  };

  if (!onExpandedElementChange || !item || !item.hasChildren || !isHovered) {
    return null;
  }
  return (
    <IconButton
      size="small"
      data-testid="expand-all"
      title="expand all"
      onClick={() => {
        onExpandAll(item);
      }}>
      <UnfoldMoreIcon style={{ fontSize: 12 }} />
    </IconButton>
  );
};

const encodeVariables = (variables: DiagramDialogVariable[]): string => {
  let encodedVariables = '';
  if (variables.length > 0) {
    encodedVariables =
      '&' + variables.map((variable) => variable.name + '=' + encodeURIComponent(variable.value)).join('&');
  }
  return encodedVariables;
};
