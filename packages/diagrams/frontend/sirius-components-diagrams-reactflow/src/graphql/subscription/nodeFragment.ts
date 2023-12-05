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
import { GraphQLNodeStyleFragment } from './nodeFragment.types';

export const nodeFragment = (contributions: GraphQLNodeStyleFragment[]) => `
fragment nodeFragment on Node {
  id
  type
  targetObjectId
  targetObjectKind
  targetObjectLabel
  descriptionId
  state
  insideLabel {
    ...insideLabelFragment
  }
  outsideLabels {
    ...outsideLabelFragment
  }
  style {
    __typename
    ... on RectangularNodeStyle {
      color
      borderColor
      borderStyle
      borderSize
      borderRadius
    }
    ... on ImageNodeStyle {
      imageURL
      borderColor
      borderStyle
      borderSize
      borderRadius
      positionDependentRotation
    }
    ... on IconLabelNodeStyle {
      backgroundColor
    }
     ${contributions.map(
       (nodeStyle) =>
         `
    ... on ${nodeStyle.type} {
      ${nodeStyle.fields}
    }
    `
     )}
  }
  childrenLayoutStrategy {
    __typename
    ... on ListLayoutStrategy {
      kind
    }
    ... on FreeFormLayoutStrategy {
      kind
    }
  }
  defaultWidth
  defaultHeight
  labelEditable
}
`;
