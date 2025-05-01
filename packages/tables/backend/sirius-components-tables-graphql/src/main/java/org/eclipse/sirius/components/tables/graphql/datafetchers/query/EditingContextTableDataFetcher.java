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
package org.eclipse.sirius.components.tables.graphql.datafetchers.query;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.tables.dto.TableInput;
import org.eclipse.sirius.components.collaborative.tables.dto.TableSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.tables.Table;

import graphql.schema.DataFetchingEnvironment;

/**
 * @author frouene
 */
@QueryDataFetcher(type = "EditingContext", field = "table")
public class EditingContextTableDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Table>> {

    private static final String TABLE_ID = "tableId";

    private final IEditingContextDispatcher editingContextDispatcher;

    public EditingContextTableDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<Table> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String tableId = environment.getArgument(TABLE_ID);

        TableInput input = new TableInput(UUID.randomUUID(), editingContextId, tableId);
        return this.editingContextDispatcher.dispatchQuery(editingContextId, input)
                .filter(TableSuccessPayload.class::isInstance)
                .map(TableSuccessPayload.class::cast)
                .map(TableSuccessPayload::table)
                .toFuture();
    }

}

