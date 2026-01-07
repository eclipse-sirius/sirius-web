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
package org.eclipse.sirius.components.tables.graphql.datafetchers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.tables.dto.ToolMenuEntriesInput;
import org.eclipse.sirius.components.collaborative.tables.dto.ToolMenuEntriesPayload;
import org.eclipse.sirius.components.collaborative.tables.dto.ToolMenuEntry;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve the table description tool menu entries.
 *
 * @author frouene
 */
@QueryDataFetcher(type = "TableDescription", field = "toolMenuEntries")
public class TableDescriptionToolMenuEntriesDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<ToolMenuEntry>>> {

    private static final String INPUT_ARGUMENT = "tableId";

    private final IEditingContextDispatcher editingContextDispatcher;

    public TableDescriptionToolMenuEntriesDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<ToolMenuEntry>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String tableId = environment.getArgument(INPUT_ARGUMENT);

        var input = new ToolMenuEntriesInput(UUID.randomUUID(), editingContextId, representationId, tableId);
        return this.editingContextDispatcher.dispatchQuery(editingContextId, input)
                .filter(ToolMenuEntriesPayload.class::isInstance)
                .map(ToolMenuEntriesPayload.class::cast)
                .map(ToolMenuEntriesPayload::tools)
                .toFuture();
    }
}
