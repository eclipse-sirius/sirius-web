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
 * This class is used to create the definition of the Project type and its related types.
 * <p>
 * The types created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type Project {
 *   id: ID!
 *   name: String!
 *   owner: Account!
 *   visibility: Visibility!
 *   currentEditingContext: EditingContext!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
public class ProjectTypeProvider {
    public static final String TYPE = "Project";

    public static final String NAME_FIELD = "name";

    public static final String OWNER_FIELD = "owner";

    public static final String VISIBILITY_FIELD = "visibility";

    public static final String CURRENT_EDITING_CONTEXT_FIELD = "currentEditingContext";
}
