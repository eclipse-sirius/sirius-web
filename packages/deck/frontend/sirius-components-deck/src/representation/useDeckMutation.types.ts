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

import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';
import { Card, Lane } from '../Deck.types';

export interface GQLDeleteCardVariables {
  input: GQLDeleteDeckCardInput;
}

export interface GQLDeleteDeckCardInput {
  id: string;
  editingContextId: string;
  representationId: string;
  cardId: string;
}

export interface GQLDeleteCardData {
  deleteDeckCard: GQLDeleteDeckCardPayload;
}

export type GQLDeleteDeckCardPayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLEditCardVariables {
  input: GQLEditDeckCardInput;
}

export interface GQLEditDeckCardInput {
  id: string;
  editingContextId: string;
  representationId: string;
  cardId: string;
  newTitle: string;
  newDescription: string;
  newLabel: string;
}

export interface GQLEditCardData {
  editDeckCard: GQLEditDeckCardPayload;
}

export type GQLEditDeckCardPayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLCreateDeckCardVariables {
  input: GQLCreateDeckCardInput;
}

export interface GQLCreateDeckCardInput {
  id: string;
  editingContextId: string;
  representationId: string;
  currentLaneId: string;
  title: string;
  label: string;
  description: string;
}

export interface GQLCreateDeckCardData {
  createDeckCard: GQLCreateDeckCardPayload;
}

export type GQLCreateDeckCardPayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLDropDeckCardVariables {
  input: GQLDropDeckCardInput;
}
export interface GQLDropDeckCardInput {
  id: string;
  editingContextId: string;
  representationId: string;
  oldLaneId: string;
  newLaneId: string;
  cardId: string;
  addedIndex: number;
}

export interface GQLDropDeckCardData {
  dropDeckCard: GQLDropDeckCardPayload;
}

export type GQLDropDeckCardPayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLEditLaneVariables {
  input: GQLEditDeckLaneInput;
}

export interface GQLEditDeckLaneInput {
  id: string;
  editingContextId: string;
  representationId: string;
  laneId: string;
  newTitle: string;
}

export interface GQLEditLaneData {
  editDeckLane: GQLEditDeckLanePayload;
}

export type GQLEditDeckLanePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLDropDeckLaneVariables {
  input: GQLDropDeckLaneInput;
}
export interface GQLDropDeckLaneInput {
  id: string;
  editingContextId: string;
  representationId: string;
  laneId: string;
  newIndex: number;
}

export interface GQLDropDeckLaneData {
  dropDeckLane: GQLDropDeckLanePayload;
}

export type GQLDropDeckLanePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLChangeLaneCollapsedStateVariables {
  input: GQLChangeLaneCollapsedStateInput;
}

export interface GQLChangeLaneCollapsedStateInput {
  id: string;
  editingContextId: string;
  representationId: string;
  laneId: string;
  collapsed: boolean;
}

export interface GQLChangeLaneCollapsedStateData {
  changeLaneCollapsedState: GQLChangeLaneCollapsedStatePayload;
}

export type GQLChangeLaneCollapsedStatePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLChangeCardsVisibilityVariables {
  input: GQLChangeCardsVisibilityInput;
}

export interface GQLChangeCardsVisibilityInput {
  id: string;
  editingContextId: string;
  representationId: string;
  visibleCardsIds: string[];
  hiddenCardsIds: string[];
}

export interface GQLChangeCardsVisibilityData {
  changeCardsVisibility: GQLChangeCardsVisibilityPayload;
}

export type GQLChangeCardsVisibilityPayload = GQLErrorPayload | GQLSuccessPayload;

export interface UseDeckMutationsValue {
  editDeckCard: (card: Card) => void;
  createCard: (card: Card, laneId: string) => void;
  deleteCard: (cardId: string) => void;
  dropDeckCard: (oldLaneId: string, newLaneId: string, cardId: string, addedIndex: number) => void;
  editDeckLane: (laneId: string, newValue: { title: string }) => void;
  changeLaneCollapsedState: (laneId: string, collapsed: boolean) => void;
  dropDeckLane: (newIndex: number, payload: Lane) => void;
  changeCardsVisibility: (hiddenCardsIds: string[], visibleCardsIds: string[]) => void;
}
