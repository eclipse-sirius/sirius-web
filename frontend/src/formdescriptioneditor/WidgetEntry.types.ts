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
import React from 'react';
import { Selection } from 'workbench/Workbench.types';
import { Widget } from './FormDescriptionEditorWebSocketContainer.types';

export interface WidgetEntryProps {
  widget: Widget;
  selection: Selection;
  setSelection: (newSelection: Selection) => void;
  onDropBefore: (event: React.DragEvent<HTMLDivElement>, widget: Widget) => void;
}

export interface WidgetProps {
  widget: Widget;
  selection: Selection;
  setSelection: (newSelection: Selection) => void;
  onDropBefore: (event: React.DragEvent<HTMLDivElement>, widget: Widget) => void;
}
