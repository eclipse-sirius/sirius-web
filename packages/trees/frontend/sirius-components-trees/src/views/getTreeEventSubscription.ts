/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
export const getTreeEventSubscription = (depth: number): string => {
  const treeChildren = recursiveGetChildren(depth);
  const subscription = `
subscription treeEvent($input: TreeEventInput!) {
  treeEvent(input: $input) {
    __typename
    ... on TreeRefreshedEventPayload {
      id
      tree {
        id
        children {
          ${treeChildren}
        }
      }
    }
  }
}
`;
  return subscription + fragment;
};

const fragment = `
fragment treeItemFields on TreeItem {
  id
  hasChildren
  expanded
  label {
    styledStringFragments {
        text
        styledStringFragmentStyle {
            isStrikedout
            underlineStyle
            borderStyle
            font
            backgroundColor
            foregroundColor
            strikeoutColor
            underlineColor
            borderColor
            }
        }
    }
  editable
  deletable
  selectable
  kind
  iconURL
}
`;

const recursiveGetChildren = (depth: number): string => {
  let children = '';
  if (depth > 0) {
    children = `
    children {
      ${recursiveGetChildren(depth - 1)}
    }`;
  }
  return `...treeItemFields${children}`;
};
