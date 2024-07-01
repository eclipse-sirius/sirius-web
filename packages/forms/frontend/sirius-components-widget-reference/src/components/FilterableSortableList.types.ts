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
import React from 'react';
import { GQLReferenceValue } from '../ReferenceWidgetFragment.types';

export interface FilterableSortableListProps {
  items: FilterableSortableListItem[];
  options: GQLReferenceValue[];
  setItems: (items: FilterableSortableListItem[]) => void;
  handleDragItemStart: (id: string) => void;
  handleDragItemEnd: () => void;
  handleDropNewItem: (event: React.DragEvent) => void;
  onClick: (event: React.MouseEvent<Element, MouseEvent>, item: FilterableSortableListItem) => void;
  moveElement: (elementId: string, fromIndex: number, toIndex: number) => void;
  selectedItems: FilterableSortableListItem[];
}

export interface FilterableSortableListItem {
  id: string;
  label: string;
  kind: string;
}

export interface FilterableSortableListState {
  filterBarText: string;
  hoveringItemId: string | undefined;
  draggingItemId: string | undefined;
  draggingStartIndex: number;
  draggingIndex: number;
}

export interface HighlightedLabelProps {
  label: string;
  textToHighlight: string;
}
