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
package org.eclipse.sirius.web.graphql.datafetchers.user;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.pagination.PageInfoWithCount;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;

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
@QueryDataFetcher(type = "User", field = "projects")
public class UserProjectsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<Project>> {

    private final IProjectService projectService;

    public UserProjectsDataFetcher(IProjectService projectService) {
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public Connection<Project> get(DataFetchingEnvironment environment) throws Exception {
        // @formatter:off
        List<Edge<Project>> projectEdges = this.projectService.getProjects().stream()
                .map(project -> {
                    String value = new Relay().toGlobalId("Project", project.getId().toString());
                    ConnectionCursor cursor = new DefaultConnectionCursor(value);
                    Edge<Project> edge = new DefaultEdge<>(project, cursor);
                    return edge;
                })
                .toList();
        // @formatter:on

        ConnectionCursor startCursor = projectEdges.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!projectEdges.isEmpty()) {
            endCursor = projectEdges.get(projectEdges.size() - 1).getCursor();
        }
        PageInfo pageInfo = new PageInfoWithCount(startCursor, endCursor, false, false, projectEdges.size());
        return new DefaultConnection<>(projectEdges, pageInfo);
    }

}
