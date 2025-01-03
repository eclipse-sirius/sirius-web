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
import { GQLTree } from '@eclipse-sirius/sirius-components-trees';

export interface UseExplorerSubscriptionValue {
  loading: boolean;
  payload: GQLTreeEventPayload | null;
  complete: boolean;
}

export interface UseExplorerSubscriptionState {
  id: string;
  complete: boolean;
  payload: GQLTreeEventPayload | null;
}

export interface GQLExplorerEventInput {
  id: string;
  editingContextId: string;
  representationId: string;
}

export interface GQLExplorerEventVariables {
  input: GQLExplorerEventInput;
}

export interface GQLExplorerEventData {
  explorerEvent: GQLTreeEventPayload;
}

export interface GQLTreeEventPayload {
  __typename: string;
}

export interface GQLTreeRefreshedEventPayload extends GQLTreeEventPayload {
  id: string;
  tree: GQLTree;
}
