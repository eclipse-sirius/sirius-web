/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { ConfirmationDialogContext, Selection, SelectionContext } from '@eclipse-sirius/sirius-components-core';
import { GQLPage } from '@eclipse-sirius/sirius-components-forms';
import { act, cleanup, fireEvent, render, screen, waitFor } from '@testing-library/react';
import { afterEach, expect, test, vi } from 'vitest';

import React from 'react';
import { addPageMutation, deletePageMutation, movePageMutation } from '../FormDescriptionEditorEventFragment';
import {
  GQLAddPageMutationData,
  GQLAddPageMutationVariables,
  GQLDeletePageMutationData,
  GQLDeletePageMutationVariables,
  GQLFormDescriptionEditor,
  GQLMovePageMutationData,
  GQLMovePageMutationVariables,
  GQLSuccessPayload,
} from '../FormDescriptionEditorEventFragment.types';
import { PageList } from '../PageList';
import { FormDescriptionEditorContext } from '../hooks/FormDescriptionEditorContext';
import { DataTransfer } from './DataTransfer';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const showConfirmation = (_title: string, _message: string, _buttonLabel: string, onConfirm: () => void) => {
  onConfirm();
};

const emptySelection: Selection = {
  entries: [],
};

const emptySetSelection = (_: Selection) => {};

const TestContextProvider = ({ mocks, formDescriptionEditor, children }) => {
  return (
    <MockedProvider mocks={mocks}>
      <ConfirmationDialogContext.Provider
        value={{
          showConfirmation,
        }}>
        <FormDescriptionEditorContext.Provider
          value={{
            editingContextId: 'editingContextId',
            representationId: 'formDescriptionEditorId',
            formDescriptionEditor,
            readOnly: false,
          }}>
          <SelectionContext.Provider value={{ selection: emptySelection, setSelection: emptySetSelection }}>
            {children}
          </SelectionContext.Provider>
        </FormDescriptionEditorContext.Provider>
      </ConfirmationDialogContext.Provider>
    </MockedProvider>
  );
};

const successPayload: GQLSuccessPayload = {
  __typename: 'SuccessPayload',
  id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
};

const addPageVariables: GQLAddPageMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    index: 1,
  },
};

const addPageSuccessData: GQLAddPageMutationData = {
  addPage: successPayload,
};

const deletePageVariables: GQLDeletePageMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    pageId: 'Page1',
  },
};

const deletePageSuccessData: GQLDeletePageMutationData = {
  deletePage: successPayload,
};

const movePageVariables: GQLMovePageMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    pageId: 'Page2',
    index: 0,
  },
};

const movePageSuccessData: GQLMovePageMutationData = {
  movePage: successPayload,
};

test('should drop the Page in the drop area', async () => {
  const page: GQLPage = {
    id: 'Page1',
    label: 'Page1',
    toolbarActions: [],
    groups: [],
  };

  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor1',
    pages: [page],
  };

  let addPageCalled: boolean = false;
  const addPageSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: addPageMutation,
      variables: addPageVariables,
    },
    result: () => {
      addPageCalled = true;
      return { data: addPageSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [addPageSuccessMock];

  render(
    <TestContextProvider mocks={mocks} formDescriptionEditor={formDescriptionEditor}>
      <PageList />
    </TestContextProvider>
  );

  const element: HTMLElement = screen.getByTestId(`PageList-DropArea`);

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('draggedElementId', 'Page');
  dataTransfer.setData('draggedElementType', 'Page');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(addPageCalled).toBeTruthy();
    });
  });
});

test('should delete the Page', async () => {
  const page: GQLPage = {
    id: 'Page1',
    label: 'Page1',
    toolbarActions: [],
    groups: [],
  };

  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'formDescriptionEditorId',
    pages: [page],
  };

  let deletePageCalled: boolean = false;
  const deletePageSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: deletePageMutation,
      variables: deletePageVariables,
    },
    result: () => {
      deletePageCalled = true;
      return { data: deletePageSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [deletePageSuccessMock];

  render(
    <TestContextProvider mocks={mocks} formDescriptionEditor={formDescriptionEditor}>
      <PageList />
    </TestContextProvider>
  );

  const page1: HTMLElement = screen.getByTestId('Page-Page1');
  expect(page1).not.toBeUndefined();

  await act(async () => {
    page1.focus();
    fireEvent.keyDown(page1, { key: 'Delete', code: 'NumpadDecimal' });
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(deletePageCalled).toBeTruthy();
    });
  });
});

test('should move the existing Page into the drop area', async () => {
  const page1: GQLPage = {
    id: 'Page1',
    label: 'Page1',
    toolbarActions: [],
    groups: [],
  };

  const page2: GQLPage = {
    id: 'Page2',
    label: 'Page2',
    toolbarActions: [],
    groups: [],
  };

  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'formDescriptionEditorId',
    pages: [page1, page2],
  };

  let movePageCalled: boolean = false;
  const movePageSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: movePageMutation,
      variables: movePageVariables,
    },
    result: () => {
      movePageCalled = true;
      return { data: movePageSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [movePageSuccessMock];

  render(
    <TestContextProvider mocks={mocks} formDescriptionEditor={formDescriptionEditor}>
      <PageList />
    </TestContextProvider>
  );

  const element: HTMLElement = screen.getByTestId(`Page-${page1.id}`);

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('draggedElementId', page2.id);
  dataTransfer.setData('draggedElementType', 'Page');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(movePageCalled).toBeTruthy();
    });
  });
});
