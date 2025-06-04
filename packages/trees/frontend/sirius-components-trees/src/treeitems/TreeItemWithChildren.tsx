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
import { makeStyles } from 'tss-react/mui';
import { TreeItem } from '../treeitems/TreeItem';
import { TreeItemWithChildrenProps } from './TreeItemWithChildren.types';

const useTreeItemWithChildrenStyle = makeStyles()((_theme) => ({
  ul: {
    marginLeft: 0,
  },
}));

export const TreeItemWithChildren = ({
  editingContextId,
  treeId,
  item,
  depth,
  expanded,
  maxDepth,
  readOnly,
  textToHighlight,
  textToFilter,
  markedItemIds,
  selectedTreeItemIds,
  onExpandedElementChange,
  onTreeItemClick,
  onDragStart,
  treeItemActionRender,
}: TreeItemWithChildrenProps) => {
  const { classes } = useTreeItemWithChildrenStyle();
  if (item.expanded && item.children) {
    return (
      <ul className={classes.ul}>
        {item.children.map((childItem, index) => {
          const itemSelected = selectedTreeItemIds.some((id) => id === childItem.id);
          const itemMarked = markedItemIds.some((id) => id === childItem.id);
          return (
            <li key={childItem.id}>
              <TreeItem
                editingContextId={editingContextId}
                treeId={treeId}
                item={childItem}
                itemIndex={index}
                depth={depth}
                expanded={expanded}
                maxDepth={maxDepth}
                readOnly={readOnly}
                textToHighlight={textToHighlight}
                textToFilter={textToFilter}
                marked={itemMarked}
                selected={itemSelected}
                onExpandedElementChange={onExpandedElementChange}
                onTreeItemClick={onTreeItemClick}
                onDragStart={onDragStart}
                treeItemActionRender={treeItemActionRender}
              />
              <TreeItemWithChildren
                editingContextId={editingContextId}
                treeId={treeId}
                item={childItem}
                itemIndex={index}
                depth={depth + 1}
                expanded={expanded}
                maxDepth={maxDepth}
                readOnly={readOnly}
                textToHighlight={textToHighlight}
                textToFilter={textToFilter}
                markedItemIds={markedItemIds}
                selectedTreeItemIds={selectedTreeItemIds}
                onExpandedElementChange={onExpandedElementChange}
                onTreeItemClick={onTreeItemClick}
                onDragStart={onDragStart}
                treeItemActionRender={treeItemActionRender}
              />
            </li>
          );
        })}
      </ul>
    );
  } else {
    return null;
  }
};
