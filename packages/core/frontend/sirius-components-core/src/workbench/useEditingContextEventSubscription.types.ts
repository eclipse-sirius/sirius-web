/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
export interface GQLEditingContextEventPayload {
  __typename: string;
}

export interface GQLRepresentationRenamedEventPayload extends GQLEditingContextEventPayload {
  id: string;
  representationId: string;
  newLabel: string;
}

export type GQLEditingContextEventSubscription = {
  editingContextEvent: GQLEditingContextEventPayload;
};

export type UseEditingContextEventSubscriptionValue = {
  loading: boolean;
  payload: GQLEditingContextEventPayload | null;
  complete: boolean;
};

export type UseEditingContextEventSubscriptionState = {
  id: string;
  payload: GQLEditingContextEventPayload | null;
  complete: boolean;
};

export type GQLEditingContextEventInput = {
  id: string;
  editingContextId: string;
};

export type GQLEditingContextEventVariables = {
  input: GQLEditingContextEventInput;
};
