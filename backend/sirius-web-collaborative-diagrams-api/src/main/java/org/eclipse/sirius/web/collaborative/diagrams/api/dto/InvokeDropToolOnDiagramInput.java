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
import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramInput;

/**
 * The input for the "Invoke drop tool on diagram" mutation.
 *
 * @author hmarchadour
 */
@GraphQLInputObjectType
public final class InvokeDropToolOnDiagramInput implements IDiagramInput {
    private UUID projectId;

    private UUID representationId;

    private String diagramElementId;

    private List<String> objectIds;

    private String toolId;

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
    public String getDiagramElementId() {
        return this.diagramElementId;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull String> getObjectIds() {
        return this.objectIds;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getToolId() {
        return this.toolId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'projectId: {1}, representationId: {2},  toolId: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.projectId, this.representationId, this.toolId);
    }
}
