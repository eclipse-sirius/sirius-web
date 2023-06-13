/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.forms.renderer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.barchart.components.BarChartComponent;
import org.eclipse.sirius.components.charts.barchart.components.BarChartComponentProps;
import org.eclipse.sirius.components.charts.piechart.components.PieChartComponent;
import org.eclipse.sirius.components.charts.piechart.components.PieChartComponentProps;
import org.eclipse.sirius.components.forms.components.ButtonComponent;
import org.eclipse.sirius.components.forms.components.ButtonComponentProps;
import org.eclipse.sirius.components.forms.components.ChartWidgetComponent;
import org.eclipse.sirius.components.forms.components.ChartWidgetComponentProps;
import org.eclipse.sirius.components.forms.components.CheckboxComponent;
import org.eclipse.sirius.components.forms.components.CheckboxComponentProps;
import org.eclipse.sirius.components.forms.components.FlexboxContainerComponent;
import org.eclipse.sirius.components.forms.components.FlexboxContainerComponentProps;
import org.eclipse.sirius.components.forms.components.ForComponent;
import org.eclipse.sirius.components.forms.components.ForComponentProps;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.components.GroupComponent;
import org.eclipse.sirius.components.forms.components.GroupComponentProps;
import org.eclipse.sirius.components.forms.components.IfComponent;
import org.eclipse.sirius.components.forms.components.IfComponentProps;
import org.eclipse.sirius.components.forms.components.ImageComponent;
import org.eclipse.sirius.components.forms.components.ImageComponentProps;
import org.eclipse.sirius.components.forms.components.LabelWidgetComponent;
import org.eclipse.sirius.components.forms.components.LabelWidgetComponentProps;
import org.eclipse.sirius.components.forms.components.LinkComponent;
import org.eclipse.sirius.components.forms.components.LinkComponentProps;
import org.eclipse.sirius.components.forms.components.ListComponent;
import org.eclipse.sirius.components.forms.components.ListComponentProps;
import org.eclipse.sirius.components.forms.components.MultiSelectComponent;
import org.eclipse.sirius.components.forms.components.MultiSelectComponentProps;
import org.eclipse.sirius.components.forms.components.PageComponent;
import org.eclipse.sirius.components.forms.components.PageComponentProps;
import org.eclipse.sirius.components.forms.components.RadioComponent;
import org.eclipse.sirius.components.forms.components.RadioComponentProps;
import org.eclipse.sirius.components.forms.components.RichTextComponent;
import org.eclipse.sirius.components.forms.components.RichTextComponentProps;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.forms.components.SelectComponentProps;
import org.eclipse.sirius.components.forms.components.TextareaComponent;
import org.eclipse.sirius.components.forms.components.TextareaComponentProps;
import org.eclipse.sirius.components.forms.components.TextfieldComponent;
import org.eclipse.sirius.components.forms.components.TextfieldComponentProps;
import org.eclipse.sirius.components.forms.components.ToolbarActionComponent;
import org.eclipse.sirius.components.forms.components.ToolbarActionComponentProps;
import org.eclipse.sirius.components.forms.components.TreeComponent;
import org.eclipse.sirius.components.forms.components.TreeComponentProps;
import org.eclipse.sirius.components.forms.components.WidgetComponent;
import org.eclipse.sirius.components.forms.components.WidgetComponentProps;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.IComponentPropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of a component.
 *
 * @author sbegaudeau
 */
public class FormComponentPropsValidator implements IComponentPropsValidator {
    private final List<IWidgetDescriptor> widgetDescriptors;

    public FormComponentPropsValidator(List<IWidgetDescriptor> widgetDescriptors) {
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
    }

    @Override
    @SuppressWarnings("checkstyle:JavaNCSS")
    public boolean validateComponentProps(Class<?> componentType, IProps props) {
        boolean checkValidProps = false;

        if (FormComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormComponentProps;
        } else if (PageComponent.class.equals(componentType)) {
            checkValidProps = props instanceof PageComponentProps;
        } else if (GroupComponent.class.equals(componentType)) {
            checkValidProps = props instanceof GroupComponentProps;
        } else if (ForComponent.class.equals(componentType)) {
            checkValidProps = props instanceof ForComponentProps;
        } else if (IfComponent.class.equals(componentType)) {
            checkValidProps = props instanceof IfComponentProps;
        } else if (WidgetComponent.class.equals(componentType)) {
            checkValidProps = props instanceof WidgetComponentProps;
        } else if (CheckboxComponent.class.equals(componentType)) {
            checkValidProps = props instanceof CheckboxComponentProps;
        } else if (ListComponent.class.equals(componentType)) {
            checkValidProps = props instanceof ListComponentProps;
        } else if (RadioComponent.class.equals(componentType)) {
            checkValidProps = props instanceof RadioComponentProps;
        } else if (SelectComponent.class.equals(componentType)) {
            checkValidProps = props instanceof SelectComponentProps;
        } else if (MultiSelectComponent.class.equals(componentType)) {
            checkValidProps = props instanceof MultiSelectComponentProps;
        } else if (TextareaComponent.class.equals(componentType)) {
            checkValidProps = props instanceof TextareaComponentProps;
        } else if (TextfieldComponent.class.equals(componentType)) {
            checkValidProps = props instanceof TextfieldComponentProps;
        } else if (DiagnosticComponent.class.equals(componentType)) {
            checkValidProps = props instanceof DiagnosticComponentProps;
        } else if (LinkComponent.class.equals(componentType)) {
            checkValidProps = props instanceof LinkComponentProps;
        } else if (ButtonComponent.class.equals(componentType)) {
            checkValidProps = props instanceof ButtonComponentProps;
        } else if (LabelWidgetComponent.class.equals(componentType)) {
            checkValidProps = props instanceof LabelWidgetComponentProps;
        } else if (ChartWidgetComponent.class.equals(componentType)) {
            checkValidProps = props instanceof ChartWidgetComponentProps;
        } else if (BarChartComponent.class.equals(componentType)) {
            checkValidProps = props instanceof BarChartComponentProps;
        } else if (PieChartComponent.class.equals(componentType)) {
            checkValidProps = props instanceof PieChartComponentProps;
        } else if (FlexboxContainerComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FlexboxContainerComponentProps;
        } else if (TreeComponent.class.equals(componentType)) {
            checkValidProps = props instanceof TreeComponentProps;
        } else if (ImageComponent.class.equals(componentType)) {
            checkValidProps = props instanceof ImageComponentProps;
        } else if (RichTextComponent.class.equals(componentType)) {
            checkValidProps = props instanceof RichTextComponentProps;
        } else if (ToolbarActionComponent.class.equals(componentType)) {
            checkValidProps = props instanceof ToolbarActionComponentProps;
        } else {
            checkValidProps = this.widgetDescriptors.stream()
                    .map(widgetDescriptor -> widgetDescriptor.validateComponentProps(componentType, props))
                    .filter(Optional::isPresent)
                    .findFirst()
                    .map(Optional::get)
                    .orElse(false);
        }
        return checkValidProps;
    }
}
