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

export interface UseTreeSubscriptionValue {
  loading: boolean;
  tree: GQLTree | null;
  complete: boolean;
}

export interface UseTreeSubscriptionState {
  id: string;
  tree: GQLTree | null;
  complete: boolean;
}

export interface GQLTreeEventInput {
  id: string;
  representationId: string;
  editingContextId: string;
}

export interface GQLTreeEventVariables {
  input: GQLTreeEventInput;
}

export interface GQLTreeEventData {
  treeEvent: GQLTreeEventPayload;
}

export interface GQLTreeEventPayload {
  __typename: string;
}

export interface GQLTreeRefreshedEventPayload extends GQLTreeEventPayload {
  id: string;
  tree: GQLTree;
}
