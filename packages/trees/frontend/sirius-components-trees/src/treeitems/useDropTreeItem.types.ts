/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface GQLDropTreeItemPayload {
  __typename: string;
}

export interface GQLDropTreeItemData {
  dropTreeItem: GQLDropTreeItemPayload;
}

export interface GQLDropTreeItemVariables {
  input: GQLDropTreeItemInput;
}

export interface GQLDropTreeItemInput {
  id: string;
  editingContextId: string;
  representationId: string;
  droppedElementIds: string[];
  targetElementId: string | null;
  index: number;
}

export interface GQLErrorPayload extends GQLDropTreeItemPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLDropTreeItemPayload {
  messages: GQLMessage[];
}
