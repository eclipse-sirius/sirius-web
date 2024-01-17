/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { getTextFromStyledString, splitText } from '@eclipse-sirius/sirius-components-core';
import { GQLTreeItem } from '../views/TreeView.types';

export const isFilterCandidate = (treeItem: GQLTreeItem, textToFilter: string | null): boolean => {
  let filter: boolean = false;
  const splitLabelWithTextToHighlight: string[] = splitText(getTextFromStyledString(treeItem.label), textToFilter);
  if (textToFilter === null || textToFilter === '') {
    filter = false;
  } else if (splitLabelWithTextToHighlight.length > 1) {
    filter = false;
  } else if (
    !treeItem.hasChildren &&
    splitLabelWithTextToHighlight.length === 1 &&
    splitLabelWithTextToHighlight[0].toLocaleLowerCase() !== textToFilter.toLocaleLowerCase()
  ) {
    filter = true;
  } else if (
    splitLabelWithTextToHighlight.length === 1 &&
    splitLabelWithTextToHighlight[0].toLocaleLowerCase() === textToFilter.toLocaleLowerCase()
  ) {
    filter = false;
  } else if (
    textToFilter &&
    treeItem.hasChildren &&
    treeItem.expanded &&
    treeItem.children
      .map(
        (child) =>
          getTextFromStyledString(child.label).toLocaleLowerCase().split(textToFilter.toLocaleLowerCase()).length
      )
      .every((v) => v === 1)
  ) {
    filter = treeItem.children.map((child) => isFilterCandidate(child, textToFilter)).every((v) => v === true);
  }
  return filter;
};
