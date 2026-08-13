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
import { ThemeProvider } from '@mui/material/styles';
import { cleanup, render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { theme } from '../../theme';
import { PanelCollapseContextProvider } from '../PanelCollapseContext';
import { ViewAccordion, ViewAccordionContent } from '../ViewAccordion';

afterEach(() => {
  cleanup();
});

const viewHeaderHeight = '28px';

const renderViewAccordion = (onCollapseChange: (id: string, collapsed: boolean) => void = () => {}) =>
  render(
    <ThemeProvider theme={theme}>
      <PanelCollapseContextProvider onCollapseChange={onCollapseChange} viewHeaderHeight={viewHeaderHeight}>
        <ViewAccordion id="view-id" title="Test View">
          <ViewAccordionContent>
            <div data-testid="content">Content</div>
          </ViewAccordionContent>
        </ViewAccordion>
      </PanelCollapseContextProvider>
    </ThemeProvider>
  );

test('given an expanded view accordion, then its content is bounded by the height of the view', () => {
  renderViewAccordion();

  const accordion = screen.getByTestId('view-Test View');
  const accordionStyle = getComputedStyle(accordion);
  expect(accordionStyle.display).toBe('grid');
  expect(accordionStyle.height).toBe('100%');
  expect(accordionStyle.overflow).toBe('hidden');
  expect(accordionStyle.gridTemplateRows).toBe(`${viewHeaderHeight} minmax(0, 1fr)`);

  const region = screen.getByRole('region');
  expect(screen.getByTestId('view-Test View-toggle').id).toBe('view-id-header');
  expect(region.id).toBe('view-id-content');
  expect(region.getAttribute('aria-labelledby')).toBe(screen.getByTestId('view-Test View-toggle').id);
  const regionStyle = getComputedStyle(region);
  expect(regionStyle.display).toBe('grid');
  expect(regionStyle.gridTemplateRows).toBe('minmax(0, 1fr)');
  expect(regionStyle.overflow).toBe('hidden');
  expect(regionStyle.visibility).toBe('visible');

  expect(region.contains(screen.getByTestId('content'))).toBe(true);
});

test('given a view accordion, when its header is clicked, then it is collapsed', async () => {
  const onCollapseChange = vi.fn();
  renderViewAccordion(onCollapseChange);

  const toggle = screen.getByTestId('view-Test View-toggle');
  expect(toggle.getAttribute('aria-expanded')).toBe('true');

  userEvent.click(toggle);

  expect(onCollapseChange).toHaveBeenCalledTimes(1);
  expect(onCollapseChange).toHaveBeenCalledWith('view-id', true);
  expect(toggle.getAttribute('aria-expanded')).toBe('false');

  const accordion = screen.getByTestId('view-Test View');
  // Only the header remains visible, the content track is collapsed.
  expect(getComputedStyle(accordion).gridTemplateRows).toBe(`${viewHeaderHeight} 0px`);
});

test('given a collapsed view accordion, then its content is kept mounted but hidden', async () => {
  renderViewAccordion();

  userEvent.click(screen.getByTestId('view-Test View-toggle'));

  // The content stays mounted so that the state and the subscriptions of the view are preserved.
  const content = screen.getByTestId('content');
  expect(document.body.contains(content)).toBe(true);
  expect(getComputedStyle(content.parentElement as HTMLElement).visibility).toBe('hidden');
});

test('given a view accordion, when it is expanded again, then it is restored', async () => {
  const onCollapseChange = vi.fn();
  renderViewAccordion(onCollapseChange);

  const toggle = screen.getByTestId('view-Test View-toggle');
  userEvent.click(toggle);
  userEvent.click(toggle);

  expect(onCollapseChange).toHaveBeenNthCalledWith(2, 'view-id', false);
  expect(toggle.getAttribute('aria-expanded')).toBe('true');
  expect(getComputedStyle(screen.getByTestId('view-Test View')).gridTemplateRows).toBe(
    `${viewHeaderHeight} minmax(0, 1fr)`
  );
});
