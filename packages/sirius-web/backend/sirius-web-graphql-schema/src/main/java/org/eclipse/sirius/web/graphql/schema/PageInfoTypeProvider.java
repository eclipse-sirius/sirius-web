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
 * This class is used to create the definition of the PageInfo type.
 * <p>
 * The type created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type PageInfo {
 *   hasPreviousPage: boolean!
 *   hasNextPage: boolean!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
public class PageInfoTypeProvider {
    public static final String TYPE = "PageInfo";

    public static final String HAS_PREVIOUS_PAGE_FIELD = "hasPreviousPage";

    public static final String HAS_NEXT_PAGE_FIELD = "hasNextPage";

    public static final String START_CURSOR_FIELD = "startCursor";

    public static final String END_CURSOR_FIELD = "endCursor";

}
