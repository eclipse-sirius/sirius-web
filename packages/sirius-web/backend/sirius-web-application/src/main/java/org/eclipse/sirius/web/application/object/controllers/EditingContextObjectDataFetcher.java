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

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.dto.EditingContextObjectInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextObjectPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field EditingContext#object.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "EditingContext", field = "object")
public class EditingContextObjectDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Object>> {

    private static final String OBJECT_ID_ARGUMENT = "objectId";

    private final IEditingContextDispatcher editingContextDispatcher;

    public EditingContextObjectDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<Object> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String objectId = environment.getArgument(OBJECT_ID_ARGUMENT);

        EditingContextObjectInput input = new EditingContextObjectInput(UUID.randomUUID(), editingContextId, objectId);
        return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .filter(EditingContextObjectPayload.class::isInstance)
                .map(EditingContextObjectPayload.class::cast)
                .map(EditingContextObjectPayload::object)
                .toFuture();
    }
}
