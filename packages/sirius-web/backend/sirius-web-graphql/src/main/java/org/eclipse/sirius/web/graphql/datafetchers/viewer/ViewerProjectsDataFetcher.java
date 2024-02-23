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
package org.eclipse.sirius.web.graphql.datafetchers.viewer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.pagination.PageInfoWithCount;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.springframework.data.domain.Page;

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import graphql.relay.Relay;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve all the projects accessible to a given viewer.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Viewer {
 *   projects(page: Int): ViewerProjectConnection!
 * }
 * </pre>
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "Viewer", field = "projects")
public class ViewerProjectsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<Project>> {

    private static final String PAGE_ARGUMENT = "page";

    private static final String LIMIT_ARGUMENT = "limit";

    private final IProjectService projectService;

    public ViewerProjectsDataFetcher(IProjectService projectService) {
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public Connection<Project> get(DataFetchingEnvironment environment) throws Exception {
        int page = this.getPage(environment);
        int limit = this.getLimit(environment);

        Page<Project> projectPage = this.projectService.getProjects(page, limit);
        List<Edge<Project>> projectEdges = projectPage.stream()
                .map(project -> {
                    String value = new Relay().toGlobalId("Project", project.getId().toString());
                    ConnectionCursor cursor = new DefaultConnectionCursor(value);
                    return (Edge<Project>) new DefaultEdge<>(project, cursor);
                })
                .toList();

        ConnectionCursor startCursor = projectEdges.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!projectEdges.isEmpty()) {
            endCursor = projectEdges.get(projectEdges.size() - 1).getCursor();
        }
        PageInfo pageInfo = new PageInfoWithCount(startCursor, endCursor, projectPage.hasPrevious(), projectPage.hasNext(), projectPage.getTotalElements());
        return new DefaultConnection<>(projectEdges, pageInfo);
    }

    private int getPage(DataFetchingEnvironment environment) {
        // @formatter:off
        return Optional.<Integer> ofNullable(environment.getArgument(PAGE_ARGUMENT))
                .filter(page -> page.intValue() > 0)
                .orElse(0)
                .intValue();
        // @formatter:on
    }

    private int getLimit(DataFetchingEnvironment environment) {
        // @formatter:off
        return Optional.<Integer> ofNullable(environment.getArgument(LIMIT_ARGUMENT))
                .filter(limit -> limit.intValue() > 0)
                .orElse(20)
                .intValue();
        // @formatter:on
    }

}
