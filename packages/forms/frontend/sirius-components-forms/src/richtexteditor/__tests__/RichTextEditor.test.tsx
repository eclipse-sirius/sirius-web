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
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import { afterEach, expect, test } from 'vitest';
import { RichTextEditor } from '../RichTextEditor';

afterEach(() => {
  cleanup();
});

test('should render markdown checklists without changing bullet lists into checkboxes', async () => {
  render(
    <RichTextEditor
      value={'- Bullet item\n- [ ] Todo\n- [x] Done'}
      placeholder="Description"
      readOnly
      onBlur={() => {}}
    />
  );

  await waitFor(() => {
    expect(screen.getAllByRole('checkbox')).toHaveLength(2);
  });

  const checkboxes = screen.getAllByRole('checkbox');
  expect(checkboxes[0].getAttribute('aria-checked')).toBe('false');
  expect(checkboxes[1].getAttribute('aria-checked')).toBe('true');
  expect(screen.getByText('Todo')).toBeDefined();
  expect(screen.getByText('Done')).toBeDefined();

  const bulletItem = screen.getByText('Bullet item');
  expect(bulletItem.closest('[role="checkbox"]')).toBeNull();
});
