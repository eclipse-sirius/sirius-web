/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { MockedProvider } from '@apollo/client/testing';
import {
  MessageOptions,
  Selection,
  SelectionContext,
  SelectionEntry,
  ServerContext,
  ToastContext,
  ToastContextValue,
} from '@eclipse-sirius/sirius-components-core';
import { act, cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';
import { afterEach, expect, test, vi } from 'vitest';
import { GQLTree, GQLTreeNode } from '../../form/FormEventFragments.types';
import { TreePropertySection } from '../TreePropertySection';

afterEach(() => cleanup());

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

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

const emptySelection: Selection = {
  entries: [],
};

const emptySetSelection = () => {};

test('should render the tree', () => {
  const treeWidget: GQLTree = {
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

  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider value={{ selection: emptySelection, setSelection: emptySetSelection }}>
            <TreePropertySection
              editingContextId="editingContextId"
              formId="formId"
              widget={treeWidget}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
  expect(screen.queryAllByRole('treeitem')).toHaveLength(1);
  expect(screen.getAllByText(/Node-/).map((element) => element.textContent)).toEqual(['Node-root1']);
});

test('should render a multi-level tree correctly', () => {
  const treeWidget: GQLTree = {
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
    expandedNodesIds: ['1', '2', '1.1', '1.3', '1.3.1'],
    diagnostics: [],
  };
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider value={{ selection: emptySelection, setSelection: emptySetSelection }}>
            <TreePropertySection
              editingContextId="editingContextId"
              formId="formId"
              widget={treeWidget}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
  expect(screen.getAllByText(/Node-/).map((element) => element.textContent)).toEqual([
    'Node-1',
    'Node-1.1',
    'Node-1.1.1',
    'Node-1.1.2',
    'Node-1.2',
    'Node-1.3',
    'Node-1.3.1',
    'Node-1.3.1.1',
    'Node-1.3.1.2',
    'Node-2',
    'Node-2.1',
  ]);
});

test('should correctly interpret the order of nodes with the same parent in the flat nodes list', () => {
  // The fixture contains the same nodes as the previous one, but with some ordering changes between siblings
  // - the root '2' is moved before '1'
  // - '1.3' moved between '1.1' and '1.2'
  // - some deep nodes appear before their parent in the list (but with the same relative order) => should not have any impact
  // - '1.1.2' moved before '1.1.1"
  const treeWidget: GQLTree = {
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
    expandedNodesIds: ['1', '2', '1.1', '1.3', '1.3.1'],
    diagnostics: [],
  };
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider value={{ selection: emptySelection, setSelection: emptySetSelection }}>
            <TreePropertySection
              editingContextId="editingContextId"
              formId="formId"
              widget={treeWidget}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
  expect(screen.getAllByText(/Node-/).map((element) => element.textContent)).toEqual([
    'Node-2',
    'Node-2.1',
    'Node-1',
    'Node-1.1',
    'Node-1.1.2',
    'Node-1.1.1',
    'Node-1.3',
    'Node-1.3.1',
    'Node-1.3.1.1',
    'Node-1.3.1.2',
    'Node-1.2',
  ]);
});

test('should only expand the specified nodes on initial render', () => {
  // Still the same fixture (in the original order), but with only the two root elements expanded
  // (and one of the leave nodes, but its parent is not expanded so this should have no effect)
  const treeWidget: GQLTree = {
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
    expandedNodesIds: ['1', '2', '1.3.1.2'],
    diagnostics: [],
  };
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider value={{ selection: emptySelection, setSelection: emptySetSelection }}>
            <TreePropertySection
              editingContextId="editingContextId"
              formId="formId"
              widget={treeWidget}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
  expect(screen.getAllByText(/Node-/).map((element) => element.textContent)).toEqual([
    'Node-1',
    'Node-1.1',
    //'Node-1.1.1',
    //'Node-1.1.2',
    'Node-1.2',
    'Node-1.3',
    // 'Node-1.3.1',
    // 'Node-1.3.1.1',
    // 'Node-1.3.1.2',
    'Node-2',
    'Node-2.1',
  ]);
});

test('should change the selection when a selectable node is clicked', () => {
  const treeWidget: GQLTree = {
    __typename: 'TreeWidget',
    id: 'treeId',
    label: 'Tree',
    iconURL: [],
    readOnly: false,
    hasHelpText: false,
    nodes: [createNode('1', ''), createNode('2', ''), createNode('1.1', '1', true), createNode('1.2', '1', false)],
    expandedNodesIds: ['1', '2'],
    diagnostics: [],
  };

  let selection: SelectionEntry = { id: 'undefined', kind: '', label: '' };

  const mocks = [];
  const { container } = render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider
            value={{
              selection,
              setSelection: (newSelection: Selection) => {
                selection = newSelection.entries[0];
              },
            }}>
            <TreePropertySection
              editingContextId="editingContextId"
              formId="formId"
              widget={treeWidget}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
  expect(screen.getAllByText(/Node-/).map((element) => element.textContent)).toEqual([
    'Node-1',
    'Node-1.1',
    'Node-1.2',
    'Node-2',
  ]);
  // 1.2 is not selectable => no change
  screen.getByText('Node-1.2').click();
  expect(selection).toEqual({
    id: 'undefined',
    label: '',
    kind: '',
  });
  // 1.1 is selectable => should be the new selection
  screen.getByText('Node-1.1').click();
  expect(selection).toEqual({
    id: '1.1',
    label: 'Node-1.1',
    kind: 'siriusComponents://testNode',
  });
});

test('should collapse/expand a non-selectable node when clicked', async () => {
  const treeWidget: GQLTree = {
    __typename: 'TreeWidget',
    id: 'treeId',
    label: 'Tree',
    iconURL: [],
    readOnly: false,
    hasHelpText: false,
    nodes: [createNode('1', ''), createNode('1.1', '1'), createNode('1.1.1', '1.1')],
    expandedNodesIds: ['1', '1.1'],
    diagnostics: [],
  };
  let selection: SelectionEntry = { id: 'undefined', kind: '', label: '' };

  const mocks = [];
  const { container } = render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider
            value={{
              selection,
              setSelection: (newSelection: Selection) => {
                selection = newSelection.entries[0];
              },
            }}>
            <TreePropertySection
              editingContextId="editingContextId"
              formId="formId"
              widget={treeWidget}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
  expect(screen.getAllByText(/Node-/).map((element) => element.textContent)).toEqual([
    'Node-1',
    'Node-1.1',
    'Node-1.1.1',
  ]);
  expect(selection).toEqual({
    id: 'undefined',
    label: '',
    kind: '',
  });
  expect(screen.getByText('Node-1.1.1')).toBeDefined();

  // Single-click on non-selectable Node-1.1 should make its child disappear
  await act(async () => {
    userEvent.click(screen.getByText('Node-1.1'));
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(selection).toEqual({
        id: 'undefined',
        label: '',
        kind: '',
      });
      expect(screen.queryByText('Node-1.1.1')).toBeNull();
    });
  });

  // Another single-click should bring the child back
  await act(async () => {
    userEvent.click(screen.getByText('Node-1.1'));
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(selection).toEqual({
        id: 'undefined',
        label: '',
        kind: '',
      });
      expect(screen.getByText('Node-1.1.1')).toBeDefined();
    });
  });
});

test('should render the tree with a help hint', () => {
  const treeWidget: GQLTree = {
    __typename: 'TreeWidget',
    id: 'treeId',
    label: 'Default Tree',
    iconURL: [],
    readOnly: false,
    hasHelpText: true,
    nodes: [createNode('root1', '')],
    expandedNodesIds: [],
    diagnostics: [],
  };

  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider value={{ selection: emptySelection, setSelection: emptySetSelection }}>
            <TreePropertySection
              editingContextId="editingContextId"
              formId="formId"
              widget={treeWidget}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
  expect(screen.queryAllByRole('treeitem')).toHaveLength(1);
  expect(screen.getAllByText(/Node-/).map((element) => element.textContent)).toEqual(['Node-root1']);
});
