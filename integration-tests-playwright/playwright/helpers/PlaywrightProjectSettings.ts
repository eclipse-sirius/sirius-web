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

import { expect, type Page } from '@playwright/test';
import { PlaywrightProjectAppearancesSettings } from './PlaywrightProjectAppearancesSettings';

export class PlaywrightProjectSettings {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  async visit(projectId: string): Promise<void> {
    await this.page.goto(`/projects/${projectId}/edit`);
    await this.page.getByTestId('navigation-bar').getByTestId('more').click();
    await this.page.getByTestId('project-settings-link').click();
    await expect(this.page).toHaveURL(new RegExp(`/projects/${projectId}/settings$`));
  }

  async getAppearancesSettings(): Promise<PlaywrightProjectAppearancesSettings> {
    await this.page.getByRole('tab', { name: 'Appearances', exact: true }).click();
    await expect(this.page).toHaveURL(/\/settings\/appearances$/);
    return new PlaywrightProjectAppearancesSettings(this.page);
  }
}
