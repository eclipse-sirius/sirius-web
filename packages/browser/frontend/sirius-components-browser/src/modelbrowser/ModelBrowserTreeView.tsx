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
  GQLTreeItem,
  TreeItemActionProps,
  TreeView,
  useExpandAllTreePath,
  useTreePath,
} from '@eclipse-sirius/sirius-components-trees';
import UnfoldMoreIcon from '@mui/icons-material/UnfoldMore';
import IconButton from '@mui/material/IconButton';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ModelBrowserTreeViewProps, ModelBrowserTreeViewState } from './ModelBrowserTreeView.types';
import { useModelBrowserSubscription } from './useModelBrowserSubscription';

const useTreeStyle = makeStyles()((theme) => ({
  title: {
    opacity: 0.6,
    fontSize: theme.typography.caption.fontSize,
  },
  borderStyle: {
    border: '1px solid',
    borderColor: theme.palette.grey[500],
    height: 300,
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

  const { getExpandAllTreePath, data: expandAllTreePathData } = useExpandAllTreePath();

  useEffect(() => {
    if (expandAllTreePathData && expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
      const { expanded, maxDepth } = state;
      const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } =
        expandAllTreePathData.viewer.editingContext.expandAllTreePath;
      const newExpanded: string[] = [...expanded];

      treeItemIdsToExpand?.forEach((itemToExpand) => {
        if (!expanded.includes(itemToExpand)) {
          newExpanded.push(itemToExpand);
        }
      });
      setState((prevState) => ({
        ...prevState,
        expanded: newExpanded,
        maxDepth: Math.max(expandedMaxDepth, maxDepth),
      }));
    }
  }, [expandAllTreePathData]);

  const treeId: string = `modelBrowser://${leafType}?ownerKind=${encodeURIComponent(
    ownerKind
  )}&targetType=${encodeURIComponent(referenceKind)}&ownerId=${ownerId}&descriptionId=${encodeURIComponent(
    descriptionId
  )}&isContainment=${isContainment}`;
  const { tree } = useModelBrowserSubscription(editingContextId, treeId, state.expanded, state.maxDepth);

  const onExpand = (id: string, depth: number) => {
    const { expanded, maxDepth } = state;

    if (expanded.includes(id)) {
      const newExpanded = [...expanded];
      newExpanded.splice(newExpanded.indexOf(id), 1);

      setState((prevState) => ({
        ...prevState,
        expanded: newExpanded,
        maxDepth: Math.max(maxDepth, depth),
      }));
    } else {
      setState((prevState) => ({ ...prevState, expanded: [...expanded, id], maxDepth: Math.max(maxDepth, depth) }));
    }
  };

  const onExpandAll = (treeItem: GQLTreeItem) => {
    if (tree) {
      const variables: GQLGetExpandAllTreePathVariables = {
        editingContextId,
        treeId: tree.id,
        treeItemId: treeItem.id,
      };
      getExpandAllTreePath({ variables });
    }
  };

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
      const { expanded, maxDepth } = state;
      const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } = treePathData.viewer.editingContext.treePath;
      const newExpanded: string[] = [...expanded];

      treeItemIdsToExpand?.forEach((itemToExpand) => {
        if (!expanded.includes(itemToExpand)) {
          newExpanded.push(itemToExpand);
        }
      });
      setState((prevState) => ({
        ...prevState,
        expanded: newExpanded,
        maxDepth: Math.max(expandedMaxDepth, maxDepth),
      }));
    }
  }, [treePathData]);

  return (
    <>
      <FilterBar
        onTextChange={(event) => setState((prevState) => ({ ...prevState, filterBarText: event.target.value }))}
        onTextClear={() => setState((prevState) => ({ ...prevState, filterBarText: '' }))}
        text={state.filterBarText}
      />
      <span className={classes.title}>{title}</span>
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
            onExpand={onExpand}
            onExpandAll={onExpandAll}
            onTreeItemClick={onTreeItemClick}
            selectedTreeItemIds={selectedTreeItemIds}
          />
        ) : null}
      </div>
    </>
  );
};

const ExpandAllTreeItemAction = ({ onExpandAll, item, isHovered }: TreeItemActionProps) => {
  if (!onExpandAll || !item || !item.hasChildren || !isHovered) {
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
