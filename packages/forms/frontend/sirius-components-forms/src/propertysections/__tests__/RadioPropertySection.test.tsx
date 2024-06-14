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
import { MessageOptions, ToastContext, ToastContextValue } from '@eclipse-sirius/sirius-components-core';
import { act, cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { GQLRadio } from '../../form/FormEventFragments.types';
import {
  RadioPropertySection,
  editRadioMutation,
  updateWidgetFocusMutation,
} from '../../propertysections/RadioPropertySection';
import {
  GQLEditRadioMutationData,
  GQLEditRadioMutationVariables,
  GQLErrorPayload,
  GQLSuccessPayload,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusSuccessPayload,
} from '../../propertysections/RadioPropertySection.types';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const defaultRadio: GQLRadio = {
  __typename: 'Radio',
  id: 'radioId',
  label: 'Status:',
  iconURL: [],
  hasHelpText: false,
  readOnly: false,
  diagnostics: [],
  options: [
    {
      id: '0',
      label: 'inactive',
      selected: true,
    },
    {
      id: '1',
      label: 'active',
      selected: false,
    },
  ],
  style: null,
};

const radioWithStyle: GQLRadio = {
  __typename: 'Radio',
  id: 'radioId',
  label: 'Status:',
  iconURL: [],
  hasHelpText: false,
  readOnly: false,
  diagnostics: [],
  options: [
    {
      id: '0',
      label: 'inactive',
      selected: true,
    },
    {
      id: '1',
      label: 'active',
      selected: false,
    },
  ],
  style: {
    color: '#de1000',
    fontSize: 20,
    italic: true,
    bold: true,
    underline: true,
    strikeThrough: true,
  },
};

const radioWithEmptyStyle: GQLRadio = {
  __typename: 'Radio',
  id: 'radioId',
  label: 'Status:',
  iconURL: [],
  hasHelpText: false,
  readOnly: false,
  diagnostics: [],
  options: [
    {
      id: '0',
      label: 'inactive',
      selected: true,
    },
    {
      id: '1',
      label: 'active',
      selected: false,
    },
  ],
  style: {
    color: '',
    fontSize: 14,
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
  },
};

const readOnlyRadio: GQLRadio = {
  __typename: 'Radio',
  id: 'radioId',
  label: 'Status:',
  iconURL: [],
  diagnostics: [],
  options: [
    {
      id: '0',
      label: 'inactive',
      selected: true,
    },
    {
      id: '1',
      label: 'active',
      selected: false,
    },
  ],
  style: null,
  readOnly: true,
  hasHelpText: false,
};

const editRadioVariables: GQLEditRadioMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    radioId: 'radioId',
    newValue: '1',
  },
};
const successPayload: GQLSuccessPayload = { messages: [], __typename: 'SuccessPayload' };
const editRadioSuccessData: GQLEditRadioMutationData = { editRadio: successPayload };

const editRadioErrorPayload: GQLErrorPayload = {
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
const editRadioErrorData: GQLEditRadioMutationData = {
  editRadio: editRadioErrorPayload,
};

const updateWidgetFocusVariables: GQLUpdateWidgetFocusMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    widgetId: 'radioId',
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

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

test('should render the radio', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultRadio}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should render a readOnly radio', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultRadio}
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

  let editRadioCalled = false;
  const editRadioSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: editRadioMutation,
      variables: editRadioVariables,
    },
    result: () => {
      editRadioCalled = true;
      return { data: editRadioSuccessData };
    },
  };

  const mocks = [updateWidgetFocusSuccessMock, editRadioSuccessMock];
  const { container } = render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultRadio}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();

  const inactive: HTMLInputElement = screen.getByTestId('inactive').querySelector('input');
  expect(inactive.checked).toBe(true);

  const active: HTMLInputElement = screen.getByTestId('active').querySelector('input');
  expect(active.checked).toBe(false);

  await act(async () => {
    userEvent.click(active);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(updateWidgetFocusCalled).toBeTruthy();
      expect(editRadioCalled).toBeTruthy();
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

  let editRadioCalled = false;
  const editRadioErrorMock: MockedResponse<Record<string, any>> = {
    request: {
      query: editRadioMutation,
      variables: editRadioVariables,
    },
    result: () => {
      editRadioCalled = true;
      return { data: editRadioErrorData };
    },
  };

  const mocks = [updateWidgetFocusErrorMock, editRadioErrorMock];
  const { baseElement } = render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultRadio}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();

  const inactive: HTMLInputElement = screen.getByTestId('inactive').querySelector('input');
  expect(inactive.checked).toBe(true);

  const active: HTMLInputElement = screen.getByTestId('active').querySelector('input');
  expect(active.checked).toBe(false);

  await act(async () => {
    userEvent.click(active);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(updateWidgetFocusCalled).toBeTruthy();
      expect(editRadioCalled).toBeTruthy();
      expect(mockEnqueue).toHaveBeenCalledTimes(3);
      expect(baseElement).toMatchSnapshot();
    });
  });
});

test('should render the radio without style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultRadio}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the radio with style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={radioWithStyle}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the radio with empty style', async () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={radioWithEmptyStyle}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the radio with help hint', async () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={{ ...defaultRadio, hasHelpText: true }}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render a readOnly radio from widget properties', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={readOnlyRadio}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
