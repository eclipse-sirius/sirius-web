/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import { gql, useLazyQuery } from '@apollo/client';
import { DataExtension, useData, useMultiToast, useSelection } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useEffect, useMemo } from 'react';
import { useTreeStore } from '../store/treeStore';
import { Tree } from '../trees/Tree';
import {
  GQLGetExpandAllTreePathData,
  GQLGetExpandAllTreePathVariables,
  GQLGetTreePathData,
  GQLGetTreePathVariables,
  GQLTree,
  GQLTreeItem,
  TreeConverter,
  TreeViewProps,
} from './TreeView.types';
import { treeViewTreeConverterExtensionPoint } from './TreeViewExtensionPoints';

const getTreePathQuery = gql`
  query getTreePath($editingContextId: ID!, $treeId: ID!, $selectionEntryIds: [ID!]!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        treePath(treeId: $treeId, selectionEntryIds: $selectionEntryIds) {
          treeItemIdsToExpand
          maxDepth
        }
      }
    }
  }
`;

const getExpandAllTreePathQuery = gql`
  query getExpandAllTreePath($editingContextId: ID!, $treeId: ID!, $treeItemId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        expandAllTreePath(treeId: $treeId, treeItemId: $treeItemId) {
          treeItemIdsToExpand
          maxDepth
        }
      }
    }
  }
`;

export const TreeView = ({
  editingContextId,
  readOnly,
  tree,
  treeId,
  enableMultiSelection,
  synchronizedWithSelection,
  textToHighlight,
  textToFilter,
  markedItemIds,
  treeItemActionRender,
  onExpandedElementChange,
  expanded: expandedProp,
  maxDepth: maxDepthProp,
}: TreeViewProps) => {
  const updateExpended = useTreeStore((state) => state.updateExpended);
  const updateMaxDepth = useTreeStore((state) => state.updateMaxDepth);

  const expanded = useTreeStore((state) => state.expanded);
  const maxDepth = useTreeStore((state) => state.maxDepth);

  useEffect(() => {
    updateExpended(expandedProp);
    updateMaxDepth(maxDepthProp);
  }, [expandedProp, maxDepthProp]);

  useEffect(() => {
    onExpandedElementChange(expanded, maxDepth);
  }, [expanded, maxDepth]);
  const { selection } = useSelection();

  const [getTreePath, { loading: treePathLoading, data: treePathData, error: treePathError }] = useLazyQuery<
    GQLGetTreePathData,
    GQLGetTreePathVariables
  >(getTreePathQuery);

  const [
    getExpandAllTreePath,
    { loading: expandAllTreePathLoading, data: expandAllTreePathData, error: expandAllTreePathError },
  ] = useLazyQuery<GQLGetExpandAllTreePathData, GQLGetExpandAllTreePathVariables>(getExpandAllTreePathQuery);

  // If we should auto-expand to reveal the selection, we need to compute the tree path to expand
  const selectionKey: string = selection?.entries
    .map((entry) => entry.id)
    .sort()
    .join(':');
  useEffect(() => {
    if (synchronizedWithSelection) {
      const variables: GQLGetTreePathVariables = {
        editingContextId,
        treeId: tree.id,
        selectionEntryIds: selection.entries.map((entry) => entry.id),
      };
      getTreePath({ variables });
    }
  }, [editingContextId, tree, selectionKey, synchronizedWithSelection, getTreePath]);

  useEffect(() => {
    if (!treePathLoading) {
      if (treePathData) {
        if (treePathData.viewer?.editingContext?.treePath) {
          const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } = treePathData.viewer.editingContext.treePath;
          const newExpanded: string[] = [...expanded];

          treeItemIdsToExpand?.forEach((itemToExpand) => {
            if (!expanded.includes(itemToExpand)) {
              newExpanded.push(itemToExpand);
            }
          });
          updateExpended(newExpanded);
          updateMaxDepth(Math.max(expandedMaxDepth, maxDepth));
        }
      }
    }
  }, [treePathLoading, treePathData]);

  useEffect(() => {
    if (!expandAllTreePathLoading) {
      if (expandAllTreePathData) {
        if (expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
          const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } =
            expandAllTreePathData.viewer.editingContext.expandAllTreePath;
          const newExpanded: string[] = [...expanded];

          treeItemIdsToExpand?.forEach((itemToExpand) => {
            if (!expanded.includes(itemToExpand)) {
              newExpanded.push(itemToExpand);
            }
          });
          updateExpended(newExpanded);
          updateMaxDepth(Math.max(expandedMaxDepth, maxDepth));
        }
      }
    }
  }, [expandAllTreePathLoading, expandAllTreePathData]);

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (expandAllTreePathError) {
      addErrorMessage(expandAllTreePathError.message);
    }
  }, [expandAllTreePathError]);
  useEffect(() => {
    if (treePathError) {
      addErrorMessage(treePathError.message);
    }
  }, [treePathError]);

  const onExpandAll = useCallback((treeItem: GQLTreeItem) => {
    const variables: GQLGetExpandAllTreePathVariables = {
      editingContextId,
      treeId: tree.id,
      treeItemId: treeItem.id,
    };
    getExpandAllTreePath({ variables });
  }, []);

  const { data: treeConverters }: DataExtension<TreeConverter[]> = useData(treeViewTreeConverterExtensionPoint);

  let renderedTree: GQLTree = useMemo(() => {
    let convertedTree: GQLTree = tree;
    treeConverters.forEach((treeConverter) => {
      convertedTree = treeConverter.convert(editingContextId, convertedTree);
    });
    return convertedTree;
  }, [tree]);

  return (
    <div data-testid={treeId}>
      <Tree
        editingContextId={editingContextId}
        tree={renderedTree}
        onExpandAll={onExpandAll}
        readOnly={readOnly}
        enableMultiSelection={enableMultiSelection}
        markedItemIds={markedItemIds}
        textToFilter={textToFilter}
        textToHighlight={textToHighlight}
        treeItemActionRender={treeItemActionRender}
      />
    </div>
  );
};
