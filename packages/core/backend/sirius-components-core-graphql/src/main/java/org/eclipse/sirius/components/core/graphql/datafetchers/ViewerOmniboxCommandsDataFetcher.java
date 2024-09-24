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

package org.eclipse.sirius.components.core.graphql.datafetchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.GetOmniboxCommandsInput;
import org.eclipse.sirius.components.collaborative.dto.GetOmniboxCommandsPayload;
import org.eclipse.sirius.components.collaborative.dto.OmniboxCommand;
import org.eclipse.sirius.components.collaborative.dto.OmniboxContextEntry;
import org.eclipse.sirius.components.core.graphql.dto.PageInfoWithCount;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.Relay;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Data fetcher for the field Viewer#omniboxCommands.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "Viewer", field = "omniboxCommands")
public class ViewerOmniboxCommandsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Connection<OmniboxCommand>>> {

    private static final String CONTEXT_ENTRIES_ARGUMENT = "contextEntries";

    private static final String QUERY_ARGUMENT = "query";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final ObjectMapper objectMapper;

    public ViewerOmniboxCommandsDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, ObjectMapper objectMapper) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public CompletableFuture<Connection<OmniboxCommand>> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(CONTEXT_ENTRIES_ARGUMENT);
        List<OmniboxContextEntry> omniboxContextEntries = this.objectMapper.convertValue(argument, new TypeReference<List<OmniboxContextEntry>>() { });
        String query = environment.getArgument(QUERY_ARGUMENT);
        var input = new GetOmniboxCommandsInput(UUID.randomUUID(), query);

        if (!omniboxContextEntries.isEmpty()) {
            var omniboxContextEntry = omniboxContextEntries.get(0);
            return this.editingContextEventProcessorRegistry.dispatchEvent(omniboxContextEntry.id(), input)
                    .filter(GetOmniboxCommandsPayload.class::isInstance)
                    .map(GetOmniboxCommandsPayload.class::cast)
                    .map(this::toConnection)
                    .switchIfEmpty(emptyMono())
                    .toFuture();
        }
        return emptyMono().toFuture();
    }

    private Mono<Connection<OmniboxCommand>> emptyMono() {
        return Mono.just(new DefaultConnection<>(List.of(), new DefaultPageInfo(null, null, false, false)));
    }

    private Connection<OmniboxCommand> toConnection(GetOmniboxCommandsPayload payload) {
        List<Edge<OmniboxCommand>> omniboxCommandsEdge = payload.omniboxCommands().stream()
                .map(omniboxCommand -> {
                    String globalId = new Relay().toGlobalId("ViewerOmniboxCommand", omniboxCommand.id().toString());
                    var cursor = new DefaultConnectionCursor(globalId);
                    return (Edge<OmniboxCommand>) new DefaultEdge<>(omniboxCommand, cursor);
                })
                .toList();

        ConnectionCursor startCursor = omniboxCommandsEdge.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!omniboxCommandsEdge.isEmpty()) {
            endCursor = omniboxCommandsEdge.get(omniboxCommandsEdge.size() - 1).getCursor();
        }

        var pageInfo = new PageInfoWithCount(startCursor, endCursor, false, false, payload.omniboxCommands().size());
        return new DefaultConnection<>(omniboxCommandsEdge, pageInfo);
    }
}
