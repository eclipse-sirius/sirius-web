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
import org.eclipse.sirius.components.collaborative.trees.dto.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeItemContextMenuInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeItemContextMenuSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve the tree description context menu.
 *
 * @author Jerome Gout
 */
@QueryDataFetcher(type = "TreeDescription", field = "contextMenu")
public class TreeDescriptionContextMenuDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<ITreeItemContextMenuEntry>>> {

    private static final String INPUT_ARGUMENT = "treeItemId";

    private final IEditingContextDispatcher editingContextDispatcher;

    public TreeDescriptionContextMenuDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<ITreeItemContextMenuEntry>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String treeItemId = environment.getArgument(INPUT_ARGUMENT);

        var input = new TreeItemContextMenuInput(UUID.randomUUID(), editingContextId, representationId, treeItemId);
        return this.editingContextDispatcher.dispatchQuery(editingContextId, input)
                .filter(TreeItemContextMenuSuccessPayload.class::isInstance)
                .map(TreeItemContextMenuSuccessPayload.class::cast)
                .map(TreeItemContextMenuSuccessPayload::actions)
                .toFuture();
    }
}
