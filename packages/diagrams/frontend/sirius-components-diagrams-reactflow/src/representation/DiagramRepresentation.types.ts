/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import {
  GQLDiagramEventPayload,
  GQLDiagramRefreshedEventPayload,
} from '../graphql/subscription/diagramEventSubscription.types';

export interface DiagramRepresentationState {
  id: string;
  diagramRefreshedEventPayload: GQLDiagramRefreshedEventPayload | null;
  complete: boolean;
  message: string | null;
}

export interface GQLDiagramEventData {
  diagramEvent: GQLDiagramEventPayload;
}

export interface GQLDiagramEventVariables {
  input: GQLDiagramEventInput;
}

export interface GQLDiagramEventInput {
  id: string;
  editingContextId: string;
  diagramId: string;
}
