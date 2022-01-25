/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.graphql.GraphQLField;
import org.eclipse.sirius.components.annotations.graphql.GraphQLID;
import org.eclipse.sirius.components.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;

/**
 * The input object for the checkbox edition mutation.
 *
 * @author sbegaudeau
 */
@GraphQLInputObjectType
public final class EditCheckboxInput implements IFormInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private String checkboxId;

    private boolean newValue;

    public EditCheckboxInput() {
        // Used by Jackson
    }

    public EditCheckboxInput(UUID id, String editingContextId, String representationId, String checkboxId, boolean newValue) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.checkboxId = Objects.requireNonNull(checkboxId);
        this.newValue = newValue;
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getId() {
        return this.id;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getEditingContextId() {
        return this.editingContextId;
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getRepresentationId() {
        return this.representationId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getCheckboxId() {
        return this.checkboxId;
    }

    @GraphQLField
    @GraphQLNonNull
    public boolean getNewValue() {
        return this.newValue;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, checkboxId: {4}, newValue: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.checkboxId, this.newValue);
    }
}
