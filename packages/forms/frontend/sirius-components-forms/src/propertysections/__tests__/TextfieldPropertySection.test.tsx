/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import { MockedProvider } from '@apollo/client/testing';
import { MessageOptions, ToastContext, ToastContextValue } from '@eclipse-sirius/sirius-components-core';
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { TextfieldPropertySection } from '../../propertysections/TextfieldPropertySection';
import {
  completionRequestMock,
  editTextfieldSuccessMock,
  textfield,
  textfieldReadOnly,
} from './TextfieldPropertySection.data';

afterEach(() => {
  cleanup();
  vi.clearAllMocks();
});

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

test('should render the textfield', () => {
  render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={textfield}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );

  const inputElement: HTMLInputElement = screen.getByRole('textbox');
  expect(inputElement.value).toBe('Composite Processor');
});

test('should render a readOnly textfield', () => {
  render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection editingContextId="editingContextId" formId="formId" widget={textfield} readOnly />
      </ToastContext.Provider>
    </MockedProvider>
  );

  const inputElement: HTMLInputElement = screen.getByRole('textbox');
  expect(inputElement.disabled).toBeTruthy();
});

test('should display the edited value', async () => {
  render(
    <MockedProvider mocks={[editTextfieldSuccessMock]}>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={textfield}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );

  const inputElement: HTMLInputElement = screen.getByRole('textbox');
  expect(inputElement.value).toBe('Composite Processor');

  await userEvent.click(inputElement);
  await userEvent.clear(inputElement);
  await userEvent.type(inputElement, 'Main Composite Processor{enter}');

  await waitFor(() => {
    expect(editTextfieldSuccessMock.result).toHaveBeenCalledTimes(1);
    expect(inputElement.value).toBe('Main Composite Processor');
  });
});

test('should support completion if configured', async () => {
  render(
    <MockedProvider mocks={[completionRequestMock]}>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={textfield}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );

  const inputElement: HTMLInputElement = screen.getByRole('textbox');
  expect(inputElement.value).toBe('Composite Processor');

  await userEvent.click(inputElement);
  await userEvent.type(inputElement, '[ControlLeft>] ');

  await waitFor(() => {
    expect(completionRequestMock.result).toHaveBeenCalledTimes(1);
  });

  await waitFor(() => {
    expect(screen.getByTestId('completion-proposals')).toBeDefined();
    expect(screen.getByTestId('proposal-CompositeProcessor 1-18')).toBeDefined();
    expect(screen.getByTestId('proposal-CompositeProcessor 2-18')).toBeDefined();
  });

  await userEvent.click(screen.getByTestId('proposal-CompositeProcessor 1-18'));
  await waitFor(() => {
    expect(inputElement.value).toBe('Composite Processor 1');
  });
});

test('should not trigger completion request if not configured', async () => {
  render(
    <MockedProvider mocks={[]}>
      <ToastContext.Provider value={toastContextMock}>
        <TextfieldPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={textfieldReadOnly}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );

  const inputElement: HTMLInputElement = screen.getByRole('textbox');
  expect(inputElement.value).toBe('Composite Processor');

  await userEvent.click(inputElement);
  await userEvent.type(inputElement, '[ControlLeft>] ');
  await waitFor(() => {
    expect(screen.queryByTestId('completion-proposals')).toBeNull();
  });
});
