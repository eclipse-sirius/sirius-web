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
package org.eclipse.sirius.web.application.object.controllers;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.dto.EditingContextObjectsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextObjectsPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field EditingContext#objects.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "EditingContext", field = "objects")
public class EditingContextObjectsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<Object>>> {

    private static final String OBJECT_IDS_ARGUMENT = "objectIds";

    private final IEditingContextDispatcher editingContextDispatcher;

    public EditingContextObjectsDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<Object>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        List<String> objectIds = environment.getArgument(OBJECT_IDS_ARGUMENT);

        EditingContextObjectsInput input = new EditingContextObjectsInput(UUID.randomUUID(), editingContextId, objectIds);
        return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .filter(EditingContextObjectsPayload.class::isInstance)
                .map(EditingContextObjectsPayload.class::cast)
                .map(EditingContextObjectsPayload::objects)
                .toFuture();
    }
}
