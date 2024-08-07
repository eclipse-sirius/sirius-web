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

import { GQLForm } from '@eclipse-sirius/sirius-components-forms';

export interface UseDetailsViewSubscriptionValue {
  loading: boolean;
  payload: GQLDetailsEventPayload | null;
  complete: boolean;
}

export interface UseDetailsViewSubscriptionState {
  id: string;
  complete: boolean;
  payload: GQLDetailsEventPayload | null;
}

export interface GQLDetailsEventInput {
  id: string;
  editingContextId: string;
  objectIds: string[];
}

export interface GQLDetailsEventVariables {
  input: GQLDetailsEventInput;
}

export interface GQLDetailsEventSubscription {
  detailsEvent: GQLDetailsEventPayload;
}

export interface GQLDetailsEventPayload {
  __typename: string;
}

export interface GQLFormRefreshedEventPayload extends GQLDetailsEventPayload {
  __typename: 'FormRefreshedEventPayload';
  form: GQLForm;
}
