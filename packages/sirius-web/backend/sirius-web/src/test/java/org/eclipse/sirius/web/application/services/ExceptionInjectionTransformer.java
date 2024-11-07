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

import java.util.Optional;

import org.eclipse.sirius.web.infrastructure.configuration.graphql.IGraphQLCodeRegistryTransformer;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLCodeRegistry.Builder;

/**
 * Used to modify some data-fetcher to deliberately throw exceptions for testing purposes.
 *
 * @author pcdavid
 */
public class ExceptionInjectionTransformer implements IGraphQLCodeRegistryTransformer {
    @Override
    public void transform(Builder builder) {
        var coordinates = FieldCoordinates.coordinates("Mutation", "renameProject");
        builder.dataFetcher(coordinates, new DataFetcher<>() {
            @Override
            public Object get(DataFetchingEnvironment environment) throws Exception {
                Optional.empty().orElseThrow(() -> new RuntimeException("injected fault"));
                return null;
            }
        });
    }
}
