/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { SetURLSearchParams } from 'react-router-dom';
import { Selection, SelectionEntry } from './SelectionContext.types';
import { useSelection } from './useSelection';
import { UseSelectionValue } from './useSelection.types';

export const updateUrlSearchParamsWithSelection = (selection: Selection, setUrlSearchParams: SetURLSearchParams) => {
  setUrlSearchParams((previousSearchParams: URLSearchParams) => {
    if (selection.entries.length > 0) {
      const selectionValue: string = selection.entries
        .map((selectionEntry) => `${selectionEntry.id}${urlSelectionEntryContentSeparator}${selectionEntry.kind}`)
        .join(urlSelectionEntriesSeparator);
      previousSearchParams.set(urlSelectionSearchParameterName, selectionValue);
    } else {
      if (previousSearchParams.has(urlSelectionSearchParameterName)) {
        previousSearchParams.delete(urlSelectionSearchParameterName);
      }
    }
    return previousSearchParams;
  });
};

export const updateSelectionBasedOnUrlSearchParamsIfNeeded = (urlSearchParams: URLSearchParams) => {
  const { selection, setSelection }: UseSelectionValue = useSelection();
  if (urlSearchParams.has(urlSelectionSearchParameterName)) {
    const urlSelection: Selection | null = createSelectionFromUrlSearchParams(urlSearchParams);
    if (urlSelection !== null && !areSelectionContentsEqual(urlSelection, selection)) {
      setSelection(urlSelection);
    }
  }
};

export const createSelectionFromUrlSearchParams = (urlSearchParams: URLSearchParams): Selection | null => {
  if (urlSearchParams !== null && urlSearchParams.has(urlSelectionSearchParameterName)) {
    const urlSelectionSearchParam: string = urlSearchParams.get(urlSelectionSearchParameterName) ?? '';
    const urlSelectionContents: string[] = urlSelectionSearchParam.split(urlSelectionEntriesSeparator);
    const urlSelectionEntries: SelectionEntry[] = urlSelectionContents
      .map((urlSelectionContent) => {
        const [projectElementId, projectElementKind]: string[] = urlSelectionContent.split(
          urlSelectionEntryContentSeparator
        );
        if (projectElementId && projectElementKind) {
          return { id: projectElementId, kind: projectElementKind };
        } else {
          return null;
        }
      })
      .filter((selectionEntry) => selectionEntry !== null) as Array<SelectionEntry>;
    return { entries: urlSelectionEntries };
  }
  return null;
};

const areSelectionContentsEqual = (left: Selection, right: Selection): boolean => {
  return (
    left.entries.length == right.entries.length &&
    left.entries.every(
      (element, index) => element.id === right.entries[index]?.id && element.kind === right.entries[index]?.kind
    )
  );
};
const urlSelectionSearchParameterName: string = 'selection';
const urlSelectionEntriesSeparator: string = ',';
const urlSelectionEntryContentSeparator: string = ';';
