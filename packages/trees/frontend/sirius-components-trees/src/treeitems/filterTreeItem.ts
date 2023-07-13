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
import { GQLTreeItem } from '../views/TreeView.types';

export const splitText = (label: string, userInput: string | null): string[] => {
  if (!userInput) {
    return [label];
  }

  // Split the label in a case insensitive manner
  const caseInsensitiveSplitLabel: string[] = label
    .toLocaleLowerCase()
    .split(userInput.toLocaleLowerCase())
    .flatMap((value, index, array) => {
      if (index === 0 && value === '') {
        return [];
      } else if (index === array.length - 1 && value === '') {
        return [userInput.toLocaleLowerCase()];
      } else if (index === 0) {
        return [value];
      }
      return [userInput.toLocaleLowerCase(), value];
    });

  // Create the real result
  const splitLabel: string[] = [];
  let index = 0;
  for (const caseInsensitiveSegment of caseInsensitiveSplitLabel) {
    const caseSensitiveSegment = label.substring(index, index + caseInsensitiveSegment.length);
    splitLabel.push(caseSensitiveSegment);
    index = index + caseInsensitiveSegment.length;
  }

  return splitLabel;
};

export const isFilterCandidate = (treeItem: GQLTreeItem, textToHighlight: string | null): boolean => {
  let filter: boolean = false;
  const splitLabelWithTextToHighlight: string[] = splitText(treeItem.label, textToHighlight);
  if (textToHighlight === null || textToHighlight === '') {
    filter = false;
  } else if (splitLabelWithTextToHighlight.length > 1) {
    filter = false;
  } else if (
    !treeItem.hasChildren &&
    splitLabelWithTextToHighlight.length === 1 &&
    splitLabelWithTextToHighlight[0].toLocaleLowerCase() !== textToHighlight.toLocaleLowerCase()
  ) {
    filter = true;
  } else if (
    splitLabelWithTextToHighlight.length === 1 &&
    splitLabelWithTextToHighlight[0].toLocaleLowerCase() === textToHighlight.toLocaleLowerCase()
  ) {
    filter = false;
  } else if (
    textToHighlight &&
    treeItem.hasChildren &&
    treeItem.expanded &&
    treeItem.children
      .map((child) => child.label.toLocaleLowerCase().split(textToHighlight.toLocaleLowerCase()).length)
      .every((v) => v === 1)
  ) {
    filter = treeItem.children.map((child) => isFilterCandidate(child, textToHighlight)).every((v) => v === true);
  }
  return filter;
};
