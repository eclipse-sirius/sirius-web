/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { TreePropertySection } from '../TreePropertySection';
import {
  tree,
  treeWithMultiLevelNodes,
  treeWithMultiLevelNodesExpanded,
  treeWithMultiLevelNodesUnordered,
  treeWithSelectableNodes,
  treeWithTwoLevelNodes,
} from './TreePropertySection.data';

afterEach(() => {
  cleanup();
  vi.clearAllMocks();
});

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

const emptySelection: Selection = {
  entries: [],
};

const emptySetSelection = () => {};

test('should render the tree', () => {
  render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider value={{ selection: emptySelection, setSelection: emptySetSelection }}>
            <TreePropertySection editingContextId="editingContextId" formId="formId" widget={tree} readOnly={false} />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(screen.queryAllByRole('treeitem')).toHaveLength(1);
  expect(screen.getAllByText(/Node-/).map((element) => element.textContent)).toEqual(['Node-root1']);
});

test('should render a multi-level tree correctly', () => {
  render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider value={{ selection: emptySelection, setSelection: emptySetSelection }}>
            <TreePropertySection
              editingContextId="editingContextId"
              formId="formId"
              widget={treeWithMultiLevelNodes}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
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
  render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider value={{ selection: emptySelection, setSelection: emptySetSelection }}>
            <TreePropertySection
              editingContextId="editingContextId"
              formId="formId"
              widget={treeWithMultiLevelNodesUnordered}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
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
  render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider value={{ selection: emptySelection, setSelection: emptySetSelection }}>
            <TreePropertySection
              editingContextId="editingContextId"
              formId="formId"
              widget={treeWithMultiLevelNodesExpanded}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
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
  let selection: SelectionEntry = { id: 'undefined' };

  const mocks = [];
  render(
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
              widget={treeWithSelectableNodes}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
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
  });
  // 1.1 is selectable => should be the new selection
  screen.getByText('Node-1.1').click();
  expect(selection).toEqual({
    id: '1.1',
  });
});

test('should collapse/expand a non-selectable node when clicked', async () => {
  let selection: SelectionEntry = { id: 'undefined' };

  const mocks = [];
  render(
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
              widget={treeWithTwoLevelNodes}
              readOnly={false}
            />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(screen.getAllByText(/Node-/).map((element) => element.textContent)).toEqual([
    'Node-1',
    'Node-1.1',
    'Node-1.1.1',
  ]);
  expect(selection).toEqual({
    id: 'undefined',
  });
  expect(screen.getByText('Node-1.1.1')).toBeDefined();

  // Single-click on non-selectable Node-1.1 should make its child disappear
  await userEvent.click(screen.getByText('Node-1.1'));

  await waitFor(() => {
    expect(selection).toEqual({
      id: 'undefined',
    });
    expect(screen.queryByText('Node-1.1.1')).toBeNull();
  });

  // Another single-click should bring the child back
  await userEvent.click(screen.getByText('Node-1.1'));

  await waitFor(() => {
    expect(selection).toEqual({
      id: 'undefined',
    });
    expect(screen.getByText('Node-1.1.1')).toBeDefined();
  });
});

test('should render the tree with a help hint', () => {
  render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
          <SelectionContext.Provider value={{ selection: emptySelection, setSelection: emptySetSelection }}>
            <TreePropertySection editingContextId="editingContextId" formId="formId" widget={tree} readOnly={false} />
          </SelectionContext.Provider>
        </ServerContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(screen.queryAllByRole('treeitem')).toHaveLength(1);
  expect(screen.getAllByText(/Node-/).map((element) => element.textContent)).toEqual(['Node-root1']);
});
