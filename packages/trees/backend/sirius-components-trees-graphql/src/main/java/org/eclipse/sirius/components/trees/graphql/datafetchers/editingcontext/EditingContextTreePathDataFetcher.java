/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePath;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the path (from the root) to selected entries in a tree.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "EditingContext", field = "treePath")
public class EditingContextTreePathDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<TreePath>> {

    private static final String TREE_ID = "treeId"; //$NON-NLS-1$

    private static final String SELECTION_ENTRY_IDS = "selectionEntryIds"; //$NON-NLS-1$

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextTreePathDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<TreePath> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String treeId = environment.getArgument(TREE_ID);
        List<String> selectionEntryIds = environment.getArgument(SELECTION_ENTRY_IDS);

        TreePathInput input = new TreePathInput(UUID.randomUUID(), editingContextId, treeId, selectionEntryIds);
        // @formatter:off
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .filter(TreePathSuccessPayload.class::isInstance)
                .map(TreePathSuccessPayload.class::cast)
                .map(TreePathSuccessPayload::getTreePath)
                .toFuture();
        // @formatter:on
    }

}
