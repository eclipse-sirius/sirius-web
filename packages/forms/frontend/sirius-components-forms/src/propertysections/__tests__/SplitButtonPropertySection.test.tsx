/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { MessageOptions, ServerContext, ToastContext, ToastContextValue } from '@eclipse-sirius/sirius-components-core';
import { act, cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';
import { afterEach, expect, test, vi } from 'vitest';
import { GQLButton, GQLSplitButton } from '../../form/FormEventFragments.types';
import { pushButtonMutation } from '../ButtonPropertySection';
import {
  GQLErrorPayload,
  GQLPushButtonMutationData,
  GQLPushButtonMutationVariables,
  GQLSuccessPayload,
} from '../ButtonPropertySection.types';
import { SplitButtonPropertySection } from '../SplitButtonPropertySection';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const defaultButton: GQLButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  buttonLabel: 'ButtonLabel',
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
  readOnly: false,
};

const defaultSplitButton: GQLSplitButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  readOnly: false,
  actions: [defaultButton, defaultButton],
};

const buttonWithStyle: GQLButton = {
  ...defaultButton,
  style: {
    backgroundColor: '#de1000',
    foregroundColor: '#fbb800',
    fontSize: 14,
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
  },
};

const readOnlyButton: GQLButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: [],
  diagnostics: [],
  buttonLabel: 'ButtonLabel',
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
  readOnly: true,
  hasHelpText: false,
};

const splitButtonWithStyle: GQLSplitButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  readOnly: false,
  actions: [buttonWithStyle, buttonWithStyle],
};

const splitButtonWithReadOnlyAction: GQLSplitButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  readOnly: false,
  actions: [readOnlyButton, readOnlyButton],
};

const pushButtonVariables: GQLPushButtonMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    buttonId: 'buttonId',
  },
};
const successPayload: GQLSuccessPayload = { __typename: 'SuccessPayload', messages: [] };
const pushButtonSuccessData: GQLPushButtonMutationData = { pushButton: successPayload };

const pushButtonErrorPayload: GQLErrorPayload = {
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
const pushButtonErrorData: GQLPushButtonMutationData = {
  pushButton: pushButtonErrorPayload,
};

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

test('should render the split button and his actions', () => {
  const { container } = render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ToastContext.Provider value={toastContextMock}>
          <SplitButtonPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={defaultSplitButton}
            readOnly={false}
          />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );
  screen.getByRole('show-actions').click();
  expect(container).toMatchSnapshot();
});

test('should render a readOnly button and his actions', () => {
  const { container } = render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ToastContext.Provider value={toastContextMock}>
          <SplitButtonPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={defaultSplitButton}
            readOnly
          />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );
  screen.getByRole('show-actions').click();
  expect(container).toMatchSnapshot();
});

test('should send mutation when clicked', async () => {
  let pushButtonCalled = false;
  const pushButtonSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: pushButtonMutation,
      variables: pushButtonVariables,
    },
    result: () => {
      pushButtonCalled = true;
      return { data: pushButtonSuccessData };
    },
  };

  const mocks = [pushButtonSuccessMock];
  const { container } = render(
    <MockedProvider mocks={mocks}>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ToastContext.Provider value={toastContextMock}>
          <SplitButtonPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={defaultSplitButton}
            readOnly={false}
          />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();

  const element: HTMLElement = screen.getByTestId('Label');

  await act(async () => {
    userEvent.click(element);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(pushButtonCalled).toBeTruthy();
      expect(container).toMatchSnapshot();
    });
  });
});

test('should display the error received', async () => {
  let pushButtonCalled = false;
  const pushButtonErrorMock: MockedResponse<Record<string, any>> = {
    request: {
      query: pushButtonMutation,
      variables: pushButtonVariables,
    },
    result: () => {
      pushButtonCalled = true;
      return { data: pushButtonErrorData };
    },
  };

  const mocks = [pushButtonErrorMock];
  const { baseElement } = render(
    <MockedProvider mocks={mocks}>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ToastContext.Provider value={toastContextMock}>
          <SplitButtonPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={defaultSplitButton}
            readOnly={false}
          />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();

  const element: HTMLElement = screen.getByTestId('Label');

  await act(async () => {
    userEvent.click(element);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(pushButtonCalled).toBeTruthy();
      expect(mockEnqueue).toHaveBeenCalledTimes(2);
      expect(baseElement).toMatchSnapshot();
    });
  });
});

test('should render the button and his actions with style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ToastContext.Provider value={toastContextMock}>
          <SplitButtonPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={splitButtonWithStyle}
            readOnly={false}
          />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );
  screen.getByRole('show-actions').click();
  expect(baseElement).toMatchSnapshot();
});

test('should render the button with help hint', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        {' '}
        <ToastContext.Provider value={toastContextMock}>
          <SplitButtonPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={{ ...defaultSplitButton, hasHelpText: true }}
            readOnly={false}
          />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});
