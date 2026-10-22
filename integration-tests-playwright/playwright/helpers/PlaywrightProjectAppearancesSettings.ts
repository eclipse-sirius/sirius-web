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

import { expect, type Locator, type Page } from '@playwright/test';
import { PlaywrightProjectAppearancesSettingsSpy } from './PlaywrightProjectAppearancesSettingsSpy';

export class PlaywrightProjectAppearancesSettings {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  getStyleCustomizationRow(name: string): Locator {
    return this.page.getByRole('row').filter({ has: this.page.getByRole('cell', { name, exact: true }) });
  }

  getStyleCustomizationCheckbox(name: string): Locator {
    return this.getStyleCustomizationRow(name).getByRole('checkbox');
  }

  getStyleCustomizationCheckboxes(): Locator {
    return this.page.getByRole('table').getByRole('checkbox');
  }

  spyOnUpdateRequests(): PlaywrightProjectAppearancesSettingsSpy {
    return new PlaywrightProjectAppearancesSettingsSpy(this.page);
  }

  async expectStyleCustomizationState(
    name: string,
    { checked, readOnly = false }: { checked: boolean; readOnly?: boolean }
  ): Promise<void> {
    const checkbox = this.getStyleCustomizationCheckbox(name);
    await expect(checkbox).toBeVisible();
    await expect(checkbox).toBeChecked({ checked });
    await expect(checkbox).toBeEnabled({ enabled: !readOnly });
  }
}
