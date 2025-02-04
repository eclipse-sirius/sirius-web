/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { useEffect } from 'react';
import { Tree } from '../trees/Tree';
import { GQLGetTreePathData, GQLGetTreePathVariables, GQLTree, TreeConverter, TreeViewProps } from './TreeView.types';
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
  const { selection } = useSelection();

  const [getTreePath, { loading: treePathLoading, data: treePathData, error: treePathError }] = useLazyQuery<
    GQLGetTreePathData,
    GQLGetTreePathVariables
  >(getTreePathQuery);

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
          onExpandedElementChange(newExpanded, Math.max(expandedMaxDepth, maxDepth));
        }
      }
    }
  }, [treePathLoading, treePathData]);

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (treePathError) {
      addErrorMessage(treePathError.message);
    }
  }, [treePathError]);

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
        expanded={expanded}
        maxDepth={maxDepth}
        onExpandedElementChange={onExpandedElementChange}
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
