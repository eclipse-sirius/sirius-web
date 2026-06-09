/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { cleanup, render, screen } from '@testing-library/react';
import { afterEach, expect, test, vi } from 'vitest';
import { ValidationView } from '../ValidationView';

const useValidationViewSubscription = vi.fn();

vi.mock('react-i18next', () => ({
  useTranslation: () => ({
    t: (key: string, options?: Record<string, unknown>) => {
      if (key === 'diagnosticCount' && typeof options?.count === 'number') {
        return `${options.count} diagnostics`;
      }

      if (key === 'noDiagnostic') {
        return 'No diagnostic available';
      }

      return key;
    },
  }),
}));

vi.mock('../useValidationViewSubscription', () => ({
  useValidationViewSubscription: (...args: unknown[]) => useValidationViewSubscription(...args),
}));

afterEach(() => {
  cleanup();
  vi.clearAllMocks();
});

test('given a fixable diagnostic, when the validation view is rendered, then the fixable icon should be displayed', async () => {
  useValidationViewSubscription.mockReturnValue({
    loading: false,
    complete: false,
    payload: {
      __typename: 'ValidationRefreshedEventPayload',
      id: 'payload-id',
      validation: {
        id: 'validation-id',
        diagnostics: [
          {
            id: 'diagnostic-id',
            kind: 'Error',
            message: 'The message',
            fixable: true,
          },
        ],
      },
    },
  });

  render(<ValidationView id="validation-view" editingContextId="editing-context-id" />);

  await screen.findByText('The message');

  expect(screen.getByTestId('diagnostic-fixable-icon')).not.toBeNull();
});

test('given a non fixable diagnostic, when the validation view is rendered, then the fixable icon should not be displayed', async () => {
  useValidationViewSubscription.mockReturnValue({
    loading: false,
    complete: false,
    payload: {
      __typename: 'ValidationRefreshedEventPayload',
      id: 'payload-id',
      validation: {
        id: 'validation-id',
        diagnostics: [
          {
            id: 'diagnostic-id',
            kind: 'Error',
            message: 'The message',
            fixable: false,
          },
        ],
      },
    },
  });

  render(<ValidationView id="validation-view" editingContextId="editing-context-id" />);

  await screen.findByText('The message');

  expect(screen.queryByTestId('diagnostic-fixable-icon')).toBeNull();
});
