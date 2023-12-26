/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.graphql.api;

import graphql.schema.GraphQLScalarType;

/**
 * Custom {@link GraphQLScalarType} for Instant.
 *
 * @author lfasani
 */
public class InstantScalarType {
    public static final String INSTANT_TYPE = "Instant";

    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name(INSTANT_TYPE)
            .description("Implementation of java.time.Instant")
            .coercing(new InstantCoercing())
            .build();
}

