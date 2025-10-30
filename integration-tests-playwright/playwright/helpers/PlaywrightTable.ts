/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import type { Page, Locator } from '@playwright/test';

export class PlaywrightTable {
  readonly page: Page;
  readonly tableLocator: Locator;

  constructor(page: Page) {
    this.page = page;
    this.tableLocator = page.getByTestId('table-representation').first();
  }

  async getRowCount() {
    return await this.tableLocator.getByTestId('table-row-header').count();
  }

  async changeRowPerPage(pageSize: number) {
    await this.page.getByTestId('cursor-based-pagination-size').click();
    await this.page.locator('ul[role="listbox"]').getByText(pageSize.toString(), { exact: true }).click();
  }

  async navigateNextPage() {
    await this.tableLocator.getByTestId('pagination-next').click();
  }

  async navigatePrevPage() {
    await this.tableLocator.getByTestId('pagination-prev').click();
  }

  async fillGlobalFilter(filter: string) {
    await this.tableLocator.locator('input[placeholder="Search"]').fill(filter);
  }

  async resetGlobalFilter() {
    await this.tableLocator.locator('button[aria-label="Clear search"]').click();
  }
}
