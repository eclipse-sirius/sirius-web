/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { GQLTreeItem } from '../views/ExplorerView.types';

export interface TreeItemProps {
  editingContextId: string;
  treeId: string;
  item: GQLTreeItem;
  depth: number;
  onExpand: (id: string, depth: number) => void;
  onExpandAll: (treeItem: GQLTreeItem) => void;
  selection: Selection;
  setSelection: (selection: Selection) => void;
  readOnly: boolean;
  textToHighlight: string | null;
  isFilterEnabled: boolean;
}
