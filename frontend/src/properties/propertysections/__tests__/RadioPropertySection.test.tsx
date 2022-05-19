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
import { Radio } from 'form/Form.types';
import {
  editRadioMutation,
  RadioPropertySection,
  updateWidgetFocusMutation,
} from 'properties/propertysections/RadioPropertySection';
import {
  GQLEditRadioMutationData,
  GQLEditRadioMutationVariables,
  GQLEditRadioSuccessPayload,
  GQLErrorPayload,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusSuccessPayload,
} from 'properties/propertysections/RadioPropertySection.types';
import React from 'react';

jest.mock('uuid', () => ({ v4: () => '48be95fc-3422-45d3-b1f9-d590e847e9e1' }));

const defaultRadio: Radio = {
  __typename: 'Radio',
  id: 'radioId',
  label: 'Status:',
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

const radioWithStyle: Radio = {
  __typename: 'Radio',
  id: 'radioId',
  label: 'Status:',
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

const radioWithEmptyStyle: Radio = {
  __typename: 'Radio',
  id: 'radioId',
  label: 'Status:',
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

const editRadioVariables: GQLEditRadioMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    radioId: 'radioId',
    newValue: '1',
  },
};
const editRadioSuccessPayload: GQLEditRadioSuccessPayload = { __typename: 'EditRadioSuccessPayload' };
const editRadioSuccessData: GQLEditRadioMutationData = { editRadio: editRadioSuccessPayload };

const editRadioErrorPayload: GQLErrorPayload = {
  __typename: 'ErrorPayload',
  message: 'An error has occurred, please refresh the page',
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
  message: 'An error has occurred, please refresh the page',
};
const updateWidgetFocusErrorData: GQLUpdateWidgetFocusMutationData = {
  updateWidgetFocus: updateWidgetFocusErrorPayload,
};

test('should render the radio', () => {
  const { container } = render(
    <MockedProvider>
      <RadioPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultRadio}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should render a readOnly radio', () => {
  const { container } = render(
    <MockedProvider>
      <RadioPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultRadio}
        subscribers={[]}
        readOnly
      />
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
      <RadioPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultRadio}
        subscribers={[]}
        readOnly={false}
      />
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
      <RadioPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultRadio}
        subscribers={[]}
        readOnly={false}
      />
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
      expect(screen.getByTestId('error')).not.toBeUndefined();
      expect(baseElement).toMatchSnapshot();
    });
  });
});

test('should render the radio without style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <RadioPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultRadio}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the radio with style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <RadioPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={radioWithStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the radio with empty style', async () => {
  const { baseElement } = render(
    <MockedProvider>
      <RadioPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={radioWithEmptyStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});
