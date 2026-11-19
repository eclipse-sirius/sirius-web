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

import { CSSProperties } from 'react';
import { BorderRadius } from './getNodeInnerBorderRadius.types';

const innerRadius = (radius: string | number | undefined, borderWidth: string | number | undefined): number => {
  return Math.max(0, Number.parseFloat(String(radius ?? 0)) - Number.parseFloat(String(borderWidth ?? 0)));
};

export const getNodeInnerBorderRadius = (style: CSSProperties): BorderRadius => {
  const topLeft = style.borderTopLeftRadius ?? style.borderRadius;
  const topRight = style.borderTopRightRadius ?? style.borderRadius;
  const bottomRight = style.borderBottomRightRadius ?? style.borderRadius;
  const bottomLeft = style.borderBottomLeftRadius ?? style.borderRadius;
  // The background fills the padding box, inside the node's borders.
  const topWidth = style.borderTopWidth ?? style.borderWidth;
  const rightWidth = style.borderRightWidth ?? style.borderWidth;
  const bottomWidth = style.borderBottomWidth ?? style.borderWidth;
  const leftWidth = style.borderLeftWidth ?? style.borderWidth;
  return {
    borderTopLeftRadius: `${innerRadius(topLeft, leftWidth)}px ${innerRadius(topLeft, topWidth)}px`,
    borderTopRightRadius: `${innerRadius(topRight, rightWidth)}px ${innerRadius(topRight, topWidth)}px`,
    borderBottomRightRadius: `${innerRadius(bottomRight, rightWidth)}px ${innerRadius(bottomRight, bottomWidth)}px`,
    borderBottomLeftRadius: `${innerRadius(bottomLeft, leftWidth)}px ${innerRadius(bottomLeft, bottomWidth)}px`,
  };
};
