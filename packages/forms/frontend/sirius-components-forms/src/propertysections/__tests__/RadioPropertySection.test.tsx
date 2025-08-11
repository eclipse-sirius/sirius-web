/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { RadioPropertySection, editRadioMutation } from '../../propertysections/RadioPropertySection';
import {
  GQLEditRadioMutationData,
  GQLEditRadioMutationVariables,
  GQLErrorPayload,
  GQLSuccessPayload,
} from '../../propertysections/RadioPropertySection.types';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const defaultRadio = {
  __typename: 'Radio',
  id: 'radioId',
  label: 'Status:',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  readOnly: false,
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
};

const radioWithEmptyStyle: GQLRadio = {
  ...defaultRadio,
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

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

test('should send mutation when clicked', async () => {
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

  const mocks = [editRadioSuccessMock];
  render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={radioWithEmptyStyle}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );

  const inactive: HTMLInputElement | null = screen.getByTestId('inactive').querySelector('input');
  expect(inactive && inactive.checked).toBe(true);

  const active: HTMLInputElement | null = screen.getByTestId('active').querySelector('input');
  expect(active && active.checked).toBe(false);

  await act(async () => {
    if (active) {
      userEvent.click(active);
      await new Promise((resolve) => setTimeout(resolve, 0));

      await waitFor(() => {
        expect(editRadioCalled).toBeTruthy();
      });
    }
  });
});

test('should display the error received', async () => {
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

  const mocks = [editRadioErrorMock];
  render(
    <MockedProvider mocks={mocks}>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={radioWithEmptyStyle}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );

  const inactive: HTMLInputElement | null = screen.getByTestId('inactive').querySelector('input');
  expect(inactive && inactive.checked).toBe(true);

  const active: HTMLInputElement | null = screen.getByTestId('active').querySelector('input');
  expect(active && active.checked).toBe(false);

  await act(async () => {
    if (active) {
      userEvent.click(active);
      await new Promise((resolve) => setTimeout(resolve, 0));

      await waitFor(() => {
        expect(editRadioCalled).toBeTruthy();
        expect(mockEnqueue).toHaveBeenCalledTimes(2);
      });
    }
  });
});
