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
package org.eclipse.sirius.web.services.api.modelers;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.core.api.IInput;

/**
 * The input object of the create modeler mutation.
 *
 * @author pcdavid
 */
@GraphQLInputObjectType
public final class CreateModelerInput implements IInput {

    private UUID id;

    private String name;

    private UUID projectId;

    public CreateModelerInput() {
        // Used by Jackson
    }

    public CreateModelerInput(UUID id, String name, UUID projectId) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.projectId = Objects.requireNonNull(projectId);
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getId() {
        return this.id;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getName() {
        return this.name;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getProjectId() {
        return this.projectId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, name: {2}, projectId: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.name, this.projectId);
    }

}
