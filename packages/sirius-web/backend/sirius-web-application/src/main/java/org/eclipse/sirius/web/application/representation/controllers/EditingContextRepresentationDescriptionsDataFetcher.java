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
package org.eclipse.sirius.web.application.representation.controllers;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.api.RepresentationDescriptionMetadata;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRepresentationDescriptionsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRepresentationDescriptionsPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.dto.PageInfoWithCount;

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
 * Data fetcher for the field EditingContext#representationDescriptions.
 *
 * @author pcdavid
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "EditingContext", field = "representationDescriptions")
public class EditingContextRepresentationDescriptionsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Connection<RepresentationDescriptionMetadata>>> {

    private static final String OBJECT_ID_ARGUMENT = "objectId";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextRepresentationDescriptionsDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<Connection<RepresentationDescriptionMetadata>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String objectId = environment.getArgument(OBJECT_ID_ARGUMENT);

        EditingContextRepresentationDescriptionsInput input = new EditingContextRepresentationDescriptionsInput(UUID.randomUUID(), editingContextId, objectId);

        return this.editingContextEventProcessorRegistry.dispatchEvent(input.editingContextId(), input)
                .filter(EditingContextRepresentationDescriptionsPayload.class::isInstance)
                .map(EditingContextRepresentationDescriptionsPayload.class::cast)
                .map(this::toConnection)
                .switchIfEmpty(Mono.just(new DefaultConnection<>(List.of(), new DefaultPageInfo(null, null, false, false))))
                .toFuture();
    }

    private Connection<RepresentationDescriptionMetadata> toConnection(EditingContextRepresentationDescriptionsPayload payload) {
        List<Edge<RepresentationDescriptionMetadata>> representationDescriptionEdges = payload.representationDescriptions().stream()
                .map(representationDescription -> {
                    var globalId = new Relay().toGlobalId("RepresentationDescription", representationDescription.getId());
                    ConnectionCursor cursor = new DefaultConnectionCursor(globalId);
                    Edge<RepresentationDescriptionMetadata> edge = new DefaultEdge<>(representationDescription, cursor);
                    return edge;
                })
                .toList();

        ConnectionCursor startCursor = representationDescriptionEdges.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!representationDescriptionEdges.isEmpty()) {
            endCursor = representationDescriptionEdges.get(representationDescriptionEdges.size() - 1).getCursor();
        }
        PageInfo pageInfo = new PageInfoWithCount(startCursor, endCursor, false, false, payload.representationDescriptions().size());
        return new DefaultConnection<>(representationDescriptionEdges, pageInfo);
    }
}
