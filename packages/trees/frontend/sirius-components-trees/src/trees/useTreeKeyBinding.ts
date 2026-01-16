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

import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useEffect, useState } from 'react';
import { useContextMenuEntries } from '../treeitems/context-menu/useContextMenuEntries';
import { GQLTreeItemContextMenuEntry } from '../treeitems/context-menu/useContextMenuEntries.types';
import { useInvokeContextMenuEntry } from '../treeitems/context-menu/useInvokeContextMenuEntry';
import { UseTreeKeyBindingState, UseTreeKeyBindingValue } from './useTreeKeyBinding.types';

const entryHasKeyBinding = (entry: GQLTreeItemContextMenuEntry, event: React.KeyboardEvent<Element>) => {
  return entry.keyBindings.some(
    (keyBinding) =>
      keyBinding.key === event.key &&
      keyBinding.isCtrl === event.ctrlKey &&
      keyBinding.isAlt === event.altKey &&
      keyBinding.isMeta === event.metaKey
  );
};

export const useTreeKeyBinding = (editingContextId: string, treeId: string): UseTreeKeyBindingValue => {
  const [state, setState] = useState<UseTreeKeyBindingState>({
    currentEvent: null,
    treeItemId: null,
  });

  const { addErrorMessage } = useMultiToast();

  const skipFetchContextMenuEntries = !state.treeItemId && !state.currentEvent;
  const { contextMenuEntries, loading } = useContextMenuEntries(
    editingContextId,
    treeId,
    state.treeItemId ?? '',
    skipFetchContextMenuEntries
  );
  const { invokeContextMenuEntry } = useInvokeContextMenuEntry();

  useEffect(() => {
    if (!loading && contextMenuEntries && state.currentEvent && state.treeItemId) {
      const entryToExecute = contextMenuEntries.filter((entry) => entryHasKeyBinding(entry, state.currentEvent!));
      if (entryToExecute.length === 1 && entryToExecute[0]) {
        invokeContextMenuEntry(editingContextId, treeId, state.treeItemId, entryToExecute[0], () => {});
      } else if (entryToExecute.length > 1) {
        const keysPressed =
          (state.currentEvent.ctrlKey ? 'CTRL + ' : '') +
          (state.currentEvent.metaKey ? 'META + ' : '') +
          (state.currentEvent.altKey ? 'ALT + ' : '') +
          state.currentEvent.key;
        const entryLabels = entryToExecute.map((entry) => entry.label).join(', ');
        addErrorMessage(`Found multiple entries associated to ${keysPressed}: ${entryLabels}`);
      }
      // There is no entry associated to the pressed keys, don't do anything
      // In any case, reset the current event, we don't need it anymore and we don't want to reuse it.
      setState((prevState) => ({
        ...prevState,
        currentEvent: null,
      }));
    }
  }, [contextMenuEntries, loading, state.currentEvent]);

  const onKeyBinding = useCallback(
    (event: React.KeyboardEvent<Element>) => {
      const { key, altKey, ctrlKey, metaKey, shiftKey } = event;
      const isTextField = event.target instanceof HTMLInputElement || event.target instanceof HTMLTextAreaElement;
      // If a modifier key is hit alone or if the event comes from a text field, do nothing
      if (
        (altKey && key === 'Alt') ||
        (ctrlKey && key === 'Control') ||
        (metaKey && key === 'Meta') ||
        (shiftKey && key === 'Shift') ||
        isTextField
      ) {
        return;
      }

      const treeItemId = (document.activeElement as HTMLElement).dataset.treeitemid || null;
      setState((prevState) => ({
        ...prevState,
        currentEvent: event,
        treeItemId,
      }));

      if (treeItemId) {
        // We have to call preventDefault here and not in the useEffect (once we are sure there is an entry for the key binding):
        // the useEffect happens after the event has been propagated, hence calling preventDefault inside it doesn't do anything.
        event.preventDefault();
      }
    },
    [treeId]
  );

  return {
    onKeyBinding,
  };
};
