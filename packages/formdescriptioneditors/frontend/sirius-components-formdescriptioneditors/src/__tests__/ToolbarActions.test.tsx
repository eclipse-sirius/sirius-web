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
import { GQLGroup, GQLPage, GQLToolbarAction } from '@eclipse-sirius/sirius-components-forms';
import { act, cleanup, fireEvent, render, screen, waitFor } from '@testing-library/react';
import { afterEach, expect, test, vi } from 'vitest';
import {
  addToolbarActionMutation,
  deleteToolbarActionMutation,
  moveToolbarActionMutation,
} from '../FormDescriptionEditorEventFragment';
import {
  GQLAddToolbarActionMutationData,
  GQLAddToolbarActionMutationVariables,
  GQLDeleteToolbarActionMutationData,
  GQLDeleteToolbarActionMutationVariables,
  GQLFormDescriptionEditor,
  GQLMoveToolbarActionMutationData,
  GQLMoveToolbarActionMutationVariables,
  GQLSuccessPayload,
} from '../FormDescriptionEditorEventFragment.types';
import { ToolbarActions } from '../ToolbarActions';
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

const addToolbarActionVariables: GQLAddToolbarActionMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    containerId: 'group1',
  },
};

const addToolbarActionVariablesForPage: GQLAddToolbarActionMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    containerId: 'Page1',
  },
};

const addToolbarActionSuccessData: GQLAddToolbarActionMutationData = {
  addToolbarAction: successPayload,
};

const deleteToolbarActionVariables: GQLDeleteToolbarActionMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    toolbarActionId: 'ToolbarAction1',
  },
};

const deleteToolbarActionSuccessData: GQLDeleteToolbarActionMutationData = {
  deleteToolbarAction: successPayload,
};

const moveToolbarActionVariables: GQLMoveToolbarActionMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    toolbarActionId: 'ToolbarAction2',
    containerId: 'group1',
    index: 0,
  },
};

const moveToolbarActionSuccessData: GQLMoveToolbarActionMutationData = {
  moveToolbarAction: successPayload,
};

const moveToolbarActionAtTheEndVariables: GQLMoveToolbarActionMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    toolbarActionId: 'ToolbarAction2',
    containerId: 'group1',
    index: 1,
  },
};

const moveToolbarActionAtTheEndSuccessData: GQLMoveToolbarActionMutationData = {
  moveToolbarAction: successPayload,
};

test('add ToolbarAction by clicking on the Add Toolbar Action button', async () => {
  const toolbarAction: GQLToolbarAction = {
    id: 'ToolbarAction1',
    label: 'ToolbarAction1',
    iconURL: [],
    __typename: 'ToolbarAction',
    diagnostics: [],
    buttonLabel: 'ToolbarAction1',
    imageURL: null,
    style: {
      backgroundColor: null,
      foregroundColor: null,
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
    },
  };

  const group: GQLGroup = {
    id: 'group1',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'group1',
    widgets: [],
    toolbarActions: [toolbarAction],
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

  let addToolbarActionCalled: boolean = false;
  const addToolbarActionSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: addToolbarActionMutation,
      variables: addToolbarActionVariables,
    },
    result: () => {
      addToolbarActionCalled = true;
      return { data: addToolbarActionSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [addToolbarActionSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <ToolbarActions
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        toolbarActions={group.toolbarActions}
        containerId={group.id}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId(`ToolbarActions-NewAction-${group.id}`);
  element.click();

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(addToolbarActionCalled).toBeTruthy();
    });
  });
});

