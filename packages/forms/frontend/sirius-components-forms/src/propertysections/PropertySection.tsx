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
  GQLButton,
  GQLChartWidget,
  GQLCheckbox,
  GQLFlexboxContainer,
  GQLLink,
  GQLList,
  GQLMultiSelect,
  GQLRadio,
  GQLSelect,
  GQLTextarea,
  GQLTextfield,
  GQLTree,
  GQLWidget,
} from '../form/FormEventFragments.types';
import { ButtonPropertySection } from './ButtonPropertySection';
import { ChartWidgetPropertySection } from './ChartWidgetPropertySection';
import { CheckboxPropertySection } from './CheckboxPropertySection';
import { FlexboxContainerPropertySection } from './FlexboxContainerPropertySection';
import { LinkPropertySection } from './LinkPropertySection';
import { ListPropertySection } from './ListPropertySection';
import { MultiSelectPropertySection } from './MultiSelectPropertySection';
import { PropertySectionProps } from './PropertySection.types';
import { RadioPropertySection } from './RadioPropertySection';
import { SelectPropertySection } from './SelectPropertySection';
import { TextfieldPropertySection } from './TextfieldPropertySection';
import { TreePropertySection } from './TreePropertySection';

const isTextfield = (widget: GQLWidget): widget is GQLTextfield => widget.__typename === 'Textfield';
const isTextarea = (widget: GQLWidget): widget is GQLTextarea => widget.__typename === 'Textarea';
const isCheckbox = (widget: GQLWidget): widget is GQLCheckbox => widget.__typename === 'Checkbox';
const isSelect = (widget: GQLWidget): widget is GQLSelect => widget.__typename === 'Select';
const isMultiSelect = (widget: GQLWidget): widget is GQLMultiSelect => widget.__typename === 'MultiSelect';
const isRadio = (widget: GQLWidget): widget is GQLRadio => widget.__typename === 'Radio';
const isList = (widget: GQLWidget): widget is GQLList => widget.__typename === 'List';
const isLink = (widget: GQLWidget): widget is GQLLink => widget.__typename === 'Link';
const isButton = (widget: GQLWidget): widget is GQLButton => widget.__typename === 'Button';
const isChartWidget = (widget: GQLWidget): widget is GQLChartWidget => widget.__typename === 'ChartWidget';
const isFlexboxContainer = (widget: GQLWidget): widget is GQLFlexboxContainer =>
  widget.__typename === 'FlexboxContainer';
const isTree = (widget: GQLWidget): widget is GQLTree => widget.__typename === 'TreeWidget';

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
  } else if (isButton(widget)) {
    propertySection = (
      <ButtonPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        subscribers={subscribers}
        readOnly={readOnly}
      />
    );
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
