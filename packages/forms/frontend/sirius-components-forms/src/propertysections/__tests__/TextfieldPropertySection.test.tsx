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
import { GQLTextfield } from '../../form/FormEventFragments.types';
import {
  TextfieldPropertySection,
  editTextfieldMutation,
  getCompletionProposalsQuery,
} from '../../propertysections/TextfieldPropertySection';
import {
  GQLCompletionProposalsQueryData,
  GQLCompletionProposalsQueryVariables,
  GQLEditTextfieldMutationData,
  GQLEditTextfieldMutationVariables,
  GQLSuccessPayload,
} from '../../propertysections/TextfieldPropertySection.types';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const defaultTextField: GQLTextfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name:',
  iconURL: [],
  hasHelpText: false,
  stringValue: 'Composite Processor',
  supportsCompletion: false,
  diagnostics: [],
  readOnly: false,
  style: {
    backgroundColor: '',
    foregroundColor: '',
    fontSize: 14,
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
  },
};

const textFieldWithStyle: GQLTextfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name:',
  iconURL: [],
  hasHelpText: false,
  stringValue: 'Composite Processor',
  supportsCompletion: false,
  diagnostics: [],
  readOnly: false,
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

const textFieldWithEmptyStyle: GQLTextfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name:',
  iconURL: [],
  hasHelpText: false,
  stringValue: 'Composite Processor',
  supportsCompletion: false,
  readOnly: false,
  diagnostics: [],
  style: {
    backgroundColor: '',
    foregroundColor: '',
    fontSize: 14,
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
  },
};

const readOnlyTextField: GQLTextfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name:',
  iconURL: [],
  stringValue: 'Composite Processor',
  supportsCompletion: false,
  diagnostics: [],
  hasHelpText: false,
  style: {
    backgroundColor: '',
    foregroundColor: '',
    fontSize: 14,
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
  },
  readOnly: true,
};

const editTextfieldVariables: GQLEditTextfieldMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    textfieldId: 'textfieldId',
    newValue: 'Main Composite Processor',
  },
};
const successPayload: GQLSuccessPayload = { messages: [], __typename: 'SuccessPayload' };
const editTextfieldSuccessData: GQLEditTextfieldMutationData = { editTextfield: successPayload };

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

test('should render the textfield', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultTextField}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should render a readOnly textfield', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultTextField}
          readOnly
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should display the edited value', async () => {
  let editTextfieldCalled = false;
  const editTextfieldSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: editTextfieldMutation,
      variables: editTextfieldVariables,
    },
    result: () => {
      editTextfieldCalled = true;
      return { data: editTextfieldSuccessData };
    },
  };

  const mocks = [editTextfieldSuccessMock];
  const { container } = render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultTextField}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();

  const element: HTMLInputElement | null = screen.getByTestId('Name:').querySelector('input');
  expect(element && element.value).toBe('Composite Processor');

  await act(async () => {
    if (element) {
      userEvent.click(element);
      await new Promise((resolve) => setTimeout(resolve, 0));

      await waitFor(() => {
        expect(element.value).toBe('Composite Processor');
        expect(container).toMatchSnapshot();
      });

      userEvent.clear(element);
      userEvent.type(element, 'Main Composite Processor{enter}');
      await new Promise((resolve) => setTimeout(resolve, 0));

      await waitFor(() => {
        expect(editTextfieldCalled).toBeTruthy();
        expect(element.value).toBe('Main Composite Processor');
        expect(container).toMatchSnapshot();
      });
    }
  });
});

