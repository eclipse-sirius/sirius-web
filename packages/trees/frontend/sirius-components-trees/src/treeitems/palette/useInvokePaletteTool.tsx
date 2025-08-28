/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import { GQLTreeItem } from '../../views/TreeView.types';
import { GQLTool } from '../palette/TreeItemPalette.types';
import { useDelete } from './toolsMutation/useDelete';
import { UseInvokePaletteToolValue } from './useInvokePaletteTool.types';

export const useInvokePaletteTool = (): UseInvokePaletteToolValue => {
  const { handleDelete } = useDelete();

  const invokeTool = (
    editingContextId: string,
    diagramElementId: string,
    treeItem: GQLTreeItem,
    onDirectEditClick: () => void,
    tool: GQLTool
  ) => {
    switch (tool.id) {
      case 'edit':
        onDirectEditClick();
        break;
      case 'semantic-delete':
        handleDelete(editingContextId, diagramElementId, treeItem);
        break;
    }
  };
  return { invokeTool };
};
