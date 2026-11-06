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
import { MessageOptions, ServerContext, ToastContext, ToastContextValue } from '@eclipse-sirius/sirius-components-core';
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { ButtonPropertySection } from '../ButtonPropertySection';
import { button, buttonReadOnly, pushButtonErrorMock, pushButtonSuccessMock } from './ButtonPropertySection.data';

afterEach(() => {
  cleanup();
  vi.clearAllMocks();
});

const mockEnqueue = vi.fn<(body: string, options?: MessageOptions) => void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

test('should render the button', () => {
  render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ToastContext.Provider value={toastContextMock}>
          <ButtonPropertySection editingContextId="editingContextId" formId="formId" widget={button} readOnly={false} />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );

  expect(screen.getByRole('button').textContent).toBe('Click Me');
});

test('should render a readOnly button', () => {
  render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ToastContext.Provider value={toastContextMock}>
          <ButtonPropertySection editingContextId="editingContextId" formId="formId" widget={buttonReadOnly} readOnly />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );

  const buttonElement: HTMLButtonElement = screen.getByRole('button');
  expect(buttonElement.disabled).toBeTruthy();
});

test('should send mutation when clicked', async () => {
  render(
    <MockedProvider mocks={[pushButtonSuccessMock]}>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ToastContext.Provider value={toastContextMock}>
          <ButtonPropertySection editingContextId="editingContextId" formId="formId" widget={button} readOnly={false} />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );

  await userEvent.click(screen.getByRole('button'));
  await waitFor(() => {
    expect(pushButtonSuccessMock.result).toHaveBeenCalledTimes(1);
  });
});

test('should display the error received', async () => {
  render(
    <MockedProvider mocks={[pushButtonErrorMock]}>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ToastContext.Provider value={toastContextMock}>
          <ButtonPropertySection editingContextId="editingContextId" formId="formId" widget={button} readOnly={false} />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );

  await userEvent.click(screen.getByRole('button'));
  await waitFor(() => {
    expect(pushButtonErrorMock.result).toHaveBeenCalledTimes(1);
    expect(mockEnqueue).toHaveBeenCalledTimes(1);
  });
});
