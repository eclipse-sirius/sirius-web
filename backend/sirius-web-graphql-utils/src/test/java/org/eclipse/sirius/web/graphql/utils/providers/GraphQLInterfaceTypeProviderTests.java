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
package org.eclipse.sirius.web.graphql.utils.providers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInterfaceType;

/**
 * Unit tests of the GraphQL interface type provider.
 *
 * @author sbegaudeau
 */
public class GraphQLInterfaceTypeProviderTests {
    /**
     * In this test, we will see if we can create a proper GraphQL interface type for a POJO containing a single String
     * field. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLInterfaceType
     * public class InterfaceTypeWithString {
     *     private String description;
     *
     *     &#64;GraphQLField
     *     public String getDescription() {
     *         return this.description;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL interface type:
     * </p>
     *
     * <pre>
     * interface InterfaceTypeWithString {
     *   description: String
     * }
     * </pre>
     */
    @Test
    public void testInterfaceTypeWithString() {
        GraphQLInterfaceType graphQLInterfaceType = new GraphQLInterfaceTypeProvider().getType(AbstractInterfaceTypeWithString.class);
        assertThat(graphQLInterfaceType.getName()).isEqualTo(AbstractInterfaceTypeWithString.class.getSimpleName());
        assertThat(graphQLInterfaceType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition fieldDefinition = graphQLInterfaceType.getFieldDefinitions().get(0);
        assertThat(fieldDefinition.getName()).isEqualTo("description"); //$NON-NLS-1$
        assertThat(fieldDefinition.getType()).isEqualTo(Scalars.GraphQLString);
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL interface type with a String field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.web.annotations.graphql.GraphQLInterfaceType
    public abstract static class AbstractInterfaceTypeWithString {
        private String description;

        @org.eclipse.sirius.web.annotations.graphql.GraphQLField
        public String getDescription() {
            return this.description;
        }
    }
}
