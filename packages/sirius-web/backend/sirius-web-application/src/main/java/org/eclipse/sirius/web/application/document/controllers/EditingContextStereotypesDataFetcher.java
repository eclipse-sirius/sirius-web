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
package org.eclipse.sirius.web.application.document.controllers;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.web.application.document.dto.Stereotype;
import org.eclipse.sirius.web.application.document.services.GetStereotypesInput;
import org.eclipse.sirius.web.application.document.services.GetStereotypesPayload;

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

/**
 * Data fetcher for the field EditingContext#stereotypes.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "EditingContext", field = "stereotypes")
public class EditingContextStereotypesDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Connection<Stereotype>>> {

    private final IEditingContextDispatcher editingContextDispatcher;

    public EditingContextStereotypesDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<Connection<Stereotype>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();

        return this.editingContextDispatcher.dispatchQuery(editingContextId, new GetStereotypesInput(UUID.randomUUID()))
                .filter(GetStereotypesPayload.class::isInstance)
                .map(GetStereotypesPayload.class::cast)
                .map(payload -> {
                    var stereotypePage = payload.stereotypes();

                    var stereotypeEdges = stereotypePage.stream()
                            .map(stereotype -> {
                                var value = new Relay().toGlobalId("Stereotype", stereotype.id());
                                var cursor = new DefaultConnectionCursor(value);
                                Edge<Stereotype> edge = new DefaultEdge<>(stereotype, cursor);
                                return edge;
                            })
                            .toList();

                    ConnectionCursor startCursor = stereotypeEdges.stream()
                            .findFirst()
                            .map(Edge::getCursor)
                            .orElse(null);
                    ConnectionCursor endCursor = null;
                    if (!stereotypeEdges.isEmpty()) {
                        endCursor = stereotypeEdges.get(stereotypeEdges.size() - 1).getCursor();
                    }
                    PageInfo pageInfo = new DefaultPageInfo(startCursor, endCursor, stereotypePage.hasPrevious(), stereotypePage.hasNext());
                    Connection<Stereotype> connection = new DefaultConnection<>(stereotypeEdges, pageInfo);
                    return connection;
                })
                .toFuture();

    }
}
