/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { MockedProvider, MockedResponse } from '@apollo/client/testing';
import {
  MessageOptions,
  Selection,
  SelectionContext,
  ToastContext,
  ToastContextValue,
} from '@eclipse-sirius/sirius-components-core';
import { act, cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { GQLList, GQLListItem } from '../../form/FormEventFragments.types';
import { clickListItemMutation, ListPropertySection } from '../ListPropertySection';
import {
  GQLClickListItemMutationData,
  GQLClickListItemMutationVariables,
  GQLSuccessPayload,
} from '../ListPropertySection.types';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const defaultListItems: GQLListItem[] = [
  {
    id: 'item1',
    label: 'item1Label',
    kind: 'itemKind',
    iconURL: [],
    deletable: true,
  },
  {
    id: 'item2',
    label: 'item2Label',
    kind: 'itemKind',
    iconURL: [],
    deletable: true,
  },
  {
    id: 'item3',
    label: 'item3Label',
    kind: 'itemKind',
    iconURL: [],
    deletable: true,
  },
];
const defaultList: GQLList = {
  label: 'myList',
  iconURL: [],
  hasHelpText: false,
  items: defaultListItems,
  style: null,
  __typename: 'List',
  diagnostics: [],
  id: 'listId',
};

const defaultListWithStyle: GQLList = {
  label: 'myList',
  iconURL: [],
  hasHelpText: false,
  items: defaultListItems,
  style: {
    color: 'RebeccaPurple',
    bold: true,
    fontSize: 20,
    italic: true,
    strikeThrough: true,
    underline: true,
  },
  __typename: 'List',
  diagnostics: [],
  id: 'listId',
};

const readOnlyList: GQLList = {
  label: 'myList',
  iconURL: [],
  items: defaultListItems,
  style: null,
  __typename: 'List',
  diagnostics: [],
  id: 'listId',
  readOnly: true,
};

const clickListItemVariables: GQLClickListItemMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    listId: 'listId',
    listItemId: 'item3',
    clickEventKind: 'SINGLE_CLICK',
  },
};

const successPayload: GQLSuccessPayload = { messages: [], __typename: 'SuccessPayload' };
const clickListItemSuccessData: GQLClickListItemMutationData = {
  clickListItem: successPayload,
};

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

const emptySelection: Selection = {
  entries: [],
};

const emptySetSelection = (_: Selection) => {};

test('render list widget', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <SelectionContext.Provider
          value={{ selection: emptySelection, setSelection: emptySetSelection, selectedRepresentations: [] }}>
          <ListPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={defaultList}
            subscribers={[]}
            readOnly={false}
          />
        </SelectionContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('render list widget with style', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <SelectionContext.Provider
          value={{ selection: emptySelection, setSelection: emptySetSelection, selectedRepresentations: [] }}>
          <ListPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={defaultListWithStyle}
            subscribers={[]}
            readOnly={false}
          />
        </SelectionContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should the click event sent on item click', async () => {
  let clickListItemMutationCalled = false;
  const itemClickCalledCalledSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: clickListItemMutation,
      variables: clickListItemVariables,
    },
    result: () => {
      clickListItemMutationCalled = true;
      return { data: clickListItemSuccessData };
    },
  };

  const { container } = render(
    <MockedProvider mocks={[itemClickCalledCalledSuccessMock]}>
      <ToastContext.Provider value={toastContextMock}>
        <SelectionContext.Provider
          value={{ selection: emptySelection, setSelection: emptySetSelection, selectedRepresentations: [] }}>
          <ListPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={defaultListWithStyle}
            subscribers={[]}
            readOnly={false}
          />
        </SelectionContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
  const element: HTMLElement = screen.getByTestId('representation-item3Label');
  expect(element.textContent).toBe('item3Label');

  await act(async () => {
    userEvent.click(element);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(clickListItemMutationCalled).toBeTruthy();
      expect(container).toMatchSnapshot();
    });
  });
});

test('render list widget with help hint', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <SelectionContext.Provider
          value={{ selection: emptySelection, setSelection: emptySetSelection, selectedRepresentations: [] }}>
          <ListPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={{ ...defaultList, hasHelpText: true }}
            subscribers={[]}
            readOnly={false}
          />
        </SelectionContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should render a readOnly list from widget properties', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <SelectionContext.Provider
          value={{ selection: emptySelection, setSelection: emptySetSelection, selectedRepresentations: [] }}>
          <ListPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={readOnlyList}
            subscribers={[]}
            readOnly={false}
          />
        </SelectionContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
