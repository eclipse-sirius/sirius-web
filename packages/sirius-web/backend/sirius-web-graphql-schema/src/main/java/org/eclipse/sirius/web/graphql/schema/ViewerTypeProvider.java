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
 * This class is used to create the definition of the Viewer interface and its related types.
 * <p>
 * The types created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * interface Viewer {
 *   id: ID!
 *   username: String!
 *   projects: [Project!]!
 *   project(projectId: ID!): Project
 *   editingContext(editingContextId: ID!): EditingContext
 *   representationDescriptions(classId: ID): ViewerRepresentationDescriptionConnection!
 * }
 *
 * type User implements Viewer {
 *   id: ID!
 *   username: String!
 *   projects: [Project!]!
 *   project(projectId: ID!): Project
 *   representationDescriptions(classId: ID): ViewerRepresentationDescriptionConnection!
 * }
 *
 * type ViewerRepresentationDescriptionConnection {
 *   edges: [UserRepresentationDescriptionEdge!]!
 *   pageInfo: PageInfo!
 * }
 *
 * type ViewerRepresentationDescriptionEdge {
 *   node: RepresentationDescription!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
public class ViewerTypeProvider {
    public static final String TYPE = "Viewer"; //$NON-NLS-1$

    public static final String USER_TYPE = "User"; //$NON-NLS-1$

    public static final String USERNAME_FIELD = "username"; //$NON-NLS-1$

    public static final String PROJECTS_FIELD = "projects"; //$NON-NLS-1$

    public static final String PROJECT_FIELD = "project"; //$NON-NLS-1$

    public static final String SCHEMA_ID_ARGUMENT = "schemaId"; //$NON-NLS-1$

    public static final String PROJECT_ID_ARGUMENT = "projectId"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_FIELD = "editingContext"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_ID_ARGUMENT = "editingContextId"; //$NON-NLS-1$
}
