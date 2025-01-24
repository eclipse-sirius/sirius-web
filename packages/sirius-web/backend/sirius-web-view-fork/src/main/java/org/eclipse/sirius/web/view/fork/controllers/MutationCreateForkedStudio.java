/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST and others.
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
package org.eclipse.sirius.web.view.fork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.sirius.web.view.fork.dto.CreateForkedStudioInput;
import graphql.schema.DataFetchingEnvironment;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Data fetcher for the field Mutation#createForkedStudio.
 *
 * @author mcharfadi
 */
@MutationDataFetcher(type = "Mutation", field = "createForkedStudio")
public class MutationCreateForkedStudio implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IExceptionWrapper exceptionWrapper;

    private final IEditingContextDispatcher editingContextDispatcher;

    public MutationCreateForkedStudio(ObjectMapper objectMapper, IExceptionWrapper exceptionWrapper, IEditingContextDispatcher editingContextDispatcher) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, CreateForkedStudioInput.class);

        return this.exceptionWrapper.wrapMono(() -> this.editingContextDispatcher.dispatchMutation(input.editingContextId(), input), input).toFuture();
    }
}
