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
package org.eclipse.sirius.web.services.api.objects;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.services.api.dto.IProjectInput;

/**
 * The input object of the create child mutation.
 *
 * @author sbegaudeau
 */
@GraphQLInputObjectType
public final class CreateChildInput implements IProjectInput {
    private UUID projectId;

    private String objectId;

    private String childCreationDescriptionId;

    public CreateChildInput() {
        // Used by Jackson
    }

    public CreateChildInput(UUID projectId, String objectId, String childCreationDescriptionId) {
        this.projectId = Objects.requireNonNull(projectId);
        this.objectId = Objects.requireNonNull(objectId);
        this.childCreationDescriptionId = Objects.requireNonNull(childCreationDescriptionId);
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
    public String getObjectId() {
        return this.objectId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getChildCreationDescriptionId() {
        return this.childCreationDescriptionId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'projectId: {1}, objectId: {2}, childCreationDescriptionId: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.projectId, this.objectId, this.childCreationDescriptionId);
    }
}
