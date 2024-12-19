/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import { GQLForm } from '@eclipse-sirius/sirius-components-forms';

export interface UseRepresentationsViewSubscriptionValue {
  loading: boolean;
  payload: GQLRepresentationsEventPayload | null;
  complete: boolean;
}

export interface UseRepresentationsViewSubscriptionState {
  id: string;
  payload: GQLRepresentationsEventPayload | null;
  complete: boolean;
}

export interface GQLRepresentationsEventInput {
  id: string;
  editingContextId: string;
  representationId: string;
}

export interface GQLRepresentationsEventVariables {
  input: GQLRepresentationsEventInput;
}

export interface GQLRepresentationsEventSubscription {
  representationsEvent: GQLRepresentationsEventPayload;
}

export interface GQLRepresentationsEventPayload {
  __typename: string;
}

export interface GQLFormRefreshedEventPayload extends GQLRepresentationsEventPayload {
  __typename: 'FormRefreshedEventPayload';
  form: GQLForm;
}
