/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

export interface TreeItemDirectEditInputProps {
  editingContextId: string;
  treeId: string;
  treeItemId: string;
  editingKey: string;
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
  representation: GQLRepresentation;
}

export interface GQLRepresentation {
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  __typename: string;
  initialDirectEditTreeItemLabel: string;
}

export interface GQLRenameTreeItemPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLRenameTreeItemPayload {
  message: string;
}

export interface GQLErrorPayload extends GQLRenameTreeItemPayload {
  message: string;
}
