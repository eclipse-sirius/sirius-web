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

import { GQLDiagram } from './diagramFragment.types';

export interface GQLDiagramEventPayload {
  id: string;
  __typename: string;
}

export interface GQLErrorPayload extends GQLDiagramEventPayload {
  message: string;
}

export interface GQLSubscribersUpdatedEventPayload extends GQLDiagramEventPayload {
  subscribers: GQLSubscriber[];
}

export interface GQLSubscriber {
  username: string;
}

export interface GQLDiagramRefreshedEventPayload extends GQLDiagramEventPayload {
  diagram: GQLDiagram;
}
