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
import { ClassNameMap } from '@material-ui/core/styles/withStyles';
import { Selection } from 'workbench/Workbench.types';
import { TreeItemType } from './TreeItem.types';

export interface TreeItemHandler {
  handles: (treeItem: TreeItemType) => boolean;
  getModal: (name: string) => TreeItemModalComponent;
  getMenuEntries: (
    item: TreeItemType,
    editingContextId: string,
    readOnly: boolean,
    openModal: (modalName: string) => void,
    closeContextMenu: () => void,
    classes: ClassNameMap<'item'>
  ) => Array<React.ReactElement>;
}

export interface TreeItemModalComponentProps {
  editingContextId: string;
  item: TreeItemType;
  depth: number;
  selection: Selection;
  setSelection: (selection: Selection) => void;
  onExpand: (id: string, depth: number) => void;
  onClose: () => void;
  readOnly: boolean;
}

export type TreeItemModalComponent = (props: TreeItemModalComponentProps) => JSX.Element;

export interface TreeItemContextMenuProps {
  menuAnchor: Element;
  item: TreeItemType;
  editingContextId: string;
  readOnly: boolean;
  enterEditingMode: () => void;
  openModal: (modalName: string) => void;
  deleteItem: () => void;
  closeContextMenu: () => void;
  treeItemHandler: TreeItemHandler;
}
