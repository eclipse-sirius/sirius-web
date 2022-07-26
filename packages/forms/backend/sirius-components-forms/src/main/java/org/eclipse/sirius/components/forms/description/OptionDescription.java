/*******************************************************************************
 * Copyright (c) 2019, 2021, 2022 Obeo.
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
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the option which contribute to define widget with multiple choices like Combobox or Radiobutton.
 *
 * @author lfasani
 */
@Immutable
public final class OptionDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> iconURLProvider;

    private Function<VariableManager, Boolean> selectedProvider;

    private OptionDescription() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, String> getIconURLProvider() {
        return this.iconURLProvider;
    }

    public Function<VariableManager, Boolean> getSelectedProvider() {
        return this.selectedProvider;
    }

    public static Builder newOptionDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create the option description.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> iconURLProvider = variableManager -> null;

        private Function<VariableManager, Boolean> selectedProvider;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder iconURLProvider(Function<VariableManager, String> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
            return this;
        }

        public Builder selectedProvider(Function<VariableManager, Boolean> selectedProvider) {
            this.selectedProvider = Objects.requireNonNull(selectedProvider);
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

        public OptionDescription build() {
            OptionDescription optionDescription = new OptionDescription();
            optionDescription.id = Objects.requireNonNull(this.id);
            optionDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            optionDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            optionDescription.selectedProvider = Objects.requireNonNull(this.selectedProvider);
            optionDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            optionDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            optionDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            return optionDescription;
        }
    }
}
