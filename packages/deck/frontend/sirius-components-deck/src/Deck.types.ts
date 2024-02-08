/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { SelectionEntry } from '@eclipse-sirius/sirius-components-core';
import { CSSProperties } from 'react';

export interface DeckProps {
  editingContextId: string;
  representationId: string;
  data: DeckData;
  onCardClick: (cardId: string, metadata: CardMetadata, laneId: string) => void;
  onLaneClick: (laneId: string) => void;
  onCardUpdate: (laneId: string, card: Card) => void;
  onCardAdd: (card: Card, laneId: string) => void;
  onCardDelete: (cardId: string, laneId: string) => void;
  onCardMoveAcrossLanes: (oldLaneId: string, newLaneId: string, cardId: string, addedIndex: number) => void;
  onLaneUpdate: (laneId: string, newValue: { title: string }) => void;
  onLaneCollapseUpdate: (laneId: string, collapsed: boolean) => void;
  handleLaneDragEnd: (oldIndew: number, newIndew: number, payload: Lane) => void;
}

export interface DeckState {
  zoom: number;
}
export interface OnCardClickProps {
  cardId: String;
  metadata: any;
  laneId: String;
}
export interface DeckData {
  lanes: Lane[];
}

export interface Lane {
  id: string;
  title: string;
  label: string;
  cards: Card[];
  editLaneTitle?: boolean;
  editable: boolean;
  collapsible?: boolean;
  collapsed?: boolean;
  style?: CSSProperties;
  'data-testid': string;
}
export interface Card {
  id: string;
  title: string;
  label: string;
  description: string;
  metadata?: CardMetadata;
  className?: string;
  editable?: boolean;
}

export interface CardMetadata {
  selection: SelectionEntry;
}
