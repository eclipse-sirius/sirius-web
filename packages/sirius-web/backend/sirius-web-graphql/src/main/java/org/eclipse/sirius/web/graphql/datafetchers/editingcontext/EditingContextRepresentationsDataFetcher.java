/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;
import org.eclipse.sirius.web.graphql.schema.EditingContextTypeProvider;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;

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
public class EditingContextRepresentationsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<RepresentationMetadata>> {

    private final IRepresentationService representationService;

    public EditingContextRepresentationsDataFetcher(IRepresentationService representationService) {
        this.representationService = Objects.requireNonNull(representationService);
    }

    @Override
    public Connection<RepresentationMetadata> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        // @formatter:off
        List<RepresentationMetadata> representations = this.representationService.getRepresentationDescriptorsForProjectId(editingContextId)
                .stream()
                .map(RepresentationDescriptor::getRepresentation)
                .map(this::toRepresentationMetadata)
                .toList();
        // @formatter:on

        // @formatter:off
        List<Edge<RepresentationMetadata>> representationEdges = representations.stream()
                .map(representation -> {
                    String value = Base64.getEncoder().encodeToString(representation.getId().getBytes());
                    ConnectionCursor cursor = new DefaultConnectionCursor(value);
                    Edge<RepresentationMetadata> edge = new DefaultEdge<>(representation, cursor);
                    return edge;
                })
                .toList();
        // @formatter:on

        ConnectionCursor startCursor = representationEdges.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!representationEdges.isEmpty()) {
            endCursor = representationEdges.get(representationEdges.size() - 1).getCursor();
        }
        PageInfo pageInfo = new DefaultPageInfo(startCursor, endCursor, false, false);
        return new DefaultConnection<>(representationEdges, pageInfo);
    }

    private RepresentationMetadata toRepresentationMetadata(IRepresentation representation) {
        // @formatter:off
        String targetObjectId = Optional.of(representation)
                .filter(ISemanticRepresentation.class::isInstance)
                .map(ISemanticRepresentation.class::cast)
                .map(ISemanticRepresentation::getTargetObjectId)
                .orElse(null);
        // @formatter:on
        return new RepresentationMetadata(representation.getId(), representation.getKind(), representation.getLabel(), representation.getDescriptionId(), targetObjectId);
    }

}
