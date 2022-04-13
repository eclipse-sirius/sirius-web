/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import { Chart } from 'charts/Charts.types';
export interface RepresentationMetadata {
  label: string;
}

export interface Form {
  id: string;
  metadata: RepresentationMetadata;
  pages: Page[];
}

export interface Subscriber {
  username: string;
}

export interface Page {
  id: string;
  label: string;
  groups: Group[];
}
export interface Group {
  id: string;
  label: string;
  displayMode: GroupDisplayMode;
  widgets: Widget[];
}

export type GroupDisplayMode = 'LIST' | 'TOGGLEABLE_AREAS';

export interface Widget {
  id: string;
  label: string;
  iconURL: string | null;
  __typename: string;
  diagnostics: Diagnostic[];
}

export interface Diagnostic {
  id: string;
  kind: string;
  message: string;
}

export interface WidgetSubscription {
  widgetId: string;
  subscribers: Subscriber[];
}

export interface Textfield extends Widget {
  stringValue: string;
  style: TextfieldStyle | null;
}

export interface TextfieldStyle {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface Textarea extends Widget {
  stringValue: string;
  style: TextareaStyle | null;
}

export interface TextareaStyle {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface Checkbox extends Widget {
  booleanValue: boolean;
  style: CheckboxStyle | null;
}

export interface CheckboxStyle {
  color: string | null;
}

export interface Select extends Widget {
  value: string;
  options: SelectOption[];
  style: SelectStyle | null;
}

export interface SelectStyle {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface MultiSelect extends Widget {
  values: string[];
  options: SelectOption[];
  style: MultiSelectStyle | null;
}

export interface MultiSelectStyle {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface SelectOption {
  id: string;
  label: string;
}

export interface Radio extends Widget {
  options: RadioOption[];
  style: RadioStyle | null;
}

export interface RadioOption {
  id: string;
  label: string;
  selected: boolean;
}

export interface RadioStyle {
  color: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface List extends Widget {
  items: ListItem[];
}

export interface ListItem {
  id: string;
  label: string;
  kind: string;
  imageURL: string;
  deletable: boolean;
}

export interface Link extends Widget {
  url: string;
}

export interface ChartWidget extends Widget {
  label: string;
  chart: Chart;
}

export interface FlexboxContainer extends Widget {
  label: string;
  flexDirection: FlexDirection;
  flexWrap: FlexWrap;
  flexGrow: number;
  children: Widget[];
}

export type FlexDirection = 'row' | 'row-reverse' | 'column' | 'column-reverse';

export type FlexWrap = 'nowrap' | 'wrap' | 'wrap-reverse';

export interface Tree extends Widget {
  label: string;
  nodes: TreeNode[];
  expandedNodesIds: string[];
}

export interface TreeNode {
  id: string;
  parentId: string;
  label: string;
  kind: string;
  imageURL: string;
  selectable: Boolean;
}
