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
import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
import { Tree } from '../trees/Tree';
import { GQLTree, TreeConverter, TreeViewProps } from './TreeView.types';
import { treeViewTreeConverterExtensionPoint } from './TreeViewExtensionPoints';

export const TreeView = ({
  editingContextId,
  readOnly,
  tree,
  treeId,
  textToHighlight,
  textToFilter,
  markedItemIds = [],
  treeItemActionRender,
  onExpandedElementChange,
  expanded,
  maxDepth,
  onTreeItemClick,
  selectedTreeItemIds,
}: TreeViewProps) => {
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
        markedItemIds={markedItemIds}
        textToFilter={textToFilter}
        textToHighlight={textToHighlight}
        treeItemActionRender={treeItemActionRender}
        onTreeItemClick={onTreeItemClick}
        selectedTreeItemIds={selectedTreeItemIds}
      />
    </div>
  );
};
