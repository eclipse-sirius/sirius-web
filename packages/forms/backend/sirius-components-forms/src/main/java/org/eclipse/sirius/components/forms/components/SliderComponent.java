/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.description.SliderDescription;
import org.eclipse.sirius.components.forms.elements.SliderElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component to render a slider.
 *
 * @author pcdavid
 */
public class SliderComponent implements IComponent {

    private final SliderComponentProps props;

    public SliderComponent(SliderComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        SliderDescription sliderDescription = this.props.getSliderDescription();

        String label = sliderDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, sliderDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, sliderDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = sliderDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = sliderDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = sliderDescription.getIsReadOnlyProvider().apply(variableManager);
        int minValue = sliderDescription.getMinValueProvider().apply(variableManager);
        int maxValue = sliderDescription.getMaxValueProvider().apply(variableManager);
        int currentValue = sliderDescription.getCurrentValueProvider().apply(variableManager);
        Function<VariableManager, IStatus> newValueHandler = sliderDescription.getNewValueHandler();
        Function<Integer, IStatus> specializedHandler = (newValue) -> {
            var childVariableManager = variableManager.createChild();
            childVariableManager.put("newValue", newValue);
            return newValueHandler.apply(childVariableManager);
        };

        var sliderElementPropsBuilder = SliderElementProps.newSliderElementProps(id)
                .label(label)
                .minValue(minValue)
                .maxValue(maxValue)
                .currentValue(currentValue)
                .newValueHandler(specializedHandler);

        if (iconURL != null) {
            sliderElementPropsBuilder.iconURL(iconURL);
        }
        if (sliderDescription.getHelpTextProvider() != null) {
            sliderElementPropsBuilder.helpTextProvider(() -> sliderDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            sliderElementPropsBuilder.readOnly(readOnly);
        }

        SliderElementProps sliderElementProps = sliderElementPropsBuilder.build();

        return new Element(SliderElementProps.TYPE, sliderElementProps);
    }
}
