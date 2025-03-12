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
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.tables.api.IRowFilterProvider;
import org.eclipse.sirius.components.collaborative.tables.api.RowFilter;
import org.eclipse.sirius.components.collaborative.tables.dto.RowFiltersInput;
import org.eclipse.sirius.components.collaborative.tables.dto.RowFiltersSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;

import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve the table description context menu.
 *
 * @author Jerome Gout
 */
@QueryDataFetcher(type = "TableDescription", field = "rowFilters")
public class TableDescriptionRowFiltersDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<RowFilter>>> {

    private static final String TABLE_ID_ARGUMENT = "tableId";

    private final IEditingContextDispatcher editingContextDispatcher;

    public TableDescriptionRowFiltersDataFetcher(List<IRowFilterProvider> tableRowFiltersProviders, IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = editingContextDispatcher;
    }

    @Override
    public CompletableFuture<List<RowFilter>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String tableId = environment.getArgument(TABLE_ID_ARGUMENT);
        TableDescription tableDescription = environment.getSource();

        var input = new RowFiltersInput(UUID.randomUUID(), editingContextId, tableId, tableDescription, representationId);
        return this.editingContextDispatcher.dispatchQuery(editingContextId, input)
                .filter(RowFiltersSuccessPayload.class::isInstance)
                .map(RowFiltersSuccessPayload.class::cast)
                .map(RowFiltersSuccessPayload::filters)
                .toFuture();
    }
}
