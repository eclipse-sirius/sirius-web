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

import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties for the Slider element.
 *
 * @author pcdavid
 */
@Immutable
public final class SliderElementProps implements IProps {
    public static final String TYPE = "Slider";

    private String id;

    private String label;

    private String iconURL;

    private int minValue;

    private int maxValue;

    private int currentValue;

    private Function<Integer, IStatus> newValueHandler;

    private SliderElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    public int getMinValue() {
        return this.minValue;
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public int getCurrentValue() {
        return this.currentValue;
    }

    public Function<Integer, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public static Builder newSliderElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, minValue: {3}, maxValue: {4}, currentValue: {5}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.minValue, this.maxValue, this.currentValue);
    }

    /**
     * The builder of the slider element props.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String iconURL;

        private int minValue;

        private int maxValue;

        private int currentValue;

        private Function<Integer, IStatus> newValueHandler;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder minValue(int minValue) {
            this.minValue = minValue;
            return this;
        }

        public Builder maxValue(int maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        public Builder currentValue(int currentValue) {
            this.currentValue = currentValue;
            return this;
        }

        public Builder newValueHandler(Function<Integer, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public SliderElementProps build() {
            SliderElementProps sliderElementProps = new SliderElementProps();
            sliderElementProps.id = Objects.requireNonNull(this.id);
            sliderElementProps.label = Objects.requireNonNull(this.label);
            sliderElementProps.iconURL = this.iconURL;
            sliderElementProps.minValue = this.minValue;
            sliderElementProps.maxValue = this.maxValue;
            sliderElementProps.currentValue = this.currentValue;
            sliderElementProps.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            return sliderElementProps;
        }
    }
}
