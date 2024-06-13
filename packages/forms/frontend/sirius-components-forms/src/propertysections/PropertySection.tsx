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
import { useContext } from 'react';
import { PropertySectionContext } from '../form/FormContext';
import { PropertySectionContextValue } from '../form/FormContext.types';
import {
  GQLButton,
  GQLChartWidget,
  GQLCheckbox,
  GQLDateTime,
  GQLFlexboxContainer,
  GQLImage,
  GQLLabelWidget,
  GQLLink,
  GQLList,
  GQLMultiSelect,
  GQLRadio,
  GQLRichText,
  GQLSelect,
  GQLSlider,
  GQLSplitButton,
  GQLTextarea,
  GQLTextfield,
  GQLTree,
  GQLWidget,
} from '../form/FormEventFragments.types';
import { ButtonPropertySection } from './ButtonPropertySection';
import { ChartWidgetPropertySection } from './ChartWidgetPropertySection';
import { CheckboxPropertySection } from './CheckboxPropertySection';
import { DateTimeWidgetPropertySection } from './DateTimeWidgetPropertySection';
import { FlexboxContainerPropertySection } from './FlexboxContainerPropertySection';
import { ImagePropertySection } from './ImagePropertySection';
import { LabelWidgetPropertySection } from './LabelWidgetPropertySection';
import { LinkPropertySection } from './LinkPropertySection';
import { ListPropertySection } from './ListPropertySection';
import { MultiSelectPropertySection } from './MultiSelectPropertySection';
import { PropertySectionProps } from './PropertySection.types';
import { RadioPropertySection } from './RadioPropertySection';
import { RichTextPropertySection } from './RichTextPropertySection';
import { SelectPropertySection } from './SelectPropertySection';
import { SliderPropertySection } from './SliderPropertySection';
import { SplitButtonPropertySection } from './SplitButtonPropertySection';
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
const isSlider = (widget: GQLWidget): widget is GQLSlider => widget.__typename === 'Slider';
const isDateTime = (widget: GQLWidget): widget is GQLDateTime => widget.__typename === 'DateTime';
const isSplitButton = (widget: GQLWidget): widget is GQLSplitButton => widget.__typename === 'SplitButton';
const isLabelWidget = (widget: GQLWidget): widget is GQLLabelWidget => widget.__typename === 'LabelWidget';
const isChartWidget = (widget: GQLWidget): widget is GQLChartWidget => widget.__typename === 'ChartWidget';
const isFlexboxContainer = (widget: GQLWidget): widget is GQLFlexboxContainer =>
  widget.__typename === 'FlexboxContainer';
const isTree = (widget: GQLWidget): widget is GQLTree => widget.__typename === 'TreeWidget';
const isImage = (widget: GQLWidget): widget is GQLImage => widget.__typename === 'Image';
const isRichText = (widget: GQLWidget): widget is GQLRichText => widget.__typename === 'RichText';

export const PropertySection = ({ editingContextId, formId, widget, readOnly }: PropertySectionProps) => {
  const { propertySectionsRegistry } = useContext<PropertySectionContextValue>(PropertySectionContext);

  let propertySection: JSX.Element | null = null;
  if (isTextfield(widget) || isTextarea(widget)) {
    propertySection = (
      <TextfieldPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
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
        readOnly={readOnly}
      />
    );
  } else if (isLink(widget)) {
    propertySection = (
      <LinkPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isButton(widget)) {
    propertySection = (
      <ButtonPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isSplitButton(widget)) {
    propertySection = (
      <SplitButtonPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isLabelWidget(widget)) {
    propertySection = (
      <LabelWidgetPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isChartWidget(widget)) {
    propertySection = (
      <ChartWidgetPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isFlexboxContainer(widget)) {
    propertySection = (
      <FlexboxContainerPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isTree(widget)) {
    propertySection = (
      <TreePropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isImage(widget)) {
    propertySection = (
      <ImagePropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isRichText(widget)) {
    propertySection = (
      <RichTextPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isSlider(widget)) {
    propertySection = (
      <SliderPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else if (isDateTime(widget)) {
    propertySection = (
      <DateTimeWidgetPropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        key={widget.id}
        readOnly={readOnly}
      />
    );
  } else {
    const CustomWidgetComponent = propertySectionsRegistry.getComponent(widget);
    if (CustomWidgetComponent) {
      propertySection = (
        <CustomWidgetComponent
          editingContextId={editingContextId}
          formId={formId}
          widget={widget}
          key={widget.id}
          readOnly={readOnly}
        />
      );
    } else {
      console.error(`Unsupported widget type ${widget.__typename}`);
    }
  }
  return propertySection;
};
