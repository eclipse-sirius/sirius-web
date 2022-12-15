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
 * This class is used to create the definition of the Object type and its related types.
 * <p>
 * The types created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type Object {
 *   id: ID!
 *   type: String!
 *   label: String!
 *   expressionBasedObjects(expression: String!): [Object!]!
 *   expressionBasedObject(expression: String!): Object
 *   expressionBasedString(expression: String!): String
 *   expressionBasedInt(expression: String!): Int
 *   expressionBasedBoolean(expression: String!): Boolean
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
public class ObjectTypeProvider {
    public static final String TYPE = "Object";

    public static final String EXPRESSION_BASED_OBJECTS_FIELD = "expressionBasedObjects";

    public static final String EXPRESSION_BASED_OBJECT_FIELD = "expressionBasedObject";

    public static final String EXPRESSION_BASED_STRING_FIELD = "expressionBasedString";

    public static final String EXPRESSION_BASED_INT_FIELD = "expressionBasedInt";

    public static final String EXPRESSION_BASED_BOOLEAN_FIELD = "expressionBasedBoolean";

    public static final String EXPRESSION_ARGUMENT = "expression";
}
