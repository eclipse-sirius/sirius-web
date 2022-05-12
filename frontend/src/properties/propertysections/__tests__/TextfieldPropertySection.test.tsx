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
import { act, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Textfield } from 'form/Form.types';
import {
  editTextfieldMutation,
  TextfieldPropertySection,
  updateWidgetFocusMutation,
} from 'properties/propertysections/TextfieldPropertySection';
import {
  GQLEditTextfieldMutationData,
  GQLEditTextfieldMutationVariables,
  GQLEditTextfieldSuccessPayload,
  GQLErrorPayload,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusSuccessPayload,
} from 'properties/propertysections/TextfieldPropertySection.types';
import React from 'react';

jest.mock('uuid', () => ({ v4: () => '48be95fc-3422-45d3-b1f9-d590e847e9e1' }));

const defaultTextField: Textfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name:',
  stringValue: 'Composite Processor',
  diagnostics: [],
  style: null,
};

const textFieldWithStyle: Textfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name:',
  stringValue: 'Composite Processor',
  diagnostics: [],
  style: {
    backgroundColor: '#de1000',
    foregroundColor: '#fbb800',
  },
};

const textFieldWithEmptyStyle: Textfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name:',
  stringValue: 'Composite Processor',
  diagnostics: [],
  style: {
    backgroundColor: '',
    foregroundColor: '',
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
const editTextfieldSuccessPayload: GQLEditTextfieldSuccessPayload = { __typename: 'EditTextfieldSuccessPayload' };
const editTextfieldSuccessData: GQLEditTextfieldMutationData = { editTextfield: editTextfieldSuccessPayload };

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
