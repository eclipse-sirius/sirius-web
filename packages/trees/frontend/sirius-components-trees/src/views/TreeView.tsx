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
import { useEffect, useState } from 'react';
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
  TreeViewState,
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

const findTreeItemById = (items: GQLTreeItem[], id: string) => {
  for (const child of items) {
    if (child.id === id) {
      return child;
    } else if (child.hasChildren) {
      const descendant = findTreeItemById(child.children, id);
      if (descendant) {
        return descendant;
      }
    }
  }
  return null;
};

const findDescendants = (tree: GQLTree, rootId: string) => {
  const result = [];
  const addWithDescendants = (item: GQLTreeItem) => {
    result.push(item.id);
    if (item.hasChildren) {
      item.children.forEach((child) => addWithDescendants(child));
    }
  };
  if (tree.children) {
    const root = findTreeItemById(tree.children, rootId);
    if (root && root.hasChildren) {
      root.children.forEach((child) => addWithDescendants(child));
    }
  }
  return result;
};

export const TreeView = ({
  editingContextId,
  readOnly,
  tree,
  treeId,
  enableMultiSelection,
  synchronizedWithSelection,
  textToHighlight,
  textToFilter,
  markedItemIds = [],
  treeItemActionRender,
  onExpandedElementChange,
  expanded,
  maxDepth,
}: TreeViewProps) => {
  const [state, setState] = useState<TreeViewState>({
    expanded: expanded,
    maxDepth: maxDepth,
  });
  const { selection, setSelection } = useSelection();

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
        const { expanded, maxDepth } = state;
        if (treePathData.viewer?.editingContext?.treePath) {
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
      }
    }
  }, [treePathLoading, treePathData]);

  useEffect(() => {
    if (!expandAllTreePathLoading) {
      if (expandAllTreePathData) {
        const { expanded, maxDepth } = state;
        if (expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
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

  const onExpand = (id: string, depth: number) => {
    const { expanded, maxDepth } = state;

    if (expanded.includes(id)) {
      // Actually a collapse

      const newExpanded = [...expanded];
      newExpanded.splice(newExpanded.indexOf(id), 1);

      // Remove all descendants of collapsed element from the selection
      const descendants = findDescendants(tree, id);
      setSelection({
        entries: selection.entries.filter((entry) => !descendants.includes(entry.id)),
      });

      setState((prevState) => ({
        ...prevState,
        expanded: newExpanded,
        maxDepth: Math.max(maxDepth, depth),
      }));
    } else {
      setState((prevState) => ({ ...prevState, expanded: [...expanded, id], maxDepth: Math.max(maxDepth, depth) }));
    }
  };

  useEffect(() => {
    onExpandedElementChange(state.expanded, state.maxDepth);
  }, [state.expanded, state.maxDepth]);

  const onExpandAll = (treeItem: GQLTreeItem) => {
    const variables: GQLGetExpandAllTreePathVariables = {
      editingContextId,
      treeId: tree.id,
      treeItemId: treeItem.id,
    };
    getExpandAllTreePath({ variables });
  };

  const { data: treeConverters }: DataExtension<TreeConverter[]> = useData(treeViewTreeConverterExtensionPoint);

  let convertedTree: GQLTree = tree;
  treeConverters.forEach((treeConverter) => {
    convertedTree = treeConverter.convert(editingContextId, convertedTree);
  });

  return (
    <div data-testid={treeId}>
      <Tree
        editingContextId={editingContextId}
        tree={convertedTree}
        onExpand={onExpand}
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
