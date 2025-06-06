/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.GetManageVisibilityActionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.GetManageVisibilityActionsSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.ManageVisibilityAction;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Data fetcher to retrieve the manage visibility actions of a node.
 *
 * @author mcharfadi
 */
@QueryDataFetcher(type = "DiagramDescription", field = "manageVisibilityActions")
public class DiagramDescriptionManageVisibilityActionsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<ManageVisibilityAction>>> {

    private static final String DIAGRAM_ELEMENT_ID = "diagramElementId";

    private final IEditingContextDispatcher editingContextDispatcher;

    public DiagramDescriptionManageVisibilityActionsDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<ManageVisibilityAction>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String diagramElementId = environment.getArgument(DIAGRAM_ELEMENT_ID);

        if (editingContextId != null && representationId != null) {
            GetManageVisibilityActionsInput input = new GetManageVisibilityActionsInput(UUID.randomUUID(), editingContextId, representationId, diagramElementId);

            return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                    .filter(GetManageVisibilityActionsSuccessPayload.class::isInstance)
                    .map(GetManageVisibilityActionsSuccessPayload.class::cast)
                    .map(GetManageVisibilityActionsSuccessPayload::manageVisibilityActions)
                    .toFuture();
        }

        return Mono.<List<ManageVisibilityAction>>empty().toFuture();
    }
}
