/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.infrastructure.graphql.GraphQLWiringFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import graphql.GraphQL;
import graphql.execution.TypeResolutionParameters;
import graphql.schema.idl.InterfaceWiringEnvironment;

/**
 * Used to test the GraphQL type resolution.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TypeResolverTests extends AbstractIntegrationTests {

    @Autowired
    private GraphQLWiringFactory graphQLWiringFactory;

    @Autowired
    private GraphQL graphQL;

    @Test
    @DisplayName("Given the type resolver, when asked for a unknown type, then the expected type is returned")
    public void givenTheTypeResolverWhenAskedForUnknownTypeThenTheExpectedTypeIsReturned() {
        var environment = TypeResolutionParameters.newParameters()
                .schema(this.graphQL.getGraphQLSchema())
                .value(this)
                .build();

        var typeResolver = this.graphQLWiringFactory.getTypeResolver((InterfaceWiringEnvironment) null);
        var graphQLObjectType = typeResolver.getType(environment);
        assertThat(graphQLObjectType.getName()).isEqualTo("Diagram");
    }
}
