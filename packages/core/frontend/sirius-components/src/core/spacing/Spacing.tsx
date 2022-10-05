/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import React, { CSSProperties } from 'react';

export const XS = '4px';
export const S = '8px';
export const M = '16px';
export const L = '32px';
export const XL = '64px';
export const XXL = '128px';

/**
 * Used to control the padding of an element.
 *
 * @author sbegaudeau
 */
export const Spacing = ({
  children = undefined,
  top = undefined,
  right = undefined,
  bottom = undefined,
  left = undefined,
}) => {
  const style = {} as CSSProperties;

  if (top) style.paddingTop = top;
  if (right) style.paddingRight = right;
  if (bottom) style.paddingBottom = bottom;
  if (left) style.paddingLeft = left;

  return <div style={style}>{children}</div>;
};
