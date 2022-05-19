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
  widgets: Widget[];
}
export interface Widget {
  id: string;
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
  label: string;
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
  label: string;
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
  label: string;
  booleanValue: boolean;
  style: CheckboxStyle | null;
}

export interface CheckboxStyle {
  color: string | null;
}

export interface Select extends Widget {
  label: string;
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
  label: string;
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
  label: string;
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
  label: string;
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
  label: string;
  url: string;
}
