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

export interface UseProjectSubscriptionValue {
  loading: boolean;
  payload: GQLProjectEventPayload | null;
  complete: boolean;
}

export interface UseProjectSubscriptionState {
  id: string;
  complete: boolean;
  payload: GQLProjectEventPayload | null;
}

export interface GQLProjectEventSubscription {
  projectEvent: GQLProjectEventPayload;
}

export interface GQLProjectEventVariables {
  input: GQLProjectEventInput;
}

export interface GQLProjectEventInput {
  id: string;
  projectId: string;
}

export interface GQLProjectEventPayload {
  __typename: string;
}

export interface GQLProjectRenamedEventPayload extends GQLProjectEventPayload {
  id: string;
  projectId: string;
  newName: string;
}
