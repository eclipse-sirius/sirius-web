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
package org.eclipse.sirius.web.application.project.controllers;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.dto.PageInfoWithCount;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
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
 * Data fetcher for the field Viewer#projects.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Viewer", field = "projects")
public class ViewerProjectsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<ProjectDTO>> {

    private static final String PAGE_ARGUMENT = "page";

    private static final String LIMIT_ARGUMENT = "limit";

    private final IProjectApplicationService projectApplicationService;

    public ViewerProjectsDataFetcher(IProjectApplicationService projectApplicationService) {
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
    }

    @Override
    public Connection<ProjectDTO> get(DataFetchingEnvironment environment) throws Exception {
        int page = Optional.<Integer> ofNullable(environment.getArgument(PAGE_ARGUMENT))
                .filter(pageArgument -> pageArgument > 0)
                .orElse(0);
        int limit = Optional.<Integer> ofNullable(environment.getArgument(LIMIT_ARGUMENT))
                .filter(limitArgument -> limitArgument > 0)
                .orElse(20);

        var pageable = PageRequest.of(page, limit);
        var projectPage = this.projectApplicationService.findAll(pageable);
        return this.toConnection(projectPage);
    }

    private Connection<ProjectDTO> toConnection(Page<ProjectDTO> projectPage) {
        var edges = projectPage.stream().map(projectDTO -> {
            var globalId = new Relay().toGlobalId("Project", projectDTO.id().toString());
            var cursor = new DefaultConnectionCursor(globalId);
            return (Edge<ProjectDTO>) new DefaultEdge<>(projectDTO, cursor);
        }).toList();

        ConnectionCursor startCursor = edges.stream().findFirst()
                .map(Edge::getCursor)
                .orElse(null);
        ConnectionCursor endCursor = null;
        if (!edges.isEmpty()) {
            endCursor = edges.get(edges.size() - 1).getCursor();
        }
        var pageInfo = new PageInfoWithCount(startCursor, endCursor, projectPage.hasPrevious(), projectPage.hasNext(), projectPage.getTotalElements());
        return new DefaultConnection<>(edges, pageInfo);
    }
}
