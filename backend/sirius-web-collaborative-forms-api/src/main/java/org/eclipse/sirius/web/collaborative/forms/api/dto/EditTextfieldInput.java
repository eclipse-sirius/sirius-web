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
package org.eclipse.sirius.web.collaborative.forms.api.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.collaborative.forms.api.IFormInput;

/**
 * The input object for the text field edition mutation.
 *
 * @author pcdavid
 */
@GraphQLInputObjectType
public final class EditTextfieldInput implements IFormInput {

    private UUID id;

    private UUID projectId;

    private UUID representationId;

    private String textfieldId;

    private String newValue;

    public EditTextfieldInput() {
        // Used by Jackson
    }

    public EditTextfieldInput(UUID id, UUID projectId, UUID representationId, String textfieldId, String newValue) {
        this.id = Objects.requireNonNull(id);
        this.projectId = Objects.requireNonNull(projectId);
        this.representationId = Objects.requireNonNull(representationId);
        this.textfieldId = Objects.requireNonNull(textfieldId);
        this.newValue = Objects.requireNonNull(newValue);
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
    public UUID getProjectId() {
        return this.projectId;
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getRepresentationId() {
        return this.representationId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getTextfieldId() {
        return this.textfieldId;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getNewValue() {
        return this.newValue;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, projectId: {2}, representationId: {3}, textfieldId: {4}, newValue: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.projectId, this.representationId, this.textfieldId, this.newValue);
    }

}
