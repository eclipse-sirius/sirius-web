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

export interface TreeItemProps {
  editingContextId: string;
  item: TreeItemType;
  depth: number;
  onExpand: (id: string, depth: number) => void;
  selection?: Selection;
  setSelection: (selection: Selection) => void;
  readOnly: boolean;
}

export interface TreeItemType {
  id: string;
  label: string;
  kind: string;
  imageURL: string;
  editable: boolean;
  deletable: boolean;
  expanded: boolean;
  hasChildren: boolean;
  children: TreeItemType[];
}