test('delete the ToolbarAction from the ToolbarActions', async () => {
  const toolbarAction1: GQLToolbarAction = {
    id: 'ToolbarAction1',
    label: 'ToolbarAction1',
    iconURL: [],
    __typename: 'ToolbarAction',
    diagnostics: [],
    buttonLabel: 'ToolbarAction1',
    imageURL: null,
    style: {
      backgroundColor: null,
      foregroundColor: null,
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
    },
  };
  const toolbarAction2: GQLToolbarAction = {
    id: 'ToolbarAction2',
    label: 'ToolbarAction2',
    iconURL: [],
    __typename: 'ToolbarAction',
    diagnostics: [],
    buttonLabel: 'ToolbarAction2',
    imageURL: null,
    style: {
      backgroundColor: null,
      foregroundColor: null,
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
    },
  };

  const group: GQLGroup = {
    id: 'group1',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'group1',
    widgets: [],
    toolbarActions: [toolbarAction1, toolbarAction2],
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

  let deleteToolbarActionCalled: boolean = false;
  const deleteToolbarActionSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: deleteToolbarActionMutation,
      variables: deleteToolbarActionVariables,
    },
    result: () => {
      deleteToolbarActionCalled = true;
      return { data: deleteToolbarActionSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [deleteToolbarActionSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <ToolbarActions
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        toolbarActions={group.toolbarActions}
        containerId={group.id}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const toolbarActionElt: HTMLElement = screen.getByTestId('ToolbarAction1');
  expect(toolbarActionElt).not.toBeUndefined();

  await act(async () => {
    toolbarActionElt.focus();
    fireEvent.keyDown(toolbarActionElt, { key: 'Delete', code: 'NumpadDecimal' });
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(deleteToolbarActionCalled).toBeTruthy();
    });
  });
});

test('move the existing ToolbarAction from/into the drop area', async () => {
  const toolbarAction1: GQLToolbarAction = {
    id: 'ToolbarAction1',
    label: 'ToolbarAction1',
    iconURL: [],
    __typename: 'ToolbarAction',
    diagnostics: [],
    buttonLabel: 'ToolbarAction1',
    imageURL: null,
    style: {
      backgroundColor: null,
      foregroundColor: null,
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
    },
  };
  const toolbarAction2: GQLToolbarAction = {
    id: 'ToolbarAction2',
    label: 'ToolbarAction2',
    iconURL: [],
    __typename: 'ToolbarAction',
    diagnostics: [],
    buttonLabel: 'ToolbarAction2',
    imageURL: null,
    style: {
      backgroundColor: null,
      foregroundColor: null,
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
    },
  };

  const group: GQLGroup = {
    id: 'group1',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'group1',
    widgets: [],
    toolbarActions: [toolbarAction1, toolbarAction2],
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

  let moveToolbarActionCalled: boolean = false;
  const moveToolbarActionSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: moveToolbarActionMutation,
      variables: moveToolbarActionVariables,
    },
    result: () => {
      moveToolbarActionCalled = true;
      return { data: moveToolbarActionSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [moveToolbarActionSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <ToolbarActions
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        toolbarActions={group.toolbarActions}
        containerId={group.id}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId('ToolbarAction-DropArea-ToolbarAction1');

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('draggedElementId', 'ToolbarAction2');
  dataTransfer.setData('draggedElementType', 'ToolbarActionWidget');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(moveToolbarActionCalled).toBeTruthy();
    });
  });
});

test('move the existing ToolbarAction from/into the drop area located at the end of the ToolbarActions', async () => {
  const toolbarAction1: GQLToolbarAction = {
    id: 'ToolbarAction1',
    label: 'ToolbarAction1',
    iconURL: [],
    __typename: 'ToolbarAction',
    diagnostics: [],
    buttonLabel: 'ToolbarAction1',
    imageURL: null,
    style: {
      backgroundColor: null,
      foregroundColor: null,
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
    },
  };
  const toolbarAction2: GQLToolbarAction = {
    id: 'ToolbarAction2',
    label: 'ToolbarAction2',
    iconURL: [],
    __typename: 'ToolbarAction',
    diagnostics: [],
    buttonLabel: 'ToolbarAction2',
    imageURL: null,
    style: {
      backgroundColor: null,
      foregroundColor: null,
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
    },
  };

  const group: GQLGroup = {
    id: 'group1',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'group1',
    widgets: [],
    toolbarActions: [toolbarAction1, toolbarAction2],
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

  let moveToolbarActionAtTheEndCalled: boolean = false;
  const moveToolbarActionAtTheEndSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: moveToolbarActionMutation,
      variables: moveToolbarActionAtTheEndVariables,
    },
    result: () => {
      moveToolbarActionAtTheEndCalled = true;
      return { data: moveToolbarActionAtTheEndSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [moveToolbarActionAtTheEndSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <ToolbarActions
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        toolbarActions={group.toolbarActions}
        containerId={group.id}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId(`ToolbarActions-DropArea-${group.id}`);

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('draggedElementId', 'ToolbarAction2');
  dataTransfer.setData('draggedElementType', 'ToolbarActionWidget');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(moveToolbarActionAtTheEndCalled).toBeTruthy();
    });
  });
});

test('add ToolbarAction by clicking on the Add Toolbar Action button for a page', async () => {
  const toolbarAction: GQLToolbarAction = {
    id: 'ToolbarAction1',
    label: 'ToolbarAction1',
    iconURL: [],
    __typename: 'ToolbarAction',
    diagnostics: [],
    buttonLabel: 'ToolbarAction1',
    imageURL: null,
    style: {
      backgroundColor: null,
      foregroundColor: null,
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
    },
  };

  const page: GQLPage = {
    id: 'Page1',
    label: 'Page1',
    toolbarActions: [toolbarAction],
    groups: [],
  };

  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor1',
    pages: [page],
  };

  let addToolbarActionCalled: boolean = false;
  const addToolbarActionSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: addToolbarActionMutation,
      variables: addToolbarActionVariablesForPage,
    },
    result: () => {
      addToolbarActionCalled = true;
      return { data: addToolbarActionSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [addToolbarActionSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <ToolbarActions
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        toolbarActions={page.toolbarActions}
        containerId={page.id}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId(`ToolbarActions-NewAction-${page.id}`);
  element.click();

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(addToolbarActionCalled).toBeTruthy();
    });
  });
});
