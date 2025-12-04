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
package org.eclipse.sirius.components.trees.graphql.datafetchers.tree;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.trees.api.TreeFilter;
import org.eclipse.sirius.components.collaborative.trees.dto.GetTreeFiltersInput;
import org.eclipse.sirius.components.collaborative.trees.dto.GetTreeFiltersSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Used to retrieve the tree description "filters" field.
 *
 * @author arichard
 */
@QueryDataFetcher(type = "TreeDescription", field = "filters")
public class TreeDescriptionFiltersDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<TreeFilter>>> {

    private final IExceptionWrapper exceptionWrapper;

    private final IEditingContextDispatcher editingContextDispatcher;

    public TreeDescriptionFiltersDataFetcher(IExceptionWrapper exceptionWrapper, IEditingContextDispatcher editingContextDispatcher) {
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<TreeFilter>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);

        if (editingContextId != null && representationId != null) {
            var input = new GetTreeFiltersInput(UUID.randomUUID(), editingContextId, representationId);

            return this.exceptionWrapper.wrapMono(() -> this.editingContextDispatcher.dispatchQuery(editingContextId, input), input)
                    .filter(GetTreeFiltersSuccessPayload.class::isInstance)
                    .map(GetTreeFiltersSuccessPayload.class::cast)
                    .map(GetTreeFiltersSuccessPayload::treeFilters)
                    .toFuture();
        }
        return Mono.<List<TreeFilter>>empty().toFuture();
    }
}
