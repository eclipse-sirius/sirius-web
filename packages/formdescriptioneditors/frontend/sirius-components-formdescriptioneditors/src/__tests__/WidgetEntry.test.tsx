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
import { act, cleanup, fireEvent, render, screen, waitFor } from '@testing-library/react';
import { afterEach, expect, test, vi } from 'vitest';
import { addWidgetMutation, deleteWidgetMutation, moveWidgetMutation } from '../FormDescriptionEditorEventFragment';
import {
  GQLAddWidgetMutationData,
  GQLAddWidgetMutationVariables,
  GQLAddWidgetSuccessPayload,
  GQLDeleteWidgetMutationData,
  GQLDeleteWidgetMutationVariables,
  GQLDeleteWidgetSuccessPayload,
  GQLFormDescriptionEditor,
  GQLFormDescriptionEditorWidget,
  GQLMoveWidgetMutationData,
  GQLMoveWidgetMutationVariables,
  GQLMoveWidgetSuccessPayload,
} from '../FormDescriptionEditorEventFragment.types';
import { WidgetEntry } from '../WidgetEntry';
import { DataTransfer } from './DataTransfer';

vi.mock('uuid', () => ({ v4: () => '48be95fc-3422-45d3-b1f9-d590e847e9e1' }));

afterEach(() => cleanup());

const emptySelection: Selection = {
  entries: [],
};

const emptySetSelection = (_: Selection) => {};

const addWidgetVariables: GQLAddWidgetMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    kind: 'Textfield',
    index: 0,
  },
};
const addWidgetSuccessPayload: GQLAddWidgetSuccessPayload = {
  __typename: 'AddWidgetSuccessPayload',
  id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
};
const addWidgetSuccessData: GQLAddWidgetMutationData = { addWidget: addWidgetSuccessPayload };

const deleteTextfieldWidgetVariables: GQLDeleteWidgetMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    widgetId: 'Textfield1',
  },
};
const deletePieChartWidgetVariables: GQLDeleteWidgetMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    widgetId: 'PieChart1',
  },
};
const deleteWidgetSuccessPayload: GQLDeleteWidgetSuccessPayload = {
  __typename: 'AddWidgetSuccessPayload',
  id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
};
const deleteWidgetSuccessData: GQLDeleteWidgetMutationData = { deleteWidget: deleteWidgetSuccessPayload };

const moveWidgetVariables: GQLMoveWidgetMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    widgetId: 'Textfield2',
    index: 0,
  },
};
const moveWidgetSuccessPayload: GQLMoveWidgetSuccessPayload = {
  __typename: 'MoveWidgetSuccessPayload',
  id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
};
const moveWidgetSuccessData: GQLMoveWidgetMutationData = { moveWidget: moveWidgetSuccessPayload };

test('should drop the Textfield in the drop area', async () => {
  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor',
    widgets: [{ id: 'TextfieldDescription', kind: 'TextfieldDescription', label: 'Textfield1' }],
    metadata: {
      id: 'FormDescriptionEditor',
      kind: 'FormDescriptionEditor',
      label: 'FormDescriptionEditor',
      description: { id: 'FormDescriptionEditorDescription' },
    },
  };

  const textfieldWidget: GQLFormDescriptionEditorWidget = {
    id: 'Textfield1',
    label: 'Textfield1',
    kind: 'Textfield',
  };

  let addWidgetCalled: boolean = false;
  const addWidgetSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: addWidgetMutation,
      variables: addWidgetVariables,
    },
    result: () => {
      addWidgetCalled = true;
      return { data: addWidgetSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [addWidgetSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <WidgetEntry
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        widget={textfieldWidget}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId('WidgetEntry-DropArea');

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('text/plain', 'Textfield');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(addWidgetCalled).toBeTruthy();
    });
  });
});

test('should delete the Textfield from the drop area', async () => {
  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor',
    widgets: [{ id: 'TextfieldDescription', kind: 'TextfieldDescription', label: 'Textfield1' }],
    metadata: {
      id: 'FormDescriptionEditor',
      kind: 'FormDescriptionEditor',
      label: 'FormDescriptionEditor',
      description: { id: 'FormDescriptionEditorDescription' },
    },
  };

  const textfieldWidget: GQLFormDescriptionEditorWidget = {
    id: 'Textfield1',
    label: 'Textfield1',
    kind: 'Textfield',
  };

  let deleteWidgetCalled: boolean = false;
  const deleteWidgetSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: deleteWidgetMutation,
      variables: deleteTextfieldWidgetVariables,
    },
    result: () => {
      deleteWidgetCalled = true;
      return { data: deleteWidgetSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [deleteWidgetSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <WidgetEntry
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        widget={textfieldWidget}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const textfield1: HTMLElement = screen.getByTestId('Textfield1');
  expect(textfield1).not.toBeUndefined();

  await act(async () => {
    textfield1.focus();
    fireEvent.keyDown(textfield1, { key: 'Delete', code: 'NumpadDecimal' });
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(deleteWidgetCalled).toBeTruthy();
    });
  });
});

test('should delete the PieChart from the drop area', async () => {
  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor',
    widgets: [{ id: 'PieChartDescription', kind: 'PieChartDescription', label: 'PieChart1' }],
    metadata: {
      id: 'FormDescriptionEditor',
      kind: 'FormDescriptionEditor',
      label: 'FormDescriptionEditor',
      description: { id: 'FormDescriptionEditorDescription' },
    },
  };

  const pieChartWidget: GQLFormDescriptionEditorWidget = {
    id: 'PieChart1',
    label: 'PieChart1',
    kind: 'PieChart',
  };

  let deleteWidgetCalled: boolean = false;
  const deleteWidgetSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: deleteWidgetMutation,
      variables: deletePieChartWidgetVariables,
    },
    result: () => {
      deleteWidgetCalled = true;
      return { data: deleteWidgetSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [deleteWidgetSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <WidgetEntry
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        widget={pieChartWidget}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const pieChart1: HTMLElement = screen.getByTestId('PieChart1');
  expect(pieChart1).not.toBeUndefined();
  await act(async () => {
    pieChart1.focus();
    fireEvent.keyDown(pieChart1, { key: 'Delete', code: 'NumpadDecimal' });
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(deleteWidgetCalled).toBeTruthy();
    });
  });
});

test('should move the existing Textfield from/into the drop area', async () => {
  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor',
    widgets: [
      { id: 'Textfield1', kind: 'TextfieldDescription', label: 'Textfield1' },
      { id: 'Textfield2', kind: 'TextfieldDescription', label: 'Textfield2' },
    ],
    metadata: {
      id: 'FormDescriptionEditor',
      kind: 'FormDescriptionEditor',
      label: 'FormDescriptionEditor',
      description: { id: 'FormDescriptionEditorDescription' },
    },
  };

  const textfieldWidget: GQLFormDescriptionEditorWidget = {
    id: 'Textfield1',
    label: 'Textfield1',
    kind: 'TextfieldDescription',
  };

  let moveWidgetCalled: boolean = false;
  const moveWidgetSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: moveWidgetMutation,
      variables: moveWidgetVariables,
    },
    result: () => {
      moveWidgetCalled = true;
      return { data: moveWidgetSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [moveWidgetSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <WidgetEntry
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        widget={textfieldWidget}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId('WidgetEntry-DropArea');

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('text/plain', 'Textfield2');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(moveWidgetCalled).toBeTruthy();
    });
  });
});
