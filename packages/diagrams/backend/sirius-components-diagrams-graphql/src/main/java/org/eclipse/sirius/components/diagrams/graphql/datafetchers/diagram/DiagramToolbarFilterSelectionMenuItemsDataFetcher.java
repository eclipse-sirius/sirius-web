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
package org.eclipse.sirius.components.diagrams.graphql.datafetchers.diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.FilterSelectionMenuItem;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Datafetcher to retrieve the toolbar diagram filterSelectionMenuItems property.
 *
 * @author mcharfadi
 */
@QueryDataFetcher(type = "DiagramToolbar", field = "filterSelectionMenuItems")
public class DiagramToolbarFilterSelectionMenuItemsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<FilterSelectionMenuItem>>> {

    private static final String DIAGRAM_ELEMENT_IDS = "diagramElementIds";

    @Override
    public CompletableFuture<List<FilterSelectionMenuItem>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        List<String> diagramElementIds = environment.getArgument(DIAGRAM_ELEMENT_IDS);
        if (editingContextId != null && representationId != null) {
            List<FilterSelectionMenuItem> mockedFilteredMenuItems = new ArrayList<>();
            if (diagramElementIds != null && diagramElementIds.isEmpty()) {
                mockedFilteredMenuItems.addAll(List.of(new FilterSelectionMenuItem("select_all_edges", "Select edges"),
                        new FilterSelectionMenuItem("select_all_nodes", "Select nodes")));
            } else {
                mockedFilteredMenuItems.addAll(List.of(new FilterSelectionMenuItem("unselect_child_nodes", "Unselect child nodes"),
                        new FilterSelectionMenuItem("unselect_all_edges", "Unselect edges"),
                        new FilterSelectionMenuItem("unselect_all_nodes", "Unselect nodes")));
            }

            return Mono.just(mockedFilteredMenuItems).toFuture();
        }

        return Mono.just(List.<FilterSelectionMenuItem> of()).toFuture();
    }

}
