/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { Selection } from 'workbench/Workbench.types';
import { TreeItemType } from './TreeItem.types';

export interface TreeItemContextMenuProps {
  menuAnchor: Element;
  item: TreeItemType;
  editingContextId: string;
  readOnly: boolean;
  depth: number;
  onExpand: (id: string, depth: number) => void;
  selection: Selection;
  setSelection: (selection: Selection) => void;
  enterEditingMode: () => void;
  deleteItem: () => void;
  onClose: () => void;
}
