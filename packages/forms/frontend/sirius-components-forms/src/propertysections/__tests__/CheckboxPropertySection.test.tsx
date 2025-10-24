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
import { CheckboxPropertySection } from '../CheckboxPropertySection';
import {
  checkbox,
  checkboxReadOnly,
  editCheckboxErrorMock,
  editCheckboxSuccessMock,
} from './CheckboxPropertySection.data';

afterEach(() => {
  cleanup();
  vi.clearAllMocks();
});

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

test('should render the checkbox', () => {
  render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={checkbox}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );

  expect(screen.getByRole('checkbox')).toBeDefined();
});

test('should render a readOnly checkbox', () => {
  render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={checkboxReadOnly}
          readOnly
        />
      </ToastContext.Provider>
    </MockedProvider>
  );

  const checkboxElement: HTMLInputElement = screen.getByRole('checkbox');
  expect(checkboxElement.disabled).toBeTruthy();
});

test('should send mutation when clicked', async () => {
  render(
    <MockedProvider mocks={[editCheckboxSuccessMock]}>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={checkbox}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );

  await userEvent.click(screen.getByRole('checkbox'));
  await waitFor(() => {
    expect(editCheckboxSuccessMock.result).toHaveBeenCalledTimes(1);
  });
});

test('should display the error received', async () => {
  render(
    <MockedProvider mocks={[editCheckboxErrorMock]}>
      <ToastContext.Provider value={toastContextMock}>
        <CheckboxPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={checkbox}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );

  await userEvent.click(screen.getByRole('checkbox'));
  await waitFor(() => {
    expect(editCheckboxErrorMock.result).toHaveBeenCalledTimes(1);
    expect(mockEnqueue).toHaveBeenCalledTimes(1);
  });
});
