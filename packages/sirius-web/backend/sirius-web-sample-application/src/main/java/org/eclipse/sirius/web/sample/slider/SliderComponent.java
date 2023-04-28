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

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.sample.slider.SliderElementProps.Builder;

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

        String id = sliderDescription.getIdProvider().apply(variableManager);
        String label = sliderDescription.getLabelProvider().apply(variableManager);
        String iconURL = sliderDescription.getIconURLProvider().apply(variableManager);
        int minValue = sliderDescription.getMinValueProvider().apply(variableManager);
        int maxValue = sliderDescription.getMaxValueProvider().apply(variableManager);
        int currentValue = sliderDescription.getCurrentValueProvider().apply(variableManager);
        Function<VariableManager, IStatus> newValueHandler = sliderDescription.getNewValueHandler();
        Function<Integer, IStatus> specializedHandler = (newValue) -> {
            var childVariableManager = variableManager.createChild();
            childVariableManager.put("newValue", newValue);
            return newValueHandler.apply(childVariableManager);
        };

        // @formatter:off
        Builder sliderElementPropsBuilder = SliderElementProps.newSliderElementProps(id)
                .label(label)
                .minValue(minValue)
                .maxValue(maxValue)
                .currentValue(currentValue)
                .newValueHandler(specializedHandler);
        // @formatter:on
        if (iconURL != null) {
            sliderElementPropsBuilder.iconURL(iconURL);
        }

        SliderElementProps sliderElementProps = sliderElementPropsBuilder.build();

        return new Element(SliderElementProps.TYPE, sliderElementProps);
    }
}
