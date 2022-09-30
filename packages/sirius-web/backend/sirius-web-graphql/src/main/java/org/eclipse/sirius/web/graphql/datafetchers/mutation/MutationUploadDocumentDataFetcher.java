/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.mutation;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.graphql.messages.IGraphQLMessageService;
import org.eclipse.sirius.web.graphql.schema.MutationTypeProvider;
import org.eclipse.sirius.web.services.api.document.UploadDocumentInput;
import org.eclipse.sirius.web.services.api.id.IDParser;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to create a new document thanks to an UploadInput.
 * <p>
 * It will be used to handle the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Mutation {
 *   uploadDocument(input: UploadDocumentInput!): UploadDocumentPayload!
 * }
 * </pre>
 *
 * @author smonnier
 */
@MutationDataFetcher(type = MutationTypeProvider.TYPE, field = MutationUploadDocumentDataFetcher.UPLOAD_DOCUMENT_FIELD)
public class MutationUploadDocumentDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    public static final String UPLOAD_DOCUMENT_FIELD = "uploadDocument"; //$NON-NLS-1$

    private static final String EDITING_CONTEXT_ID = "editingContextId"; //$NON-NLS-1$

    private static final String FILE = "file"; //$NON-NLS-1$

    private static final String ID = "id"; //$NON-NLS-1$

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IGraphQLMessageService messageService;

    public MutationUploadDocumentDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IGraphQLMessageService messageService) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Map<Object, Object> inputArgument = environment.getArgument(MutationTypeProvider.INPUT_ARGUMENT);

        // We cannot use directly UploadDocumentInput, the objectMapper cannot handle the file stream.

        // @formatter:off
        UUID id = Optional.of(inputArgument.get(ID))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .flatMap(new IDParser()::parse)
                .orElse(null);

        String editingContextId = Optional.of(inputArgument.get(EDITING_CONTEXT_ID))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse(null);

        UploadFile file = Optional.of(inputArgument.get(FILE))
                .filter(UploadFile.class::isInstance)
                .map(UploadFile.class::cast)
                .orElse(null);
        // @formatter:on

        UploadDocumentInput input = new UploadDocumentInput(id, editingContextId, file);

        // @formatter:off
        return this.editingContextEventProcessorRegistry.dispatchEvent(input.getEditingContextId(), input)
                .defaultIfEmpty(new ErrorPayload(input.getId(), this.messageService.unexpectedError()))
                .toFuture();
        // @formatter:on
    }

}
