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

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.Edge;
import graphql.relay.Relay;
import graphql.schema.DataFetchingEnvironment;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.graphql.dto.PageInfoWithCount;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.pagination.services.api.ILimitProvider;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Data fetcher for the field Viewer#projects.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Viewer", field = "projects")
public class ViewerProjectsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<ProjectDTO>> {

    private static final String FIRST_ARGUMENT = "first";

    private static final String LAST_ARGUMENT = "last";

    private static final String AFTER_ARGUMENT = "after";

    private static final String BEFORE_ARGUMENT = "before";

    private static final String FILTER_ARGUMENT = "filter";

    private final IProjectApplicationService projectApplicationService;

    private final ILimitProvider limitProvider;

    public ViewerProjectsDataFetcher(IProjectApplicationService projectApplicationService, ILimitProvider limitProvider) {
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
        this.limitProvider = Objects.requireNonNull(limitProvider);
    }

    @Override
    public Connection<ProjectDTO> get(DataFetchingEnvironment environment) throws Exception {
        Optional<Integer> first = Optional.ofNullable(environment.getArgument(FIRST_ARGUMENT));
        Optional<Integer> last = Optional.ofNullable(environment.getArgument(LAST_ARGUMENT));
        Optional<String> after = Optional.ofNullable(environment.getArgument(AFTER_ARGUMENT));
        Optional<String> before = Optional.ofNullable(environment.getArgument(BEFORE_ARGUMENT));
        Map<String, Object> filter = Optional.ofNullable(environment.<Map<String, Object>>getArgument(FILTER_ARGUMENT)).orElseGet(Map::of);

        KeysetScrollPosition position = this.getPosition(after, before);
        int limit = this.limitProvider.getLimit(20, first, last, after, before);

        var projectPage = this.projectApplicationService.findAll(position, limit, filter);
        return this.toConnection(projectPage);
    }

    public KeysetScrollPosition getPosition(Optional<String> after, Optional<String> before) {
        KeysetScrollPosition position = ScrollPosition.keyset();
        if (after.isPresent() && before.isEmpty()) {
            var projectId = after.get();
            var cursorProjectId = new Relay().fromGlobalId(projectId).getId();
            position = ScrollPosition.forward(Map.of("id", cursorProjectId));
        } else if (before.isPresent() && after.isEmpty()) {
            var projectId = before.get();
            var cursorProjectId = new Relay().fromGlobalId(projectId).getId();
            position = ScrollPosition.backward(Map.of("id", cursorProjectId));
        }
        return position;
    }

    private Connection<ProjectDTO> toConnection(Window<ProjectDTO> window) {
        List<Edge<ProjectDTO>> edges = window.stream().map(projectDTO -> {
            var globalId = new Relay().toGlobalId("Project", projectDTO.id());
            var cursor = new DefaultConnectionCursor(globalId);
            return (Edge<ProjectDTO>) new DefaultEdge<>(projectDTO, cursor);
        }).collect(Collectors.toCollection(ArrayList::new));

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
