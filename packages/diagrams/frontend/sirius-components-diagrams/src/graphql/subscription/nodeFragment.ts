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

export const nodeFragment = `
fragment nodeFragment on Node {
  id
  type
  targetObjectId
  targetObjectKind
  targetObjectLabel
  descriptionId
  state
  pinned
  insideLabel {
    ...insideLabelFragment
  }
  outsideLabels {
    ...outsideLabelFragment
  }
  style {
    __typename
    ... on RectangularNodeStyle {
      background
      borderColor
      borderStyle
      borderSize
      borderRadius
      childrenLayoutStrategy {
        ...childrenLayoutStrategyFragment
      }
    }
    ... on ImageNodeStyle {
      imageURL
      borderColor
      borderStyle
      borderSize
      borderRadius
      positionDependentRotation
      childrenLayoutStrategy {
        ...childrenLayoutStrategyFragment
      }
    }
    ... on IconLabelNodeStyle {
      background
      childrenLayoutStrategy {
        ...childrenLayoutStrategyFragment
      }
    }
  }
  defaultWidth
  defaultHeight
  labelEditable
  deletable
  customizedStyleProperties
  initialBorderNodePosition
}

fragment childrenLayoutStrategyFragment on ILayoutStrategy {
  __typename
  ... on ListLayoutStrategy {
    kind
    areChildNodesDraggable
    topGap
    bottomGap
    growableNodeIds
  }
  ... on FreeFormLayoutStrategy {
    kind
  }
}
`;
