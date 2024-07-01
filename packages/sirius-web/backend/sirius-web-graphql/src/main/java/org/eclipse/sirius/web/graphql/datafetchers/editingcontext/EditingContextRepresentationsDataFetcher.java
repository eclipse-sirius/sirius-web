/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;

import graphql.execution.DataFetcherResult;
import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve all the representations of an editing context.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   representations: EditingContextRepresentationConnection!
 * }
 * </pre>
 *
 * @author wpiers
 */
@QueryDataFetcher(type = "EditingContext", field = "representations")
public class EditingContextRepresentationsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<DataFetcherResult<RepresentationMetadata>>> {

    private static final String REPRESENTATION_IDS_ARGUMENT = "representationIds";

    private final IRepresentationService representationService;

    public EditingContextRepresentationsDataFetcher(IRepresentationService representationService) {
        this.representationService = Objects.requireNonNull(representationService);
    }

    @Override
    public Connection<DataFetcherResult<RepresentationMetadata>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        List<RepresentationMetadata> representations = List.of();

        List<String> representationIds = environment.getArgument(REPRESENTATION_IDS_ARGUMENT);
        if (representationIds != null) {
            representations = representationIds.stream()
                    .map(UUID::fromString)
                    .map(this.representationService::getRepresentation)
                    .flatMap(Optional::stream)
                    .map(RepresentationDescriptor::getRepresentation)
                    .map(this::toRepresentationMetadata)
                    .toList();
        } else {
            representations = this.representationService.getRepresentationDescriptorsForProjectId(editingContextId)
                    .stream()
                    .map(RepresentationDescriptor::getRepresentation)
                    .map(this::toRepresentationMetadata)
                    .toList();
        }

        List<Edge<DataFetcherResult<RepresentationMetadata>>> representationEdges = representations.stream()
                .map(representation -> {
                    String value = Base64.getEncoder().encodeToString(representation.getId().getBytes());
                    ConnectionCursor cursor = new DefaultConnectionCursor(value);
                    Map<String, Object> localContext = new HashMap<>(environment.getLocalContext());
                    localContext.put(LocalContextConstants.REPRESENTATION_ID, representation.getId());
                    return (Edge<DataFetcherResult<RepresentationMetadata>>) new DefaultEdge<>(DataFetcherResult.<RepresentationMetadata>newResult()
                            .data(representation)
                            .localContext(localContext)
                            .build(), cursor);
                })
                .toList();

        ConnectionCursor startCursor = representationEdges.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!representationEdges.isEmpty()) {
            endCursor = representationEdges.get(representationEdges.size() - 1).getCursor();
        }
        PageInfo pageInfo = new DefaultPageInfo(startCursor, endCursor, false, false);

        return new DefaultConnection<>(representationEdges, pageInfo);
    }

    private RepresentationMetadata toRepresentationMetadata(IRepresentation representation) {
        return new RepresentationMetadata(representation.getId(), representation.getKind(), representation.getLabel(), representation.getDescriptionId());
    }

}
