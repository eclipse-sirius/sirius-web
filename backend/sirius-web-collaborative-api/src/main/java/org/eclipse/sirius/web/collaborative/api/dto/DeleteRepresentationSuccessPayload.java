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

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.services.api.projects.Project;

/**
 * The payload returned by the delete representation mutation.
 *
 * @author lfasani
 */
@GraphQLObjectType
public final class DeleteRepresentationSuccessPayload implements IPayload {

    private final Project project;

    public DeleteRepresentationSuccessPayload(Project project) {
        this.project = Objects.requireNonNull(project);
    }

    @GraphQLField
    @GraphQLNonNull
    public Project getproject() {
        return this.project;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'project: '{'id: {1}, name: {2}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.project.getId(), this.project.getName());
    }
}
