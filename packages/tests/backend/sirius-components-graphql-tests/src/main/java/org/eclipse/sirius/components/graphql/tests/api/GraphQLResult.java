/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import java.util.List;
import java.util.Map;

import graphql.GraphQLError;

/**
 * Used to retrieve the result of a query or mutation.
 *
 * @author sbegaudeau
 */
public record GraphQLResult(String data, List<GraphQLError> errors, Map<Object, Object> extensions) {
}
