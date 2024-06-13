/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

export interface GQLFormEventSubscription {
  formEvent: GQLFormEventPayload;
}

export interface GQLFormEventPayload {
  __typename: string;
}

export interface GQLPropertiesEventVariables {
  input: GQLPropertiesEventInput;
}

export interface GQLPropertiesEventInput {
  id: string;
  editingContextId: string;
  objectIds: string[];
}

export interface GQLPropertiesEventSubscription {
  propertiesEvent: GQLPropertiesEventPayload;
}

export interface GQLRelatedElementsEventSubscription {
  relatedElementsEvent: GQLPropertiesEventPayload;
}

export interface GQLPropertiesEventPayload {
  __typename: string;
}

export interface GQLRepresentationsEventSubscription {
  representationsEvent: GQLRepresentationsEventPayload;
}

export interface GQLRepresentationsEventPayload {
  __typename: string;
}

export interface GQLFormRefreshedEventPayload
  extends GQLFormEventPayload,
    GQLPropertiesEventPayload,
    GQLRepresentationsEventPayload {
  id: string;
  form: GQLForm;
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  id: string;
}

export interface GQLForm {
  id: string;
  metadata: GQLRepresentationMetadata;
  pages: GQLPage[];
}

export interface GQLPage {
  id: string;
  label: string;
  toolbarActions: GQLToolbarAction[];
  groups: GQLGroup[];
}

export interface GQLGroup {
  id: string;
  label: string;
  displayMode: GQLGroupDisplayMode;
  toolbarActions: GQLToolbarAction[];
  widgets: GQLWidget[];
  borderStyle: GQLContainerBorderStyle;
  __typename: string;
}

export interface GQLContainerBorderStyle {
  color: string | null;
  lineStyle: string;
  size: number;
  radius: number;
}

export type GQLGroupDisplayMode = 'LIST' | 'TOGGLEABLE_AREAS';

export interface GQLWidget {
  id: string;
  label: string;
  iconURL: string[];
  __typename: string;
  diagnostics: GQLDiagnostic[];
  hasHelpText: boolean;
  readOnly: boolean;
}

export interface GQLDiagnostic {
  id: string;
  kind: string;
  message: string;
}

export interface GQLTextfield extends GQLWidget {
  stringValue: string;
  supportsCompletion: boolean;
  style: GQLTextfieldStyle;
}

export interface GQLTextfieldStyle {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface GQLTextarea extends GQLWidget {
  stringValue: string;
  supportsCompletion: boolean;
  style: GQLTextareaStyle;
}

export interface GQLTextareaStyle {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface GQLCheckbox extends GQLWidget {
  booleanValue: boolean;
  style: GQLCheckboxStyle;
}

export interface GQLCheckboxStyle {
  color: string | null;
  labelPlacement: 'end' | 'start' | 'top' | 'bottom';
}

export interface GQLSelect extends GQLWidget {
  value: string;
  options: GQLSelectOption[];
  style: GQLSelectStyle;
}

export interface GQLSelectStyle {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface GQLMultiSelect extends GQLWidget {
  values: string[];
  options: GQLSelectOption[];
  style: GQLMultiSelectStyle;
}

export interface GQLMultiSelectStyle {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface GQLSelectOption {
  id: string;
  label: string;
  iconURL: string[];
}

export interface GQLRadio extends GQLWidget {
  options: GQLRadioOption[];
  style: GQLRadioStyle;
}

export interface GQLRadioStyle {
  color: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface GQLRadioOption {
  id: string;
  label: string;
  selected: boolean;
}

export interface GQLList extends GQLWidget {
  items: GQLListItem[];
  style: GQLListStyle;
}

export interface GQLListStyle {
  color: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface GQLListItem {
  id: string;
  label: string;
  kind: string;
  iconURL: string[];
  deletable: Boolean;
}

export interface GQLLink extends GQLWidget {
  url: string;
  style: GQLLinkStyle;
}

export interface GQLLinkStyle {
  color: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface GQLButton extends GQLWidget {
  buttonLabel: string | null;
  imageURL: string | null;
  style: GQLButtonStyle;
}

export interface GQLSplitButton extends GQLWidget {
  actions: GQLButton[];
}

export interface GQLButtonStyle {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | string | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface GQLToolbarAction extends GQLWidget {
  buttonLabel: string | null;
  imageURL: string | null;
  style: GQLButtonStyle;
}

export interface GQLChartWidget extends GQLWidget {
  chart: GQLChart;
}

interface GQLRepresentation {
  metadata: GQLRepresentationMetadata;
}

export type GQLChart = GQLBarChart | GQLPieChart;

export interface GQLBarChart extends GQLRepresentation {
  label: string;
  entries: GQLBarChartEntry[];
  style: GQLBarChartStyle | null;
  width: number;
  height: number;
}

export interface GQLFontStyle {
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface GQLBarChartStyle extends GQLFontStyle {
  barsColor: string | null;
}

export interface GQLBarChartEntry {
  key: string;
  value: number;
}

export interface GQLPieChart extends GQLRepresentation {
  label: string;
  entries: GQLPieChartEntry[];
  style: GQLPieChartStyle | null;
}

export interface GQLPieChartStyle extends GQLFontStyle {
  colors: string[] | null;
  strokeColor: string | null;
  strokeWidth: number | null;
}

export interface GQLPieChartEntry {
  key: string;
  value: number;
}

export interface GQLLabelWidget extends GQLWidget {
  stringValue: string;
  style: GQLLabelWidgetStyle;
}

export interface GQLLabelWidgetStyle {
  color: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface GQLContainer extends GQLWidget {
  children: GQLWidget[];
}

export interface GQLFlexboxContainer extends GQLContainer {
  flexDirection: GQLFlexDirection;
  flexWrap: GQLFlexWrap;
  flexGrow: number;
  borderStyle?: GQLContainerBorderStyle;
}

export type GQLFlexDirection = 'row' | 'row-reverse' | 'column' | 'column-reverse';

export type GQLFlexWrap = 'nowrap' | 'wrap' | 'wrap-reverse';

export interface GQLTree extends GQLWidget {
  label: string;
  nodes: GQLTreeNode[];
  expandedNodesIds: string[];
}

export interface GQLTreeNode {
  id: string;
  parentId: string;
  label: string;
  kind: string;
  iconURL: string[];
  endIconsURL: string[][];
  selectable: boolean;
  checkable: boolean;
  value: boolean;
}

export interface GQLImage extends GQLWidget {
  url: string;
  maxWidth: string;
}

export interface GQLRichText extends GQLWidget {
  label: string;
  stringValue: string;
}

export interface GQLSlider extends GQLWidget {
  label: string;
  minValue: number;
  maxValue: number;
  currentValue: number;
}

export interface GQLDateTime extends GQLWidget {
  stringValue: string;
  type: string;
  style: GQLDateTimeStyle | null;
}

export interface GQLDateTimeStyle {
  foregroundColor: string | null;
  backgroundColor: string | null;
  italic: boolean | null;
  bold: boolean | null;
}
