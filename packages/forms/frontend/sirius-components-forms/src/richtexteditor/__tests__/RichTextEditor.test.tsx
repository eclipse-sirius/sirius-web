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
 *     tbezierslafosse - test markdown table rendering
 *******************************************************************************/
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import { afterEach, expect, test } from 'vitest';
import { RichTextEditor } from '../RichTextEditor';

afterEach(() => {
  cleanup();
});

test('should render GFM markdown tables', async () => {
  render(
    <RichTextEditor
      value={'| Name | Path | Status |\n| --- | --- | --- |\n| Sirius | C:\\tmp | Ready |'}
      placeholder="Description"
      readOnly
      onBlur={() => {}}
    />
  );

  await waitFor(() => {
    expect(screen.getByRole('table')).toBeDefined();
  });

  expect(screen.getByRole('columnheader', { name: 'Name' })).toBeDefined();
  expect(screen.getByRole('columnheader', { name: 'Path' })).toBeDefined();
  expect(screen.getByRole('columnheader', { name: 'Status' })).toBeDefined();
  expect(screen.getByRole('cell', { name: 'Sirius' })).toBeDefined();
  expect(screen.getByRole('cell', { name: 'C:\\tmp' })).toBeDefined();
  expect(screen.getByRole('cell', { name: 'Ready' })).toBeDefined();
});

test('should not render pipe text as a GFM markdown table without separator row', async () => {
  render(<RichTextEditor value={'| Name | Status |'} placeholder="Description" readOnly onBlur={() => {}} />);

  await waitFor(() => {
    expect(screen.getByText('| Name | Status |')).toBeDefined();
  });

  expect(screen.queryByRole('table')).toBeNull();
});
