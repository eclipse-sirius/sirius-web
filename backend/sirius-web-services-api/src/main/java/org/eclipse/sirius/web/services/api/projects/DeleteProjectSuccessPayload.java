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
package org.eclipse.sirius.web.services.api.projects;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.services.api.viewer.IViewer;

/**
 * Represent the result returned when deleting a project through the graphql API.
 *
 * @author fbarbin
 */
@GraphQLObjectType
public final class DeleteProjectSuccessPayload implements IPayload {

    private final IViewer viewer;

    public DeleteProjectSuccessPayload(IViewer viewer) {
        this.viewer = Objects.requireNonNull(viewer);
    }

    @GraphQLField
    @GraphQLNonNull
    public IViewer getViewer() {
        return this.viewer;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'viewer: '{'id: {1}, username: {2}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.viewer.getId(), this.viewer.getUsername());
    }
}
