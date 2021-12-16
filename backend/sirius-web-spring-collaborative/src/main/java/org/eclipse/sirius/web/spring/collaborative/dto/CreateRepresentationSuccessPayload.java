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
package org.eclipse.sirius.web.spring.collaborative.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.representations.IRepresentationMetadata;

/**
 * The payload of the create representation mutation.
 *
 * @author sbegaudeau
 */
@GraphQLObjectType
public final class CreateRepresentationSuccessPayload implements IPayload {

    private final UUID id;

    private final IRepresentationMetadata representationMetadata;

    public CreateRepresentationSuccessPayload(UUID id, IRepresentationMetadata representationMetadata) {
        this.id = Objects.requireNonNull(id);
        this.representationMetadata = Objects.requireNonNull(representationMetadata);
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
    public IRepresentationMetadata getRepresentation() {
        return this.representationMetadata;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, representation: '{'id: {2}, kind: {3}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.representationMetadata.getId(), this.representationMetadata.getKind());
    }
}
