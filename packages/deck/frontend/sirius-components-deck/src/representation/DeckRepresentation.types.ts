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
import { GQLMessage } from '@eclipse-sirius/sirius-components-core/src/graphql/GQLTypes.types';

export interface DeckRepresentationState {
  id: string;
  deck: GQLDeck | null;
  complete: boolean;
}
export interface GQLDeckEventSubscription {
  deckEvent: GQLDeckEventPayload;
}

export interface GQLDeckEventPayload {
  __typename: string;
}

export interface GQLSubscribersUpdatedEventPayload extends GQLDeckEventPayload {
  id: string;
  subscribers: GQLSubscriber[];
}

export interface GQLSubscriber {
  username: string;
}

export interface GQLDeckRefreshedEventPayload extends GQLDeckEventPayload {
  id: string;
  deck: GQLDeck;
}

export interface Subscriber {
  username: string;
}

export interface GQLErrorPayload extends GQLDeckEventPayload {
  messages: GQLMessage[];
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
}

export interface GQLDeck {
  id: string;
  metadata: GQLRepresentationMetadata;
  targetObjectId: string;
  lanes: GQLLane[];
}

export interface GQLLane {
  id: string;
  title: string;
  label: string;
  cards: GQLCard[];
}

export interface GQLCard {
  id: string;
  title: string;
  label: string;
  description: string;
}
