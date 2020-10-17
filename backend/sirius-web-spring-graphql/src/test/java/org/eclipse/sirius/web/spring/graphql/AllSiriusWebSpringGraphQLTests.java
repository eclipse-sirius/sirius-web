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
package org.eclipse.sirius.web.spring.graphql;

import org.eclipse.sirius.web.spring.graphql.architecture.CodingRulesTestCases;
import org.eclipse.sirius.web.spring.graphql.architecture.SpringCodingRulesTestCases;
import org.eclipse.sirius.web.spring.graphql.controllers.GraphQLControllerTestCases;
import org.eclipse.sirius.web.spring.graphql.ws.WebSocketHandlerTestCases;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Tests suite of the Sirius Web Spring GraphQL.
 *
 * @author sbegaudeau
 */
@RunWith(Suite.class)
@SuiteClasses({ CodingRulesTestCases.class, SpringCodingRulesTestCases.class, GraphQLControllerTestCases.class, WebSocketHandlerTestCases.class })
public final class AllSiriusWebSpringGraphQLTests {
    private AllSiriusWebSpringGraphQLTests() {
        // Prevent instantiation
    }
}
