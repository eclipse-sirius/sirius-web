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
import { GQLTreeItem } from '../views/ExplorerView.types';

export const isFilterCandidate = (treeItem: GQLTreeItem, highlightRegExp: RegExp): boolean => {
  let filter: boolean = false;
  const splitLabelWithTextToHighlight: string[] = treeItem.label.split(highlightRegExp);
  if (splitLabelWithTextToHighlight.length > 1) {
    filter = false;
  } else if (!treeItem.hasChildren && splitLabelWithTextToHighlight.length === 1) {
    filter = true;
  } else if (
    treeItem.hasChildren &&
    treeItem.expanded &&
    treeItem.children.map((child) => child.label.split(highlightRegExp).length).every((v) => v === 1)
  ) {
    filter = treeItem.children.map((child) => isFilterCandidate(child, highlightRegExp)).every((v) => v === true);
  }
  return filter;
};
