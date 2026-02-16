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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetToolbarInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetToolbarSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramToolbar;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Datafetcher to retrieve the toolbar diagram property.
 *
 * @author tgiraudet
 */
@QueryDataFetcher(type = "DiagramDescription", field = "toolbar")
public class DiagramDescriptionToolbarDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<DiagramToolbar>> {

    private final IEditingContextDispatcher editingContextDispatcher;

    public DiagramDescriptionToolbarDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<DiagramToolbar> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);

        if (editingContextId != null && representationId != null) {
            GetToolbarInput input = new GetToolbarInput(UUID.randomUUID(), editingContextId, representationId);

            return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .filter(GetToolbarSuccessPayload.class::isInstance)
                .map(GetToolbarSuccessPayload.class::cast)
                .map(GetToolbarSuccessPayload::toolbar)
                .toFuture();
        }

        return Mono.<DiagramToolbar>empty().toFuture();
    }
}
