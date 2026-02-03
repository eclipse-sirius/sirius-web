/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import { WorkbenchViewHandle } from '@eclipse-sirius/sirius-components-core';
import { ForwardedRef, RefObject, useImperativeHandle } from 'react';
import { SearchQuery } from './useSearch.types';
import { EMPTY_QUERY } from './useSearchQuery';

export const useSearchViewHandle = (
  id: string,
  searchQueryRef: RefObject<SearchQuery | null>,
  ref: ForwardedRef<WorkbenchViewHandle>
) => {
  const detailsViewHandlerProvider = () => {
    return {
      id,
      getWorkbenchViewConfiguration: () => {
        return {
          searchQuery: searchQueryRef.current || EMPTY_QUERY,
        };
      },
      applySelection: null,
    };
  };
  useImperativeHandle(ref, detailsViewHandlerProvider, [id]);
};
