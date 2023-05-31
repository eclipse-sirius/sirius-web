/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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

export const nodeFragment = `
fragment nodeFragment on Node {
  id
  type
  targetObjectId
  targetObjectKind
  targetObjectLabel
  descriptionId
  state
  label {
    ...labelFragment
  }
  style {
    __typename
    ... on RectangularNodeStyle {
      color
      borderColor
      borderStyle
      borderSize
      borderRadius
      withHeader
    }
    ... on ImageNodeStyle {
      imageURL
      borderColor
      borderStyle
      borderSize
      borderRadius
    }
    ... on IconLabelNodeStyle {
      backgroundColor
    }
  }
  position {
    x
    y
  }
  size {
    width
    height
  }
  userResizable
}
`;
