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
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the text area widget.
 *
 * @author lfasani
 */
@Immutable
public final class TextareaDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private Function<VariableManager, String> valueProvider;

    private BiFunction<VariableManager, String, IStatus> newValueHandler;

    private Function<VariableManager, TextareaStyle> styleProvider;

    private Function<VariableManager, List<CompletionProposal>> completionProposalsProvider;

    private TextareaDescription() {
        // Prevent instantiation
    }

    public static Builder newTextareaDescription(String id) {
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

    public Function<VariableManager, String> getValueProvider() {
        return this.valueProvider;
    }

    public BiFunction<VariableManager, String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public Function<VariableManager, TextareaStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public Function<VariableManager, List<CompletionProposal>> getCompletionProposalsProvider() {
        return this.completionProposalsProvider;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the textarea description.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> List.of();

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private Function<VariableManager, String> valueProvider;

        private BiFunction<VariableManager, String, IStatus> newValueHandler;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, String> helpTextProvider;

        private Function<VariableManager, TextareaStyle> styleProvider = vm -> null;

        private Function<VariableManager, List<CompletionProposal>> completionProposalsProvider;

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

        public Builder isReadOnlyProvider(Function<VariableManager, Boolean> isReadOnlyProvider) {
            this.isReadOnlyProvider = Objects.requireNonNull(isReadOnlyProvider);
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

        public Builder styleProvider(Function<VariableManager, TextareaStyle> styleProvider) {
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

        public TextareaDescription build() {
            TextareaDescription textareaDescription = new TextareaDescription();
            textareaDescription.id = Objects.requireNonNull(this.id);
            textareaDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            textareaDescription.idProvider = Objects.requireNonNull(this.idProvider);
            textareaDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            textareaDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            textareaDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            textareaDescription.valueProvider = Objects.requireNonNull(this.valueProvider);
            textareaDescription.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            textareaDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            textareaDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            textareaDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            textareaDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            textareaDescription.completionProposalsProvider = this.completionProposalsProvider; // Optional on purpose
            textareaDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return textareaDescription;
        }
    }
}
