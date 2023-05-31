/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import org.eclipse.sirius.components.forms.CompletionProposal;
import org.eclipse.sirius.components.forms.TextfieldStyle;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the textfield widget.
 *
 * @author sbegaudeau
 */
@Immutable
public final class TextfieldDescription extends AbstractWidgetDescription {
    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> iconURLProvider;

    private Function<VariableManager, String> valueProvider;

    private BiFunction<VariableManager, String, IStatus> newValueHandler;

    private Function<VariableManager, TextfieldStyle> styleProvider;

    private Function<VariableManager, List<CompletionProposal>> completionProposalsProvider;

    private TextfieldDescription() {
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

    public Function<VariableManager, String> getValueProvider() {
        return this.valueProvider;
    }

    public BiFunction<VariableManager, String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public Function<VariableManager, TextfieldStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public Function<VariableManager, List<CompletionProposal>> getCompletionProposalsProvider() {
        return this.completionProposalsProvider;
    }

    public static Builder newTextfieldDescription(String id) {
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

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> iconURLProvider = variableManager -> null;

        private Function<VariableManager, String> valueProvider;

        private BiFunction<VariableManager, String, IStatus> newValueHandler;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, String> helpTextProvider;

        private Function<VariableManager, TextfieldStyle> styleProvider = vm -> null;

        private Function<VariableManager, List<CompletionProposal>> completionProposalsProvider;

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

        public Builder valueProvider(Function<VariableManager, String> valueProvider) {
            this.valueProvider = Objects.requireNonNull(valueProvider);
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

        public Builder styleProvider(Function<VariableManager, TextfieldStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public Builder completionProposalsProvider(Function<VariableManager, List<CompletionProposal>> completionProposalsProvider) {
            this.completionProposalsProvider = Objects.requireNonNull(completionProposalsProvider);
            return this;
        }

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public TextfieldDescription build() {
            TextfieldDescription textfieldDescription = new TextfieldDescription();
            textfieldDescription.id = Objects.requireNonNull(this.id);
            textfieldDescription.idProvider = Objects.requireNonNull(this.idProvider);
            textfieldDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            textfieldDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            textfieldDescription.valueProvider = Objects.requireNonNull(this.valueProvider);
            textfieldDescription.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            textfieldDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            textfieldDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            textfieldDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            textfieldDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            textfieldDescription.completionProposalsProvider = this.completionProposalsProvider; // Optional on purpose
            textfieldDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return textfieldDescription;
        }

    }
}
