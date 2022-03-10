/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.validation.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.annotations.PublicApi;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The root concept of the description of a validation representation.
 *
 * @author gcoutable
 */
@PublicApi
@Immutable
public final class ValidationDescription implements IRepresentationDescription {
    private String id;

    private String label;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, List<?>> diagnosticsProvider;

    private Function<Object, String> kindProvider;

    private Function<Object, String> messageProvider;

    private ValidationDescription() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    public Function<VariableManager, List<?>> getDiagnosticsProvider() {
        return this.diagnosticsProvider;
    }

    public Function<Object, String> getKindProvider() {
        return this.kindProvider;
    }

    public Function<Object, String> getMessageProvider() {
        return this.messageProvider;
    }

    public static Builder newValidationDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * Builder used to create the validation description.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder canCreatePredicate(Predicate<VariableManager> canCreatePredicate) {
            this.canCreatePredicate = Objects.requireNonNull(canCreatePredicate);
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

        public ValidationDescription build() {
            ValidationDescription validationDescription = new ValidationDescription();
            validationDescription.id = Objects.requireNonNull(this.id);
            validationDescription.label = Objects.requireNonNull(this.label);
            validationDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            validationDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            validationDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            validationDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            return validationDescription;
        }
    }
}
