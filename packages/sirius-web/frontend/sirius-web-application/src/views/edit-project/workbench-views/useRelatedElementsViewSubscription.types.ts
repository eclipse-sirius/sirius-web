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

export interface UseRelatedElementsViewSubscriptionValue {
  loading: boolean;
  form: GQLForm | null;
  complete: boolean;
}

export interface UseRelatedElementsViewSubscriptionState {
  id: string;
  form: GQLForm | null;
  complete: boolean;
}

export interface GQLRelatedElementsEventInput {
  id: string;
  editingContextId: string;
  objectIds: string[];
}

export interface GQLRelatedElementsEventVariables {
  input: GQLRelatedElementsEventInput;
}

export interface GQLRelatedElementsEventSubscription {
  relatedElementsEvent: GQLRelatedElementsEventPayload;
}

export interface GQLRelatedElementsEventPayload {
  __typename: string;
}

export interface GQLFormRefreshedEventPayload extends GQLRelatedElementsEventPayload {
  __typename: 'FormRefreshedEventPayload';
  form: GQLForm;
}
