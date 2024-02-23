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
import org.eclipse.sirius.web.application.project.dto.ProjectTemplateDTO;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateApplicationService;
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
 * Data fetcher for the field Viewer#projectTemplates.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Viewer", field = "projectTemplates")
public class ViewerProjectTemplatesDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<ProjectTemplateDTO>> {

    private static final String PAGE_ARGUMENT = "page";

    private static final String LIMIT_ARGUMENT = "limit";

    private final IProjectTemplateApplicationService projectTemplateApplicationService;

    public ViewerProjectTemplatesDataFetcher(IProjectTemplateApplicationService projectTemplateApplicationService) {
        this.projectTemplateApplicationService = Objects.requireNonNull(projectTemplateApplicationService);
    }

    @Override
    public Connection<ProjectTemplateDTO> get(DataFetchingEnvironment environment) throws Exception {
        int page = Optional.<Integer> ofNullable(environment.getArgument(PAGE_ARGUMENT))
                .filter(pageArgument -> pageArgument > 0)
                .orElse(0);
        int limit = Optional.<Integer> ofNullable(environment.getArgument(LIMIT_ARGUMENT))
                .filter(limitArgument -> limitArgument > 0)
                .orElse(20);

        var pageable = PageRequest.of(page, limit);
        var projectTemplatePage = this.projectTemplateApplicationService.findAll(pageable);
        return this.toConnection(projectTemplatePage);
    }

    private Connection<ProjectTemplateDTO> toConnection(Page<ProjectTemplateDTO> projectTemplatePage) {
        var edges = projectTemplatePage.stream().map(projectTemplateDTO -> {
            var globalId = new Relay().toGlobalId("ProjectTemplate", projectTemplateDTO.id());
            var cursor = new DefaultConnectionCursor(globalId);
            return (Edge<ProjectTemplateDTO>) new DefaultEdge<>(projectTemplateDTO, cursor);
        }).toList();

        ConnectionCursor startCursor = edges.stream().findFirst()
                .map(Edge::getCursor)
                .orElse(null);
        ConnectionCursor endCursor = null;
        if (!edges.isEmpty()) {
            endCursor = edges.get(edges.size() - 1).getCursor();
        }
        var pageInfo = new PageInfoWithCount(startCursor, endCursor, projectTemplatePage.hasPrevious(), projectTemplatePage.hasNext(), projectTemplatePage.getTotalElements());
        return new DefaultConnection<>(edges, pageInfo);
    }
}
