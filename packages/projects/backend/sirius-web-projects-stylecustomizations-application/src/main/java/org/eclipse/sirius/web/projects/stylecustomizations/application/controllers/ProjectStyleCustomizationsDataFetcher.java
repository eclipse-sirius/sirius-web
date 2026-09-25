/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.projects.stylecustomizations.application.controllers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.graphql.dto.PageInfoWithCount;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
import org.eclipse.sirius.web.application.pagination.services.api.ILimitProvider;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.core.domain.pagination.Window;
import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.StyleCustomizationDTO;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IProjectStyleCustomizationSearchApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Data fetcher for the field Project#styleCustomizations.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "Project", field = "styleCustomizations")
public class ProjectStyleCustomizationsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<StyleCustomizationDTO>> {

    private static final String FIRST_ARGUMENT = "first";

    private static final String LAST_ARGUMENT = "last";

    private static final String AFTER_ARGUMENT = "after";

    private static final String BEFORE_ARGUMENT = "before";

    private final ICapabilityEvaluator capabilityEvaluator;

    private final IProjectStyleCustomizationSearchApplicationService styleCustomizationSearchApplicationService;

    private final ILimitProvider limitProvider;

    private final Logger logger = LoggerFactory.getLogger(ProjectStyleCustomizationsDataFetcher.class);

    public ProjectStyleCustomizationsDataFetcher(ICapabilityEvaluator capabilityEvaluator,
            IProjectStyleCustomizationSearchApplicationService styleCustomizationSearchApplicationService, ILimitProvider limitProvider) {
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
        this.styleCustomizationSearchApplicationService = Objects.requireNonNull(styleCustomizationSearchApplicationService);
        this.limitProvider = Objects.requireNonNull(limitProvider);
    }

    @Override
    public Connection<StyleCustomizationDTO> get(DataFetchingEnvironment environment) {
        ProjectDTO project = environment.getSource();
        boolean hasCapability = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.PROJECT, project.id(), SiriusWebCapabilities.Project.LIST_STYLE_CUSTOMIZATIONS);
        if (!hasCapability) {
            this.logger.atWarn()
                    .setMessage("Access denied to style customizations for project {}")
                    .addArgument(project.id())
                    .addKeyValue("projectId", project.id())
                    .addKeyValue("capabilityType", SiriusWebCapabilities.PROJECT)
                    .addKeyValue("capability", SiriusWebCapabilities.Project.LIST_STYLE_CUSTOMIZATIONS)
                    .log();
            return new DefaultConnection<>(List.of(), new PageInfoWithCount(null, null, false, false, 0));
        }

        Optional<Integer> first = Optional.ofNullable(environment.getArgument(FIRST_ARGUMENT));
        Optional<Integer> last = Optional.ofNullable(environment.getArgument(LAST_ARGUMENT));
        Optional<String> after = Optional.ofNullable(environment.getArgument(AFTER_ARGUMENT));
        Optional<String> before = Optional.ofNullable(environment.getArgument(BEFORE_ARGUMENT));
        KeysetScrollPosition position = this.getPosition(after, before, first, last);
        int limit = this.limitProvider.getLimit(20, first, last, after, before);

        var styleCustomizationWindow = this.styleCustomizationSearchApplicationService.getStyleCustomizations(project.id(), position, limit);
        var connection = this.toConnection(styleCustomizationWindow);

        this.logger.atInfo()
                .setMessage("{} style customization(s) of project {} retrieved")
                .addArgument(connection.getEdges().size())
                .addArgument(project.id())
                .log();

        return connection;
    }

    public KeysetScrollPosition getPosition(Optional<String> after, Optional<String> before, Optional<Integer> first, Optional<Integer> last) {
        KeysetScrollPosition position = ScrollPosition.keyset();
        if (after.isPresent() && before.isEmpty()) {
            var styleCustomizationId = after.get();
            var cursorStyleCustomizationId = new Relay().fromGlobalId(styleCustomizationId).getId();
            position = ScrollPosition.forward(Map.of("id", cursorStyleCustomizationId));
        } else if (before.isPresent() && after.isEmpty()) {
            var styleCustomizationId = before.get();
            var cursorStyleCustomizationId = new Relay().fromGlobalId(styleCustomizationId).getId();
            position = ScrollPosition.backward(Map.of("id", cursorStyleCustomizationId));
        } else if (last.isPresent() && first.isEmpty()) {
            position = ScrollPosition.backward(Map.of());
        }
        return position;
    }

    private Connection<StyleCustomizationDTO> toConnection(Window<StyleCustomizationDTO> window) {
        var edges = window.stream().map(styleCustomizationDTO -> {
            var globalId = new Relay().toGlobalId("StyleCustomization", styleCustomizationDTO.id());
            var cursor = new DefaultConnectionCursor(globalId);

            return (Edge<StyleCustomizationDTO>) new DefaultEdge<>(styleCustomizationDTO, cursor);
        }).toList();

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
