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
package org.eclipse.sirius.web.services.api.projects;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.core.api.IPayload;

/**
 * Represent the result returned when renaming a project through the graphql API.
 *
 * @author fbarbin
 */
@GraphQLObjectType
public final class RenameProjectSuccessPayload implements IPayload {

    private final UUID id;

    private final Project project;

    public RenameProjectSuccessPayload(UUID id, Project project) {
        this.id = Objects.requireNonNull(id);
        this.project = Objects.requireNonNull(project);
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
    public Project getProject() {
        return this.project;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, project: '{'id: {2}, name: {3}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.project.getId(), this.project.getName());
    }
}
