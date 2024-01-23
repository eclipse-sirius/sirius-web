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
package org.eclipse.sirius.components.forms.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Custom slider widget description.
 *
 * @author pcdavid
 */
@Immutable
public final class SliderDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private Function<VariableManager, Integer> minValueProvider;

    private Function<VariableManager, Integer> maxValueProvider;

    private Function<VariableManager, Integer> currentValueProvider;

    private Function<VariableManager, IStatus> newValueHandler;

    private SliderDescription() {
        // Prevent instantiation
    }

    public static Builder newSliderDescription(String id) {
        return new Builder(id);
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, List<String>> getIconURLProvider() {
        return this.iconURLProvider;
    }

    public Function<VariableManager, Integer> getMinValueProvider() {
        return this.minValueProvider;
    }

    public Function<VariableManager, Integer> getMaxValueProvider() {
        return this.maxValueProvider;
    }

    public Function<VariableManager, Integer> getCurrentValueProvider() {
        return this.currentValueProvider;
    }

    public Function<VariableManager, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'idProvider: {1}, labelProvider: {2}, iconURLProvider: {3}, minValueProvider: {4}, maxValueProvider: {5}, currentValueProvider: {6}, newValueHandler: {7}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getIdProvider(), this.getLabelProvider(), this.getIconURLProvider(), this.getMinValueProvider(),
                this.getMaxValueProvider(), this.getCurrentValueProvider(), this.getNewValueHandler());
    }

    /**
     * Builder used to create the SliderDescription.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> List.of();

        private Function<VariableManager, String> helpTextProvider;

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private Function<VariableManager, Integer> minValueProvider;

        private Function<VariableManager, Integer> maxValueProvider;

        private Function<VariableManager, Integer> currentValueProvider;

        private Function<VariableManager, IStatus> newValueHandler;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder iconURLProvider(Function<VariableManager, List<String>> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
            return this;
        }

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder isReadOnlyProvider(Function<VariableManager, Boolean> isReadOnlyProvider) {
            this.isReadOnlyProvider = Objects.requireNonNull(isReadOnlyProvider);
            return this;
        }

        public Builder minValueProvider(Function<VariableManager, Integer> minValueProvider) {
            this.minValueProvider = Objects.requireNonNull(minValueProvider);
            return this;
        }

        public Builder maxValueProvider(Function<VariableManager, Integer> maxValueProvider) {
            this.maxValueProvider = Objects.requireNonNull(maxValueProvider);
            return this;
        }

        public Builder currentValueProvider(Function<VariableManager, Integer> currentValueProvider) {
            this.currentValueProvider = Objects.requireNonNull(currentValueProvider);
            return this;
        }

        public Builder newValueHandler(Function<VariableManager, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public Builder diagnosticsProvider(Function<VariableManager, List<?>> diagnosticsProvider) {
            this.diagnosticsProvider = Objects.requireNonNull(diagnosticsProvider);
            return this;
        }

        public Builder kindProvider(Function<Object, String> kindProvider) {
            this.kindProvider = Objects.requireNonNull(kindProvider);
            return this;
        }

        public Builder messageProvider(Function<Object, String> messageProvider) {
            this.messageProvider = Objects.requireNonNull(messageProvider);
            return this;
        }

        public SliderDescription build() {
            SliderDescription sliderDescription = new SliderDescription();
            sliderDescription.id = Objects.requireNonNull(this.id);
            sliderDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            sliderDescription.idProvider = Objects.requireNonNull(this.idProvider);
            sliderDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            sliderDescription.iconURLProvider = this.iconURLProvider; // Optional on purpose
            sliderDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            sliderDescription.minValueProvider = Objects.requireNonNull(this.minValueProvider);
            sliderDescription.maxValueProvider = Objects.requireNonNull(this.maxValueProvider);
            sliderDescription.currentValueProvider = Objects.requireNonNull(this.currentValueProvider);
            sliderDescription.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            sliderDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            sliderDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            sliderDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            sliderDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return sliderDescription;
        }
    }

}
