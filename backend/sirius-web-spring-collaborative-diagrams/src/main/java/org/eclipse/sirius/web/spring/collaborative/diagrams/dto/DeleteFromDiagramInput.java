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
 * The class of the inputs for the "Delete from diagram" mutation.
 *
 * @author pcdavid
 * @author hmarchadour
 */
@GraphQLInputObjectType
public final class DeleteFromDiagramInput implements IDiagramInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private List<String> nodeIds;

    private List<String> edgeIds;

    private DeletionPolicy deletionPolicy;

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
    public String getEditingContextId() {
        return this.editingContextId;
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getRepresentationId() {
        return this.representationId;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull @GraphQLID String> getNodeIds() {
        return this.nodeIds;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull @GraphQLID String> getEdgeIds() {
        return this.edgeIds;
    }

    public DeletionPolicy getDeletionPolicy() {
        return this.deletionPolicy;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, deletionPolicy: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.deletionPolicy);
    }
}
