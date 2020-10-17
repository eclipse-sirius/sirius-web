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
 * The input object of the create root objec mutation.
 *
 * @author lfasani
 */
@GraphQLInputObjectType
public final class CreateRootObjectInput implements IProjectInput {
    private UUID projectId;

    private UUID documentId;

    private String namespaceId;

    private String rootObjectCreationDescriptionId;

    public CreateRootObjectInput() {
        // Used by Jackson
    }

    public CreateRootObjectInput(UUID projectId, UUID documentId, String namespaceId, String rootObjectCreationDescriptionId) {
        this.projectId = Objects.requireNonNull(projectId);
        this.documentId = Objects.requireNonNull(documentId);
        this.namespaceId = Objects.requireNonNull(namespaceId);
        this.rootObjectCreationDescriptionId = Objects.requireNonNull(rootObjectCreationDescriptionId);
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
    public UUID getDocumentId() {
        return this.documentId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getNamespaceId() {
        return this.namespaceId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getRootObjectCreationDescriptionId() {
        return this.rootObjectCreationDescriptionId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'projectId: {1}, documentId: {2}, namespaceId: {3}, rootObjectCreationDescriptionId: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.projectId, this.documentId, this.namespaceId, this.rootObjectCreationDescriptionId);
    }
}
