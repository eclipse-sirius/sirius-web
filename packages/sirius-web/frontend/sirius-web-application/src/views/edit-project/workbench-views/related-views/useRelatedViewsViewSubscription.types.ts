/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

export interface UseRelatedViewsViewSubscriptionValue {
  loading: boolean;
  payload: GQLRelatedViewsEventPayload | null;
  complete: boolean;
}

export interface UseRelatedViewsViewSubscriptionState {
  id: string;
  payload: GQLRelatedViewsEventPayload | null;
  complete: boolean;
}

export interface GQLRelatedViewsEventInput {
  id: string;
  editingContextId: string;
  representationId: string;
}

export interface GQLRelatedViewsEventVariables {
  input: GQLRelatedViewsEventInput;
}

export interface GQLRelatedViewsEventSubscription {
  relatedViewsEvent: GQLRelatedViewsEventPayload;
}

export interface GQLRelatedViewsEventPayload {
  __typename: string;
}

export interface GQLFormRefreshedEventPayload extends GQLRelatedViewsEventPayload {
  __typename: 'FormRefreshedEventPayload';
  form: GQLForm;
}

export interface GQLFormCapabilitiesRefreshedEventPayload extends GQLRelatedViewsEventPayload {
  __typename: 'FormCapabilitiesRefreshedEventPayload';
  capabilities: GQLFormCapabilities;
}

export interface GQLFormCapabilities {
  canEdit: boolean;
}
