/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.validation;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.representations.IRepresentation;

/**
 * Root concept of the validation representation.
 *
 * @author gcoutable
 */
@Immutable
@GraphQLObjectType
public final class Validation implements IRepresentation {

    public static final String KIND = "Validation"; //$NON-NLS-1$

    private String id;

    private String kind;

    private String label;

    private UUID descriptionId;

    private List<Diagnostic> diagnostics;

    private Validation() {
        // Prevent instantiation
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getId() {
        return this.id;
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    @GraphQLNonNull
    @GraphQLField
    public String getLabel() {
        return this.label;
    }

    @Override
    @GraphQLNonNull
    @GraphQLField
    public String getKind() {
        return this.kind;
    }

    @GraphQLNonNull
    @GraphQLField
    public List<@GraphQLNonNull Diagnostic> getDiagnostics() {
        return this.diagnostics;
    }

    public static Builder newValidation(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, descriptionId: {3} diagnosticCount: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.descriptionId, this.diagnostics.size());
    }

    /**
     * The builder used to create the validation.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String kind = KIND;

        private String label;

        private UUID descriptionId;

        private List<Diagnostic> diagnostics;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = diagnostics;
            return this;
        }

        public Validation build() {
            Validation validation = new Validation();
            validation.id = Objects.requireNonNull(this.id);
            validation.kind = Objects.requireNonNull(this.kind);
            validation.label = Objects.requireNonNull(this.label);
            validation.descriptionId = Objects.requireNonNull(this.descriptionId);
            validation.diagnostics = Objects.requireNonNull(this.diagnostics);
            return validation;
        }

    }

}
