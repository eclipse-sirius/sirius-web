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
import { useData } from '@eclipse-sirius/sirius-components-core';
import { treeItemContextMenuEntryOverrideExtensionPoint } from './TreeItemContextMenuEntryExtensionPoints';
import { TreeItemContextMenuOverrideContribution } from './TreeItemContextMenuEntryExtensionPoints.types';
import { GQLTreeItemContextMenuEntry } from './useContextMenuEntries.types';
import { UseInvokeContextMenuEntryValue } from './useInvokeContextMenuEntry.types';
import { useInvokeFetchContextMenuEntry } from './useInvokeFetchContextMenuEntry';
import { useInvokeSingleClickContextMenuEntry } from './useInvokeSingleClickContextMenuEntry';

export const useInvokeContextMenuEntry = (): UseInvokeContextMenuEntryValue => {
  const { data: treeItemContextMenuOverrideContributions } = useData<TreeItemContextMenuOverrideContribution[]>(
    treeItemContextMenuEntryOverrideExtensionPoint
  );

  const { invokeFetchContextMenuEntry } = useInvokeFetchContextMenuEntry();
  const { invokeSingleClickContextMenuEntry } = useInvokeSingleClickContextMenuEntry();

  const invokeEntry = (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    menuEntry: GQLTreeItemContextMenuEntry,
    onClick: () => void
  ) => {
    if (menuEntry.__typename === 'FetchTreeItemContextMenuEntry') {
      invokeFetchContextMenuEntry(editingContextId, treeId, treeItemId, menuEntry, onClick);
    } else if (menuEntry.__typename === 'SingleClickTreeItemContextMenuEntry') {
      invokeSingleClickContextMenuEntry(editingContextId, treeId, treeItemId, menuEntry, onClick);
    }
  };

  const invokeContextMenuEntry = (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    menuEntry: GQLTreeItemContextMenuEntry,
    onClick: () => void
  ) => {
    const menuEntryIsOverridden = treeItemContextMenuOverrideContributions.some((contribution) =>
      contribution.canHandle(menuEntry)
    );

    if (menuEntryIsOverridden) {
      // Do not attempt to invoke an overridden menu entry with the regular entry invocation mutations:
      // overridden entries define their own behavior and we cannot assume they rely on these mutations.
      return;
    }
    invokeEntry(editingContextId, treeId, treeItemId, menuEntry, onClick);
  };
  return { invokeContextMenuEntry };
};
