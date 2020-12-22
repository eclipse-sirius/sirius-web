/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import PropTypes from 'prop-types';
import React, { useEffect, useRef } from 'react';
import { TreeItem } from 'tree/TreeItem';

const propTypes = {
  tree: PropTypes.object.isRequired,
  onExpand: PropTypes.func.isRequired,
  selection: PropTypes.object,
  displayedRepresentation: PropTypes.object,
  setSelection: PropTypes.func.isRequired,
};

export const Tree = ({ projectId, tree, onExpand, selection, displayedRepresentation, setSelection }) => {
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

        const previousItem = document.activeElement;
        const dataset = (previousItem as any).dataset;
        if (dataset.treeitemid) {
          const treeItemDomElements = document.querySelectorAll<HTMLElement>('[data-treeitemid]');
          const index = [].indexOf.call(treeItemDomElements, previousItem);
          const id = dataset.treeitemid;
          const hasChildren = dataset.haschildren;
          const expanded = dataset.expanded;

          switch (event.key) {
            case 'ArrowLeft':
              if (hasChildren && expanded === 'true') {
                onExpand(id, dataset.depth);
              }
              break;
            case 'ArrowRight':
              if (hasChildren && expanded === 'false') {
                onExpand(id, dataset.depth);
              }
              break;
            case 'ArrowUp':
              if (index > 0) {
                treeItemDomElements[index - 1].focus();
              }
              break;
            case 'ArrowDown':
              if (index < treeItemDomElements.length - 1) {
                treeItemDomElements[index + 1].focus();
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
  }, [treeElement, onExpand]);

  return (
    <div ref={treeElement}>
      <ul>
        {tree.children.map((item) => (
          <li key={item.id}>
            <TreeItem
              projectId={projectId}
              item={item}
              depth={1}
              onExpand={onExpand}
              selection={selection}
              // displayedRepresentation={displayedRepresentation} TODO TreeItem has no such prop
              setSelection={setSelection}
            />
          </li>
        ))}
      </ul>
    </div>
  );
};
Tree.propTypes = propTypes;
