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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.document.dto.UploadDocumentInput;
import org.eclipse.sirius.web.domain.services.api.IMessageService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#uploadDocument.
 *
 * @author sbegaudeau
 */
@MutationDataFetcher(type = "Mutation", field = "uploadDocument")
public class MutationUploadDocumentDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    private static final String INPUT_ARGUMENT = "input";

    private static final String EDITING_CONTEXT_ID = "editingContextId";

    private static final String FILE = "file";

    private static final String ID = "id";

    private final IEditingContextDispatcher editingContextDispatcher;

    private final IMessageService messageService;

    public MutationUploadDocumentDataFetcher(IEditingContextDispatcher editingContextDispatcher, IMessageService messageService) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Map<Object, Object> inputArgument = environment.getArgument(INPUT_ARGUMENT);

        var optionalId = Optional.ofNullable(inputArgument.get(ID))
                .map(Object::toString)
                .flatMap(new UUIDParser()::parse);
        var optionalEditingContextId = Optional.ofNullable(inputArgument.get(EDITING_CONTEXT_ID))
                .filter(String.class::isInstance)
                .map(String.class::cast);
        var optionalFile = Optional.ofNullable(inputArgument.get(FILE))
                .filter(UploadFile.class::isInstance)
                .map(UploadFile.class::cast);

        if (optionalId.isPresent() && optionalEditingContextId.isPresent() && optionalFile.isPresent()) {
            var id = optionalId.get();
            var editingContextId = optionalEditingContextId.get();
            var file = optionalFile.get();

            var input = new UploadDocumentInput(id, editingContextId, file);
            return this.editingContextDispatcher.dispatchMutation(input.editingContextId(), input).toFuture();
        }

        return CompletableFuture.completedFuture(new ErrorPayload(UUID.randomUUID(), this.messageService.unexpectedError()));
    }
}
