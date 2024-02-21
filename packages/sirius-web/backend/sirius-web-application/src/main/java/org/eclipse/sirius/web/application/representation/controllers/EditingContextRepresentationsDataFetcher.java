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

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.dto.PageInfoWithCount;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
public class EditingContextRepresentationsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<RepresentationMetadata>> {

    private final IRepresentationApplicationService representationApplicationService;

    public EditingContextRepresentationsDataFetcher(IRepresentationApplicationService representationApplicationService) {
        this.representationApplicationService = Objects.requireNonNull(representationApplicationService);
    }

    @Override
    public Connection<RepresentationMetadata> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        var representationMetadataPage = this.representationApplicationService.findAllByEditingContextId(editingContextId, PageRequest.of(0, 20));
        return this.toConnection(representationMetadataPage);
    }

    private Connection<RepresentationMetadata> toConnection(Page<RepresentationMetadata> representationMetadataPage) {
        var edges = representationMetadataPage.stream().map(representationMetadata -> {
            var globalId = new Relay().toGlobalId("RepresentationMetadata", representationMetadata.getId());
            var cursor = new DefaultConnectionCursor(globalId);
            return (Edge<RepresentationMetadata>) new DefaultEdge<>(representationMetadata, cursor);
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
