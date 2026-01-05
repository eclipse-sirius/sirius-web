/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.graphql.dto.PageInfoWithCount;
import org.eclipse.sirius.components.core.graphql.dto.RepresentationMetadataDTO;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.pagination.services.api.ILimitProvider;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationApplicationService;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;

import graphql.execution.DataFetcherResult;
import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.Edge;
import graphql.relay.Relay;
import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field EditingContext#representations.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "EditingContext", field = "representations")
public class EditingContextRepresentationsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<DataFetcherResult<RepresentationMetadataDTO>>> {

    private static final String REPRESENTATION_IDS_ARGUMENT = "representationIds";

    private static final String FIRST_ARGUMENT = "first";

    private static final String LAST_ARGUMENT = "last";

    private static final String AFTER_ARGUMENT = "after";

    private static final String BEFORE_ARGUMENT = "before";

    private final IRepresentationApplicationService representationApplicationService;

    private final ILimitProvider limitProvider;

    public EditingContextRepresentationsDataFetcher(IRepresentationApplicationService representationApplicationService, ILimitProvider limitProvider) {
        this.representationApplicationService = Objects.requireNonNull(representationApplicationService);
        this.limitProvider = Objects.requireNonNull(limitProvider);
    }

    @Override
    public Connection<DataFetcherResult<RepresentationMetadataDTO>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();

        Connection<DataFetcherResult<RepresentationMetadataDTO>> connection = null;

        List<String> representationIds = environment.getArgument(REPRESENTATION_IDS_ARGUMENT);
        if (representationIds != null) {
            List<RepresentationMetadataDTO> allRepresentationMetadata = representationIds.stream()
                    .map(representationMetadataId -> this.representationApplicationService.findRepresentationMetadataById(editingContextId, representationMetadataId))
                    .flatMap(Optional::stream)
                    .toList();
            connection = this.toConnection(environment, allRepresentationMetadata);
        } else {
            Optional<Integer> first = Optional.ofNullable(environment.getArgument(FIRST_ARGUMENT));
            Optional<Integer> last = Optional.ofNullable(environment.getArgument(LAST_ARGUMENT));
            Optional<String> after = Optional.ofNullable(environment.getArgument(AFTER_ARGUMENT));
            Optional<String> before = Optional.ofNullable(environment.getArgument(BEFORE_ARGUMENT));

            KeysetScrollPosition position = this.getPosition(after, before);
            int limit = this.limitProvider.getLimit(20, first, last, after, before);

            var window = this.representationApplicationService.findAllByEditingContextId(editingContextId, position, limit);
            connection = this.toConnection(environment, window);
        }

        return connection;
    }

    public KeysetScrollPosition getPosition(Optional<String> after, Optional<String> before) {
        KeysetScrollPosition position = ScrollPosition.keyset();

        var afterId = after.map(new Relay()::fromGlobalId)
                .map(Relay.ResolvedGlobalId::getId)
                .flatMap(new UUIDParser()::parse);
        var beforeId = before.map(new Relay()::fromGlobalId)
                .map(Relay.ResolvedGlobalId::getId)
                .flatMap(new UUIDParser()::parse);
        if (afterId.isPresent() && beforeId.isEmpty()) {
            var representationMetadataId = afterId.get();
            position = ScrollPosition.forward(Map.of("id", representationMetadataId));
        } else if (beforeId.isPresent() && afterId.isEmpty()) {
            var representationMetadataId = beforeId.get();
            position = ScrollPosition.backward(Map.of("id", representationMetadataId));
        }
        return position;
    }

    private Connection<DataFetcherResult<RepresentationMetadataDTO>> toConnection(DataFetchingEnvironment environment, List<RepresentationMetadataDTO> allRepresentationMetadata) {
        var edges = allRepresentationMetadata.stream().map(representationMetadata -> {
            var globalId = new Relay().toGlobalId("RepresentationMetadata", representationMetadata.id().toString());
            var cursor = new DefaultConnectionCursor(globalId);

            Map<String, Object> localContext = new HashMap<>(environment.getLocalContext());
            localContext.put(LocalContextConstants.REPRESENTATION_ID, representationMetadata.id().toString());
            return (Edge<DataFetcherResult<RepresentationMetadataDTO>>) new DefaultEdge<>(DataFetcherResult.<RepresentationMetadataDTO>newResult()
                    .data(representationMetadata)
                    .localContext(localContext)
                    .build(), cursor);
        }).toList();

        ConnectionCursor startCursor = edges.stream().findFirst()
                .map(Edge::getCursor)
                .orElse(null);
        ConnectionCursor endCursor = null;
        if (!edges.isEmpty()) {
            endCursor = edges.get(edges.size() - 1).getCursor();
        }
        var pageInfo = new PageInfoWithCount(startCursor, endCursor, false, false, allRepresentationMetadata.size());
        return new DefaultConnection<>(edges, pageInfo);
    }

    private Connection<DataFetcherResult<RepresentationMetadataDTO>> toConnection(DataFetchingEnvironment environment, Window<RepresentationMetadataDTO> window) {
        List<Edge<DataFetcherResult<RepresentationMetadataDTO>>> edges = window.stream().map(representationMetadata -> {
            var globalId = new Relay().toGlobalId("RepresentationMetadata", representationMetadata.id());
            var cursor = new DefaultConnectionCursor(globalId);
            Map<String, Object> localContext = new HashMap<>(environment.getLocalContext());
            localContext.put(LocalContextConstants.REPRESENTATION_ID, representationMetadata.id().toString());

            return (Edge<DataFetcherResult<RepresentationMetadataDTO>>) new DefaultEdge<>(DataFetcherResult.<RepresentationMetadataDTO>newResult()
                    .data(representationMetadata)
                    .localContext(localContext)
                    .build(), cursor);
        }).toList();

        ConnectionCursor startCursor = edges.stream().findFirst()
                .map(Edge::getCursor)
                .orElse(null);
        ConnectionCursor endCursor = null;
        if (!edges.isEmpty()) {
            endCursor = edges.get(edges.size() - 1).getCursor();
        }

        var pageInfo = new PageInfoWithCount(startCursor, endCursor, window.hasPrevious(), window.hasNext(), window.size());
        return new DefaultConnection<>(edges, pageInfo);
    }
}
