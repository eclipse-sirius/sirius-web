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
package org.eclipse.sirius.web.infrastructure.configuration.graphql;

import java.util.List;

import org.eclipse.sirius.web.infrastructure.configuration.graphql.api.IGraphQLInstrumentationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;

/**
 * Used to provide the tracing instrumentation.
 *
 * @author sbegaudeau
 */
@Service
public class GraphQLTracingInstrumentationProvider implements IGraphQLInstrumentationProvider {

    private final boolean activateTracing;

    public GraphQLTracingInstrumentationProvider(@Value("${sirius.web.graphql.tracing:false}") boolean activateTracing) {
        this.activateTracing = activateTracing;
    }

    @Override
    public List<Instrumentation> getInstrumentations() {
        if (activateTracing) {
            var tracingOptions = TracingInstrumentation.Options.newOptions().includeTrivialDataFetchers(false);
            return List.of(new TracingInstrumentation(tracingOptions));
        }
        return List.of();
    }
}
