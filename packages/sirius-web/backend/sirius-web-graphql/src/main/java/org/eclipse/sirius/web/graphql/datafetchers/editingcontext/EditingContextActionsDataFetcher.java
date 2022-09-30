/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.editingcontext;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.EditingContextAction;
import org.eclipse.sirius.components.collaborative.dto.GetEditingContextActionsInput;
import org.eclipse.sirius.components.collaborative.dto.GetEditingContextActionsSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.pagination.PageInfoWithCount;

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The data fetcher used to retrieve the actions accessible in an editing context.
 *
 * @author rpage
 */
@QueryDataFetcher(type = "EditingContext", field = "actions")
public class EditingContextActionsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Connection<EditingContextAction>>> {

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextActionsDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<Connection<EditingContextAction>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        GetEditingContextActionsInput input = new GetEditingContextActionsInput(UUID.randomUUID(), editingContextId);
        // @formatter:off
        return this.editingContextEventProcessorRegistry.dispatchEvent(input.getEditingContextId(), input)
                .filter(GetEditingContextActionsSuccessPayload.class::isInstance)
                .map(GetEditingContextActionsSuccessPayload.class::cast)
                .map(this::toConnection)
                .switchIfEmpty(Mono.just(new DefaultConnection<>(List.of(), new DefaultPageInfo(null, null, false, false))))
                .toFuture();
        // @formatter:on
    }

    private Connection<EditingContextAction> toConnection(GetEditingContextActionsSuccessPayload payload) {
        // @formatter:off
        List<Edge<EditingContextAction>> representationDescriptionEdges = payload.getEditingContextActions().stream()
                .map(representationDescription -> {
                    String value = Base64.getEncoder().encodeToString(representationDescription.getId().getBytes());
                    ConnectionCursor cursor = new DefaultConnectionCursor(value);
                    return new DefaultEdge<>(representationDescription, cursor);
                })
                .collect(Collectors.toList());
        // @formatter:on

        ConnectionCursor startCursor = representationDescriptionEdges.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!representationDescriptionEdges.isEmpty()) {
            endCursor = representationDescriptionEdges.get(representationDescriptionEdges.size() - 1).getCursor();
        }
        PageInfo pageInfo = new PageInfoWithCount(startCursor, endCursor, false, false, payload.getEditingContextActions().size());
        return new DefaultConnection<>(representationDescriptionEdges, pageInfo);
    }

}
