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

export class PlaywrightExplorer {
  readonly page: Page;
  readonly explorerLocator: Locator;

  constructor(page: Page) {
    this.page = page;
    this.explorerLocator = page.locator('[data-testid^="explorer://"]').first();
  }

  async createRepresentation(
    treeItemLabel: string,
    representationDescriptionName: string,
    representationLabel: string
  ) {
    await this.explorerLocator.getByTestId(`${treeItemLabel}-more`).click();
    await this.page.getByTestId('new-representation').click({ force: true });
    await this.page.getByTestId('name').clear();
    await this.page.getByTestId('name').fill(representationLabel);
    await this.page.getByTestId('representationDescription').click();
    await this.page.getByTestId(representationDescriptionName).click();
    await this.page.getByTestId('create-representation').click();
  }

  async select(treeItemLabel: string) {
    await this.explorerLocator.locator(`[data-treeitemlabel="${treeItemLabel}"]`).click();
  }

  async expand(treeItemLabel: string) {
    await this.explorerLocator.locator(`[data-treeitemlabel="${treeItemLabel}"]`).dblclick();
  }

  async uploadDocument(fileName: string) {
    await this.page.getByTestId('upload-document-icon').click();
    await this.page.locator('input[name="file"]').setInputFiles(`./playwright/resources/${fileName}`);
    await this.page.getByTestId('upload-document-split-button').click();
    await this.page.getByTestId('upload-document-close').click();
  }

  async dragTo(treeItemLabel: string, target: Locator) {
    await this.explorerLocator.locator(`[data-treeitemlabel="${treeItemLabel}"]`).dragTo(target);
  }
}
