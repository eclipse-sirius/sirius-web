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

import React from 'react';
import { Card } from '../Deck.types';
import { DeckTagProps } from './DeckTag.types';

export interface DeckCardProps {
  style: React.CSSProperties;
  tagStyle: React.CSSProperties;
  onClick: (event: React.MouseEvent<HTMLDivElement>) => void;
  onDelete: () => void;
  onChange: (card: Card) => void;
  className: string;
  id: string;
  title: string;
  label: string;
  description: string;
  tags: DeckTagProps[];
  cardDraggable: boolean;
  editable: boolean;
  t: (value: string) => string; // the translate function passed by the Board to handle i18n
}
