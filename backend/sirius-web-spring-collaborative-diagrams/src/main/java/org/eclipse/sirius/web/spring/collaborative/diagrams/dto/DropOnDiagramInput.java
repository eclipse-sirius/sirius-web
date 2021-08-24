/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams.dto;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramInput;

/**
 * The input for the "drop on diagram" mutation.
 *
 * @author hmarchadour
 */
@GraphQLInputObjectType
public final class DropOnDiagramInput implements IDiagramInput {
    private UUID id;

    private UUID editingContextId;

    private UUID representationId;

    private UUID diagramTargetElementId;

    private List<String> objectIds;

    private double startingPositionX;

    private double startingPositionY;

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getId() {
        return this.id;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getEditingContextId() {
        return this.editingContextId;
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
    public UUID getDiagramTargetElementId() {
        return this.diagramTargetElementId;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull @GraphQLID String> getObjectIds() {
        return this.objectIds;
    }

    @GraphQLField
    @GraphQLNonNull
    public double getStartingPositionX() {
        return this.startingPositionX;
    }

    @GraphQLField
    @GraphQLNonNull
    public double getStartingPositionY() {
        return this.startingPositionY;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, diagramTargetElementId: {3}, representationId: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.diagramTargetElementId, this.representationId);
    }
}
