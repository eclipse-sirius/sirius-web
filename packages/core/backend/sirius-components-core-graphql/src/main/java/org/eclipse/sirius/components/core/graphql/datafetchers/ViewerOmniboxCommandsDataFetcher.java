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

package org.eclipse.sirius.components.core.graphql.datafetchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandSeachService;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.eclipse.sirius.components.core.graphql.dto.PageInfoWithCount;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.Edge;
import graphql.relay.Relay;
import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Viewer#omniboxCommands.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "Viewer", field = "omniboxCommands")
public class ViewerOmniboxCommandsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<OmniboxCommand>> {

    private static final String EDITING_CONTEXT_ID_ARGUMENT = "editingContextId";

    private static final String SELECTED_OBJECT_IDS_ARGUMENT = "selectedObjectIds";

    private static final String QUERY_ARGUMENT = "query";

    private final IOmniboxCommandSeachService omniboxCommandSeachService;

    private final ObjectMapper objectMapper;

    public ViewerOmniboxCommandsDataFetcher(IOmniboxCommandSeachService omniboxCommandSeachService, ObjectMapper objectMapper) {
        this.omniboxCommandSeachService = Objects.requireNonNull(omniboxCommandSeachService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public Connection<OmniboxCommand> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getArgument(EDITING_CONTEXT_ID_ARGUMENT);
        Object argument = environment.getArgument(SELECTED_OBJECT_IDS_ARGUMENT);
        List<String> selectedObjectIds = this.objectMapper.convertValue(argument, new TypeReference<>() { });
        String query = environment.getArgument(QUERY_ARGUMENT);

        List<OmniboxCommand> omniboxCommands = this.omniboxCommandSeachService.findAll(editingContextId, selectedObjectIds, query);

        return this.toConnection(omniboxCommands);
    }

    private Connection<OmniboxCommand> toConnection(List<OmniboxCommand> omniboxCommands) {
        List<Edge<OmniboxCommand>> omniboxCommandsEdge = omniboxCommands.stream()
                .map(omniboxCommand -> {
                    String globalId = new Relay().toGlobalId("ViewerOmniboxCommand", omniboxCommand.id().toString());
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
