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
package org.eclipse.sirius.web.services.api.representations;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.services.api.dto.IProjectInput;
import org.eclipse.sirius.web.services.api.dto.IRepresentationInput;

/**
 * The input of the rename representation mutation.
 *
 * @author arichard
 */
@GraphQLInputObjectType
public final class RenameRepresentationInput implements IProjectInput, IRepresentationInput {

    private UUID projectId;

    private UUID representationId;

    private String newLabel;

    public RenameRepresentationInput() {
        // Used by Jackson
    }

    public RenameRepresentationInput(UUID projectId, UUID representationId, String newLabel) {
        this.projectId = Objects.requireNonNull(projectId);
        this.representationId = Objects.requireNonNull(representationId);
        this.newLabel = Objects.requireNonNull(newLabel);
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
        String pattern = "{0} '{'representationId: {1}, newLabel: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.representationId, this.newLabel);
    }
}
