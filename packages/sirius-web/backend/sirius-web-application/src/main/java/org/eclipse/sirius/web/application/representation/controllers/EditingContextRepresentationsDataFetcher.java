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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.core.graphql.dto.PageInfoWithCount;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
public class EditingContextRepresentationsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<DataFetcherResult<RepresentationMetadata>>> {

    private static final String REPRESENTATION_IDS_ARGUMENT = "representationIds";

    private final IRepresentationApplicationService representationApplicationService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    public EditingContextRepresentationsDataFetcher(IRepresentationApplicationService representationApplicationService, IRepresentationMetadataSearchService representationMetadataSearchService) {
        this.representationApplicationService = Objects.requireNonNull(representationApplicationService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
    }

    @Override
    public Connection<DataFetcherResult<RepresentationMetadata>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        Page<RepresentationMetadata> representationMetadataPage = Page.empty();

        List<String> representationIds = environment.getArgument(REPRESENTATION_IDS_ARGUMENT);
        if (representationIds != null) {
            List<RepresentationMetadata> allRepresentationMetadata = representationIds.stream()
                    .map(this.representationMetadataSearchService::findByRepresentationId)
                    .flatMap(Optional::stream)
                    .toList();
            representationMetadataPage = new PageImpl<>(allRepresentationMetadata, Pageable.unpaged(), allRepresentationMetadata.size());
        } else {
            representationMetadataPage = this.representationApplicationService.findAllByEditingContextId(editingContextId, PageRequest.of(0, 20));
        }

        return this.toConnection(environment, representationMetadataPage);
    }

    private Connection<DataFetcherResult<RepresentationMetadata>> toConnection(DataFetchingEnvironment environment, Page<RepresentationMetadata> representationMetadataPage) {
        var edges = representationMetadataPage.stream().map(representationMetadata -> {
            var globalId = new Relay().toGlobalId("RepresentationMetadata", representationMetadata.getId());
            var cursor = new DefaultConnectionCursor(globalId);

            Map<String, Object> localContext = new HashMap<>(environment.getLocalContext());
            localContext.put(LocalContextConstants.REPRESENTATION_ID, representationMetadata.getId());
            return (Edge<DataFetcherResult<RepresentationMetadata>>) new DefaultEdge<>(DataFetcherResult.<RepresentationMetadata>newResult()
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
        var pageInfo = new PageInfoWithCount(startCursor, endCursor, representationMetadataPage.hasPrevious(), representationMetadataPage.hasNext(), representationMetadataPage.getTotalElements());
        return new DefaultConnection<>(edges, pageInfo);
    }
}
