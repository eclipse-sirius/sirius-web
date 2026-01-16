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

import { useEffect, useState } from 'react';
import { GQLTreeItemContextMenuEntry } from './useContextMenuEntries.types';
import { useFetchContextMenuEntryData } from './useFetchContextMenuEntryData';
import {
  UseInvokeFetchContextMenuEntryState,
  UseInvokeFetchContextMenuEntryValue,
} from './useInvokeFetchContextMenuEntry.types';

export const useInvokeFetchContextMenuEntry = (): UseInvokeFetchContextMenuEntryValue => {
  const [state, setState] = useState<UseInvokeFetchContextMenuEntryState>({
    onClick: () => {},
  });

  const { getFetchContextMenuEntryData, loading, fetchData } = useFetchContextMenuEntryData();

  useEffect(() => {
    if (fetchData) {
      const { urlToFetch, fetchKind } = fetchData;
      if (fetchKind === 'DOWNLOAD') {
        window.location.href = urlToFetch;
      } else if (fetchKind === 'OPEN') {
        window.open(urlToFetch, '_blank', 'noopener,noreferrer');
      }
      state.onClick();
    }
  }, [loading, fetchData]);

  const invokeFetchContextMenuEntry = (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    menuEntry: GQLTreeItemContextMenuEntry,
    onClick: () => void
  ) => {
    setState((prevState) => ({
      ...prevState,
      onClick,
    }));
    getFetchContextMenuEntryData(editingContextId, treeId, treeItemId, menuEntry.id);
  };

  return { invokeFetchContextMenuEntry };
};
