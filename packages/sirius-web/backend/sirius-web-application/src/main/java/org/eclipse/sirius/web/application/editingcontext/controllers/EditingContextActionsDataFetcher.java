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
package org.eclipse.sirius.web.application.editingcontext.controllers;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.dto.EditingContextAction;
import org.eclipse.sirius.components.collaborative.dto.GetEditingContextActionsInput;
import org.eclipse.sirius.components.collaborative.dto.GetEditingContextActionsSuccessPayload;
import org.eclipse.sirius.components.core.graphql.dto.PageInfoWithCount;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import graphql.relay.Relay;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Data fetcher for the field EditingContext#actions.
 *
 * @author rpage
 */
@QueryDataFetcher(type = "EditingContext", field = "actions")
public class EditingContextActionsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Connection<EditingContextAction>>> {

    private final IEditingContextDispatcher editingContextDispatcher;

    public EditingContextActionsDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<Connection<EditingContextAction>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        GetEditingContextActionsInput input = new GetEditingContextActionsInput(UUID.randomUUID(), editingContextId);

        return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .filter(GetEditingContextActionsSuccessPayload.class::isInstance)
                .map(GetEditingContextActionsSuccessPayload.class::cast)
                .map(this::toConnection)
                .switchIfEmpty(Mono.just(new DefaultConnection<>(List.of(), new DefaultPageInfo(null, null, false, false))))
                .toFuture();
    }

    private Connection<EditingContextAction> toConnection(GetEditingContextActionsSuccessPayload payload) {
        List<Edge<EditingContextAction>> representationDescriptionEdges = payload.editingContextActions().stream()
                .map(representationDescription -> {
                    var globalId = new Relay().toGlobalId("EditingContextAction", representationDescription.getId());
                    ConnectionCursor cursor = new DefaultConnectionCursor(globalId);
                    return (Edge<EditingContextAction>) new DefaultEdge<>(representationDescription, cursor);
                })
                .toList();

        ConnectionCursor startCursor = representationDescriptionEdges.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!representationDescriptionEdges.isEmpty()) {
            endCursor = representationDescriptionEdges.get(representationDescriptionEdges.size() - 1).getCursor();
        }
        PageInfo pageInfo = new PageInfoWithCount(startCursor, endCursor, false, false, payload.editingContextActions().size());
        return new DefaultConnection<>(representationDescriptionEdges, pageInfo);
    }
}
