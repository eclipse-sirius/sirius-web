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
import { cleanup, render, screen } from '@testing-library/react';
import { afterEach, expect, test } from 'vitest';
import { LinkPropertySection } from '../LinkPropertySection';
import { link } from './LinkPropertySection.data';

afterEach(() => cleanup());

test('should render the link', () => {
  render(
    <MockedProvider>
      <LinkPropertySection editingContextId="editingContextId" formId="formId" widget={link} readOnly={false} />
    </MockedProvider>
  );

  expect(screen.getByRole('link')).toBeDefined();
  expect(screen.getByRole('link').textContent).toBe('Google');
});
