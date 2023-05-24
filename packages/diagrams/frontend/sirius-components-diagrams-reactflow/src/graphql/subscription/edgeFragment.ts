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

export const edgeFragment = `
fragment edgeFragment on Edge {
  id
  type
  targetObjectId
  targetObjectKind
  targetObjectLabel
  descriptionId
  sourceId
  targetId
  state
  beginLabel {
    ...labelFragment
  }
  centerLabel {
    ...labelFragment
  }
  endLabel {
    ...labelFragment
  }
  style {
    size
    lineStyle
    sourceArrow
    targetArrow
    color
  }
  routingPoints {
    x
    y
  }
  sourceAnchorRelativePosition {
    x
    y
  }
  targetAnchorRelativePosition {
    x
    y
  }
}
`;
