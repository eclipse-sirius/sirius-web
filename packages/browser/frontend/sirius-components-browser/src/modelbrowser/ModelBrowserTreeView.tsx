/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import {
  GQLGetExpandAllTreePathVariables,
  GQLGetTreePathVariables,
  useTreePath,
  GQLTreeItem,
  TreeItemActionProps,
  TreeView,
  useExpandAllTreePath,
} from '@eclipse-sirius/sirius-components-trees';
import UnfoldMoreIcon from '@mui/icons-material/UnfoldMore';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ModelBrowserTreeViewProps, ModelBrowserTreeViewState } from './ModelBrowserTreeView.types';
import { useModelBrowserSubscription } from './useModelBrowserSubscription';

const useTreeStyle = makeStyles()((theme) => ({
  modelBrowserTreeView: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(1),
  },
  borderStyle: {
    border: '1px solid',
    borderColor: theme.palette.grey[500],
    height: 500,
    overflow: 'auto',
  },
}));

export const ModelBrowserTreeView = ({
  editingContextId,
  referenceKind,
  ownerId,
  descriptionId,
  isContainment,
  markedItemIds,
  title,
  leafType,
  ownerKind,
  onTreeItemClick,
  selectedTreeItemIds,
}: ModelBrowserTreeViewProps) => {
  const { classes } = useTreeStyle();

  const [state, setState] = useState<ModelBrowserTreeViewState>({
    filterBarText: '',
    expanded: [],
    maxDepth: 1,
  });

  const treeId: string = `modelBrowser://${leafType}?ownerKind=${encodeURIComponent(
    ownerKind
  )}&targetType=${encodeURIComponent(referenceKind)}&ownerId=${ownerId}&descriptionId=${encodeURIComponent(
    descriptionId
  )}&isContainment=${isContainment}`;
  const { tree } = useModelBrowserSubscription(editingContextId, treeId, state.expanded, state.maxDepth);

  const { getTreePath, data: treePathData } = useTreePath();
  useEffect(() => {
    if (tree?.id) {
      const variables: GQLGetTreePathVariables = {
        editingContextId,
        treeId: tree.id,
        selectionEntryIds: selectedTreeItemIds,
      };
      getTreePath({ variables });
    }
  }, [editingContextId, tree?.id, selectedTreeItemIds, getTreePath]);

  useEffect(() => {
    if (treePathData && treePathData.viewer?.editingContext?.treePath) {
      setState((prevState) => {
        const { expanded, maxDepth } = prevState;
        const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } = treePathData.viewer.editingContext.treePath;
        const newExpanded: string[] = [...expanded];

        treeItemIdsToExpand?.forEach((itemToExpand) => {
          if (!expanded.includes(itemToExpand)) {
            newExpanded.push(itemToExpand);
          }
        });
        return {
          ...prevState,
          expanded: newExpanded,
          maxDepth: Math.max(expandedMaxDepth, maxDepth),
        };
      });
    }
  }, [treePathData]);

  const onExpandedElementChange = (newExpandedIds: string[], newMaxDepth: number) => {
    setState((prevState) => ({
      ...prevState,
      expanded: newExpandedIds,
      maxDepth: Math.max(newMaxDepth, prevState.maxDepth),
    }));
  };

  return (
    <div className={classes.modelBrowserTreeView}>
      <FilterBar
        onTextChange={(event) => setState((prevState) => ({ ...prevState, filterBarText: event.target.value }))}
        onTextClear={() => setState((prevState) => ({ ...prevState, filterBarText: '' }))}
        text={state.filterBarText}
      />
      <div>
        <Typography variant="subtitle1" gutterBottom>
          {title}
        </Typography>
        <div className={classes.borderStyle}>
          {tree !== null ? (
            <TreeView
              editingContextId={editingContextId}
              readOnly={true}
              treeId={treeId}
              tree={tree}
              textToFilter={state.filterBarText}
              textToHighlight={state.filterBarText}
              markedItemIds={markedItemIds}
              treeItemActionRender={(props) => <ExpandAllTreeItemAction {...props} />}
              onExpandedElementChange={onExpandedElementChange}
              expanded={state.expanded}
              maxDepth={state.maxDepth}
              onTreeItemClick={onTreeItemClick}
              selectedTreeItemIds={selectedTreeItemIds}
            />
          ) : null}
        </div>
      </div>
    </div>
  );
};

const ExpandAllTreeItemAction = ({
  editingContextId,
  treeId,
  item,
  expanded,
  isHovered,
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
