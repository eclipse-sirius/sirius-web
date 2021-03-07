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
package org.eclipse.sirius.web.services.api.document;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.services.api.dto.IProjectInput;

/**
 * The input object of the create document mutation.
 *
 * @author sbegaudeau
 */
@GraphQLInputObjectType
public final class CreateDocumentInput implements IProjectInput {
    private UUID projectId;

    private String name;

    private String stereotypeDescriptionId;

    public CreateDocumentInput() {
        // Used by Jackson
    }

    public CreateDocumentInput(UUID projectId, String name, String stereotypeDescriptionId) {
        this.projectId = Objects.requireNonNull(projectId);
        this.name = Objects.requireNonNull(name);
        this.stereotypeDescriptionId = Objects.requireNonNull(stereotypeDescriptionId);
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getProjectId() {
        return this.projectId;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getName() {
        return this.name;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getStereotypeDescriptionId() {
        return this.stereotypeDescriptionId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'projectId: {1}, name: {2}, stereotypeDescriptionId: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.projectId, this.name, this.stereotypeDescriptionId);
    }
}
