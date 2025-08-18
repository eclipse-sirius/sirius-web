/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { GQLTreeNode, GQLTree } from '../../form/FormEventFragments.types';

// Helper to make fixtures more readable
function createNode(
  id: string,
  parentId: string,
  selectable: boolean = false,
  checkable: boolean = false,
  value: boolean = false
): GQLTreeNode {
  return {
    id,
    parentId,
    label: `Node-${id}`,
    kind: 'siriusComponents://testNode',
    iconURL: [],
    endIconsURL: [[]],
    selectable,
    checkable,
    value,
  };
}

export const tree: GQLTree = {
  __typename: 'TreeWidget',
  id: 'treeId',
  label: 'Default Tree',
  iconURL: [],
  hasHelpText: false,
  readOnly: false,
  nodes: [createNode('root1', '')],
  expandedNodesIds: [],
  diagnostics: [],
};

export const treeWithMultiLevelNodes: GQLTree = {
  __typename: 'TreeWidget',
  id: 'treeId',
  label: 'Deep Tree',
  iconURL: [],
  readOnly: false,
  hasHelpText: false,
  nodes: [
    createNode('1', ''),
    createNode('2', ''),
    createNode('1.1', '1'),
    createNode('1.1.1', '1.1'),
    createNode('1.1.2', '1.1'),
    createNode('1.2', '1'),
    createNode('1.3', '1'),
    createNode('1.3.1', '1.3'),
    createNode('1.3.1.1', '1.3.1'),
    createNode('1.3.1.2', '1.3.1'),
    createNode('2.1', '2'),
  ],
  expandedNodesIds: ['0', '1', '0/0', '0/2', '0/2/0'],
  diagnostics: [],
};

// The fixture contains the same nodes as the previous one, but with some ordering changes between siblings
// - the root '2' is moved before '1'
// - '1.3' moved between '1.1' and '1.2'
// - some deep nodes appear before their parent in the list (but with the same relative order) => should not have any impact
// - '1.1.2' moved before '1.1.1"
export const treeWithMultiLevelNodesUnordered: GQLTree = {
  __typename: 'TreeWidget',
  id: 'treeId',
  label: 'Deep Tree',
  iconURL: [],
  readOnly: false,
  hasHelpText: false,
  nodes: [
    createNode('2', ''),
    createNode('1', ''),
    createNode('1.3.1.1', '1.3.1'),
    createNode('1.3.1.2', '1.3.1'),
    createNode('1.1', '1'),
    createNode('1.1.2', '1.1'),
    createNode('1.1.1', '1.1'),
    createNode('1.3', '1'),
    createNode('1.2', '1'),
    createNode('1.3.1', '1.3'),
    createNode('2.1', '2'),
  ],
  expandedNodesIds: ['1', '0', '1/0', '1/1', '1/1/0'],
  diagnostics: [],
};

// Still the same fixture (in the original order), but with only the two root elements expanded
// (and one of the leave nodes, but its parent is not expanded so this should have no effect)
export const treeWithMultiLevelNodesExpanded: GQLTree = {
  __typename: 'TreeWidget',
  id: 'treeId',
  label: 'Deep Tree',
  iconURL: [],
  readOnly: false,
  hasHelpText: false,
  nodes: [
    createNode('1', ''),
    createNode('2', ''),
    createNode('1.1', '1'),
    createNode('1.1.1', '1.1'),
    createNode('1.1.2', '1.1'),
    createNode('1.2', '1'),
    createNode('1.3', '1'),
    createNode('1.3.1', '1.3'),
    createNode('1.3.1.1', '1.3.1'),
    createNode('1.3.1.2', '1.3.1'),
    createNode('2.1', '2'),
  ],
  expandedNodesIds: ['0', '1', '0/2/0/1'],
  diagnostics: [],
};

export const treeWithSelectableNodes: GQLTree = {
  __typename: 'TreeWidget',
  id: 'treeId',
  label: 'Tree',
  iconURL: [],
  readOnly: false,
  hasHelpText: false,
  nodes: [createNode('1', ''), createNode('2', ''), createNode('1.1', '1', true), createNode('1.2', '1', false)],
  expandedNodesIds: ['0', '1'],
  diagnostics: [],
};

export const treeWithTwoLevelNodes: GQLTree = {
  __typename: 'TreeWidget',
  id: 'treeId',
  label: 'Tree',
  iconURL: [],
  readOnly: false,
  hasHelpText: false,
  nodes: [createNode('1', ''), createNode('1.1', '1'), createNode('1.1.1', '1.1')],
  expandedNodesIds: ['0', '0/0'],
  diagnostics: [],
};
