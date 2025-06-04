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
import { DataExtension, DRAG_SOURCES_TYPE, useData } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useMemo } from 'react';
import { Tree } from '../trees/Tree';
import { GQLTree, TreeConverter, TreeViewProps } from './TreeView.types';
import { treeViewTreeConverterExtensionPoint } from './TreeViewExtensionPoints';

const convertTree = (editingContextId: string, treeConverters: TreeConverter[], tree: GQLTree) => {
  let convertedTree: GQLTree = tree;
  treeConverters.forEach((treeConverter) => {
    convertedTree = treeConverter.convert(editingContextId, convertedTree);
  });
  return convertedTree;
};

export const TreeView = ({
  editingContextId,
  readOnly,
  tree,
  treeId,
  textToHighlight,
  textToFilter,
  markedItemIds = [],
  selectedTreeItemIds,
  expanded,
  maxDepth,
  onExpandedElementChange,
  onTreeItemClick,
  treeItemActionRender,
}: TreeViewProps) => {
  const { data: treeConverters }: DataExtension<TreeConverter[]> = useData(treeViewTreeConverterExtensionPoint);
  const convertedTree: GQLTree = useMemo(() => {
    return convertTree(editingContextId, treeConverters, tree);
  }, [editingContextId, tree, treeConverters]);

  const onDragStart: React.DragEventHandler<HTMLDivElement> = useCallback(
    (event) => {
      const target = event.target as HTMLDivElement;
      const itemId = target.getAttribute('data-treeitemid') || '';
      const isDraggedItemSelected = selectedTreeItemIds.some((selectedItemId) => selectedItemId === itemId);
      if (!isDraggedItemSelected) {
        // If we're dragging a non-selected item, drag it alone
        event.dataTransfer.setData(DRAG_SOURCES_TYPE, JSON.stringify([itemId]));
      } else if (selectedTreeItemIds.length > 0) {
        // Otherwise drag the whole selection
        event.dataTransfer.setData(DRAG_SOURCES_TYPE, JSON.stringify(selectedTreeItemIds));
      }
    },
    [selectedTreeItemIds]
  );

  return (
    <div data-testid={treeId}>
      <Tree
        editingContextId={editingContextId}
        tree={convertedTree}
        expanded={expanded}
        maxDepth={maxDepth}
        readOnly={readOnly}
        markedItemIds={markedItemIds}
        selectedTreeItemIds={selectedTreeItemIds}
        textToFilter={textToFilter}
        textToHighlight={textToHighlight}
        onExpandedElementChange={onExpandedElementChange}
        onTreeItemClick={onTreeItemClick}
        onDragStart={onDragStart}
        treeItemActionRender={treeItemActionRender}
      />
    </div>
  );
};
