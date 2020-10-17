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
package org.eclipse.sirius.web.graphql.schema;

/**
 * Constants for the creation of the GraphQL schema.
 *
 * @author sbegaudeau
 */
public final class GraphQLConstants {
    /**
     * The suffix used for GraphQL types used as connection for multi-valued references with pagination support.
     */
    public static final String CONNECTION = "Connection"; //$NON-NLS-1$

    /**
     * The suffix used for GraphQL types used as an edge for multi-valued references with pagination support.
     */
    public static final String EDGE = "Edge"; //$NON-NLS-1$

    /**
     * This class will only be used to store some constants, as such we are hiding its constructor.
     */
    private GraphQLConstants() {
        // Prevent instantiation
    }
}
