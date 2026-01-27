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
package org.eclipse.sirius.components.trees.graphql.datafetchers.tree;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeItemTooltipInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeItemTooltipSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Retrieves the tooltip of a tree item.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "TreeDescription", field = "treeItemTooltip")
public class TreeDescriptionTreeItemTooltipDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<String>> {

    private static final String TREE_ITEM_ID = "treeItemId";

    private final IEditingContextDispatcher editingContextDispatcher;

    public TreeDescriptionTreeItemTooltipDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<String> get(DataFetchingEnvironment environment) throws Exception {
        CompletableFuture<String> result  = Mono.<String>just("").toFuture();
        Map<String, Object> localContext = environment.getLocalContext();

        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String treeItemId = environment.getArgument(TREE_ITEM_ID);

        if (editingContextId != null && representationId != null && treeItemId != null) {
            TreeItemTooltipInput input = new TreeItemTooltipInput(UUID.randomUUID(), editingContextId, representationId, treeItemId);
            result = this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                    .filter(TreeItemTooltipSuccessPayload.class::isInstance)
                    .map(TreeItemTooltipSuccessPayload.class::cast)
                    .map(TreeItemTooltipSuccessPayload::tooltip)
                    .toFuture();
        }

        return result;
    }
}
