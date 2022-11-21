/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { GQLToolbarAction } from '@eclipse-sirius/sirius-components-forms';
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
  GQLAddToolbarActionSuccessPayload,
  GQLDeleteToolbarActionMutationData,
  GQLDeleteToolbarActionMutationVariables,
  GQLDeleteToolbarActionSuccessPayload,
  GQLFormDescriptionEditor,
  GQLMoveToolbarActionMutationData,
  GQLMoveToolbarActionMutationVariables,
  GQLMoveToolbarActionSuccessPayload,
} from '../FormDescriptionEditorEventFragment.types';
import { ToolbarActions } from '../ToolbarActions';
import { DataTransfer } from './DataTransfer';

vi.mock('uuid', () => ({ v4: () => '48be95fc-3422-45d3-b1f9-d590e847e9e1' }));

afterEach(() => cleanup());

const emptySelection: Selection = {
  entries: [],
};

const emptySetSelection = (_: Selection) => {};

const addToolbarActionVariables: GQLAddToolbarActionMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    containerId: null,
  },
};
const addToolbarActionSuccessPayload: GQLAddToolbarActionSuccessPayload = {
  __typename: 'AddToolbarActionSuccessPayload',
  id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
};
const addToolbarActionSuccessData: GQLAddToolbarActionMutationData = {
  addToolbarAction: addToolbarActionSuccessPayload,
};

const deleteToolbarActionVariables: GQLDeleteToolbarActionMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    toolbarActionId: 'ToolbarAction1',
  },
};
const deleteToolbarActionSuccessPayload: GQLDeleteToolbarActionSuccessPayload = {
  __typename: 'DeleteToolbarActionSuccessPayload',
  id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
};
const deleteToolbarActionSuccessData: GQLDeleteToolbarActionMutationData = {
  deleteToolbarAction: deleteToolbarActionSuccessPayload,
};

const moveToolbarActionVariables: GQLMoveToolbarActionMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    toolbarActionId: 'ToolbarAction2',
    containerId: null,
    index: 0,
  },
};
const moveToolbarActionSuccessPayload: GQLMoveToolbarActionSuccessPayload = {
  __typename: 'MoveToolbarActionSuccessPayload',
  id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
};
const moveToolbarActionSuccessData: GQLMoveToolbarActionMutationData = {
  moveToolbarAction: moveToolbarActionSuccessPayload,
};

const moveToolbarActionAtTheEndVariables: GQLMoveToolbarActionMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    toolbarActionId: 'ToolbarAction2',
    containerId: null,
    index: 1,
  },
};
const moveToolbarActionAtTheEndSuccessPayload: GQLMoveToolbarActionSuccessPayload = {
  __typename: 'MoveToolbarActionSuccessPayload',
  id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
};
const moveToolbarActionAtTheEndSuccessData: GQLMoveToolbarActionMutationData = {
  moveToolbarAction: moveToolbarActionAtTheEndSuccessPayload,
};

test('add ToolbarAction by clicking on the Add Toolbar Action button', async () => {
  const toolbarAction: GQLToolbarAction = {
    id: 'ToolbarAction1',
    label: 'ToolbarAction1',
    iconURL: null,
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
  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor1',
    metadata: {
      id: 'FormDescriptionEditor1',
      description: { id: 'FormDescriptionEditorRepresentation' },
      kind: 'FormDescriptionEditor',
      label: 'FormDescriptionEditor1',
    },
    toolbarActions: [toolbarAction],
    widgets: [],
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
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId('FormDescriptionEditor-ToolbarActions-NewAction');
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
    iconURL: null,
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
    iconURL: null,
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
  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor1',
    metadata: {
      id: 'FormDescriptionEditor1',
      description: { id: 'FormDescriptionEditorRepresentation' },
      kind: 'FormDescriptionEditor',
      label: 'FormDescriptionEditor1',
    },
    toolbarActions: [toolbarAction1, toolbarAction2],
    widgets: [],
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
    iconURL: null,
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
    iconURL: null,
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
  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor1',
    metadata: {
      id: 'FormDescriptionEditor1',
      description: { id: 'FormDescriptionEditorRepresentation' },
      kind: 'FormDescriptionEditor',
      label: 'FormDescriptionEditor1',
    },
    toolbarActions: [toolbarAction1, toolbarAction2],
    widgets: [],
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
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId('ToolbarAction-DropArea-ToolbarAction1');

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('text/plain', 'ToolbarAction2');
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
    iconURL: null,
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
    iconURL: null,
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
  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor1',
    metadata: {
      id: 'FormDescriptionEditor1',
      description: { id: 'FormDescriptionEditorRepresentation' },
      kind: 'FormDescriptionEditor',
      label: 'FormDescriptionEditor1',
    },
    toolbarActions: [toolbarAction1, toolbarAction2],
    widgets: [],
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
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId('FormDescriptionEditor-ToolbarActions-DropArea');

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('text/plain', 'ToolbarAction2');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(moveToolbarActionAtTheEndCalled).toBeTruthy();
    });
  });
});
