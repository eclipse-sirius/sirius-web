/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

export const labelFragment = `
fragment labelFragment on Label {
  id
  text
  style {
    color
    fontSize
    bold
    italic
    underline
    strikeThrough
    iconURL
    background
    borderColor
    borderStyle
    borderSize
    borderRadius
    maxWidth
  }
}
`;

export const insideLabelFragment = `
fragment insideLabelFragment on InsideLabel {
  id
  text
  insideLabelLocation
  isHeader
  headerSeparatorDisplayMode
  overflowStrategy
  textAlign
  style {
    color
    fontSize
    bold
    italic
    underline
    strikeThrough
    iconURL
    background
    borderColor
    borderStyle
    borderSize
    borderRadius
    maxWidth
  }
  customizedStyleProperties
}
`;

export const outsideLabelFragment = `
fragment outsideLabelFragment on OutsideLabel {
  id
  text
  outsideLabelLocation
  textAlign
  style {
    color
    fontSize
    bold
    italic
    underline
    strikeThrough
    iconURL
    background
    borderColor
    borderStyle
    borderSize
    borderRadius
    maxWidth
  }
  overflowStrategy
}
`;
