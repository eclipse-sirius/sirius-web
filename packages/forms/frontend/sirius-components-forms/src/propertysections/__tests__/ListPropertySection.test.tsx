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
import {
  MessageOptions,
  Selection,
  SelectionContext,
  ToastContext,
  ToastContextValue,
} from '@eclipse-sirius/sirius-components-core';
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { ListPropertySection } from '../ListPropertySection';
import { listReadOnly, list, itemClickCalledCalledSuccessMock } from './ListPropertySection.data';

const setSelection = () => {};

afterEach(() => {
  cleanup();
  vi.clearAllMocks();
});

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

const emptySelection: Selection = {
  entries: [],
};

test('should the click event sent on item click', async () => {
  render(
    <MockedProvider mocks={[itemClickCalledCalledSuccessMock]}>
      <ToastContext.Provider value={toastContextMock}>
        <SelectionContext.Provider value={{ selection: emptySelection, setSelection, selectedRepresentations: [] }}>
          <ListPropertySection editingContextId="editingContextId" formId="formId" widget={list} readOnly={false} />
        </SelectionContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );
  const element: HTMLElement = screen.getByTestId('representation-item3Label');
  expect(element.textContent).toBe('item3Label');

  await userEvent.click(element);
  await waitFor(() => {
    expect(itemClickCalledCalledSuccessMock.result).toHaveBeenCalledTimes(1);
  });
});

test('when in read-only mode, list items can still be selected but handlers are not invoked', async () => {
  render(
    <MockedProvider mocks={[itemClickCalledCalledSuccessMock]}>
      <ToastContext.Provider value={toastContextMock}>
        <SelectionContext.Provider value={{ selection: emptySelection, setSelection, selectedRepresentations: [] }}>
          <ListPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={listReadOnly}
            readOnly={false}
          />
        </SelectionContext.Provider>
      </ToastContext.Provider>
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId('representation-item3Label');
  expect(element.textContent).toBe('item3Label');

  await userEvent.click(element);
  await waitFor(() => {
    expect(itemClickCalledCalledSuccessMock.result).toHaveBeenCalledTimes(0);
  });
});
