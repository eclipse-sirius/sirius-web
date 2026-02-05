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
import type { Page } from '@playwright/test';

export class PlaywrightDiagram {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  async hideDebugPanel() {
    // Hide Node Panel Info to avoid overlap in diagram
    const panel = this.page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });
  }
}
