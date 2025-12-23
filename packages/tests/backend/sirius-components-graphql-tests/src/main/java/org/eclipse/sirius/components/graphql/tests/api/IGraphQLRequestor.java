/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.graphql.tests.api;

import java.util.Map;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * Interface used during the integration tests to simplify the execution of GraphQL requests.
 *
 * @author sbegaudeau
 */
public interface IGraphQLRequestor {
    GraphQLResult execute(String query, Map<String, Object> variables);

    GraphQLResult execute(String query, IInput input);

    GraphQLSubscriptionResult subscribe(String query, IInput input);

    GraphQLSubscriptionResult subscribeToSpecification(String query, IInput input);
}
