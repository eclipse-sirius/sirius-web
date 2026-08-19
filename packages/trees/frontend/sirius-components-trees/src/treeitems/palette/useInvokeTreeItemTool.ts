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
import { GQLTool } from '@eclipse-sirius/sirius-components-palette';
import {
  GQLFetchTreeItemContextMenuEntry,
  GQLSingleClickTreeItemContextMenuEntry,
} from '../context-menu/useContextMenuEntries.types';
import { treeItemContextMenuEntryOverrideExtensionPoint } from './../context-menu/TreeItemContextMenuEntryExtensionPoints';
import { TreeItemContextMenuOverrideContribution } from './../context-menu/TreeItemContextMenuEntryExtensionPoints.types';
import { useInvokeFetchContextMenuEntry } from './../context-menu/useInvokeFetchContextMenuEntry';
import { useInvokeSingleClickContextMenuEntry } from './../context-menu/useInvokeSingleClickContextMenuEntry';
import { isFetchTreeItemTool, isSingleClickTreeItemTool } from './TreeItemPalette';
import { UseInvokeTreeItemToolValue } from './useInvokeTreeItemTool.types';

export const useInvokeTreeItemTool = (): UseInvokeTreeItemToolValue => {
  const { data: treeItemContextMenuOverrideContributions } = useData<TreeItemContextMenuOverrideContribution[]>(
    treeItemContextMenuEntryOverrideExtensionPoint
  );

  const { invokeFetchContextMenuEntry } = useInvokeFetchContextMenuEntry();
  const { invokeSingleClickContextMenuEntry } = useInvokeSingleClickContextMenuEntry();

  const invokeTreeItemTool = (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    tool: GQLTool,
    onClick: () => void
  ) => {
    if (isFetchTreeItemTool(tool)) {
      const menuEntry: GQLFetchTreeItemContextMenuEntry = {
        ...tool,
        __typename: 'FetchTreeItemContextMenuEntry',
      };
      invokeFetchContextMenuEntry(editingContextId, treeId, treeItemId, menuEntry, onClick);
    } else if (isSingleClickTreeItemTool(tool)) {
      const menuEntry: GQLSingleClickTreeItemContextMenuEntry = {
        ...tool,
        withImpactAnalysis: tool.withImpactAnalysis,
        __typename: 'SingleClickTreeItemContextMenuEntry',
      };
      invokeSingleClickContextMenuEntry(editingContextId, treeId, treeItemId, menuEntry, onClick);
    }
  };

  const invokeTool = (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    tool: GQLTool,
    onClick: () => void
  ) => {
    const menuEntryIsOverridden = treeItemContextMenuOverrideContributions.some((contribution) => {
      if (isSingleClickTreeItemTool(tool)) {
        const menuEntry: GQLSingleClickTreeItemContextMenuEntry = {
          ...tool,
          withImpactAnalysis: tool.withImpactAnalysis,
          __typename: 'SingleClickTreeItemContextMenuEntry',
        };
        return contribution.canHandle(menuEntry);
      } else if (isFetchTreeItemTool(tool)) {
        const menuEntry: GQLFetchTreeItemContextMenuEntry = {
          ...tool,
          __typename: 'FetchTreeItemContextMenuEntry',
        };
        return contribution.canHandle(menuEntry);
      }
      return false;
    });

    if (menuEntryIsOverridden) {
      // Do not attempt to invoke an overridden menu entry with the regular entry invocation mutations:
      // overridden entries define their own behavior and we cannot assume they rely on these mutations.
      return;
    }
    invokeTreeItemTool(editingContextId, treeId, treeItemId, tool, onClick);
  };
  return { invokeTreeItemTool: invokeTool };
};
