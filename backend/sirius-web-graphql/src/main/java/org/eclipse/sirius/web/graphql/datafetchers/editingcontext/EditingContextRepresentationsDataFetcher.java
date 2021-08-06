/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import java.util.stream.Collectors;

import org.eclipse.sirius.web.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.graphql.schema.EditingContextTypeProvider;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;

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
@QueryDataFetcher(type = EditingContextTypeProvider.TYPE, field = EditingContextTypeProvider.REPRESENTATIONS_FIELD)
public class EditingContextRepresentationsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<IRepresentation>> {

    private final IRepresentationService representationService;

    public EditingContextRepresentationsDataFetcher(IRepresentationService representationService) {
        this.representationService = Objects.requireNonNull(representationService);
    }

    @Override
    public Connection<IRepresentation> get(DataFetchingEnvironment environment) throws Exception {
        UUID editingContextId = environment.getSource();
        // @formatter:off
        List<IRepresentation> representations = this.representationService.getRepresentationDescriptorsForProjectId(editingContextId).stream()
                .map(RepresentationDescriptor::getRepresentation).collect(Collectors.toList());
        // @formatter:on

        // @formatter:off
        List<Edge<IRepresentation>> representationEdges = representations.stream()
                .map(representation -> {
                    String value = Base64.getEncoder().encodeToString(representation.getId().toString().getBytes());
                    ConnectionCursor cursor = new DefaultConnectionCursor(value);
                    return new DefaultEdge<>(representation, cursor);
                })
                .collect(Collectors.toList());
        // @formatter:on

        ConnectionCursor startCursor = representationEdges.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!representationEdges.isEmpty()) {
            endCursor = representationEdges.get(representationEdges.size() - 1).getCursor();
        }
        PageInfo pageInfo = new DefaultPageInfo(startCursor, endCursor, false, false);
        return new DefaultConnection<>(representationEdges, pageInfo);
    }

}
