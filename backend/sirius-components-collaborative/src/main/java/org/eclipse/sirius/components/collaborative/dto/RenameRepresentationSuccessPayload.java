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
package org.eclipse.sirius.components.collaborative.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.graphql.GraphQLField;
import org.eclipse.sirius.components.annotations.graphql.GraphQLID;
import org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * The payload returned by the rename representation mutation.
 *
 * @author arichard
 */
@GraphQLObjectType
public final class RenameRepresentationSuccessPayload implements IPayload {

    private final UUID id;

    private final IRepresentation representation;

    public RenameRepresentationSuccessPayload(UUID id, IRepresentation representation) {
        this.id = Objects.requireNonNull(id);
        this.representation = Objects.requireNonNull(representation);
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
    public IRepresentation getRepresentation() {
        return this.representation;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, representation: '{'id: {2}, label: {3}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.representation.getId(), this.representation.getLabel());
    }
}
