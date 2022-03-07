/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
 * The description of the Link widget.
 *
 * @author ldelaigue
 */
@Immutable
public final class LinkDescription extends AbstractWidgetDescription {
    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> urlProvider;

    private LinkDescription() {
        // Prevent instantiation
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, String> getUrlProvider() {
        return this.urlProvider;
    }

    public static Builder newLinkDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
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

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> urlProvider;

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

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder urlProvider(Function<VariableManager, String> valueProvider) {
            this.urlProvider = Objects.requireNonNull(valueProvider);
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

        public LinkDescription build() {
            LinkDescription linkDescription = new LinkDescription();
            linkDescription.id = this.id;
            linkDescription.idProvider = Objects.requireNonNull(this.idProvider);
            linkDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            linkDescription.urlProvider = Objects.requireNonNull(this.urlProvider);
            linkDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            linkDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            linkDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            return linkDescription;
        }
    }
}
