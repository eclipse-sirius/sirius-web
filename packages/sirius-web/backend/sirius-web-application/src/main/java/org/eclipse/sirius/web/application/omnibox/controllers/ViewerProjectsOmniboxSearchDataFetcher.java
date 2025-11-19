/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.web.application.omnibox.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.eclipse.sirius.components.core.graphql.dto.PageInfoWithCount;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.index.services.api.IIndexEntry;
import org.eclipse.sirius.web.application.index.services.api.IIndexQueryService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.Edge;
import graphql.relay.Relay;
import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Viewer#projectsOmniboxSearch.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "Viewer", field = "projectsOmniboxSearch")
public class ViewerProjectsOmniboxSearchDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<OmniboxCommand>> {

    private static final String QUERY_ARGUMENT = "query";

    private final IIndexQueryService indexQueryService;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final IProjectSearchService projectSearchService;

    public ViewerProjectsOmniboxSearchDataFetcher(IIndexQueryService indexQueryService, IProjectSemanticDataSearchService projectSemanticDataSearchService, IProjectSearchService projectSearchService) {
        this.indexQueryService = Objects.requireNonNull(indexQueryService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
    }

    @Override
    public Connection<OmniboxCommand> get(DataFetchingEnvironment environment) throws Exception {
        String query = environment.getArgument(QUERY_ARGUMENT);
        List<OmniboxCommand> result = List.of();
        if (this.indexQueryService.isAvailable()) {
            result = this.indexQueryService.search(query).stream()
                .map(this::toCommand)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        }

        return this.toConnection(result);
    }

    private Optional<OmniboxCommand> toCommand(IIndexEntry hit) {
        Optional<OmniboxCommand> result = Optional.empty();
        Optional<UUID> optionalEditingContextId = new UUIDParser().parse(hit.editingContextId());
        if (optionalEditingContextId.isPresent()) {
            Optional<ProjectSemanticData> optionalProjectSemanticData = this.projectSemanticDataSearchService.findBySemanticDataId(AggregateReference.to(optionalEditingContextId.get()));
            if (optionalProjectSemanticData.isPresent()) {
                ProjectSemanticData projectSemanticData = optionalProjectSemanticData.get();
                String projectId = projectSemanticData.getProject().getId();
                String projectName = this.projectSearchService.findById(projectId)
                        .map(Project::getName)
                        .orElse("");
                String commandId = projectId + "@" + projectSemanticData.getName() + "#" + hit.id();
                result = Optional.of(new OmniboxCommand(commandId, hit.label() + " (project: " + projectName + ")", hit.iconURLs(), "Navigate to object"));
            }
        }
        return result;
    }

    private Connection<OmniboxCommand> toConnection(List<OmniboxCommand> omniboxCommands) {
        List<Edge<OmniboxCommand>> omniboxCommandsEdge = omniboxCommands.stream()
                .map(omniboxCommand -> {
                    String globalId = new Relay().toGlobalId("ViewerOmniboxCommand", omniboxCommand.id());
                    var cursor = new DefaultConnectionCursor(globalId);
                    return (Edge<OmniboxCommand>) new DefaultEdge<>(omniboxCommand, cursor);
                })
                .toList();

        ConnectionCursor startCursor = omniboxCommandsEdge.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!omniboxCommandsEdge.isEmpty()) {
            endCursor = omniboxCommandsEdge.get(omniboxCommandsEdge.size() - 1).getCursor();
        }

        var pageInfo = new PageInfoWithCount(startCursor, endCursor, false, false, omniboxCommands.size());
        return new DefaultConnection<>(omniboxCommandsEdge, pageInfo);
    }
}
