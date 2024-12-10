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
import { GQLDiagramRefreshedEventPayload } from '../graphql/subscription/diagramEventSubscription.types';

export type DiagramSubscriptionContextValue = {
  diagramRefreshedEventPayload: GQLDiagramRefreshedEventPayload | null;
  refreshEventPayloadId: string;
};

export type DiagramSubscriptionState = {
  id: string;
  diagramRefreshedEventPayload: GQLDiagramRefreshedEventPayload | null;
  complete: boolean;
  message: string;
};

export interface DiagramSubscriptionProviderProps {
  editingContextId: string;
  diagramId: string;
  readOnly: boolean;
}
