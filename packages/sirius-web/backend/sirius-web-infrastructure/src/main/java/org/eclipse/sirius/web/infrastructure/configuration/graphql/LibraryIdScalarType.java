/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.infrastructure.configuration.graphql;

import graphql.schema.GraphQLScalarType;

/**
 * Custom {@link GraphQLScalarType} for library identifiers.
 *
 * @author gdaniel
 */
public class LibraryIdScalarType {

    public static final String LIBRARY_ID_TYPE = "LibraryId";

    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name(LIBRARY_ID_TYPE)
            .description("Library identifier serialized as namespace:name@version")
            .coercing(new LibraryIdCoercing())
            .build();
}
