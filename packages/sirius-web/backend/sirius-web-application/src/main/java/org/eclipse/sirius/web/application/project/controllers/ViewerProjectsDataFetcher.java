/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.graphql.dto.PageInfoWithCount;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.Window;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;

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

    private static final int DEFAULT_PAGE_SIZE = 20;

    private static final String FIRST_ARGUMENT = "first";

    private static final String LAST_ARGUMENT = "last";

    private static final String AFTER_ARGUMENT = "after";

    private static final String BEFORE_ARGUMENT = "before";

    private final IProjectApplicationService projectApplicationService;

    public ViewerProjectsDataFetcher(IProjectApplicationService projectApplicationService) {
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
    }

    @Override
    public Connection<ProjectDTO> get(DataFetchingEnvironment environment) throws Exception {
        Optional<Integer> first = Optional.<Integer> ofNullable(environment.getArgument(FIRST_ARGUMENT));
        Optional<Integer> last = Optional.<Integer> ofNullable(environment.getArgument(LAST_ARGUMENT));
        Optional<String> after = Optional.<String> ofNullable(environment.getArgument(AFTER_ARGUMENT));
        Optional<String> before = Optional.<String> ofNullable(environment.getArgument(BEFORE_ARGUMENT));

        final KeysetScrollPosition position;
        final int limit;
        if (after.isPresent() && before.isEmpty()) {
            var projectId = after.get();
            var cursorProjectId = new Relay().fromGlobalId(projectId).getId();
            position = ScrollPosition.forward(Map.of("id", cursorProjectId));
            if (last.isPresent()) {
                limit = 0;
            } else if (first.isPresent()) {
                limit = first.get();
            } else {
                limit = DEFAULT_PAGE_SIZE;
            }
        } else if (before.isPresent() && after.isEmpty()) {
            var projectId = before.get();
            var cursorProjectId = new Relay().fromGlobalId(projectId).getId();
            position = ScrollPosition.backward(Map.of("id", cursorProjectId));
            if (first.isPresent()) {
                limit = 0;
            } else if (last.isPresent()) {
                limit = last.get();
            } else {
                limit = DEFAULT_PAGE_SIZE;
            }
        } else if (before.isPresent() && after.isPresent()) {
            position = ScrollPosition.keyset();
            limit = 0;
        } else {
            position = ScrollPosition.keyset();
            if (first.isPresent() && last.isPresent()) {
                limit = 0;
            } else if (first.isPresent()) {
                limit = first.get();
            } else if (last.isPresent()) {
                limit = last.get();
            } else {
                limit = DEFAULT_PAGE_SIZE;
            }
        }

        var projectPage = this.projectApplicationService.findAll(position, limit);
        return this.toConnection(projectPage, position);
    }

    private Connection<ProjectDTO> toConnection(Window<ProjectDTO> projectPage, KeysetScrollPosition position) {
        List<Edge<ProjectDTO>> edges = projectPage.stream().map(projectDTO -> {
            var globalId = new Relay().toGlobalId("Project", projectDTO.id().toString());
            var cursor = new DefaultConnectionCursor(globalId);
            return (Edge<ProjectDTO>) new DefaultEdge<>(projectDTO, cursor);
        }).collect(Collectors.toCollection(ArrayList::new));

        if (position.scrollsBackward()) {
            Collections.reverse(edges);
        }

        ConnectionCursor startCursor = edges.stream().findFirst()
                .map(Edge::getCursor)
                .orElse(null);
        ConnectionCursor endCursor = null;
        if (!edges.isEmpty()) {
            endCursor = edges.get(edges.size() - 1).getCursor();
        }

        boolean hasNextPage = false;
        boolean hasPreviousPage = false;
        if (position.scrollsForward()) {
            hasNextPage = projectPage.hasNext();
            hasPreviousPage = projectPage.hasPrevious();
        }
        if (position.scrollsBackward()) {
            hasNextPage = projectPage.hasNext();
            hasPreviousPage = projectPage.hasPrevious();
        }
        var pageInfo = new PageInfoWithCount(startCursor, endCursor, hasPreviousPage, hasNextPage, projectPage.size());
        return new DefaultConnection<>(edges, pageInfo);
    }
}
