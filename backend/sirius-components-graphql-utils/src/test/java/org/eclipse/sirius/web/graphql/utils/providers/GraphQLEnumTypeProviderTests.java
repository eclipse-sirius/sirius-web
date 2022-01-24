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

import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLEnumValueDefinition;

/**
 * Unit tests of the GraphQL enum type provider.
 *
 * @author sbegaudeau
 */
public class GraphQLEnumTypeProviderTests {
    /**
     * In this test, we will see if we can create a proper GraphQL enum type. For that we will consider the following
     * POJO:
     *
     * <pre>
     * &#64;GraphQLEnumType
     * public enum MyEnum {
     *     Enum1, Enum2
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL enum type:
     * </p>
     *
     * <pre>
     * enum MyEnum {
     *   Enum1
     *   Enum2
     * }
     * </pre>
     */
    @Test
    public void testEnumType() {
        GraphQLEnumType graphQLEnumType = new GraphQLEnumTypeProvider().getType(MyEnum.class);
        assertThat(graphQLEnumType.getName()).isEqualTo(MyEnum.class.getSimpleName());
        assertThat(graphQLEnumType.getValues()).hasSize(2);

        GraphQLEnumValueDefinition enum1 = graphQLEnumType.getValues().get(0);
        assertThat(enum1.getName()).isEqualTo(MyEnum.Enum1.name());
        GraphQLEnumValueDefinition enum2 = graphQLEnumType.getValues().get(1);
        assertThat(enum2.getName()).isEqualTo(MyEnum.Enum2.name());
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL enum type.
     *
     * @author hmarchadour
     */
    @org.eclipse.sirius.web.annotations.graphql.GraphQLEnumType
    public enum MyEnum {
        Enum1, Enum2
    }
}
