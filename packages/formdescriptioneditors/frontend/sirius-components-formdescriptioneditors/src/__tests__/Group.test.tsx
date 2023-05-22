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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { GQLGroup, GQLPage } from '@eclipse-sirius/sirius-components-forms';
import { act, cleanup, fireEvent, render, screen, waitFor } from '@testing-library/react';
import { afterEach, expect, test, vi } from 'vitest';
import { addGroupMutation, deleteGroupMutation, moveGroupMutation } from '../FormDescriptionEditorEventFragment';
import {
  GQLAddGroupMutationData,
  GQLAddGroupMutationVariables,
  GQLDeleteGroupMutationData,
  GQLDeleteGroupMutationVariables,
  GQLFormDescriptionEditor,
  GQLMoveGroupMutationData,
  GQLMoveGroupMutationVariables,
  GQLSuccessPayload,
} from '../FormDescriptionEditorEventFragment.types';
import { Group } from '../Group';
import { DataTransfer } from './DataTransfer';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const emptySelection: Selection = {
  entries: [],
};

const emptySetSelection = (_: Selection) => {};

const successPayload: GQLSuccessPayload = {
  __typename: 'SuccessPayload',
  id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
};

const addGroupVariables: GQLAddGroupMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    pageId: 'Page1',
    index: 0,
  },
};

const addGroupSuccessData: GQLAddGroupMutationData = {
  addGroup: successPayload,
};

const deleteGroupVariables: GQLDeleteGroupMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    groupId: 'Group1',
  },
};

const deleteGroupSuccessData: GQLDeleteGroupMutationData = {
  deleteGroup: successPayload,
};

const moveGroupVariables: GQLMoveGroupMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    pageId: 'Page1',
    groupId: 'Group2',
    index: 0,
  },
};

const moveGroupSuccessData: GQLMoveGroupMutationData = {
  moveGroup: successPayload,
};

test('should drop the Group in the drop area', async () => {
  const group: GQLGroup = {
    id: 'Group1',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'Group1',
    widgets: [],
    toolbarActions: [],
  };

  const page: GQLPage = {
    id: 'Page1',
    label: 'Page1',
    toolbarActions: [],
    groups: [group],
  };

  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor1',
    pages: [page],
  };

  let addGroupCalled: boolean = false;
  const addGroupSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: addGroupMutation,
      variables: addGroupVariables,
    },
    result: () => {
      addGroupCalled = true;
      return { data: addGroupSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [addGroupSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <Group
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        page={page}
        group={group}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId(`Group-DropArea-${group.id}`);

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('draggedElementId', 'Group');
  dataTransfer.setData('draggedElementType', 'Group');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(addGroupCalled).toBeTruthy();
    });
  });
});

test('should delete the Group from the drop area', async () => {
  const group: GQLGroup = {
    id: 'Group1',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'Group1',
    widgets: [],
    toolbarActions: [],
  };

  const page: GQLPage = {
    id: 'Page1',
    label: 'Page1',
    toolbarActions: [],
    groups: [group],
  };

  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'formDescriptionEditorId',
    pages: [page],
  };

  let deleteGroupCalled: boolean = false;
  const deleteGroupSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: deleteGroupMutation,
      variables: deleteGroupVariables,
    },
    result: () => {
      deleteGroupCalled = true;
      return { data: deleteGroupSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [deleteGroupSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <Group
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        page={page}
        group={group}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const group1: HTMLElement = screen.getByTestId('Group1');
  expect(group1).not.toBeUndefined();

  await act(async () => {
    group1.focus();
    fireEvent.keyDown(group1, { key: 'Delete', code: 'NumpadDecimal' });
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(deleteGroupCalled).toBeTruthy();
    });
  });
});

test('should move the existing Group from/into the drop area', async () => {
  const group1: GQLGroup = {
    id: 'Group1',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'Group1',
    widgets: [],
    toolbarActions: [],
  };

  const group2: GQLGroup = {
    id: 'Group2',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'Group2',
    widgets: [],
    toolbarActions: [],
  };

  const page: GQLPage = {
    id: 'Page1',
    label: 'Page1',
    toolbarActions: [],
    groups: [group1, group2],
  };

  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'formDescriptionEditorId',
    pages: [page],
  };

  let moveGroupCalled: boolean = false;
  const moveGroupSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: moveGroupMutation,
      variables: moveGroupVariables,
    },
    result: () => {
      moveGroupCalled = true;
      return { data: moveGroupSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [moveGroupSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <Group
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        page={page}
        group={group1}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId(`Group-DropArea-${group1.id}`);

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('draggedElementId', group2.id);
  dataTransfer.setData('draggedElementType', 'Group');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(moveGroupCalled).toBeTruthy();
    });
  });
});
