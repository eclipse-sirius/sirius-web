/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import {
  GQLButton,
  GQLChartWidget,
  GQLCheckbox,
  GQLFlexboxContainer,
  GQLFlexDirection,
  GQLGroup,
  GQLImage,
  GQLLabelWidget,
  GQLLink,
  GQLList,
  GQLMultiSelect,
  GQLPage,
  GQLRadio,
  GQLRichText,
  GQLSelect,
  GQLTextarea,
  GQLTextfield,
  GQLWidget,
} from '@eclipse-sirius/sirius-components-forms';
import React from 'react';
import { GQLFormDescriptionEditor } from './FormDescriptionEditorEventFragment.types';

export interface WidgetEntryProps {
  editingContextId: string;
  representationId: string;
  formDescriptionEditor: GQLFormDescriptionEditor;
  page: GQLPage;
  container: GQLGroup | GQLFlexboxContainer;
  widget: GQLWidget;
  selection: Selection;
  setSelection: (newSelection: Selection) => void;
  flexDirection: GQLFlexDirection;
  flexGrow: number;
}

export interface WidgetProps<WidgetType extends GQLWidget> {
  widget: WidgetType;
  selection: Selection;
  setSelection: (newSelection: Selection) => void;
  onDropBefore: (event: React.DragEvent<HTMLDivElement>, widget: WidgetType) => void;
}

export type BarChartWidgetProps = WidgetProps<GQLChartWidget>;
export type ButtonWidgetProps = WidgetProps<GQLButton>;
export type CheckboxWidgetProps = WidgetProps<GQLCheckbox>;

export interface FlexboxContainerWidgetProps {
  editingContextId: string;
  representationId: string;
  formDescriptionEditor: GQLFormDescriptionEditor;
  page: GQLPage;
  container: GQLGroup | GQLFlexboxContainer;
  widget: GQLFlexboxContainer;
  selection: Selection;
  setSelection: (newSelection: Selection) => void;
}

export type ImageWidgetProps = WidgetProps<GQLImage>;
export type LabelWidgetProps = WidgetProps<GQLLabelWidget>;
export type LinkWidgetProps = WidgetProps<GQLLink>;
export type ListWidgetProps = WidgetProps<GQLList>;
export type MultiSelectWidgetProps = WidgetProps<GQLMultiSelect>;
export type PieChartWidgetProps = WidgetProps<GQLChartWidget>;
export type RadioWidgetProps = WidgetProps<GQLRadio>;
export type RichTextWidgetProps = WidgetProps<GQLRichText>;
export type SelectWidgetProps = WidgetProps<GQLSelect>;
export type TextareaWidgetProps = WidgetProps<GQLTextarea>;
export type TextfieldWidgetProps = WidgetProps<GQLTextfield>;

export interface WidgetEntryStyleProps {
  flexDirection: GQLFlexDirection;
  flexGrow: number;
  kind: string;
}
