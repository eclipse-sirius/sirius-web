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
package org.eclipse.sirius.components.tables.graphql.datafetchers.query;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.tables.dto.TableConfiguration;
import org.eclipse.sirius.components.collaborative.tables.dto.TableConfigurationInput;
import org.eclipse.sirius.components.collaborative.tables.dto.TableConfigurationSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Data fetcher used to retrieve table configuration.
 *
 * @author frouene
 */
@QueryDataFetcher(type = "RepresentationMetadata", field = "configuration")
public class RepresentationMetadataConfigurationDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<TableConfiguration>> {

    private final IEditingContextDispatcher editingContextDispatcher;

    public RepresentationMetadataConfigurationDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<TableConfiguration> get(DataFetchingEnvironment environment) throws Exception {
        CompletableFuture<TableConfiguration> result = Mono.<TableConfiguration>empty().toFuture();
        Map<String, Object> localContext = environment.getLocalContext();

        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        if (editingContextId != null && representationId != null) {
            TableConfigurationInput input = new TableConfigurationInput(UUID.randomUUID(), editingContextId, representationId);
            result = this.editingContextDispatcher.dispatchQuery(editingContextId, input)
                    .filter(TableConfigurationSuccessPayload.class::isInstance)
                    .map(TableConfigurationSuccessPayload.class::cast)
                    .map(TableConfigurationSuccessPayload::tableConfiguration)
                    .toFuture();
        }
        return result;
    }

}

