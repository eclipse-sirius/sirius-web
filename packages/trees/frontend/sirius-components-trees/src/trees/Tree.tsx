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

import { DRAG_SOURCES_TYPE } from '@eclipse-sirius/sirius-components-core';
import { useEffect, useRef } from 'react';
import { TreeItem } from '../treeitems/TreeItem';
import { TreeItemWithChildren } from '../treeitems/TreeItemWithChildren';
import { TreeProps } from './Tree.types';

const useTreeStyle = makeStyles()((_) => ({
  ul: {
    width: 'inherit',
    minWidth: 'fit-content',
  },
}));

export const Tree = ({
  editingContextId,
  tree,
  expanded,
  maxDepth,
  onExpandedElementChange,
  readOnly,
  textToHighlight,
  textToFilter,
  markedItemIds,
  treeItemActionRender,
  onTreeItemClick,
  selectedTreeItemIds,
}: TreeProps) => {
  const { classes } = useTreeStyle();
  const treeElement = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    const downHandler = (event) => {
      if (
        (event.key === 'ArrowLeft' ||
          event.key === 'ArrowRight' ||
          event.key === 'ArrowUp' ||
          event.key === 'ArrowDown') &&
        event.target.tagName !== 'INPUT'
      ) {
        event.preventDefault();

        const previousItem = document.activeElement as HTMLElement;
        const dataset = previousItem.dataset;
        if (dataset.treeitemid) {
          const treeItemDomElements = document.querySelectorAll<HTMLElement>('[data-treeitemid]');
          const index = Array.from(treeItemDomElements).indexOf(previousItem);
          const id = dataset.treeitemid;
          const hasChildren = dataset.haschildren === 'true';
          const isExpanded = dataset.expanded === 'true';
          const depth: number = parseInt(dataset.depth ?? '0');

          switch (event.key) {
            case 'ArrowLeft':
              if (hasChildren && isExpanded) {
                const newExpanded = [...expanded];
                newExpanded.splice(newExpanded.indexOf(id), 1);
                onExpandedElementChange(newExpanded, Math.max(depth, maxDepth));
              } else if (index > 0) {
                const parentDepth = (depth - 1).toString();

                let positionFromParent: number = 0;
                while (!(treeItemDomElements[index - positionFromParent]?.dataset.depth === parentDepth)) {
                  positionFromParent++;
                }
                treeItemDomElements[index - positionFromParent]?.click();
              }
              break;
            case 'ArrowRight':
              if (hasChildren && !isExpanded) {
                onExpandedElementChange([...expanded, id], Math.max(depth, maxDepth));
              } else if (index < treeItemDomElements.length - 1) {
                treeItemDomElements[index + 1]?.click();
              }
              break;
            case 'ArrowUp':
              if (index > 0) {
                treeItemDomElements[index - 1]?.click();
              }
              break;
            case 'ArrowDown':
              if (index < treeItemDomElements.length - 1) {
                treeItemDomElements[index + 1]?.click();
              }
              break;
            default:
          }
        }
      }
    };

    const element = treeElement.current;
    if (element) {
      element.addEventListener('keydown', downHandler);

      return () => {
        element.removeEventListener('keydown', downHandler);
      };
    }
    return () => {};
  }, [treeElement, onExpandedElementChange]);

  return (
    <div ref={treeElement}>
      <ul className={classes.ul} data-testid="tree-root-elements">
        {tree.children.map((childItem, index) => {
          const itemSelected = selectedTreeItemIds.some((id) => id === childItem.id);
          const itemMarked = markedItemIds.some((id) => id === childItem.id);
          const dragStart: React.DragEventHandler<HTMLDivElement> = (event) => {
            const isDraggedItemSelected = selectedTreeItemIds.map((id) => id).includes(childItem.id);
            if (!isDraggedItemSelected) {
              // If we're dragging a non-selected item, drag it alone
              event.dataTransfer.setData(DRAG_SOURCES_TYPE, JSON.stringify([childItem.id]));
            } else if (selectedTreeItemIds.length > 0) {
              // Otherwise drag the whole selection
              event.dataTransfer.setData(DRAG_SOURCES_TYPE, JSON.stringify(selectedTreeItemIds));
            }
          };

          return (
            <li key={childItem.id}>
              <TreeItem
                editingContextId={editingContextId}
                treeId={tree.id}
                item={childItem}
                itemIndex={index}
                depth={1}
                expanded={expanded}
                maxDepth={maxDepth}
                readOnly={readOnly}
                textToHighlight={textToHighlight}
                textToFilter={textToFilter}
                marked={itemMarked}
                selected={itemSelected}
                onExpandedElementChange={onExpandedElementChange}
                onTreeItemClick={onTreeItemClick}
                onDragStart={dragStart}
                treeItemActionRender={treeItemActionRender}
              />
              <TreeItemWithChildren
                editingContextId={editingContextId}
                treeId={tree.id}
                item={childItem}
                itemIndex={index}
                depth={2}
                expanded={expanded}
                maxDepth={maxDepth}
                onExpandedElementChange={onExpandedElementChange}
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
    </div>
  );
};
