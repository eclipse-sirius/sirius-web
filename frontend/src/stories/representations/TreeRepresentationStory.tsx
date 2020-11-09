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
import React, { useState } from 'react';

import { Tree } from 'tree/Tree';
import { Border } from 'stories/common/Border';

const createTree = (expandedIds) => {
  const ben = {
    id: 'ben',
    kind: 'Document',
    label: 'Ben',
    imageURL: '',
    hasChildren: false,
    expanded: false,
    children: [],
  };

  const leia = {
    id: 'leia',
    kind: 'Document',
    label: 'Leia',
    imageURL: '',
    hasChildren: true,
    expanded: expandedIds.includes('leia'),
    children: [ben],
  };

  const luke = {
    id: 'luke',
    kind: 'Document',
    label: 'Luke',
    imageURL: '',
    hasChildren: false,
    expanded: false,
    children: [],
  };

  const anakin = {
    id: 'anakin',
    kind: 'Document',
    label: 'Anakin',
    imageURL: '',
    hasChildren: true,
    expanded: expandedIds.includes('anakin'),
    children: [luke, leia],
  };

  const tree = {
    id: 'treeId',
    label: 'Family',
    children: [anakin],
  };

  return tree;
};

const selection = { id: 'luke' };

export const TreeRepresentationStory = () => {
  const [state, setState] = useState([]);

  const expand = (item) => {
    setState((prevState) => {
      const newState = [...prevState];
      const index = newState.indexOf(item.id);
      if (index === -1) {
        newState.push(item.id);
      } else {
        newState.splice(index, 1);
      }
      return newState;
    });
  };

  const tree = createTree(state);

  return (
    <Border>
      <Tree tree={tree} onExpand={(item) => expand(item)} selection={selection} setSelection={() => {}} />
    </Border>
  );
};
