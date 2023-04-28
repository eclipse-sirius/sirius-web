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

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Component;

/**
 * Widget descriptor for the Slider custom widget.
 *
 * @author pcdavid
 */
@Component
public class SliderWidgetDescriptor implements IWidgetDescriptor {
    public static final String TYPE = "Slider";

    @Override
    public String getWidgetType() {
        return TYPE;
    }

    @Override
    public Class<? extends IComponent> getComponentClass() {
        return SliderComponent.class;
    }

    @Override
    public Class<? extends IProps> getInstancePropsClass() {
        return SliderElementProps.class;
    }

    @Override
    public Class<? extends IProps> getComponentPropsClass() {
        return SliderComponentProps.class;
    }

    @Override
    public Optional<Object> instanciate(IProps elementProps, List<Object> children) {
        if (elementProps instanceof SliderElementProps props) {
            var sliderBuilder = Slider.newSlider(props.getId())
                    .label(props.getLabel())
                    .minValue(props.getMinValue())
                    .maxValue(props.getMaxValue())
                    .currentValue(props.getCurrentValue())
                    .diagnostics(List.of())
                    .newValueHandler(props.getNewValueHandler());
            if (props.getIconURL() != null) {
                sliderBuilder.iconURL(props.getIconURL());
            }
            return Optional.of(sliderBuilder.build());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Element> createElement(VariableManager variableManager, AbstractWidgetDescription widgetDescription) {
        if (widgetDescription instanceof SliderDescription sliderDescription) {
            SliderComponentProps sliderComponentProps = new SliderComponentProps(variableManager, sliderDescription);
            return Optional.of(new Element(SliderComponent.class, sliderComponentProps));
        } else {
            return Optional.empty();
        }
    }

}
