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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetLayoutGroupsSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetLayoutGroupsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutGroup;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The data fetcher used to retrieve the layout groups from the diagram description.
 *
 * @author ocailleau
 */
@QueryDataFetcher(type = "DiagramDescription", field = "layoutGroups")
public class DiagramDescriptionLayoutGroupsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<LayoutGroup>>> {

    private final IEditingContextDispatcher editingContextDispatcher;

    public DiagramDescriptionLayoutGroupsDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<LayoutGroup>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);

        if (editingContextId != null && representationId != null) {
            GetLayoutGroupsInput input = new GetLayoutGroupsInput(UUID.randomUUID(), editingContextId, representationId);

            return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                    .filter(GetLayoutGroupsSuccessPayload.class::isInstance)
                    .map(GetLayoutGroupsSuccessPayload.class::cast)
                    .map(GetLayoutGroupsSuccessPayload::layoutGroups)
                    .toFuture();
        }

        return Mono.<List<LayoutGroup>>empty().toFuture();
    }

}