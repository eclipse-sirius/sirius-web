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

import { FontStyle } from './Charts.types';

export const getFontWeight = (style: FontStyle | null): string => {
  if (style?.bold != null) {
    return style.bold ? 'bold' : 'normal';
  }
  return 'normal';
};

export const getFontSize = (style: FontStyle | null): number => {
  //The default size is 10 in D3 but 14 is more confortable.
  return style?.fontSize ?? 14;
};

export const getFontStyle = (style: FontStyle | null): string => {
  if (style?.italic != null) {
    return style.italic ? 'italic' : 'normal';
  }
  return 'normal';
};

export const getTextDecoration = (style: FontStyle | null): string => {
  let decoration: string = '';
  if (style?.strikeThrough != null && style.strikeThrough) {
    decoration = decoration + 'line-through';
  }
  if (style?.underline != null && style.underline) {
    let separator: string = decoration.length > 0 ? ' ' : '';
    decoration = decoration + separator + 'underline';
  }
  return decoration.length > 0 ? decoration : 'none';
};
