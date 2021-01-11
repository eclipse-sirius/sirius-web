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
package org.eclipse.sirius.web.collaborative.diagrams.api.dto;

import java.text.MessageFormat;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramInput;

/**
 * The input for the "Update Node Position" mutation.
 *
 * @author fbarbin
 */
@GraphQLInputObjectType
public final class UpdateNodePositionInput implements IDiagramInput {
    private UUID projectId;

    private UUID representationId;

    private UUID diagramElementId;

    private double newPositionX;

    private double newPositionY;

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

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getDiagramElementId() {
        return this.diagramElementId;
    }

    @GraphQLField
    @GraphQLNonNull
    public double getNewPositionX() {
        return this.newPositionX;
    }

    @GraphQLField
    @GraphQLNonNull
    public double getNewPositionY() {
        return this.newPositionY;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'projectId: {1}, representationId: {2}, diagramElementId: {3}, newPositionX: {4}, newPositionY: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.projectId, this.representationId, this.diagramElementId, this.newPositionX, this.newPositionY);
    }
}
