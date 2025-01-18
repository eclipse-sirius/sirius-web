/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import org.eclipse.sirius.components.forms.LabelWidgetStyle;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the label widget.
 *
 * @author fbarbin
 */
@Immutable
public final class LabelDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> valueProvider;

    private Function<VariableManager, LabelWidgetStyle> styleProvider;

    private LabelDescription() {
        // Prevent instantiation
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, String> getValueProvider() {
        return this.valueProvider;
    }

    public Function<VariableManager, LabelWidgetStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public static Builder newLabelDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the textfield description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> labelProvider;

        private final Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> true;

        private Function<VariableManager, String> valueProvider;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, String> helpTextProvider;

        private Function<VariableManager, LabelWidgetStyle> styleProvider = vm -> null;

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

        public Builder valueProvider(Function<VariableManager, String> valueProvider) {
            this.valueProvider = Objects.requireNonNull(valueProvider);
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

        public Builder styleProvider(Function<VariableManager, LabelWidgetStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public LabelDescription build() {
            LabelDescription labelDescription = new LabelDescription();
            labelDescription.id = Objects.requireNonNull(this.id);
            labelDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            labelDescription.idProvider = Objects.requireNonNull(this.idProvider);
            labelDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            labelDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            labelDescription.valueProvider = Objects.requireNonNull(this.valueProvider);
            labelDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            labelDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            labelDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            labelDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            labelDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return labelDescription;
        }

    }
}
