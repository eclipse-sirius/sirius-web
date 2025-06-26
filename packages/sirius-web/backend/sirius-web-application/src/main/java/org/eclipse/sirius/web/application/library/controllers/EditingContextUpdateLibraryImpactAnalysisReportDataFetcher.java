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
package org.eclipse.sirius.web.application.library.controllers;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.dto.ImpactAnalysisReport;
import org.eclipse.sirius.components.collaborative.dto.InvokeImpactAnalysisSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.web.application.library.dto.InvokeUpdateLibraryImpactAnalysisInput;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Used to retrieve the impact analysis for a library update.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "EditingContext", field = "updateLibraryImpactAnalysisReport")
public class EditingContextUpdateLibraryImpactAnalysisReportDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<ImpactAnalysisReport>> {

    private static final String LIBRARY_ID = "libraryId";

    private final IEditingContextDispatcher editingContextDispatcher;

    public EditingContextUpdateLibraryImpactAnalysisReportDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<ImpactAnalysisReport> get(DataFetchingEnvironment environment) throws Exception {
        CompletableFuture<ImpactAnalysisReport> result = Mono.<ImpactAnalysisReport>empty().toFuture();
        Map<String, Object> localContext = environment.getLocalContext();

        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String libraryId = environment.getArgument(LIBRARY_ID);

        if (editingContextId != null && libraryId != null) {
            InvokeUpdateLibraryImpactAnalysisInput input = new InvokeUpdateLibraryImpactAnalysisInput(UUID.randomUUID(), editingContextId, UUID.fromString(libraryId));
            result = this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                    .filter(InvokeImpactAnalysisSuccessPayload.class::isInstance)
                    .map(InvokeImpactAnalysisSuccessPayload.class::cast)
                    .map(InvokeImpactAnalysisSuccessPayload::impactAnalysisReport)
                    .toFuture();
        }

        return result;
    }
}
