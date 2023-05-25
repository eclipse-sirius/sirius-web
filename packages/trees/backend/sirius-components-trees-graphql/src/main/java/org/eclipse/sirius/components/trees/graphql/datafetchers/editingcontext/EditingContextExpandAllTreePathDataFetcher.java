/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.trees.graphql.datafetchers.editingcontext;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePath;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the path (from the given tree item) of all children tree items.
 *
 * @author arichard
 */
@QueryDataFetcher(type = "EditingContext", field = "expandAllTreePath")
public class EditingContextExpandAllTreePathDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<TreePath>> {

    private static final String TREE_ID = "treeId";

    private static final String TREE_ITEM_ID = "treeItemId";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextExpandAllTreePathDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<TreePath> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String treeId = environment.getArgument(TREE_ID);
        String treeItemId = environment.getArgument(TREE_ITEM_ID);

        ExpandAllTreePathInput input = new ExpandAllTreePathInput(UUID.randomUUID(), editingContextId, treeId, treeItemId);
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .filter(ExpandAllTreePathSuccessPayload.class::isInstance)
                .map(ExpandAllTreePathSuccessPayload.class::cast)
                .map(ExpandAllTreePathSuccessPayload::treePath)
                .toFuture();
    }

}
