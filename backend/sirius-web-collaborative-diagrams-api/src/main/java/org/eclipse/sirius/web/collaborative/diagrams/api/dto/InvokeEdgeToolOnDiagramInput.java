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
 * The input for the "Invoke an edge tool on diagram" mutation.
 *
 * @author pcdavid
 * @author hmarchadour
 */
@GraphQLInputObjectType
public final class InvokeEdgeToolOnDiagramInput implements IDiagramInput {
    private UUID projectId;

    private UUID representationId;

    private String diagramSourceElementId;

    private String diagramTargetElementId;

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
    @GraphQLNonNull
    public String getDiagramSourceElementId() {
        return this.diagramSourceElementId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getDiagramTargetElementId() {
        return this.diagramTargetElementId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getToolId() {
        return this.toolId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'projectId: {1}, representationId: {2}, diagramSourceElementId: {3}, diagramTargetElementId: {4}, toolId: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.projectId, this.representationId, this.diagramSourceElementId, this.diagramTargetElementId, this.toolId);
    }
}
