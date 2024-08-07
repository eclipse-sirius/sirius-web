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

import { GQLForm } from '../form/FormEventFragments.types';

export interface UseFormSubscriptionValue {
  loading: boolean;
  payload: GQLFormEventPayload | null;
  complete: boolean;
}

export interface UseFormSubscriptionState {
  id: string;
  complete: boolean;
}

export interface GQLFormEventInput {
  id: string;
  editingContextId: string;
  formId: string;
}

export interface GQLFormEventVariables {
  input: GQLFormEventInput;
}

export interface GQLFormEventSubscription {
  formEvent: GQLFormEventPayload;
}

export interface GQLFormEventPayload {
  __typename: string;
}

export interface GQLFormRefreshedEventPayload extends GQLFormEventPayload {
  __typename: 'FormRefreshedEventPayload';
  form: GQLForm;
}
