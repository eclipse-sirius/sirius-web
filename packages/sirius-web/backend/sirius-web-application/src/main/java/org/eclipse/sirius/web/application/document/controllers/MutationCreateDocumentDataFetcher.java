/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.document.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.web.application.document.dto.CreateDocumentInput;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#createDocument.
 *
 * @author sbegaudeau
 */
@MutationDataFetcher(type = "Mutation", field = "createDocument")
public class MutationCreateDocumentDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IEditingContextDispatcher editingContextDispatcher;

    public MutationCreateDocumentDataFetcher(ObjectMapper objectMapper, IEditingContextDispatcher editingContextDispatcher) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, CreateDocumentInput.class);

        return this.editingContextDispatcher.dispatchMutation(input.editingContextId(), input).toFuture();
    }
}
