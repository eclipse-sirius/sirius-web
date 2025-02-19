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
import { IElementSVGExportHandler, svgNamespace } from './SVGExportEngine.types';

const TEXT_NEW_LINE_SEPARATOR = '\n';

/**
 * Convert a dom element marked as a text with the attribute 'data-svg="text"' into a svg text element.
 * It converts carriage return into many tspan element (one tspan for a line)
 * It converts tabulations into space based on the css 'tab-size' property.
 * Does not support automatic text wrap.
 */
export class TextElementSVGExportHandler implements IElementSVGExportHandler {
  canHandle(element: Element): boolean {
    return element.getAttribute('data-svg') === 'text';
  }
  handle(element: Element, parentSvgElement: SVGElement, svgDocument: XMLDocument): SVGElement[] {
    if (element instanceof HTMLElement && element.firstChild instanceof Text) {
      const svgTextElement = this.handleTextElement(element.firstChild, element, svgDocument);
      parentSvgElement.appendChild(svgTextElement);
    }
    return [];
  }

  private handleTextElement(textElement: Text, parentElement: HTMLElement, svgDocument: XMLDocument): SVGTextElement {
    const svgTextElement: SVGTextElement = svgDocument.createElementNS(svgNamespace, 'text');
    const styles = parentElement.style;
    // Copy text styles
    // https://css-tricks.com/svg-properties-and-css
    this.copyTextStyles(styles, svgTextElement);

    const tabSize = parseInt(styles.tabSize, 10);

    // Make sure the y attribute is the bottom of the box, not the baseline
    svgTextElement.setAttribute('dominant-baseline', 'text-after-edge');
    const lines = (textElement?.nodeValue ?? '').split(TEXT_NEW_LINE_SEPARATOR);
    if (lines.length === 1 && lines[0]) {
      const lineRange: Range = textElement.ownerDocument.createRange();
      lineRange.setStart(textElement, 0);
      lineRange.setEnd(textElement, lines[0].length);
      let lineRectangle = lineRange.getBoundingClientRect();
      const textSpan = svgDocument.createElementNS(svgNamespace, 'tspan');
      // TODO: try with a value with many white-space, if it works, use white-space css property
      textSpan.setAttribute('xml:space', 'preserve');
      // SVG does not support tabs in text. Tabs get rendered as one space character. Convert the
      // tabs to spaces according to tab-size instead.
      // Ideally we would keep the tab and create offset tspans.
      // TODO: Test with a value containing a tab character (\t)
      textSpan.textContent = lines[0].replace(/\t/g, ' '.repeat(tabSize));
      textSpan.setAttribute('x', lineRectangle.x.toString());
      textSpan.setAttribute('y', lineRectangle.bottom.toString()); // intentionally bottom because of dominant-baseline setting
      textSpan.setAttribute('textLength', lineRectangle.width.toString());
      textSpan.setAttribute('lengthAdjust', 'spacingAndGlyphs');
      svgTextElement.append(textSpan);
    } else if (lines.length > 1) {
      let textIndex = 0;
      lines.forEach((lineValue) => {
        const lineRange: Range = textElement.ownerDocument.createRange();
        lineRange.setStart(textElement, textIndex);
        lineRange.setEnd(textElement, textIndex + lineValue.length);
        let lineRectangle = lineRange.getBoundingClientRect();
        const textSpan = svgDocument.createElementNS(svgNamespace, 'tspan');
        // TODO: try with a value with many white-space, if it works, use white-space css property
        textSpan.setAttribute('xml:space', 'preserve');
        // SVG does not support tabs in text. Tabs get rendered as one space character. Convert the
        // tabs to spaces according to tab-size instead.
        // Ideally we would keep the tab and create offset tspans.
        // TODO: Test with a value containing a tab character (\t) with and without the replace by space
        textSpan.textContent = lineValue.replace(/\t/g, ' '.repeat(tabSize));
        textSpan.setAttribute('x', lineRectangle.x.toString());
        textSpan.setAttribute('y', lineRectangle.bottom.toString()); // intentionally bottom because of dominant-baseline setting
        textSpan.setAttribute('textLength', lineRectangle.width.toString());
        textSpan.setAttribute('lengthAdjust', 'spacingAndGlyphs');
        svgTextElement.append(textSpan);
        textIndex = textIndex + lineValue.length + TEXT_NEW_LINE_SEPARATOR.length;
        // TODO: try with a text with ellipsis and see the difference in chrome and firefox
        // TODO: If there is an issue look for an hint in `@dom-to-svg$text.ts#handleTextNode (l.93)`
      });
    }
    return svgTextElement;
  }

  private copyTextStyles(styles: CSSStyleDeclaration, svgElement: SVGElement): void {
    for (const textProperty of textAttributes) {
      const value = styles.getPropertyValue(textProperty);
      if (value) {
        svgElement.setAttribute(textProperty, value);
      }
    }
    // tspan uses fill, CSS uses color
    svgElement.setAttribute('fill', styles.color);
  }
}

const textAttributes = new Set([
  'color',
  'dominant-baseline',
  'font-family',
  'font-size',
  'font-size-adjust',
  'font-stretch',
  'font-style',
  'font-variant',
  'font-weight',
  'direction',
  'letter-spacing',
  'text-decoration',
  'text-anchor',
  'text-decoration',
  'text-rendering',
  'unicode-bidi',
  'word-spacing',
  'writing-mode',
  'user-select',
] as const);
