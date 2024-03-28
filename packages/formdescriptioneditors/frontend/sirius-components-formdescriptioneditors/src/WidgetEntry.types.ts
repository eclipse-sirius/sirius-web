/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import {
  GQLButton,
  GQLChartWidget,
  GQLCheckbox,
  GQLFlexDirection,
  GQLFlexboxContainer,
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
  GQLSlider,
  GQLSplitButton,
  GQLTextarea,
  GQLTextfield,
  GQLTree,
  GQLWidget,
} from '@eclipse-sirius/sirius-components-forms';
import React from 'react';

export interface WidgetEntryProps {
  page: GQLPage;
  container: GQLGroup | GQLFlexboxContainer;
  widget: GQLWidget;
  flexDirection: GQLFlexDirection;
  flexGrow: number;
}

export interface WidgetProps<WidgetType extends GQLWidget> {
  widget: WidgetType;
  onDropBefore: (event: React.DragEvent<HTMLDivElement>, widget: WidgetType) => void;
}

export type BarChartWidgetProps = WidgetProps<GQLChartWidget>;
export type ButtonWidgetProps = WidgetProps<GQLButton>;
export type CheckboxWidgetProps = WidgetProps<GQLCheckbox>;

export interface SplitButtonWidgetProps extends WidgetProps<GQLSplitButton> {
  editingContextId: string;
  representationId: string;
}

export interface FlexboxContainerWidgetProps {
  page: GQLPage;
  container: GQLGroup | GQLFlexboxContainer;
  widget: GQLFlexboxContainer;
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
export type SliderWidgetProps = WidgetProps<GQLSlider>;
export type TextareaWidgetProps = WidgetProps<GQLTextarea>;
export type TextfieldWidgetProps = WidgetProps<GQLTextfield>;
export type TreeWidgetProps = WidgetProps<GQLTree>;

export interface WidgetEntryState {
  message: string | null;
}

export interface WidgetEntryStyleProps {
  flexDirection: GQLFlexDirection;
  flexGrow: number;
  kind: string;
}
