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
package org.eclipse.sirius.web.collaborative.api.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.core.api.IRepresentationInput;

/**
 * The input of the rename representation mutation.
 *
 * @author arichard
 */
@GraphQLInputObjectType
public final class RenameRepresentationInput implements IRepresentationInput {

    private UUID id;

    private UUID projectId;

    private UUID representationId;

    private String newLabel;

    public RenameRepresentationInput() {
        // Used by Jackson
    }

    public RenameRepresentationInput(UUID id, UUID projectId, UUID representationId, String newLabel) {
        this.id = Objects.requireNonNull(id);
        this.projectId = Objects.requireNonNull(projectId);
        this.representationId = Objects.requireNonNull(representationId);
        this.newLabel = Objects.requireNonNull(newLabel);
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

    @GraphQLField
    @GraphQLNonNull
    public String getNewLabel() {
        return this.newLabel;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, representationId: {2}, newLabel: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.representationId, this.newLabel);
    }
}
