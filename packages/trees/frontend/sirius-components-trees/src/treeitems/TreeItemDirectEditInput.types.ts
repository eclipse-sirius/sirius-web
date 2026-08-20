/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

export interface TreeItemDirectEditInputProps {
  editingContextId: string;
  treeId: string;
  treeItemId: string;
  editingKey: string | null;
  onClose: () => void;
}

export interface TreeItemDirectEditInputState {
  newLabel: string;
}

export interface GQLInitialDirectEditElementLabelVariables {
  editingContextId: string;
  representationId: string;
  treeItemId: string;
}

export interface GQLInitialDirectEditElementLabelData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata;
}

export interface GQLRepresentationMetadata {
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  __typename: string;
  initialDirectEditTreeItemLabel: string;
}

export interface GQLRenameTreeItemMutationVariables {
  input: GQLRenameTreeItemInput;
}

export interface GQLRenameTreeItemInput {
  id: string;
  editingContextId: string;
  representationId: string;
  treeItemId: string;
  newLabel: string;
}

export interface GQLRenameTreeItemMutationData {
  renameTreeItem: GQLRenameTreeItemPayload;
}

export type GQLRenameTreeItemPayload = GQLSuccessPayload | GQLErrorPayload;
