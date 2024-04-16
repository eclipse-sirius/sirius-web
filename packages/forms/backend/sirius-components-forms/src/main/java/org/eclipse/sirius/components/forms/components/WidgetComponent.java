/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.forms.components;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.DateTimeDescription;
import org.eclipse.sirius.components.forms.description.FlexboxContainerDescription;
import org.eclipse.sirius.components.forms.description.ImageDescription;
import org.eclipse.sirius.components.forms.description.LabelDescription;
import org.eclipse.sirius.components.forms.description.LinkDescription;
import org.eclipse.sirius.components.forms.description.ListDescription;
import org.eclipse.sirius.components.forms.description.MultiSelectDescription;
import org.eclipse.sirius.components.forms.description.RadioDescription;
import org.eclipse.sirius.components.forms.description.RichTextDescription;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.forms.description.SliderDescription;
import org.eclipse.sirius.components.forms.description.SplitButtonDescription;
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The component used to render the widget.
 *
 * @author sbegaudeau
 */
public class WidgetComponent implements IComponent {

    private final Logger logger = LoggerFactory.getLogger(WidgetComponent.class);

    private final WidgetComponentProps props;

    public WidgetComponent(WidgetComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    @SuppressWarnings("checkstyle:JavaNCSS")
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        AbstractWidgetDescription widgetDescription = this.props.getWidgetDescription();

        Element element = null;
        if (widgetDescription instanceof TextfieldDescription) {
            TextfieldComponentProps textfieldProps = new TextfieldComponentProps(variableManager, (TextfieldDescription) widgetDescription);
            element = new Element(TextfieldComponent.class, textfieldProps);
        } else if (widgetDescription instanceof TextareaDescription) {
            TextareaComponentProps textareaProps = new TextareaComponentProps(variableManager, (TextareaDescription) widgetDescription);
            element = new Element(TextareaComponent.class, textareaProps);
        } else if (widgetDescription instanceof CheckboxDescription) {
            CheckboxComponentProps checkboxProps = new CheckboxComponentProps(variableManager, (CheckboxDescription) widgetDescription);
            element = new Element(CheckboxComponent.class, checkboxProps);
        } else if (widgetDescription instanceof SelectDescription) {
            SelectComponentProps selectProps = new SelectComponentProps(variableManager, (SelectDescription) widgetDescription);
            element = new Element(SelectComponent.class, selectProps);
        } else if (widgetDescription instanceof MultiSelectDescription) {
            MultiSelectComponentProps multiSelectProps = new MultiSelectComponentProps(variableManager, (MultiSelectDescription) widgetDescription);
            element = new Element(MultiSelectComponent.class, multiSelectProps);
        } else if (widgetDescription instanceof RadioDescription) {
            RadioComponentProps radioProps = new RadioComponentProps(variableManager, (RadioDescription) widgetDescription);
            element = new Element(RadioComponent.class, radioProps);
        } else if (widgetDescription instanceof ListDescription) {
            ListComponentProps listProps = new ListComponentProps(variableManager, (ListDescription) widgetDescription);
            element = new Element(ListComponent.class, listProps);
        } else if (widgetDescription instanceof LinkDescription) {
            LinkComponentProps linkProps = new LinkComponentProps(variableManager, (LinkDescription) widgetDescription);
            element = new Element(LinkComponent.class, linkProps);
        } else if (widgetDescription instanceof ButtonDescription) {
            ButtonComponentProps buttonProps = new ButtonComponentProps(variableManager, (ButtonDescription) widgetDescription);
            element = new Element(ButtonComponent.class, buttonProps);
        } else if (widgetDescription instanceof SplitButtonDescription) {
            SplitButtonComponentProps splitButtonProps = new SplitButtonComponentProps(variableManager, (SplitButtonDescription) widgetDescription);
            element = new Element(SplitButtonComponent.class, splitButtonProps);
        } else if (widgetDescription instanceof LabelDescription) {
            LabelWidgetComponentProps labelProps = new LabelWidgetComponentProps(variableManager, (LabelDescription) widgetDescription);
            element = new Element(LabelWidgetComponent.class, labelProps);
        } else if (widgetDescription instanceof ChartWidgetDescription) {
            ChartWidgetComponentProps chartComponentProps = new ChartWidgetComponentProps(variableManager, (ChartWidgetDescription) widgetDescription);
            element = new Element(ChartWidgetComponent.class, chartComponentProps);
        } else if (widgetDescription instanceof FlexboxContainerDescription) {
            FlexboxContainerComponentProps flexboxContainerProps = new FlexboxContainerComponentProps(variableManager, (FlexboxContainerDescription) widgetDescription, this.props.getWidgetDescriptors());
            element = new Element(FlexboxContainerComponent.class, flexboxContainerProps);
        } else if (widgetDescription instanceof TreeDescription) {
            TreeComponentProps treeComponentProps = new TreeComponentProps(variableManager, (TreeDescription) widgetDescription);
            element = new Element(TreeComponent.class, treeComponentProps);
        } else if (widgetDescription instanceof ImageDescription) {
            ImageComponentProps imageComponentProps = new ImageComponentProps(variableManager, (ImageDescription) widgetDescription);
            element = new Element(ImageComponent.class, imageComponentProps);
        } else if (widgetDescription instanceof RichTextDescription) {
            RichTextComponentProps richTextComponentProps = new RichTextComponentProps(variableManager, (RichTextDescription) widgetDescription);
            element = new Element(RichTextComponent.class, richTextComponentProps);
        } else if (widgetDescription instanceof SliderDescription) {
            SliderComponentProps sliderComponentProps = new SliderComponentProps(variableManager, (SliderDescription) widgetDescription);
            element = new Element(SliderComponent.class, sliderComponentProps);
        } else if (widgetDescription instanceof DateTimeDescription) {
            DateTimeComponentProps dateTimeComponentProps = new DateTimeComponentProps(variableManager, (DateTimeDescription) widgetDescription);
            element = new Element(DateTimeComponent.class, dateTimeComponentProps);
        } else {
            element = this.props.getWidgetDescriptors().stream()
                    .map(widgetDescriptor -> widgetDescriptor.createElement(variableManager, widgetDescription))
                    .filter(Optional::isPresent)
                    .findFirst()
                    .map(Optional::get)
                    .orElse(null);
            if (element == null) {
                String pattern = "Unsupported widget description: {}";
                this.logger.warn(pattern, widgetDescription.getClass().getSimpleName());
            }
        }
        return element;
    }
}
