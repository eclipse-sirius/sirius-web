/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.RadioStyle;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the radio widget.
 *
 * @author lfasani
 */
@Immutable
public final class RadioDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> iconURLProvider;

    private Function<VariableManager, List<?>> optionsProvider;

    private Function<VariableManager, String> optionIdProvider;

    private Function<VariableManager, String> optionLabelProvider;

    private Function<VariableManager, Boolean> optionSelectedProvider;

    private BiFunction<VariableManager, String, IStatus> newValueHandler;

    private Function<VariableManager, RadioStyle> styleProvider;

    private RadioDescription() {
        // Prevent instantiation
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, String> getIconURLProvider() {
        return this.iconURLProvider;
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

    public Function<VariableManager, Boolean> getOptionSelectedProvider() {
        return this.optionSelectedProvider;
    }

    public BiFunction<VariableManager, String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public Function<VariableManager, RadioStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public static Builder newRadioDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the radio description.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> iconURLProvider = variableManager -> null;

        private Function<VariableManager, List<?>> optionsProvider;

        private Function<VariableManager, String> optionIdProvider;

        private Function<VariableManager, String> optionLabelProvider;

        private Function<VariableManager, Boolean> optionSelectedProvider;

        private BiFunction<VariableManager, String, IStatus> newValueHandler;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, RadioStyle> styleProvider = vm -> null;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder iconURLProvider(Function<VariableManager, String> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
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

        public Builder optionSelectedProvider(Function<VariableManager, Boolean> optionSelectedProvider) {
            this.optionSelectedProvider = Objects.requireNonNull(optionSelectedProvider);
            return this;
        }

        public Builder newValueHandler(BiFunction<VariableManager, String, IStatus> newValueHandler) {
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

        public Builder styleProvider(Function<VariableManager, RadioStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public RadioDescription build() {
            RadioDescription radioDescription = new RadioDescription();
            radioDescription.id = Objects.requireNonNull(this.id);
            radioDescription.idProvider = Objects.requireNonNull(this.idProvider);
            radioDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            radioDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            radioDescription.optionsProvider = Objects.requireNonNull(this.optionsProvider);
            radioDescription.optionIdProvider = Objects.requireNonNull(this.optionIdProvider);
            radioDescription.optionLabelProvider = Objects.requireNonNull(this.optionLabelProvider);
            radioDescription.optionSelectedProvider = Objects.requireNonNull(this.optionSelectedProvider);
            radioDescription.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            radioDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            radioDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            radioDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            radioDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            return radioDescription;
        }

    }

}
