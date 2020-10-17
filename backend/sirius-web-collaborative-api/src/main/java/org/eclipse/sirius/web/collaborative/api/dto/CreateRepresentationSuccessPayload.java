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
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.dto.IPayload;

/**
 * The payload of the create representation mutation.
 *
 * @author sbegaudeau
 */
@GraphQLObjectType
public final class CreateRepresentationSuccessPayload implements IPayload {

    private final IRepresentation representation;

    public CreateRepresentationSuccessPayload(IRepresentation representation) {
        this.representation = Objects.requireNonNull(representation);
    }

    @GraphQLField
    @GraphQLNonNull
    public IRepresentation getRepresentation() {
        return this.representation;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'representation: '{'id: {1}, type: {2}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.representation.getId(), this.representation.getClass().getSimpleName());
    }
}
