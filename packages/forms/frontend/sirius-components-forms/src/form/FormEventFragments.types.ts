/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

export interface GQLSubscribersUpdatedEventPayload
  extends GQLFormEventPayload,
    GQLPropertiesEventPayload,
    GQLRepresentationsEventPayload {
  id: string;
  subscribers: GQLSubscriber[];
}

export interface GQLWidgetSubscriptionsUpdatedEventPayload
  extends GQLFormEventPayload,
    GQLPropertiesEventPayload,
    GQLRepresentationsEventPayload {
  id: string;
  widgetSubscriptions: GQLWidgetSubscription[];
}

export interface GQLWidgetSubscription {
  widgetId: string;
  subscribers: GQLSubscriber[];
}

export interface GQLSubscriber {
  username: string;
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
  __typename: string;
}

export type GQLGroupDisplayMode = 'LIST' | 'TOGGLEABLE_AREAS';

export interface GQLWidget {
  id: string;
  label: string;
  iconURL: string | null;
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
  iconURL: string;
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
  imageURL: string;
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

export interface GQLButtonStyle {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
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
export interface GQLFlexboxContainer extends GQLWidget {
  flexDirection: GQLFlexDirection;
  flexWrap: GQLFlexWrap;
  flexGrow: number;
  children: GQLWidget[];
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
  imageURL: string;
  selectable: Boolean;
}

export interface GQLImage extends GQLWidget {
  url: string;
  maxWidth: string;
}

export interface GQLRichText extends GQLWidget {
  label: string;
  stringValue: string;
}

export interface GQLMessage {
  body: string;
  level: string;
}
