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

import { Card } from '../Deck.types';

export interface DeckLaneHeaderProps {
  cards: Card[];
  id: string;
  updateTitle: (value: string) => void;
  editLaneTitle: boolean;
  label: string;
  title: string;
  t: (value: string) => string; // the translate function passed by the Board to handle i18n
  laneDraggable: boolean;
}

export interface DeckLaneHeaderStateValue {
  showContextMenu: boolean;
  menuAnchor: Element | null;
}
