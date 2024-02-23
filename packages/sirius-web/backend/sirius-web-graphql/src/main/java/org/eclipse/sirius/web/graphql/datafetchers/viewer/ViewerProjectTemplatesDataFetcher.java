/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.pagination.PageInfoWithCount;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateProvider;
import org.eclipse.sirius.web.services.api.projects.ProjectTemplate;

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
 * The data fetcher used to retrieve all the project templates accessible to a given viewer.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "Viewer", field = "projectTemplates")
public class ViewerProjectTemplatesDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<ProjectTemplate>> {
    private static final String PAGE_ARGUMENT = "page";

    private static final String LIMIT_ARGUMENT = "limit";

    private final List<IProjectTemplateProvider> projectTemplateProviders;

    public ViewerProjectTemplatesDataFetcher(List<IProjectTemplateProvider> projectTemplateProviders) {
        this.projectTemplateProviders = Objects.requireNonNull(projectTemplateProviders);
    }

    @Override
    public Connection<ProjectTemplate> get(DataFetchingEnvironment environment) throws Exception {
        int page = this.getPage(environment);
        int limit = this.getLimit(environment);

        // @formatter:off
        List<ProjectTemplate> allProjectTemplates = this.projectTemplateProviders.stream()
                                                        .flatMap(projectTemplateProvider -> projectTemplateProvider.getProjectTemplates().stream())
                                                        .sorted(Comparator.comparing(ProjectTemplate::getLabel))
                                                        .collect(Collectors.toList());
        List<Edge<ProjectTemplate>> projectTemplateEdges = allProjectTemplates.subList(page * limit, Math.min((page + 1) * limit, allProjectTemplates.size())).stream()
                .map(projectTemplate -> {
                    String value = new Relay().toGlobalId("ProjectTemplate", projectTemplate.getId());
                    ConnectionCursor cursor = new DefaultConnectionCursor(value);
                    return new DefaultEdge<>(projectTemplate, cursor);
                })
                .collect(Collectors.toList());
        // @formatter:on

        ConnectionCursor startCursor = projectTemplateEdges.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!projectTemplateEdges.isEmpty()) {
            endCursor = projectTemplateEdges.get(projectTemplateEdges.size() - 1).getCursor();
        }
        PageInfo pageInfo = new PageInfoWithCount(startCursor, endCursor, false, false, allProjectTemplates.size());
        return new DefaultConnection<>(projectTemplateEdges, pageInfo);
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
