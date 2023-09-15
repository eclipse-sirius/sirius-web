/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import org.eclipse.sirius.components.forms.LinkStyle;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the Link widget.
 *
 * @author ldelaigue
 */
@Immutable
public final class LinkDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private Function<VariableManager, String> urlProvider;

    private Function<VariableManager, LinkStyle> styleProvider;

    private LinkDescription() {
        // Prevent instantiation
    }

    public static Builder newLinkDescription(String id) {
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

    public Function<VariableManager, String> getUrlProvider() {
        return this.urlProvider;
    }

    public Function<VariableManager, LinkStyle> getStyleProvider() {
        return this.styleProvider;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the Link description.
     *
     * @author ldelaigue
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private final Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> true;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> List.of();

        private Function<VariableManager, String> urlProvider;

        private Function<VariableManager, LinkStyle> styleProvider;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, String> helpTextProvider;

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

        public Builder urlProvider(Function<VariableManager, String> valueProvider) {
            this.urlProvider = Objects.requireNonNull(valueProvider);
            return this;
        }

        public Builder styleProvider(Function<VariableManager, LinkStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
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

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public LinkDescription build() {
            LinkDescription linkDescription = new LinkDescription();
            linkDescription.id = this.id;
            linkDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            linkDescription.idProvider = Objects.requireNonNull(this.idProvider);
            linkDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            linkDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            linkDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            linkDescription.urlProvider = Objects.requireNonNull(this.urlProvider);
            linkDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            linkDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            linkDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            linkDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            linkDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return linkDescription;
        }
    }
}
