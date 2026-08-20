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
import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';
import { GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';

export interface DeleteMenuItemProps {
  editingContextId: string;
  treeId: string;
  item: GQLTreeItem;
  readOnly: boolean;
  onClick: () => void;
}

export interface UseDeleteValue {
  handleDelete: (editingContextId: string, treeId: string, item: GQLTreeItem) => void;
}

export interface GQLDeleteTreeItemData {
  deleteTreeItem: GQLDeleteTreeItemPayload;
}

export type GQLDeleteTreeItemPayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLDeleteTreeItemVariables {
  input: GQLDeleteTreeItemInput;
}

export interface GQLDeleteTreeItemInput {
  id: string;
  editingContextId: string;
  representationId: string;
  treeItemId: string;
}
