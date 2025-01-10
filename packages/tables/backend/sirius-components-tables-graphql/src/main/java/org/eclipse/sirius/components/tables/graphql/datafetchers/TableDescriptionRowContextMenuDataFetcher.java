/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.tables.dto.RowContextMenuEntriesInput;
import org.eclipse.sirius.components.collaborative.tables.dto.RowContextMenuEntry;
import org.eclipse.sirius.components.collaborative.tables.dto.RowContextMenuSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve the table description context menu.
 *
 * @author Jerome Gout
 */
@QueryDataFetcher(type = "TableDescription", field = "rowContextMenuEntries")
public class TableDescriptionRowContextMenuDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<RowContextMenuEntry>>> {

    private static final String TABLE_ID_ARGUMENT = "tableId";

    private static final String ROW_ID_ARGUMENT = "rowId";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public TableDescriptionRowContextMenuDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<RowContextMenuEntry>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String tableId = environment.getArgument(TABLE_ID_ARGUMENT);
        String rowId = environment.getArgument(ROW_ID_ARGUMENT);

        var input = new RowContextMenuEntriesInput(UUID.randomUUID(), editingContextId, representationId, tableId, UUID.fromString(rowId));
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .filter(RowContextMenuSuccessPayload.class::isInstance)
                .map(RowContextMenuSuccessPayload.class::cast)
                .map(RowContextMenuSuccessPayload::entries)
                .toFuture();
    }
}
