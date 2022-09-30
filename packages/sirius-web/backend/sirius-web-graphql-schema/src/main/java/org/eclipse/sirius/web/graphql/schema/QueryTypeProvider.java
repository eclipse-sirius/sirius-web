/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
 * This class is used to create the definition of the Query type.
 * <p>
 * The type created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type Query {
 *   viewer: Viewer!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
public class QueryTypeProvider {
    public static final String TYPE = "Query"; //$NON-NLS-1$

    public static final String VIEWER_FIELD = "viewer"; //$NON-NLS-1$
}
