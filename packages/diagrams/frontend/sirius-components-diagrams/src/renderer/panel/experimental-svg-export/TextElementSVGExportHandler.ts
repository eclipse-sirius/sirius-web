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
    const textContent: string = textElement.nodeValue ?? '';

    if (parentElement.parentElement?.parentElement?.style.opacity) {
      // The opacity is put on the parent of both the label and the label icon, thus, we have to get opacity from the great-grandparent
      svgTextElement.setAttribute('opacity', parentElement.parentElement?.parentElement?.style.opacity);
    }
    // Copy text styles
    // https://css-tricks.com/svg-properties-and-css
    this.copyTextStyles(styles, svgTextElement);

    // Make sure the y attribute is the bottom of the box, not the baseline
    svgTextElement.setAttribute('dominant-baseline', 'text-after-edge');

    const oneCharRange: Range = svgDocument.createRange();
    oneCharRange.setStart(textElement, 0);
    oneCharRange.setEnd(textElement, 1);
    const oneCharBounds: DOMRect = oneCharRange.getBoundingClientRect();

    const textRange: Range = svgDocument.createRange();
    textRange.setStart(textElement, 0);
    textRange.setEnd(textElement, textElement.nodeValue?.length ?? 0);
    const textBounds: DOMRect = textRange.getBoundingClientRect();

    const isMultiline = textBounds.height > oneCharBounds.height;
    if (!isMultiline) {
      this.handleTextLine(svgTextElement, textContent, textBounds, styles, svgDocument);
    } else {
      const lineRanges: Range[] = this.computeLineRanges(textContent, textElement, oneCharBounds.height, svgDocument);

      lineRanges.forEach((lineRange) => {
        const lineBounds: DOMRect = lineRange.getBoundingClientRect();
        const lineTextContent = lineRange.toString();
        if (lineTextContent) {
          this.handleTextLine(svgTextElement, lineTextContent, lineBounds, styles, svgDocument);
        }
      });
    }
    return svgTextElement;
  }

  private handleTextLine(
    svgTextElement: SVGTextElement,
    textContent: string,
    textBounds: DOMRect,
    parentStyles: CSSStyleDeclaration,
    svgDocument: XMLDocument
  ): void {
    const tabSize = parseInt(parentStyles.tabSize, 10);
    const textSpan: SVGTSpanElement = svgDocument.createElementNS(svgNamespace, 'tspan');
    // TODO: try with a value with many white-space, if it works, use white-space css property
    textSpan.setAttribute('xml:space', 'preserve');
    // SVG does not support tabs in text. Tabs get rendered as one space character. Convert the
    // tabs to spaces according to tab-size instead.
    // Ideally we would keep the tab and create offset tspans.
    // TODO: Test with a value containing a tab character (\t)
    textSpan.textContent = textContent.replace(/\t/g, ' '.repeat(tabSize));
    textSpan.setAttribute('x', textBounds.x.toString());
    textSpan.setAttribute('y', textBounds.bottom.toString()); // intentionally bottom because of dominant-baseline setting
    textSpan.setAttribute('textLength', textBounds.width.toString());
    svgTextElement.append(textSpan);
  }

  /**
   * Compute the ranges of each line in a text element.
   * We suppose the text element is multiline because we first verified that its height is greater than the height of one character.
   *
   * Character by character until the whole text content has been processed, tests if the height of the range is greater than the height of one character.
   * When it becomes greater, we know we have a line break, thus we store the range and start a new one.
   * Before storing the range, we trim the end of the range to remove any space at the end of the line.
   */
  private computeLineRanges(
    textContent: string,
    textElement: Text,
    oneCharHeight: number,
    svgDocument: XMLDocument
  ): Range[] {
    const lineRanges: Range[] = [];
    let i = 0;
    while (i < textContent.length) {
      const lineRange: Range = svgDocument.createRange();
      lineRange.setStart(textElement, i);
      let shouldSwitchLine = false;
      let j = i + 1;
      do {
        lineRange.setEnd(textElement, j);
        const lineBounds: DOMRect = lineRange.getBoundingClientRect();
        if (lineBounds.height > oneCharHeight) {
          // We have a line break, update the range end to the previous character
          lineRange.setEnd(textElement, --j);
          shouldSwitchLine = true;
        } else if (j >= textContent.length) {
          // We reached the end of the text content
          shouldSwitchLine = true;
        } else {
          // Only increase the range when the line is not complete
          ++j;
        }
      } while (!shouldSwitchLine);
      i = j;
      const lineLength = lineRange.toString().length;
      const trimmedLineLength = lineRange.toString().trimEnd().length;
      if (trimmedLineLength < lineLength) {
        // We trimmed some spaces at the end of the line, we can remove them from the range
        lineRange.setEnd(textElement, j - (lineLength - trimmedLineLength));
      }
      lineRanges.push(lineRange);
    }
    return lineRanges;
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
