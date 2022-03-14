/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { Label } from 'diagram/sprotty/Diagram.types';
import { EditLabelUI, getAbsoluteClientBounds, getZoom } from 'sprotty';

export class EditLabelUIWithInitialContent extends EditLabelUI {
  protected applyTextContents() {
    if (this.label instanceof Label) {
      this.editControl.value = this.label.initialText || this.label.text;
      if (this.label.preSelect) {
        this.editControl.setSelectionRange(0, this.editControl.value.length);
      }
    } else {
      super.applyTextContents();
    }
  }

  /**
   * Overriden to have the same editing area size and to center it with the edited label
   */
  protected setPosition(containerElement: HTMLElement) {
    let x = 0;
    let y = 0;
    let width = 100;
    let height = 20;
    // used to avoid the scrollbar
    const extraWidth: number = 50;
    const extraHeight: number = 10;

    if (this.label) {
      const nbLines: number = this.label.text.split('\n').length;
      const zoom = getZoom(this.label);
      const bounds = getAbsoluteClientBounds(this.label, this.domHelper, this.viewerOptions);
      // make the edit area centered on the label
      x = bounds.x + (bounds.width * (1 - 1 / zoom)) / 2;
      y = bounds.y;
      height = height * nbLines + extraHeight;
      width = bounds.width / zoom + extraWidth;
    }

    containerElement.style.left = `${x}px`;
    containerElement.style.top = `${y}px`;
    containerElement.style.width = `${width}px`;
    this.editControl.style.width = `${width}px`;
    containerElement.style.height = `${height}px`;
    this.editControl.style.height = `${height}px`;
  }

  /**
   * Overriden to keep the same font size whatever the zoom and to center the text
   */
  protected applyFontStyling() {
    // super.applyFontStyling();
    if (this.label) {
      this.labelElement = document.getElementById(this.domHelper.createUniqueDOMElementId(this.label));
      if (this.labelElement) {
        this.labelElement.style.visibility = 'hidden';
        const style = window.getComputedStyle(this.labelElement);
        this.editControl.style.font = style.font;
        this.editControl.style.fontStyle = style.fontStyle;
        this.editControl.style.fontFamily = style.fontFamily;
        this.editControl.style.fontWeight = style.fontWeight;
        this.editControl.style.textAlign = 'center';
      }
    }
  }
}
