/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.representation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationApplicationService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Data fetcher for the field Mutation#deleteRepresentation.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Mutation", field = "deleteRepresentation")
public class MutationDeleteRepresentationDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IExceptionWrapper exceptionWrapper;

    private final IEditingContextDispatcher editingContextDispatcher;

    private final IRepresentationApplicationService representationApplicationService;

    private final IMessageService messageService;

    public MutationDeleteRepresentationDataFetcher(ObjectMapper objectMapper, IExceptionWrapper exceptionWrapper, IEditingContextDispatcher editingContextDispatcher, IRepresentationApplicationService representationApplicationService, IMessageService messageService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
        this.representationApplicationService = Objects.requireNonNull(representationApplicationService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, DeleteRepresentationInput.class);

        var optionalEditingContextId = this.representationApplicationService.findEditingContextIdFromRepresentationId(input.representationId());
        if (optionalEditingContextId.isPresent()) {
            var editingContextId = optionalEditingContextId.get();
            return this.exceptionWrapper.wrapMono(() -> this.editingContextDispatcher.dispatchMutation(editingContextId, input), input).toFuture();
        } else {
            return Mono.just((IPayload) new ErrorPayload(input.id(), this.messageService.notFound())).toFuture();
        }
    }
}
