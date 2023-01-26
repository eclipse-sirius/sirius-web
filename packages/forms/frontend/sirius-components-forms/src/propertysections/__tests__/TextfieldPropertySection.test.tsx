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
import { act, cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { GQLTextfield } from '../../form/FormEventFragments.types';
import {
  editTextfieldMutation,
  getCompletionProposalsQuery,
  TextfieldPropertySection,
  updateWidgetFocusMutation,
} from '../../propertysections/TextfieldPropertySection';
import {
  GQLCompletionProposalsQueryData,
  GQLCompletionProposalsQueryVariables,
  GQLEditTextfieldMutationData,
  GQLEditTextfieldMutationVariables,
  GQLErrorPayload,
  GQLSuccessPayload,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusSuccessPayload,
} from '../../propertysections/TextfieldPropertySection.types';

vi.mock('uuid', () => ({ v4: () => '48be95fc-3422-45d3-b1f9-d590e847e9e1' }));

afterEach(() => cleanup());

const defaultTextField: GQLTextfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name:',
  iconURL: null,
  stringValue: 'Composite Processor',
  supportsCompletion: false,
  diagnostics: [],
  style: null,
};

const textFieldWithStyle: GQLTextfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name:',
  iconURL: null,
  stringValue: 'Composite Processor',
  supportsCompletion: false,
  diagnostics: [],
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
  iconURL: null,
  stringValue: 'Composite Processor',
  supportsCompletion: false,
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

const editTextfieldVariables: GQLEditTextfieldMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    textfieldId: 'textfieldId',
    newValue: 'Main Composite Processor',
  },
};
const successPayload: GQLSuccessPayload = { __typename: 'SuccessPayload' };
const editTextfieldSuccessData: GQLEditTextfieldMutationData = { editTextfield: successPayload };

const updateWidgetFocusVariables: GQLUpdateWidgetFocusMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    widgetId: 'textfieldId',
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
  message: 'An error has occurred, please refresh the page',
};
const updateWidgetFocusErrorData: GQLUpdateWidgetFocusMutationData = {
  updateWidgetFocus: updateWidgetFocusErrorPayload,
};

test('should render the textfield', () => {
  const { container } = render(
    <MockedProvider>
      <TextfieldPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultTextField}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should render a readOnly textfield', () => {
  const { container } = render(
    <MockedProvider>
      <TextfieldPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultTextField}
        subscribers={[]}
        readOnly
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should display the edited value', async () => {
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

  const mocks = [updateWidgetFocusSuccessMock, editTextfieldSuccessMock];
  const { container } = render(
    <MockedProvider mocks={mocks}>
      <TextfieldPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultTextField}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();

  const element: HTMLInputElement = screen.getByTestId('Name:').querySelector('input');
  expect(element.value).toBe('Composite Processor');

  await act(async () => {
    userEvent.click(element);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(updateWidgetFocusCalled).toBeTruthy();
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

  const mocks = [updateWidgetFocusErrorMock];
  const { baseElement } = render(
    <MockedProvider mocks={mocks}>
      <TextfieldPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultTextField}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();

  const element: HTMLInputElement = screen.getByTestId('Name:').querySelector('input');
  expect(element.value).toBe('Composite Processor');

  await act(async () => {
    userEvent.click(element);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(updateWidgetFocusCalled).toBeTruthy();
      expect(screen.getByTestId('error')).not.toBeUndefined();
      expect(baseElement).toMatchSnapshot();
    });
  });
});

test('should render the textfield without style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <TextfieldPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultTextField}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the textfield with style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <TextfieldPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={textFieldWithStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the textfield with empty style', async () => {
  const { baseElement } = render(
    <MockedProvider>
      <TextfieldPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={textFieldWithEmptyStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should support completion if configured', async () => {
  const textFieldWithCompletion: GQLTextfield = {
    __typename: 'Textfield',
    id: 'textfieldId',
    label: 'Text:',
    iconURL: null,
    stringValue: 'fo',
    supportsCompletion: true,
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

  const leaveWidgetFocusVariables: GQLUpdateWidgetFocusMutationVariables = {
    input: {
      id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
      editingContextId: 'editingContextId',
      representationId: 'formId',
      widgetId: 'textfieldId',
      selected: false,
    },
  };

  let leaveWidgetFocusCalled = false;
  const leaveWidgetFocusSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: updateWidgetFocusMutation,
      variables: leaveWidgetFocusVariables,
    },
    result: () => {
      leaveWidgetFocusCalled = true;
      return { data: updateWidgetFocusSuccessData };
    },
  };

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

  const mocks = [
    updateWidgetFocusSuccessMock,
    completionRequestMock,
    ,
    leaveWidgetFocusSuccessMock,
    updateWidgetFocusSuccessMock,
  ];
  const { baseElement } = render(
    <MockedProvider mocks={mocks}>
      <TextfieldPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={textFieldWithCompletion}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();

  const element: HTMLInputElement = screen.getByTestId('Text:').querySelector('input');
  expect(element.value).toBe('fo');

  await act(async () => {
    userEvent.click(element);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(updateWidgetFocusCalled).toBeTruthy();
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
  });
});

test('should not trigger completion request if not configured', async () => {
  const textFieldWithoutCompletion: GQLTextfield = {
    __typename: 'Textfield',
    id: 'textfieldId',
    label: 'Text:',
    iconURL: null,
    stringValue: 'fo',
    supportsCompletion: false,
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

  const leaveWidgetFocusVariables: GQLUpdateWidgetFocusMutationVariables = {
    input: {
      id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
      editingContextId: 'editingContextId',
      representationId: 'formId',
      widgetId: 'textfieldId',
      selected: false,
    },
  };

  let leaveWidgetFocusCalled = false;
  const leaveWidgetFocusSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: updateWidgetFocusMutation,
      variables: leaveWidgetFocusVariables,
    },
    result: () => {
      leaveWidgetFocusCalled = true;
      return { data: updateWidgetFocusSuccessData };
    },
  };

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

  const mocks = [updateWidgetFocusSuccessMock, leaveWidgetFocusSuccessMock];
  const { baseElement } = render(
    <MockedProvider mocks={mocks}>
      <TextfieldPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={textFieldWithoutCompletion}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();

  const element: HTMLInputElement = screen.getByTestId('Text:').querySelector('input');
  expect(element.value).toBe('fo');

  await act(async () => {
    userEvent.click(element);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(updateWidgetFocusCalled).toBeTruthy();
      expect(element.value).toBe('fo');
      expect(baseElement).toMatchSnapshot();
    });

    userEvent.type(element, '[ControlLeft>] ');
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(leaveWidgetFocusCalled).toBeFalsy();
      expect(baseElement).toMatchSnapshot();
      expect(screen.queryByTestId('completion-proposals')).toBeNull();
    });
  });
});
