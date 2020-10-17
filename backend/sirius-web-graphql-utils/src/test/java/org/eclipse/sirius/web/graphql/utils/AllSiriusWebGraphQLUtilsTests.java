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
package org.eclipse.sirius.web.graphql.utils;

import org.eclipse.sirius.web.graphql.utils.architecture.CodingRulesTestCases;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLEnumTypeProviderTestCases;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLInterfaceTypeProviderTestCases;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLObjectTypeProviderTestCases;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for Sirius Web GraphQL Utils.
 *
 * @author sbegaudeau
 */
@RunWith(Suite.class)
@SuiteClasses({ CodingRulesTestCases.class, GraphQLEnumTypeProviderTestCases.class, GraphQLInterfaceTypeProviderTestCases.class, GraphQLObjectTypeProviderTestCases.class, })
public final class AllSiriusWebGraphQLUtilsTests {
    private AllSiriusWebGraphQLUtilsTests() {
        // Prevent instantiation
    }
}
