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
import { GQLDiagramEventPayload } from '../graphql/subscription/diagramEventSubscription.types';
export interface UseDiagramSubscriptionValue {
  loading: boolean;
  payload: GQLDiagramEventPayload | null;
  complete: boolean;
}

export interface UseDiagramSubscriptionState {
  id: string;
  complete: boolean;
  payload: GQLDiagramEventPayload | null;
}

export interface GQLDiagramEventInput {
  id: string;
  editingContextId: string;
  diagramId: string;
}

export interface GQLDiagramEventVariables {
  input: GQLDiagramEventInput;
}

export interface GQLDiagramEventSubscription {
  diagramEvent: GQLDiagramEventPayload;
}
