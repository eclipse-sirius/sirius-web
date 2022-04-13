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
import {
  ChartWidget,
  Checkbox,
  FlexboxContainer,
  Link,
  List,
  MultiSelect,
  Radio,
  Select,
  Textarea,
  Textfield,
  Tree,
  Widget,
} from 'form/Form.types';
import React from 'react';
import { PropertySectionProps } from './PropertySection.types';
import { ChartWidgetPropertySection } from './propertysections/ChartWidgetPropertySection';
import { CheckboxPropertySection } from './propertysections/CheckboxPropertySection';
import { FlexboxContainerPropertySection } from './propertysections/FlexboxContainerPropertySection';
import { LinkPropertySection } from './propertysections/LinkPropertySection';
import { ListPropertySection } from './propertysections/ListPropertySection';
import { MultiSelectPropertySection } from './propertysections/MultiSelectPropertySection';
import { RadioPropertySection } from './propertysections/RadioPropertySection';
import { SelectPropertySection } from './propertysections/SelectPropertySection';
import { TextfieldPropertySection } from './propertysections/TextfieldPropertySection';
import { TreePropertySection } from './propertysections/TreePropertySection';

const isTextfield = (widget: Widget): widget is Textfield => widget.__typename === 'Textfield';
const isTextarea = (widget: Widget): widget is Textarea => widget.__typename === 'Textarea';
const isCheckbox = (widget: Widget): widget is Checkbox => widget.__typename === 'Checkbox';
const isSelect = (widget: Widget): widget is Select => widget.__typename === 'Select';
const isMultiSelect = (widget: Widget): widget is MultiSelect => widget.__typename === 'MultiSelect';
const isRadio = (widget: Widget): widget is Radio => widget.__typename === 'Radio';
const isList = (widget: Widget): widget is List => widget.__typename === 'List';
const isLink = (widget: Widget): widget is Link => widget.__typename === 'Link';
const isChartWidget = (widget: Widget): widget is ChartWidget => widget.__typename === 'ChartWidget';
const isFlexboxContainer = (widget: Widget): widget is FlexboxContainer => widget.__typename === 'FlexboxContainer';
const isTree = (widget: Widget): widget is Tree => widget.__typename === 'TreeWidget';

export const PropertySection = ({
  editingContextId,
  formId,
  widget,
  widgetSubscriptions,
  setSelection,
  readOnly,
}: PropertySectionProps) => {
  let subscribers = [];

  widgetSubscriptions
    .filter((subscription) => subscription.widgetId === widget.id)
    .forEach((subscription) => subscribers.push(...subscription.subscribers));

  let propertySection: JSX.Element | null = null;
  if (isTextfield(widget) || isTextarea(widget)) {
    propertySection = (
      <TextfieldPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        subscribers={subscribers}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isCheckbox(widget)) {
    propertySection = (
      <CheckboxPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        subscribers={subscribers}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isSelect(widget)) {
    propertySection = (
      <SelectPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        subscribers={subscribers}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isMultiSelect(widget)) {
    propertySection = (
      <MultiSelectPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        subscribers={subscribers}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isRadio(widget)) {
    propertySection = (
      <RadioPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        subscribers={subscribers}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isList(widget)) {
    propertySection = (
      <ListPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        subscribers={subscribers}
        setSelection={setSelection}
        readOnly={readOnly}
      />
    );
  } else if (isLink(widget)) {
    propertySection = <LinkPropertySection widget={widget} key={widget.id} />;
  } else if (isChartWidget(widget)) {
    propertySection = <ChartWidgetPropertySection widget={widget} subscribers={subscribers} key={widget.id} />;
  } else if (isFlexboxContainer(widget)) {
    propertySection = (
      <FlexboxContainerPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        widgetSubscriptions={widgetSubscriptions}
        setSelection={setSelection}
        readOnly={readOnly}
      />
    );
  } else if (isTree(widget)) {
    propertySection = (
      <TreePropertySection widget={widget} key={widget.id} subscribers={subscribers} setSelection={setSelection} />
    );
  } else {
    console.error(`Unsupported widget type ${widget.__typename}`);
  }
  return propertySection;
};
