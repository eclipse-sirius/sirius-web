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
 ******************************************************************************/

package org.eclipse.sirius.components.diagrams.graphql.datafetchers.diagram;

import graphql.schema.DataFetchingEnvironment;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetLayoutConfigurationInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetLayoutConfigurationSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutConfiguration;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * The data fetcher used to retrieve the layout configurations from the diagram description.
 *
 * @author ocailleau
 */

@QueryDataFetcher(type = "DiagramDescription", field = "layoutConfigurations")
public class LayoutConfigurationDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<LayoutConfiguration>>> {

    private final IEditingContextDispatcher editingContextDispatcher;

    public LayoutConfigurationDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<LayoutConfiguration>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);

        if (editingContextId != null && representationId != null) {
            GetLayoutConfigurationInput input = new GetLayoutConfigurationInput(UUID.randomUUID(), editingContextId, representationId);

            return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                    .filter(GetLayoutConfigurationSuccessPayload.class::isInstance)
                    .map(GetLayoutConfigurationSuccessPayload.class::cast)
                    .map(GetLayoutConfigurationSuccessPayload::layoutConfigurations)
                    .toFuture();
        }

        return Mono.<List<LayoutConfiguration>>empty().toFuture();
    }

}
