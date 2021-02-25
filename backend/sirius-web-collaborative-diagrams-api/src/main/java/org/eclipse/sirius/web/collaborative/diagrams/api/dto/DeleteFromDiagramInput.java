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
package org.eclipse.sirius.web.collaborative.diagrams.api.dto;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramInput;

/**
 * The class of the inputs for the "Delete from diagram" mutation.
 *
 * @author pcdavid
 * @author hmarchadour
 */
@GraphQLInputObjectType
public final class DeleteFromDiagramInput implements IDiagramInput {
    private UUID id;

    private UUID projectId;

    private UUID representationId;

    private List<UUID> nodeIds;

    private List<UUID> edgeIds;

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
    public List<@GraphQLNonNull @GraphQLID UUID> getNodeIds() {
        return this.nodeIds;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull @GraphQLID UUID> getEdgeIds() {
        return this.edgeIds;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, projectId: {2}, representationId: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.projectId, this.representationId);
    }
}
