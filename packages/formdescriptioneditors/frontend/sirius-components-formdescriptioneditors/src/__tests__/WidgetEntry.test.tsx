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
import { MockedProvider, MockedResponse } from '@apollo/client/testing';
import { ConfirmationDialogContext, Selection, SelectionContext } from '@eclipse-sirius/sirius-components-core';
import {
  GQLChartWidget,
  GQLContainerBorderStyle,
  GQLGroup,
  GQLPage,
  GQLPieChart,
  GQLTextfield,
} from '@eclipse-sirius/sirius-components-forms';
import { act, cleanup, fireEvent, render, screen, waitFor } from '@testing-library/react';
import { afterEach, expect, test, vi } from 'vitest';
import { addWidgetMutation, deleteWidgetMutation, moveWidgetMutation } from '../FormDescriptionEditorEventFragment';
import {
  GQLAddWidgetMutationData,
  GQLAddWidgetMutationVariables,
  GQLDeleteWidgetMutationData,
  GQLDeleteWidgetMutationVariables,
  GQLFormDescriptionEditor,
  GQLMoveWidgetMutationData,
  GQLMoveWidgetMutationVariables,
  GQLSuccessPayload,
} from '../FormDescriptionEditorEventFragment.types';
import { WidgetEntry } from '../WidgetEntry';
import { FormDescriptionEditorContext } from '../hooks/FormDescriptionEditorContext';
import { DataTransfer } from './DataTransfer';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const successPayload: GQLSuccessPayload = {
  __typename: 'SuccessPayload',
  id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
};

const addWidgetVariables: GQLAddWidgetMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    containerId: 'group1',
    kind: 'Textfield',
    index: 0,
  },
};

const addWidgetSuccessData: GQLAddWidgetMutationData = { addWidget: successPayload };

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
    widgetId: 'ChartWidget1',
  },
};

const deleteWidgetSuccessData: GQLDeleteWidgetMutationData = { deleteWidget: successPayload };

const moveWidgetVariables: GQLMoveWidgetMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    widgetId: 'Textfield2',
    containerId: 'group1',
    index: 0,
  },
};

const moveWidgetSuccessData: GQLMoveWidgetMutationData = { moveWidget: successPayload };

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
    </MockedProvider>
  );
};

test('should drop the Textfield in the drop area', async () => {
  const textfieldWidget: GQLTextfield = {
    id: 'Textfield1',
    label: 'Textfield1',
    iconURL: [],
    hasHelpText: false,
    __typename: 'Textfield',
    diagnostics: [],
    stringValue: '',
    style: {
      backgroundColor: null,
      foregroundColor: null,
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
    },
    supportsCompletion: false,
    readOnly: false,
  };

  const containerBorderStyle: GQLContainerBorderStyle = {
    color: null,
    lineStyle: '',
    radius: 0,
    size: 0,
  };

  const group: GQLGroup = {
    id: 'group1',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'group1',
    widgets: [textfieldWidget],
    toolbarActions: [],
    borderStyle: containerBorderStyle,
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
    <TestContextProvider mocks={mocks} formDescriptionEditor={formDescriptionEditor}>
      <WidgetEntry page={page} container={group} flexDirection={'row'} flexGrow={0} widget={textfieldWidget} />
    </TestContextProvider>
  );

  const element: HTMLElement = screen.getByTestId(`WidgetEntry-DropArea-${textfieldWidget.id}`);

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('draggedElementId', 'Textfield');
  dataTransfer.setData('draggedElementType', 'Widget');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(addWidgetCalled).toBeTruthy();
    });
  });
});

test('should delete the Textfield from the drop area', async () => {
  const textfieldWidget: GQLTextfield = {
    id: 'Textfield1',
    label: 'Textfield1',
    iconURL: [],
    hasHelpText: false,
    __typename: 'Textfield',
    diagnostics: [],
    stringValue: '',
    style: {
      backgroundColor: null,
      foregroundColor: null,
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
    },
    supportsCompletion: false,
    readOnly: false,
  };

  const containerBorderStyle: GQLContainerBorderStyle = {
    color: null,
    lineStyle: '',
    radius: 0,
    size: 0,
  };

  const group: GQLGroup = {
    id: 'group1',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'group1',
    widgets: [textfieldWidget],
    toolbarActions: [],
    borderStyle: containerBorderStyle,
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
    <TestContextProvider mocks={mocks} formDescriptionEditor={formDescriptionEditor}>
      <ConfirmationDialogContext.Provider
        value={{
          showConfirmation,
        }}>
        <WidgetEntry page={page} container={group} flexDirection={'row'} flexGrow={0} widget={textfieldWidget} />
      </ConfirmationDialogContext.Provider>
    </TestContextProvider>
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
  const pieChart: GQLPieChart = {
    metadata: {
      description: { id: 'PieChartDescription' },
      id: 'PieChart1',
      kind: 'PieChart',
      label: 'PieChart1',
    },
    label: 'PieChart1',
    entries: [{ key: 'entry1', value: 1 }],
    style: {
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
      colors: ['blue'],
      strokeColor: null,
      strokeWidth: null,
    },
  };
  const pieChartWidget: GQLChartWidget = {
    id: 'ChartWidget1',
    label: 'PieChart1',
    iconURL: [],
    hasHelpText: false,
    __typename: 'ChartWidget',
    diagnostics: [],
    chart: pieChart,
    readOnly: false,
  };

  const containerBorderStyle: GQLContainerBorderStyle = {
    color: null,
    lineStyle: '',
    radius: 0,
    size: 0,
  };

  const group: GQLGroup = {
    id: 'group1',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'group1',
    widgets: [pieChartWidget],
    toolbarActions: [],
    borderStyle: containerBorderStyle,
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
    <TestContextProvider mocks={mocks} formDescriptionEditor={formDescriptionEditor}>
      <ConfirmationDialogContext.Provider
        value={{
          showConfirmation,
        }}>
        <WidgetEntry page={page} container={group} flexDirection={'row'} flexGrow={0} widget={pieChartWidget} />
      </ConfirmationDialogContext.Provider>
    </TestContextProvider>
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
  const textfieldWidget: GQLTextfield = {
    id: 'Textfield1',
    label: 'Textfield1',
    iconURL: [],
    hasHelpText: false,
    __typename: 'Textfield',
    diagnostics: [],
    stringValue: '',
    style: {
      backgroundColor: null,
      foregroundColor: null,
      fontSize: null,
      italic: null,
      bold: null,
      underline: null,
      strikeThrough: null,
    },
    supportsCompletion: false,
    readOnly: false,
  };

  const containerBorderStyle: GQLContainerBorderStyle = {
    color: null,
    lineStyle: '',
    radius: 0,
    size: 0,
  };

  const group: GQLGroup = {
    id: 'group1',
    displayMode: 'LIST',
    __typename: 'Group',
    label: 'group1',
    widgets: [textfieldWidget],
    toolbarActions: [],
    borderStyle: containerBorderStyle,
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
    <TestContextProvider mocks={mocks} formDescriptionEditor={formDescriptionEditor}>
      <WidgetEntry page={page} container={group} flexDirection={'row'} flexGrow={0} widget={textfieldWidget} />
    </TestContextProvider>
  );

  const element: HTMLElement = screen.getByTestId(`WidgetEntry-DropArea-${textfieldWidget.id}`);

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('draggedElementId', 'Textfield2');
  dataTransfer.setData('draggedElementType', 'Widget');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(moveWidgetCalled).toBeTruthy();
    });
  });
});
