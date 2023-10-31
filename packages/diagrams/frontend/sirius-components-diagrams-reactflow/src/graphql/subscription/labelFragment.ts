/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
  type
  text
  style {
    color
    fontSize
    bold
    italic
    underline
    strikeThrough
    iconURL
  }
}
`;

export const insideLabelFragment = `
fragment insideLabelFragment on InsideLabel {
  id
  type
  text
  insideLabelLocation
  isHeader
  displayHeaderSeparator
  style {
    color
    fontSize
    bold
    italic
    underline
    strikeThrough
    iconURL
  }
}
`;
