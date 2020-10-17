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
package org.eclipse.sirius.web.spring.graphql.api;

/**
 * Utility constants for GraphQL.
 *
 * @author sbegaudeau
 */
public final class GraphQLConstants {

    public static final String GRAPHQL_BASE_PATH = "/api/graphql"; //$NON-NLS-1$

    public static final String PRINCIPAL = "principal"; //$NON-NLS-1$

    public static final String SUBSCRIPTION_ID = "userId"; //$NON-NLS-1$

    private GraphQLConstants() {
        // Prevent instantiation
    }
}
