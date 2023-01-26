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
import { ServerContext } from '@eclipse-sirius/sirius-components-core';
import { act, cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { GQLButton } from '../../form/FormEventFragments.types';
import { ButtonPropertySection, pushButtonMutation, updateWidgetFocusMutation } from '../ButtonPropertySection';
import {
  GQLErrorPayload,
  GQLPushButtonMutationData,
  GQLPushButtonMutationVariables,
  GQLSuccessPayload,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusSuccessPayload,
} from '../ButtonPropertySection.types';

vi.mock('uuid', () => ({ v4: () => '48be95fc-3422-45d3-b1f9-d590e847e9e1' }));

afterEach(() => cleanup());

const defaultButton: GQLButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: null,
  diagnostics: [],
  buttonLabel: 'ButtonLabel',
  imageURL: null,
  style: null,
};

const buttonWithStyle: GQLButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: null,
  diagnostics: [],
  buttonLabel: 'ButtonLabel',
  imageURL: null,
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

const buttonWithEmptyStyle: GQLButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: null,
  diagnostics: [],
  buttonLabel: 'ButtonLabel',
  imageURL: null,
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

const pushButtonVariables: GQLPushButtonMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    buttonId: 'buttonId',
  },
};
const successPayload: GQLSuccessPayload = { __typename: 'SuccessPayload' };
const pushButtonSuccessData: GQLPushButtonMutationData = { pushButton: successPayload };

const pushButtonErrorPayload: GQLErrorPayload = {
  __typename: 'ErrorPayload',
  message: 'An error has occurred, please refresh the page',
};
const pushButtonErrorData: GQLPushButtonMutationData = {
  pushButton: pushButtonErrorPayload,
};

const updateWidgetFocusVariables: GQLUpdateWidgetFocusMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formId',
    widgetId: 'buttonId',
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

test('should render the button', () => {
  const { container } = render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ButtonPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultButton}
          subscribers={[]}
          readOnly={false}
        />
      </ServerContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should render a readOnly button', () => {
  const { container } = render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ButtonPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultButton}
          subscribers={[]}
          readOnly
        />
      </ServerContext.Provider>
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

  const mocks = [updateWidgetFocusSuccessMock, pushButtonSuccessMock];
  const { container } = render(
    <MockedProvider mocks={mocks}>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ButtonPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultButton}
          subscribers={[]}
          readOnly={false}
        />
      </ServerContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();

  const element: HTMLElement = screen.getByTestId('ButtonLabel');

  await act(async () => {
    userEvent.click(element);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(updateWidgetFocusCalled).toBeTruthy();
      expect(pushButtonCalled).toBeTruthy();
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

  const mocks = [updateWidgetFocusErrorMock, pushButtonErrorMock];
  const { baseElement } = render(
    <MockedProvider mocks={mocks}>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ButtonPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultButton}
          subscribers={[]}
          readOnly={false}
        />
      </ServerContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();

  const element: HTMLElement = screen.getByTestId('ButtonLabel');

  await act(async () => {
    userEvent.click(element);
    await new Promise((resolve) => setTimeout(resolve, 0));

    await waitFor(() => {
      expect(updateWidgetFocusCalled).toBeTruthy();
      expect(pushButtonCalled).toBeTruthy();
      expect(screen.getByTestId('error')).not.toBeUndefined();
      expect(baseElement).toMatchSnapshot();
    });
  });
});

test('should render the button without style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ButtonPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultButton}
          subscribers={[]}
          readOnly={false}
        />
      </ServerContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the button with style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ButtonPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={buttonWithStyle}
          subscribers={[]}
          readOnly={false}
        />
      </ServerContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the button with empty style', async () => {
  const { baseElement } = render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ButtonPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={buttonWithEmptyStyle}
          subscribers={[]}
          readOnly={false}
        />
      </ServerContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});
