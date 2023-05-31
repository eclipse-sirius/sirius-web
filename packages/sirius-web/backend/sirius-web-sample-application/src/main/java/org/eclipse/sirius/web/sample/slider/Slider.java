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
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * A simple Slider widget to edit an integer value inside a given range.
 *
 * @author pcdavid
 */
@Immutable
public final class Slider extends AbstractWidget {
    private int minValue;

    private int maxValue;

    private int currentValue;

    private Function<Integer, IStatus> newValueHandler;

    private Slider() {
        // Prevent instantiation
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

    @Override
    public String toString() {
        String pattern = "{0} '{'minValue: {1}, maxValue: {2}, currentValue: {3}, newValue: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getMinValue(), this.getMaxValue(), this.getCurrentValue(), this.getNewValueHandler());
    }

    public static Builder newSlider(String id) {
        return new Builder(id);
    }

    /**
     * Builder used to create the Slider.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String iconURL;

        private List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

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
        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }
        public Builder minValue(int minValue) {
            this.minValue = Objects.requireNonNull(minValue);
            return this;
        }

        public Builder maxValue(int maxValue) {
            this.maxValue = Objects.requireNonNull(maxValue);
            return this;
        }

        public Builder currentValue(int currentValue) {
            this.currentValue = Objects.requireNonNull(currentValue);
            return this;
        }

        public Builder newValueHandler(Function<Integer, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Slider build() {
            Slider slider = new Slider();
            slider.id = Objects.requireNonNull(this.id);
            slider.label = Objects.requireNonNull(this.label);
            slider.iconURL = this.iconURL;
            slider.minValue = Objects.requireNonNull(this.minValue);
            slider.maxValue = Objects.requireNonNull(this.maxValue);
            slider.currentValue = Objects.requireNonNull(this.currentValue);
            slider.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            slider.diagnostics = Objects.requireNonNull(this.diagnostics);
            slider.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return slider;
        }
    }

}
