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
import { MessageOptions, ToastContext, ToastContextValue } from '@eclipse-sirius/sirius-components-core';
import { act, cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';
import { afterEach, expect, test, vi } from 'vitest';
import { GQLCheckbox } from '../../form/FormEventFragments.types';
import { CheckboxPropertySection, editCheckboxMutation } from '../CheckboxPropertySection';
import {
  GQLEditCheckboxMutationData,
  GQLEditCheckboxMutationVariables,
  GQLErrorPayload,
  GQLSuccessPayload,
} from '../CheckboxPropertySection.types';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const defaultCheckbox = {
  __typename: 'Checkbox',
  id: 'checkboxId',
  label: 'CheckboxLabel',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  booleanValue: false,
  readOnly: false,
};

const checkboxWithStyle: GQLCheckbox = {
  ...defaultCheckbox,
  style: {
    color: '#de1000',
    labelPlacement: 'top',
  },
};

const checkboxWithEmptyStyle: GQLCheckbox = {
  ...defaultCheckbox,
  style: {
    color: '',
    labelPlacement: 'end',
  },
};

const readOnlyCheckbox: GQLCheckbox = {
  ...checkboxWithEmptyStyle,
  readOnly: true,
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

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

test('should render the checkbox', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={checkboxWithStyle}
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
          widget={readOnlyCheckbox}
          readOnly
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should send mutation when clicked', async () => {
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

  const mocks = [editCheckboxSuccessMock];
  const { container } = render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={checkboxWithStyle}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();

  const element: HTMLInputElement | null = screen.getByTestId('CheckboxLabel').querySelector('input');
  expect(element && element.checked).toBe(false);

  await act(async () => {
    if (element) {
      userEvent.click(element);
      await new Promise((resolve) => setTimeout(resolve, 0));

      await waitFor(() => {
        expect(editCheckboxCalled).toBeTruthy();
        expect(container).toMatchSnapshot();
      });
    }
  });
});

test('should display the error received', async () => {
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

  const mocks = [editCheckboxErrorMock];
  const { baseElement } = render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={checkboxWithStyle}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();

  const element: HTMLInputElement | null = screen.getByTestId('CheckboxLabel').querySelector('input');
  expect(element && element.checked).toBe(false);

  await act(async () => {
    if (element) {
      userEvent.click(element);
      await new Promise((resolve) => setTimeout(resolve, 0));

      await waitFor(() => {
        expect(editCheckboxCalled).toBeTruthy();
        expect(mockEnqueue).toHaveBeenCalledTimes(2);
        expect(baseElement).toMatchSnapshot();
      });
    }
  });
});

test('should render the checkbox without style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={checkboxWithEmptyStyle}
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
          widget={{ ...checkboxWithEmptyStyle, hasHelpText: true }}
          readOnly
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should render a readOnly checkbox from widget properties', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={readOnlyCheckbox}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
