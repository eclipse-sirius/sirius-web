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
package org.eclipse.sirius.components.trees.graphql.datafetchers.tree;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.dto.ImpactAnalysisReport;
import org.eclipse.sirius.components.collaborative.dto.InvokeImpactAnalysisSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.dto.InvokeTreeImpactAnalysisInput;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Used to retrieve the impact analysis on a contextual menu entry execution.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "TreeDescription", field = "treeImpactAnalysisReport")
public class TreeDescriptionTreeImpactAnalysisReportDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<ImpactAnalysisReport>> {

    private static final String TREE_ITEM_ID = "treeItemId";

    private static final String MENU_ENTRY_ID = "menuEntryId";

    private final IEditingContextDispatcher editingContextDispatcher;

    public TreeDescriptionTreeImpactAnalysisReportDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<ImpactAnalysisReport> get(DataFetchingEnvironment environment) throws Exception {
        CompletableFuture<ImpactAnalysisReport> result = Mono.<ImpactAnalysisReport>empty().toFuture();
        Map<String, Object> localContext = environment.getLocalContext();

        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String treeItemId = environment.getArgument(TREE_ITEM_ID);
        String menuEntryId = environment.getArgument(MENU_ENTRY_ID);

        if (editingContextId != null && representationId != null) {
            InvokeTreeImpactAnalysisInput input = new InvokeTreeImpactAnalysisInput(UUID.randomUUID(), editingContextId, representationId, treeItemId, menuEntryId);
            result = this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                    .filter(InvokeImpactAnalysisSuccessPayload.class::isInstance)
                    .map(InvokeImpactAnalysisSuccessPayload.class::cast)
                    .map(InvokeImpactAnalysisSuccessPayload::impactAnalysisReport)
                    .toFuture();
        }

        return result;
    }
}
