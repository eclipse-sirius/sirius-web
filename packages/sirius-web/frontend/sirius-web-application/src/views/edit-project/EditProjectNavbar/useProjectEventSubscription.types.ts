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
export interface GQLProjectEventPayload {
  __typename: string;
}

export type UseProjectEventSubscriptionValue = {
  loading: boolean;
  payload: GQLProjectEventPayload | null;
  complete: boolean;
};

export type UseProjectEventSubscriptionState = {
  id: string;
  payload: GQLProjectEventPayload | null;
  complete: boolean;
};

export type GQLProjectEventInput = {
  id: string;
  projectId: string;
};

export type GQLProjectEventVariables = {
  input: GQLProjectEventInput;
};

export interface GQLProjectEventSubscription {
  projectEvent: GQLProjectEventPayload;
}

export interface GQLProjectEventPayload {
  __typename: string;
}

export interface GQLProjectRenamedEventPayload extends GQLProjectEventPayload {
  id: string;
  projectId: string;
  newName: string;
}
