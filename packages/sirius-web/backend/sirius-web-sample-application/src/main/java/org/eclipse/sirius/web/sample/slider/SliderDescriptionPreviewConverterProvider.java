/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.sample.slider;

import java.util.UUID;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetPreviewConverterProvider;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.customwidgets.SliderDescription;
import org.eclipse.sirius.web.customwidgets.util.CustomwidgetsSwitch;
import org.springframework.stereotype.Service;

/**
 * Provides the widget converter needed for the Slider widget preview in the context of a Form Description Editor.
 *
 * @author pcdavid
 */
@Service
public class SliderDescriptionPreviewConverterProvider implements IWidgetPreviewConverterProvider {

    @Override
    public Switch<AbstractWidgetDescription> getWidgetConverter(FormDescriptionEditorDescription formDescriptionEditorDescription, VariableManager variableManager) {
        return new CustomwidgetsSwitch<>() {
            @Override
            public AbstractWidgetDescription caseSliderDescription(SliderDescription sliderDescription) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VariableManager.SELF, sliderDescription);
                String id = formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);
                var builder =  org.eclipse.sirius.web.sample.slider.SliderDescription.newSliderDescription(UUID.randomUUID().toString())
                        .idProvider(vm -> id)
                        .targetObjectIdProvider(vm -> "")
                        .labelProvider(vm -> SliderDescriptionPreviewConverterProvider.this.getWidgetLabel(sliderDescription, "Slider"))
                        .minValueProvider(vm -> 0)
                        .maxValueProvider(vm -> 100)
                        .currentValueProvider(vm -> 50)
                        .newValueHandler(vm -> new Success());
                if (sliderDescription.getHelpExpression() != null && !sliderDescription.getHelpExpression().isBlank()) {
                    String helpText = SliderDescriptionPreviewConverterProvider.this.getWidgetHelpText(sliderDescription);
                    builder.helpTextProvider(variableManager -> helpText);
                }
                return builder.build();
            }
        };
    }

    public String getWidgetLabel(org.eclipse.sirius.components.view.form.WidgetDescription widgetDescription, String defaultLabel) {
        String widgetLabel = defaultLabel;
        String name = widgetDescription.getName();
        String labelExpression = widgetDescription.getLabelExpression();
        if (labelExpression != null && !labelExpression.isBlank() && !labelExpression.startsWith("aql:")) {
            widgetLabel = labelExpression;
        } else if (name != null && !name.isBlank()) {
            widgetLabel = name;
        }
        return widgetLabel;
    }

    public String getWidgetHelpText(org.eclipse.sirius.components.view.form.WidgetDescription widgetDescription) {
        String helpText = "Help";
        String helpExpression = widgetDescription.getHelpExpression();
        if (helpExpression != null && !helpExpression.isBlank() && !helpExpression.startsWith("aql:")) {
            helpText = helpExpression;
        }
        return helpText;
    }

}
