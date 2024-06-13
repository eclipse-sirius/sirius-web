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
import { GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface GQLDeckEventSubscription {
  deckEvent: GQLDeckEventPayload;
}

export interface GQLDeckEventPayload {
  __typename: string;
}

export interface GQLDeckRefreshedEventPayload extends GQLDeckEventPayload {
  id: string;
  deck: GQLDeck;
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
  style?: GQLDeckStyle;
}

export interface GQLDeckStyle {
  backgroundColor: string;
}

export interface GQLLane {
  id: string;
  title: string;
  label: string;
  cards: GQLCard[];
  targetObjectId: string;
  collapsible: boolean;
  collapsed: boolean;
  style?: GQLDeckElementStyle;
}

export interface GQLCard {
  id: string;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  title: string;
  label: string;
  description: string;
  visible: boolean;
  style?: GQLDeckElementStyle;
}

export interface GQLDeckElementStyle {
  bold: boolean;
  color: string;
  fontSize: number;
  italic: boolean;
  strikeThrough: boolean;
  underline: boolean;
  backgroundColor: string;
}
