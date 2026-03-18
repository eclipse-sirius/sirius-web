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
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.MinimapVisibleInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.MinimapVisiblePayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Datafetcher to retrieve the minimapVisible diagram property.
 *
 * @author fbarbin
 */
@QueryDataFetcher(type = "DiagramDescription", field = "minimapVisible")
public class DiagramDescriptionMinimapVisibleDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Boolean>> {

    private final IEditingContextDispatcher editingContextDispatcher;

    public DiagramDescriptionMinimapVisibleDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<Boolean> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);

        if (editingContextId != null && representationId != null) {
            MinimapVisibleInput input = new MinimapVisibleInput(editingContextId, representationId);
            return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .ofType(MinimapVisiblePayload.class)
                .map(MinimapVisiblePayload::minimapVisible)
                .switchIfEmpty(Mono.just(true))
                .toFuture();
        }
        //We fall back on a true value in order to keep the same current behavior.
        return Mono.just(true).toFuture();
    }
}
