/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import org.eclipse.sirius.web.services.api.projects.IProjectInput;

/**
 * The input object of the create representation mutation.
 *
 * @author sbegaudeau
 */
@GraphQLInputObjectType
public final class CreateRepresentationInput implements IProjectInput {

    private UUID projectId;

    private UUID representationDescriptionId;

    private String objectId;

    private String representationName;

    public CreateRepresentationInput() {
        // Used by Jackson
    }

    public CreateRepresentationInput(UUID projectId, UUID representationDescriptionId, String objectId, String representationName) {
        this.projectId = Objects.requireNonNull(projectId);
        this.representationDescriptionId = Objects.requireNonNull(representationDescriptionId);
        this.objectId = Objects.requireNonNull(objectId);
        this.representationName = Objects.requireNonNull(representationName);
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getProjectId() {
        return this.projectId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getRepresentationDescriptionId() {
        return this.representationDescriptionId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getObjectId() {
        return this.objectId;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getRepresentationName() {
        return this.representationName;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'projectId: {1}, representationDescriptionId: {2}, objectId: {3}, representationName: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.projectId, this.representationDescriptionId, this.objectId, this.representationName);
    }
}
