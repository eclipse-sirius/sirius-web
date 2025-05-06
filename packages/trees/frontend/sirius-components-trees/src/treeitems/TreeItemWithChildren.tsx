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
  onExpand,
  onExpandAll,
  readOnly,
  textToHighlight,
  textToFilter,
  markedItemIds,
  treeItemActionRender,
  onTreeItemClick,
  selectedTreeItemIds,
}: TreeItemWithChildrenProps) => {
  const { classes } = useTreeItemWithChildrenStyle();
  if (item.expanded && item.children) {
    return (
      <ul className={classes.ul}>
        {item.children.map((childItem, index) => {
          return (
            <li key={childItem.id}>
              <TreeItem
                editingContextId={editingContextId}
                treeId={treeId}
                item={childItem}
                itemIndex={index}
                depth={depth}
                onExpand={onExpand}
                onExpandAll={onExpandAll}
                readOnly={readOnly}
                textToHighlight={textToHighlight}
                textToFilter={textToFilter}
                markedItemIds={markedItemIds}
                treeItemActionRender={treeItemActionRender}
                onTreeItemClick={onTreeItemClick}
                selectedTreeItemIds={selectedTreeItemIds}
              />
              <TreeItemWithChildren
                editingContextId={editingContextId}
                treeId={treeId}
                item={childItem}
                itemIndex={index}
                depth={depth + 1}
                onExpand={onExpand}
                onExpandAll={onExpandAll}
                readOnly={readOnly}
                textToHighlight={textToHighlight}
                textToFilter={textToFilter}
                markedItemIds={markedItemIds}
                treeItemActionRender={treeItemActionRender}
                onTreeItemClick={onTreeItemClick}
                selectedTreeItemIds={selectedTreeItemIds}
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
