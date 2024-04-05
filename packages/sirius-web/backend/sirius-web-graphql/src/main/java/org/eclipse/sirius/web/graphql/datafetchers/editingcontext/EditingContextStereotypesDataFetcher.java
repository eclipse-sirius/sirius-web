/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.editingcontext;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.services.api.document.Stereotype;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.services.api.stereotypes.IStereotypeService;

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the stereotypes accessible in an editing context.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   stereotypes: EditingContextStereotypesConnection!
 * }
 * </pre>
 *
 * @author hmarchadour
 */
@QueryDataFetcher(type = "EditingContext", field = "stereotypes")
public class EditingContextStereotypesDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<Stereotype>> {

    private final IStereotypeService stereotypeService;

    public EditingContextStereotypesDataFetcher(IStereotypeService stereotypeService) {
        this.stereotypeService = Objects.requireNonNull(stereotypeService);
    }

    @Override
    public Connection<Stereotype> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        var stereotypes = this.stereotypeService.getStereotypes(editingContextId);

        // @formatter:off
        List<Edge<Stereotype>> stereotypeEdges = stereotypes.stream()
                .map(stereotype -> {
                    String value = Base64.getEncoder().encodeToString(stereotype.getId().toString().getBytes());
                    ConnectionCursor cursor = new DefaultConnectionCursor(value);
                    Edge<Stereotype> edge = new DefaultEdge<>(stereotype, cursor);
                    return edge;
                })
                .toList();
        // @formatter:on

        ConnectionCursor startCursor = stereotypeEdges.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!stereotypeEdges.isEmpty()) {
            endCursor = stereotypeEdges.get(stereotypeEdges.size() - 1).getCursor();
        }
        PageInfo pageInfo = new DefaultPageInfo(startCursor, endCursor, false, false);
        return new DefaultConnection<>(stereotypeEdges, pageInfo);
    }
}
