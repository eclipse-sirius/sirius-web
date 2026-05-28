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

/*react-resizable-panel uses [ResizeObserver](https://developer.mozilla.org/en-US/docs/Web/API/ResizeObserver) which is currently not supported by JSDom: https://github.com/jsdom/jsdom/issues/3368.
 * So we mock what react-resizable-panel uses from the browser API.
 */

// react-resizable-panels mounts a ResizeObserver on the Group, which jsdom does not implement.
globalThis.ResizeObserver = class ResizeObserver {
  observe() {}
  unobserve() {}
  disconnect() {}
};

// react-resizable-panels builds DOMRect instances when computing hit regions, which jsdom does not implement.
globalThis.DOMRect = class DOMRect {
  constructor(x = 0, y = 0, width = 0, height = 0) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.left = x;
    this.top = y;
    this.right = x + width;
    this.bottom = y + height;
  }

  static fromRect(rect) {
    return new DOMRect(rect?.x, rect?.y, rect?.width, rect?.height);
  }

  toJSON() {
    return { ...this };
  }
};
