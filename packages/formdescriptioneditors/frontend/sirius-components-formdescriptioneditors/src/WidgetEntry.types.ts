/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { GQLFlexDirection } from '@eclipse-sirius/sirius-components-forms';
import React from 'react';
import {
  GQLAbstractFormDescriptionEditorWidget,
  GQLFormDescriptionEditorFlexboxContainer,
  GQLFormDescriptionEditorWidget,
} from './FormDescriptionEditorEventFragment.types';

export interface WidgetEntryProps {
  editingContextId: string;
  representationId: string;
  containerId: string | null;
  siblings: GQLAbstractFormDescriptionEditorWidget[];
  widget: GQLAbstractFormDescriptionEditorWidget;
  selection: Selection;
  setSelection: (newSelection: Selection) => void;
  flexDirection: GQLFlexDirection;
  flexGrow: number;
}

export interface WidgetProps {
  widget: GQLFormDescriptionEditorWidget;
  selection: Selection;
  setSelection: (newSelection: Selection) => void;
  onDropBefore: (event: React.DragEvent<HTMLDivElement>, widget: GQLFormDescriptionEditorWidget) => void;
}

export interface FlexboxContainerWidgetProps {
  editingContextId: string;
  representationId: string;
  widget: GQLFormDescriptionEditorFlexboxContainer;
  selection: Selection;
  setSelection: (newSelection: Selection) => void;
}

export interface WidgetEntryState {
  message: string | null;
}

export interface WidgetEntryStyleProps {
  flexDirection: GQLFlexDirection;
  flexGrow: number;
}
