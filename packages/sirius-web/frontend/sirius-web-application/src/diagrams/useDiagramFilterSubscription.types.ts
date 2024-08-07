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

export interface UseDiagramFilterSubscriptionValue {
  loading: boolean;
  payload: GQLDiagramFilterEventPayload | null;
  complete: boolean;
}

export interface UseDiagramFilterSubscriptionState {
  id: string;
  complete: boolean;
}

export interface GQLDiagramFilterEventInput {
  id: string;
  editingContextId: string;
  objectIds: string[];
}

export interface GQLDiagramFilterEventVariables {
  input: GQLDiagramFilterEventInput;
}

export interface GQLDiagramFilterEventSubscription {
  diagramFilterEvent: GQLDiagramFilterEventPayload;
}

export interface GQLDiagramFilterEventPayload {
  __typename: string;
}

export interface GQLFormRefreshedEventPayload extends GQLDiagramFilterEventPayload {
  __typename: 'FormRefreshedEventPayload';
  form: GQLForm;
}
