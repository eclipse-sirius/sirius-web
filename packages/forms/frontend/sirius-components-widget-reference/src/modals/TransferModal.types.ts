/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { GQLReferenceValue, GQLReferenceWidget } from '../ReferenceWidgetFragment.types';
import { FilterableSortableListItem } from '../components/FilterableSortableList.types';

export interface TransferModalProps {
  editingContextId: string;
  formId: string;
  widget: GQLReferenceWidget;
  onClose: () => void;
  addElements: (elementIds: string[]) => void;
  removeElement: (elementId: string) => void;
  moveElement: (elementId: string, fromIndex: number, toIndex: number) => void;
}

export interface TransferModalState {
  leftSelection: FilterableSortableListItem[];
  right: FilterableSortableListItem[];
  rightSelection: FilterableSortableListItem[];
  draggingRightItemId: string | undefined;
  options: GQLReferenceValue[];
  selectedTreeItemIds: string[];
}
