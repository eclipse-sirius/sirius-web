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
export interface GQLDeleteDeckCardPayload {
  __typename: string;
}

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
export interface GQLEditDeckCardPayload {
  __typename: string;
}

export interface GQLCreateCardVariables {
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
export interface GQLCreateCardData {
  createCard: GQLCreateDeckCardPayload;
}
export interface GQLCreateDeckCardPayload {
  __typename: string;
}