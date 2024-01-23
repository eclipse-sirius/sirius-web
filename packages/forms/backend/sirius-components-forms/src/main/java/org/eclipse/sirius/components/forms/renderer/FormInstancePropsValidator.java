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
package org.eclipse.sirius.components.forms.renderer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.barchart.elements.BarChartElementProps;
import org.eclipse.sirius.components.charts.piechart.elements.PieChartElementProps;
import org.eclipse.sirius.components.forms.elements.ButtonElementProps;
import org.eclipse.sirius.components.forms.elements.ChartWidgetElementProps;
import org.eclipse.sirius.components.forms.elements.CheckboxElementProps;
import org.eclipse.sirius.components.forms.elements.FlexboxContainerElementProps;
import org.eclipse.sirius.components.forms.elements.FormElementProps;
import org.eclipse.sirius.components.forms.elements.GroupElementProps;
import org.eclipse.sirius.components.forms.elements.ImageElementProps;
import org.eclipse.sirius.components.forms.elements.LabelWidgetElementProps;
import org.eclipse.sirius.components.forms.elements.LinkElementProps;
import org.eclipse.sirius.components.forms.elements.ListElementProps;
import org.eclipse.sirius.components.forms.elements.MultiSelectElementProps;
import org.eclipse.sirius.components.forms.elements.PageElementProps;
import org.eclipse.sirius.components.forms.elements.RadioElementProps;
import org.eclipse.sirius.components.forms.elements.RichTextElementProps;
import org.eclipse.sirius.components.forms.elements.SelectElementProps;
import org.eclipse.sirius.components.forms.elements.SliderElementProps;
import org.eclipse.sirius.components.forms.elements.SplitButtonElementProps;
import org.eclipse.sirius.components.forms.elements.TextareaElementProps;
import org.eclipse.sirius.components.forms.elements.TextfieldElementProps;
import org.eclipse.sirius.components.forms.elements.ToolbarActionElementProps;
import org.eclipse.sirius.components.forms.elements.TreeElementProps;
import org.eclipse.sirius.components.forms.validation.DiagnosticElementProps;
import org.eclipse.sirius.components.representations.IInstancePropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the instance props.
 *
 * @author sbegaudeau
 */
public class FormInstancePropsValidator implements IInstancePropsValidator {

    private final List<IWidgetDescriptor> widgetDescriptors;

    public FormInstancePropsValidator(List<IWidgetDescriptor> widgetDescriptors) {
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
    }

    @Override
    @SuppressWarnings("checkstyle:JavaNCSS")
    public boolean validateInstanceProps(String type, IProps props) {
        boolean checkValidProps = false;

        if (FormElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof FormElementProps;
        } else if (PageElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof PageElementProps;
        } else if (GroupElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof GroupElementProps;
        } else if (CheckboxElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof CheckboxElementProps;
        } else if (ListElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof ListElementProps;
        } else if (RadioElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof RadioElementProps;
        } else if (SelectElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof SelectElementProps;
        } else if (MultiSelectElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof MultiSelectElementProps;
        } else if (TextareaElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof TextareaElementProps;
        } else if (TextfieldElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof TextfieldElementProps;
        } else if (DiagnosticElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof DiagnosticElementProps;
        } else if (LinkElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof LinkElementProps;
        } else if (ButtonElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof ButtonElementProps;
        } else if (SplitButtonElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof SplitButtonElementProps;
        } else if (LabelWidgetElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof LabelWidgetElementProps;
        } else if (ChartWidgetElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof ChartWidgetElementProps;
        } else if (BarChartElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof BarChartElementProps;
        } else if (PieChartElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof PieChartElementProps;
        } else if (FlexboxContainerElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof FlexboxContainerElementProps;
        } else if (TreeElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof TreeElementProps;
        } else if (ImageElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof ImageElementProps;
        } else if (RichTextElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof RichTextElementProps;
        } else if (ToolbarActionElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof ToolbarActionElementProps;
        } else if (SliderElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof SliderElementProps;
        } else {
            checkValidProps = this.widgetDescriptors.stream()
                    .map(widgetDescriptor -> widgetDescriptor.validateInstanceProps(type, props))
                    .filter(Optional::isPresent)
                    .findFirst()
                    .map(Optional::get)
                    .orElse(false);
        }

        return checkValidProps;
    }

}