test('should render the textfield without style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultTextField}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the textfield with style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={textFieldWithStyle}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the textfield with empty style', async () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={textFieldWithEmptyStyle}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should support completion if configured', async () => {
  const textFieldWithCompletion: GQLTextfield = {
    __typename: 'Textfield',
    id: 'textfieldId',
    label: 'Text:',
    iconURL: [],
    hasHelpText: false,
    stringValue: 'fo',
    supportsCompletion: true,
    readOnly: false,
    diagnostics: [],
    style: {
      backgroundColor: 'lightblue',
      foregroundColor: '',
      fontSize: 14,
      italic: false,
      bold: false,
      underline: false,
      strikeThrough: false,
    },
  };

  let completionRequestCalled = false;

  const getCompletionProposalsVariables: GQLCompletionProposalsQueryVariables = {
    editingContextId: 'editingContextId',
    formId: 'formId',
    widgetId: 'textfieldId',
    currentText: 'fo',
    cursorPosition: 2,
  };

  const completionResultData: GQLCompletionProposalsQueryData = {
    viewer: {
      editingContext: {
        representation: {
          description: {
            completionProposals: [
              {
                description: 'Choice 1',
                textToInsert: 'foo',
                charsToReplace: 1,
              },
              {
                description: 'Choice 2',
                textToInsert: 'fooBar',
                charsToReplace: 2,
              },
            ],
            __typename: 'FormDescription',
          },
        },
      },
    },
  };
  const completionRequestMock: MockedResponse<Record<string, any>> = {
    request: {
      query: getCompletionProposalsQuery,
      variables: getCompletionProposalsVariables,
    },
    result: () => {
      completionRequestCalled = true;
      return { data: completionResultData };
    },
  };

  const mocks = [completionRequestMock];

  const { baseElement } = render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={textFieldWithCompletion}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();

  const element: HTMLInputElement | null = screen.getByTestId('Text:').querySelector('input');
  expect(element && element.value).toBe('fo');

  await act(async () => {
    if (element) {
      userEvent.click(element);
      await new Promise((resolve) => setTimeout(resolve, 0));

      await waitFor(() => {
        expect(element.value).toBe('fo');
        expect(baseElement).toMatchSnapshot();
      });

      userEvent.type(element, '[ControlLeft>] ');
      await new Promise((resolve) => setTimeout(resolve, 0));

      await waitFor(() => {
        expect(completionRequestCalled).toBeTruthy();
        expect(baseElement).toMatchSnapshot();
        expect(screen.getByTestId('completion-proposals')).toBeDefined();
        expect(screen.getByTestId('proposal-foo-1')).toBeDefined();
        expect(screen.getByTestId('proposal-fooBar-2')).toBeDefined();
      });
      screen.getByTestId('proposal-fooBar-2').click();
      await waitFor(() => {
        expect(element.value).toBe('fooBar');
      });
    }
  });
});

test('should not trigger completion request if not configured', async () => {
  const textFieldWithoutCompletion: GQLTextfield = {
    __typename: 'Textfield',
    id: 'textfieldId',
    label: 'Text:',
    iconURL: [],
    hasHelpText: false,
    stringValue: 'fo',
    supportsCompletion: false,
    diagnostics: [],
    readOnly: false,
    style: {
      backgroundColor: 'lightblue',
      foregroundColor: '',
      fontSize: 14,
      italic: false,
      bold: false,
      underline: false,
      strikeThrough: false,
    },
  };

  const mocks = [];
  const { baseElement } = render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={textFieldWithoutCompletion}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();

  const element: HTMLInputElement | null = screen.getByTestId('Text:').querySelector('input');
  expect(element && element.value).toBe('fo');

  await act(async () => {
    if (element) {
      userEvent.click(element);
      await new Promise((resolve) => setTimeout(resolve, 0));

      await waitFor(() => {
        expect(element.value).toBe('fo');
        expect(baseElement).toMatchSnapshot();
      });

      userEvent.type(element, '[ControlLeft>] ');
      await new Promise((resolve) => setTimeout(resolve, 0));

      await waitFor(() => {
        expect(baseElement).toMatchSnapshot();
        expect(screen.queryByTestId('completion-proposals')).toBeNull();
      });
    }
  });
});

test('should render the textfield with help hint', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={{ ...defaultTextField, hasHelpText: true }}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should render a readOnly textfield from widget properties', () => {
  const { container } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={readOnlyTextField}
          readOnly
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
