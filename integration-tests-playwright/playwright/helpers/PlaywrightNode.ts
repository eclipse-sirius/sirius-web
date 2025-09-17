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
import { type Locator, type Page } from '@playwright/test';

export class PlaywrightNode {
  readonly page: Page;
  readonly nodeLocator: Locator;

  constructor(page: Page, name: string, type: string = 'FreeForm') {
    this.page = page;
    this.nodeLocator = this.page.locator(`[data-testid="${type} - ${name}"]`).first().locator('..');
  }

  async click() {
    await this.nodeLocator.click({ position: { x: 10, y: 10 } });
  }

  async controlClick() {
    await this.nodeLocator.click({ position: { x: 10, y: 10 }, modifiers: ['ControlOrMeta'] });
  }

  async resetNodeLabelPosition() {
    this.openPalette();
    await this.page.locator(`[data-testid="reset-label-position"]`).first().click();
  }

  async getDOMXYPosition() {
    const box = (await this.nodeLocator.boundingBox())!;
    return {
      x: box.x,
      y: box.y,
    };
  }

  async getDOMBoundingBox() {
    return (await this.nodeLocator.boundingBox())!;
  }

  async getReactFlowXYPosition(index = 0, withClick = true) {
    if (withClick) {
      await this.nodeLocator.click({ position: { x: 10, y: 10 } });
    }
    const nodePanel = this.page.locator(`div[data-testid="nodePanelInfos"]`).nth(index);

    const xSpan = nodePanel.locator('span:has-text("x :")');
    const xText = await xSpan.textContent();
    const xValue = Number(xText?.split(':')[1].trim());

    const ySpan = nodePanel.locator('span:has-text("y :")');
    const yText = await ySpan.textContent();
    const yValue = Number(yText?.split(':')[1].trim());

    return {
      x: xValue,
      y: yValue,
    };
  }

  async getReactFlowSize(index = 0, withClick = true) {
    if (withClick) {
      await this.nodeLocator.click({ position: { x: 10, y: 10 } });
    }
    const nodePanel = this.page.locator(`div[data-testid="nodePanelInfos"]`).nth(index);

    const heightSpan = nodePanel.locator('span:has-text("Height :")');
    const heightText = await heightSpan.textContent();
    const heightValue = Number(heightText?.split(':')[1].trim());

    const widthSpan = nodePanel.locator('span:has-text("Height :")');
    const widthText = await widthSpan.textContent();
    const widthValue = Number(widthText?.split(':')[1].trim());

    return {
      height: heightValue,
      width: widthValue,
    };
  }

  async move(offset: { x: number; y: number }) {
    const xyPosition = await this.getDOMXYPosition();
    await this.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await this.page.mouse.down();
    await this.page.mouse.move(xyPosition.x + offset.x, xyPosition.y + offset.y, { steps: 10 });
    await this.page.mouse.up();
  }

  async resize(offset: { height: number; width: number }, anchor: string = 'bottom.right') {
    const resizeAnchor = this.page.locator(`.react-flow__resize-control.${anchor}`);

    const box = (await resizeAnchor.boundingBox())!;
    await resizeAnchor.hover();
    await this.page.mouse.down();
    await this.page.mouse.move(box.x + offset.height, box.y + offset.width, { steps: 2 });
    await this.page.mouse.up();
  }

  async openPalette() {
    await this.nodeLocator.click({ button: 'right', position: { x: 10, y: 10 } });
  }

  async closePalette() {
    await this.page.getByTestId('Close-palette').click();
  }
}
