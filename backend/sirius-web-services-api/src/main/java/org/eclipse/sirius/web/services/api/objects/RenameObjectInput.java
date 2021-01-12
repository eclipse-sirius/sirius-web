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
import org.eclipse.sirius.web.services.api.projects.IProjectInput;

/**
 * The input of the rename object mutation.
 *
 * @author arichard
 */
@GraphQLInputObjectType
public final class RenameObjectInput implements IProjectInput {

    private UUID projectId;

    private String objectId;

    private String newName;

    public RenameObjectInput() {
        // Used by Jackson
    }

    public RenameObjectInput(UUID projectId, String objectId, String newName) {
        this.projectId = Objects.requireNonNull(projectId);
        this.objectId = Objects.requireNonNull(objectId);
        this.newName = Objects.requireNonNull(newName);
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

    @GraphQLField
    @GraphQLNonNull
    public String getNewName() {
        return this.newName;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'objectId: {1}, newName: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.objectId, this.newName);
    }
}
