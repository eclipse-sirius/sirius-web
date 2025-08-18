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
import { MockedProvider } from '@apollo/client/testing';
import { MessageOptions, ToastContext, ToastContextValue } from '@eclipse-sirius/sirius-components-core';
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { RadioPropertySection } from '../RadioPropertySection';
import { editRadioErrorMock, editRadioSuccessMock, radio } from './RadioPropertySection.data';

afterEach(() => {
  cleanup();
  vi.clearAllMocks();
});

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

test('should send mutation when clicked', async () => {
  render(
    <MockedProvider mocks={[editRadioSuccessMock]}>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection editingContextId="editingContextId" formId="formId" widget={radio} readOnly={false} />
      </ToastContext.Provider>
    </MockedProvider>
  );

  const inactive: HTMLInputElement | null = screen.getByTestId('inactive').querySelector('input');
  expect(inactive && inactive.checked).toBe(true);

  const active: HTMLInputElement | null = screen.getByTestId('active').querySelector('input');
  expect(active && active.checked).toBe(false);

  await userEvent.click(active);
  await waitFor(() => {
    expect(editRadioSuccessMock.result).toHaveBeenCalledTimes(1);
  });
});

test('should display the error received', async () => {
  render(
    <MockedProvider mocks={[editRadioErrorMock]}>
      <ToastContext.Provider value={toastContextMock}>
        <RadioPropertySection editingContextId="editingContextId" formId="formId" widget={radio} readOnly={false} />
      </ToastContext.Provider>
    </MockedProvider>
  );

  const inactive: HTMLInputElement | null = screen.getByTestId('inactive').querySelector('input');
  expect(inactive && inactive.checked).toBe(true);

  const active: HTMLInputElement | null = screen.getByTestId('active').querySelector('input');
  expect(active && active.checked).toBe(false);

  await userEvent.click(active);

  await waitFor(() => {
    expect(editRadioErrorMock.result).toHaveBeenCalledTimes(1);
    expect(mockEnqueue).toHaveBeenCalledTimes(2);
  });
});
