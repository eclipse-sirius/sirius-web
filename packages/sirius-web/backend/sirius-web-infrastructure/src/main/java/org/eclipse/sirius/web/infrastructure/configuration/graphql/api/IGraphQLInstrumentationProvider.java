/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.infrastructure.configuration.graphql.api;

import java.util.List;

import graphql.execution.instrumentation.Instrumentation;

/**
 * Used to contribute GraphQL instrumentations.
 *
 * @author sbegaudeau
 * @since v2026.7.0
 */
public interface IGraphQLInstrumentationProvider {
    List<Instrumentation> getInstrumentations();
}
