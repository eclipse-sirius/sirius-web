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
package org.eclipse.sirius.components.dynamicdialogs.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the select widget for dynamic dialog.
 *
 * @author lfasani
 */
@Immutable
public final class DSelectWidgetDescription extends AbstractDWidgetDescription {

    private Function<VariableManager, List<?>> optionsProvider;

    private Function<VariableManager, String> optionIdProvider;

    private Function<VariableManager, String> optionLabelProvider;

    private Function<VariableManager, String> valueProvider;

    // private Function<VariableManager, List<?>> diagnosticsProvider;

    private DSelectWidgetDescription() {
        // Prevent instantiation
    }

    @Override
    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, List<?>> getOptionsProvider() {
        return this.optionsProvider;
    }

    public Function<VariableManager, String> getOptionIdProvider() {
        return this.optionIdProvider;
    }

    public Function<VariableManager, String> getOptionLabelProvider() {
        return this.optionLabelProvider;
    }

    public Function<VariableManager, String> getValueProvider() {
        return this.valueProvider;
    }

    public static Builder newDSelectWidgetDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the select description for dynamic dialog.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> labelProvider;

        private DWidgetOutputDescription output;

        private List<DWidgetOutputDescription> inputs;

        // private Function<VariableManager, String> idProvider;

        private Function<VariableManager, List<?>> optionsProvider;

        private Function<VariableManager, String> optionIdProvider;

        private Function<VariableManager, String> optionLabelProvider;

        private Function<VariableManager, String> initialValueProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder id(Function<VariableManager, String> idProvider) {
            this.id = Objects.requireNonNull(this.id);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder inputs(List<DWidgetOutputDescription> inputs) {
            this.inputs = Objects.requireNonNull(inputs);
            return this;
        }

        public Builder output(DWidgetOutputDescription output) {
            this.output = Objects.requireNonNull(output);
            return this;
        }

        public Builder optionsProvider(Function<VariableManager, List<?>> optionsProvider) {
            this.optionsProvider = Objects.requireNonNull(optionsProvider);
            return this;
        }

        public Builder optionIdProvider(Function<VariableManager, String> optionIdProvider) {
            this.optionIdProvider = Objects.requireNonNull(optionIdProvider);
            return this;
        }

        public Builder optionLabelProvider(Function<VariableManager, String> optionLabelProvider) {
            this.optionLabelProvider = Objects.requireNonNull(optionLabelProvider);
            return this;
        }

        public Builder initialValueProvider(Function<VariableManager, String> initialValueProvider) {
            this.initialValueProvider = Objects.requireNonNull(initialValueProvider);
            return this;
        }

        public DSelectWidgetDescription build() {
            DSelectWidgetDescription selectDescription = new DSelectWidgetDescription();
            selectDescription.id = Objects.requireNonNull(this.id);
            selectDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            selectDescription.inputs = Objects.requireNonNull(this.inputs);
            selectDescription.output = Objects.requireNonNull(this.output);
            selectDescription.optionsProvider = Objects.requireNonNull(this.optionsProvider);
            selectDescription.optionIdProvider = Objects.requireNonNull(this.optionIdProvider);
            selectDescription.optionLabelProvider = Objects.requireNonNull(this.optionLabelProvider);
            selectDescription.initialValueProvider = Objects.requireNonNull(this.initialValueProvider);
            return selectDescription;
        }

    }
}
