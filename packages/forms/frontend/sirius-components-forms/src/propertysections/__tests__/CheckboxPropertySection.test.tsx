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
import { ToastContext, ToastContextValue } from '@eclipse-sirius/sirius-components-core';
import { act, cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { GQLCheckbox } from '../../form/FormEventFragments.types';
import { CheckboxPropertySection, editCheckboxMutation, updateWidgetFocusMutation } from '../CheckboxPropertySection';
import {
  GQLEditCheckboxMutationData,
  GQLEditCheckboxMutationVariables,
  GQLErrorPayload,
  GQLSuccessPayload,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusSuccessPayload,
} from '../CheckboxPropertySection.types';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const defaultCheckbox: GQLCheckbox = {
  __typename: 'Checkbox',
  id: 'checkboxId',
  label: 'CheckboxLabel',
  iconURL: null,
  hasHelpText: false,
  diagnostics: [],
  booleanValue: false,
  style: null,
};

const checkboxWithStyle: GQLCheckbox = {
  __typename: 'Checkbox',
  id: 'checkboxId',
  label: 'CheckboxLabel',
  iconURL: null,
  hasHelpText: false,
  diagnostics: [],
  booleanValue: false,
  style: {
    color: '#de1000',
  },
};

const checkboxWithEmptyStyle: GQLCheckbox = {
  __typename: 'Checkbox',
  id: 'checkboxId',
  label: 'CheckboxLabel',
  iconURL: null,
  hasHelpText: false,
  diagnostics: [],
  booleanValue: false,
  style: {
    color: '',
  },
};

const editCheckboxVariables: GQLEditCheckboxMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    checkboxId: 'checkboxId',
    newValue: true,
  },
};
const successPayload: GQLSuccessPayload = { messages: [], __typename: 'SuccessPayload' };
const editCheckboxSuccessData: GQLEditCheckboxMutationData = { editCheckbox: successPayload };

const editCheckboxErrorPayload: GQLErrorPayload = {
  __typename: 'ErrorPayload',
  messages: [
    {
      body: 'An error has occurred, please refresh the page',
      level: 'ERROR',
    },
    {
      body: 'FeedbackMessage',
      level: 'INFO',
    },
  ],
};
const editCheckboxErrorData: GQLEditCheckboxMutationData = {
  editCheckbox: editCheckboxErrorPayload,
};

const updateWidgetFocusVariables: GQLUpdateWidgetFocusMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    widgetId: 'checkboxId',
    selected: true,
  },
};
const updateWidgetFocusSuccessPayload: GQLUpdateWidgetFocusSuccessPayload = {
  __typename: 'UpdateWidgetFocusSuccessPayload',
};
const updateWidgetFocusSuccessData: GQLUpdateWidgetFocusMutationData = {
  updateWidgetFocus: updateWidgetFocusSuccessPayload,
};

const updateWidgetFocusErrorPayload: GQLErrorPayload = {
  __typename: 'ErrorPayload',
  messages: [
    {
      body: 'An error has occurred, please refresh the page',
      level: 'ERROR',
    },
  ],
};
const updateWidgetFocusErrorData: GQLUpdateWidgetFocusMutationData = {
  updateWidgetFocus: updateWidgetFocusErrorPayload,
};

const mockEnqueue = vi.fn();

const toastContextMock: ToastContextValue = {
  useToast: () => {
    return {
      enqueueSnackbar: mockEnqueue,
      closeSnackbar: () => {},
    };
  },
};

test('should render the checkbox', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultCheckbox}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should render a readOnly checkbox', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultCheckbox}
          subscribers={[]}
          readOnly
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should send mutation when clicked', async () => {
  let updateWidgetFocusCalled = false;
  const updateWidgetFocusSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: updateWidgetFocusMutation,
      variables: updateWidgetFocusVariables,
    },
    result: () => {
      updateWidgetFocusCalled = true;
      return { data: updateWidgetFocusSuccessData };
    },
  };

  let editCheckboxCalled = false;
  const editCheckboxSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: editCheckboxMutation,
      variables: editCheckboxVariables,
    },
    result: () => {
      editCheckboxCalled = true;
      return { data: editCheckboxSuccessData };
    },
  };

  const mocks = [updateWidgetFocusSuccessMock, editCheckboxSuccessMock];
  const { container } = render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultCheckbox}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();

  const element: HTMLInputElement = screen.getByTestId('CheckboxLabel').querySelector('input');
  expect(element.checked).toBe(false);

  await act(async () => {
    userEvent.click(element);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(updateWidgetFocusCalled).toBeTruthy();
      expect(editCheckboxCalled).toBeTruthy();
      expect(container).toMatchSnapshot();
    });
  });
});

test('should display the error received', async () => {
  let updateWidgetFocusCalled = false;
  const updateWidgetFocusErrorMock: MockedResponse<Record<string, any>> = {
    request: {
      query: updateWidgetFocusMutation,
      variables: updateWidgetFocusVariables,
    },
    result: () => {
      updateWidgetFocusCalled = true;
      return { data: updateWidgetFocusErrorData };
    },
  };

  let editCheckboxCalled = false;
  const editCheckboxErrorMock: MockedResponse<Record<string, any>> = {
    request: {
      query: editCheckboxMutation,
      variables: editCheckboxVariables,
    },
    result: () => {
      editCheckboxCalled = true;
      return { data: editCheckboxErrorData };
    },
  };

  const mocks = [updateWidgetFocusErrorMock, editCheckboxErrorMock];
  const { baseElement } = render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultCheckbox}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();

  const element: HTMLInputElement = screen.getByTestId('CheckboxLabel').querySelector('input');
  expect(element.checked).toBe(false);

  await act(async () => {
    userEvent.click(element);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(updateWidgetFocusCalled).toBeTruthy();
      expect(editCheckboxCalled).toBeTruthy();
      expect(mockEnqueue).toHaveBeenCalledTimes(3);
      expect(baseElement).toMatchSnapshot();
    });
  });
});

test('should render the checkbox without style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultCheckbox}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the checkbox with style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={checkboxWithStyle}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the checkbox with empty style', async () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={checkboxWithEmptyStyle}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render a checkbox with help hint', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={{ ...defaultCheckbox, hasHelpText: true }}
          subscribers={[]}
          readOnly
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
