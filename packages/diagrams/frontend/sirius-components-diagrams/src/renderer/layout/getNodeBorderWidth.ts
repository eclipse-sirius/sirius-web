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

export const getNodeBorderWidth = (style: CSSProperties): number => {
  const borderWidthStyle: string | number | undefined = style.borderLeftWidth ?? style.borderWidth;
  if (borderWidthStyle === undefined) {
    return 0;
  }

  const borderWidth: number =
    typeof borderWidthStyle === 'number' ? borderWidthStyle : Number.parseFloat(borderWidthStyle);
  return Number.isFinite(borderWidth) ? borderWidth : 0;
};
