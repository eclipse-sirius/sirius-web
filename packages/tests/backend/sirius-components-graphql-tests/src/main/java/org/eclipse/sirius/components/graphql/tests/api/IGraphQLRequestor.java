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
package org.eclipse.sirius.components.graphql.tests.api;

import java.util.Map;

import org.eclipse.sirius.components.core.api.IInput;

import reactor.core.publisher.Flux;

/**
 * Interface used during the integration tests to simplify the execution of GraphQL requests.
 *
 * @author sbegaudeau
 */
public interface IGraphQLRequestor {
    String execute(String query, Map<String, Object> variables);

    String execute(String query, IInput input);

    Flux<Object> subscribe(String query, IInput input);

    Flux<String> subscribeToSpecification(String query, IInput input);
}
