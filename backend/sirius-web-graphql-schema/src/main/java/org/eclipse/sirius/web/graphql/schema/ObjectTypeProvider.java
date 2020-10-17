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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.springframework.stereotype.Service;

import graphql.Scalars;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;

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
@Service
public class ObjectTypeProvider implements ITypeProvider {
    public static final String TYPE = "Object"; //$NON-NLS-1$

    public static final String EXPRESSION_BASED_OBJECTS_FIELD = "expressionBasedObjects"; //$NON-NLS-1$

    public static final String EXPRESSION_BASED_OBJECT_FIELD = "expressionBasedObject"; //$NON-NLS-1$

    public static final String EXPRESSION_BASED_STRING_FIELD = "expressionBasedString"; //$NON-NLS-1$

    public static final String EXPRESSION_BASED_INT_FIELD = "expressionBasedInt"; //$NON-NLS-1$

    public static final String EXPRESSION_BASED_BOOLEAN_FIELD = "expressionBasedBoolean"; //$NON-NLS-1$

    public static final String EXPRESSION_ARGUMENT = "expression"; //$NON-NLS-1$

    @Override
    public Set<GraphQLType> getTypes() {
        // @formatter:off
        GraphQLObjectType objectType = GraphQLObjectType.newObject()
                .name(TYPE)
                .field(new IdFieldProvider().getField())
                .field(new LabelFieldProvider().getField())
                .field(new KindFieldProvider().getField())
                .field(this.getExpressionBasedObjectsField())
                .field(this.getExpressionBasedObjectField())
                .field(this.getExpressionBasedStringField())
                .field(this.getExpressionBasedIntField())
                .field(this.getExpressionBasedBooleanField())
                .build();
        // @formatter:on

        Set<GraphQLType> types = new LinkedHashSet<>();

        types.add(objectType);

        return types;
    }

    private GraphQLFieldDefinition getExpressionBasedObjectsField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(EXPRESSION_BASED_OBJECTS_FIELD)
                .argument(this.getExpressionArgument())
                .type(new GraphQLNonNull(new GraphQLList(new GraphQLNonNull(new GraphQLTypeReference(TYPE)))))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getExpressionBasedObjectField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(EXPRESSION_BASED_OBJECT_FIELD)
                .argument(this.getExpressionArgument())
                .type(new GraphQLTypeReference(TYPE))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getExpressionBasedStringField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(EXPRESSION_BASED_STRING_FIELD)
                .argument(this.getExpressionArgument())
                .type(Scalars.GraphQLString)
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getExpressionBasedIntField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(EXPRESSION_BASED_INT_FIELD)
                .argument(this.getExpressionArgument())
                .type(Scalars.GraphQLInt)
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getExpressionBasedBooleanField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(EXPRESSION_BASED_BOOLEAN_FIELD)
                .argument(this.getExpressionArgument())
                .type(Scalars.GraphQLBoolean)
                .build();
        // @formatter:on
    }

    private GraphQLArgument getExpressionArgument() {
        // @formatter:off
        return GraphQLArgument.newArgument()
                .name(EXPRESSION_ARGUMENT)
                .type(new GraphQLNonNull(Scalars.GraphQLString))
                .build();
        // @formatter:on
    }
}
