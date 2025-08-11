/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.library.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.graphql.dto.PageInfoWithCount;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.eclipse.sirius.web.application.library.dto.LibraryDTO;
import org.eclipse.sirius.web.application.library.services.api.ILibraryApplicationService;
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
 * Data fetcher for the field Viewer#libraries.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "Viewer", field = "libraries")
public class ViewerLibrariesDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<LibraryDTO>> {

    private static final String PAGE_ARGUMENT = "page";

    private static final String LIMIT_ARGUMENT = "limit";

    private final List<ICapabilityVoter> capabilityVoters;

    private final ILibraryApplicationService libraryApplicationService;

    public ViewerLibrariesDataFetcher(List<ICapabilityVoter> capabilityVoters, ILibraryApplicationService libraryApplicationService) {
        this.capabilityVoters = Objects.requireNonNull(capabilityVoters);
        this.libraryApplicationService = Objects.requireNonNull(libraryApplicationService);
    }

    @Override
    public Connection<LibraryDTO> get(DataFetchingEnvironment environment) throws Exception {
        var hasCapability = this.capabilityVoters.stream().allMatch(voter -> voter.vote(SiriusWebCapabilities.LIBRARY, null, SiriusWebCapabilities.Library.LIST) == CapabilityVote.GRANTED);
        if (!hasCapability) {
            return new DefaultConnection<>(List.of(), new PageInfoWithCount(null, null, false, false, 0));
        }

        int page = Optional.<Integer> ofNullable(environment.getArgument(PAGE_ARGUMENT))
                .filter(pageArgument -> pageArgument > 0)
                .orElse(0);
        int limit = Optional.<Integer> ofNullable(environment.getArgument(LIMIT_ARGUMENT))
                .filter(limitArgument -> limitArgument > 0)
                .orElse(20);

        var pageable = PageRequest.of(page, limit);
        var libraryPage = this.libraryApplicationService.findAll(pageable);
        return this.toConnection(libraryPage);
    }

    private Connection<LibraryDTO> toConnection(Page<LibraryDTO> libraryPage) {
        var edges = libraryPage.stream().map(libraryDTO -> {
            var globalId = new Relay().toGlobalId("Library", libraryDTO.namespace() + libraryDTO.name() + libraryDTO.version());
            var cursor = new DefaultConnectionCursor(globalId);
            return (Edge<LibraryDTO>) new DefaultEdge<>(libraryDTO, cursor);
        }).toList();

        ConnectionCursor startCursor = edges.stream().findFirst()
                .map(Edge::getCursor)
                .orElse(null);
        ConnectionCursor endCursor = null;
        if (!edges.isEmpty()) {
            endCursor = edges.get(edges.size() - 1).getCursor();
        }
        var pageInfo = new PageInfoWithCount(startCursor, endCursor, libraryPage.hasPrevious(), libraryPage.hasNext(), libraryPage.getTotalElements());
        return new DefaultConnection<>(edges, pageInfo);
    }
}
