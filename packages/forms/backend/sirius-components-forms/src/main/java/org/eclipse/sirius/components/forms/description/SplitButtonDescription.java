/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.text.MessageFormat;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the split button widget.
 *
 * @author mcharfadi
 */
@Immutable
public final class SplitButtonDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private List<ButtonDescription> actions;

    private SplitButtonDescription() {
        // Prevent instantiation
    }

    public static Builder newSplitButtonDescription(String id) {
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

    public List<ButtonDescription> getActions() {
        return this.actions;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the splitButton description.
     *
     * @author mcharfadi
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> List.of();

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, String> helpTextProvider;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private List<ButtonDescription> actions;

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

        public Builder actions(List<ButtonDescription> actions) {
            this.actions = Objects.requireNonNull(actions);
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

        public Builder diagnosticsProvider(Function<VariableManager, List<?>> diagnosticsProvider) {
            this.diagnosticsProvider = Objects.requireNonNull(diagnosticsProvider);
            return this;
        }
        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public SplitButtonDescription build() {
            SplitButtonDescription splitButtonDescription = new SplitButtonDescription();
            splitButtonDescription.id = Objects.requireNonNull(this.id);
            splitButtonDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            splitButtonDescription.idProvider = Objects.requireNonNull(this.idProvider);
            splitButtonDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            splitButtonDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            splitButtonDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            splitButtonDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            splitButtonDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            splitButtonDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            splitButtonDescription.actions = Objects.requireNonNull(this.actions);
            splitButtonDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return splitButtonDescription;
        }

    }
}
