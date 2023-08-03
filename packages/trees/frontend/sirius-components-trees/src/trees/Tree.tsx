/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import { useEffect, useRef } from 'react';
import { TreeItem } from '../treeitems/TreeItem';
import { TreeProps } from './Tree.types';

const useTreeStyle = makeStyles((_) => ({
  ul: {
    width: 'inherit',
    minWidth: 'fit-content',
  },
}));

export const Tree = ({
  editingContextId,
  tree,
  onExpand,
  onExpandAll,
  readOnly,
  enableMultiSelection = true,
  textToHighlight,
  textToFilter,
  markedItemIds,
}: TreeProps) => {
  const classes = useTreeStyle();
  const treeElement = useRef(null);

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
        const dataset = (previousItem as any).dataset;
        if (dataset.treeitemid) {
          const treeItemDomElements = document.querySelectorAll<HTMLElement>('[data-treeitemid]');
          const index = Array.from(treeItemDomElements).indexOf(previousItem);
          const id = dataset.treeitemid;
          const hasChildren = dataset.haschildren === 'true';
          const isExpanded = dataset.expanded === 'true';

          switch (event.key) {
            case 'ArrowLeft':
              if (hasChildren && isExpanded) {
                onExpand(id, dataset.depth);
              } else if (index > 0) {
                const parentDepth = (dataset.depth - 1).toString();
                let positionFromParent = 0;
                while (!(treeItemDomElements[index - positionFromParent].dataset.depth === parentDepth)) {
                  positionFromParent++;
                }
                treeItemDomElements[index - positionFromParent].click();
              }
              break;
            case 'ArrowRight':
              if (hasChildren && !isExpanded) {
                onExpand(id, dataset.depth);
              } else if (index < treeItemDomElements.length - 1) {
                treeItemDomElements[index + 1].click();
              }
              break;
            case 'ArrowUp':
              if (index > 0) {
                treeItemDomElements[index - 1].click();
              }
              break;
            case 'ArrowDown':
              if (index < treeItemDomElements.length - 1) {
                treeItemDomElements[index + 1].click();
              }
              break;
            default:
          }
        }
      }
    };

    const element = treeElement?.current;
    if (element) {
      element.addEventListener('keydown', downHandler);

      return () => {
        element.removeEventListener('keydown', downHandler);
      };
    }
    return null;
  }, [treeElement, onExpand]);

  return (
    <>
      <div ref={treeElement}>
        <ul className={classes.ul} data-testid="tree-root-elements">
          {tree.children.map((item) => (
            <li key={item.id}>
              <TreeItem
                editingContextId={editingContextId}
                treeId={tree.id}
                item={item}
                depth={1}
                onExpand={onExpand}
                onExpandAll={onExpandAll}
                enableMultiSelection={enableMultiSelection}
                readOnly={readOnly}
                textToHighlight={textToHighlight}
                textToFilter={textToFilter}
                markedItemIds={markedItemIds}
              />
            </li>
          ))}
        </ul>
      </div>
    </>
  );
};
