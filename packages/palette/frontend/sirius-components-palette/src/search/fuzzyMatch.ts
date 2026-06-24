/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { MatchResult } from './fuzzyMatch.types';

export const fuzzyMatch = (candidate: string, searchTerm: string): MatchResult => {
  const result: MatchResult = {
    candidate,
    searchTerm,
    matches: false,
    matchingIndices: [],
  };
  if (!searchTerm) {
    return result;
  }

  const isCaseSensitive = /^[A-Z]/.test(searchTerm);
  const searchChars = isCaseSensitive ? searchTerm.split('') : searchTerm.toLowerCase().split('');

  const candidateChars = isCaseSensitive ? candidate.split('') : candidate.toLowerCase().split('');

  let searchIdx = 0;

  for (let i = 0; i < candidateChars.length && searchIdx < searchChars.length; i++) {
    if (candidateChars[i] === searchChars[searchIdx]) {
      result.matchingIndices.push(i);
      searchIdx++;
    }
  }

  result.matches = searchIdx === searchChars.length;

  return result;
};
