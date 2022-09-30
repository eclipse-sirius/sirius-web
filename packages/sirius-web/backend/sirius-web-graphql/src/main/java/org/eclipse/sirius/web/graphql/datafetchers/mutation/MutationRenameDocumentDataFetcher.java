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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.RenameDocumentInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.messages.IGraphQLMessageService;
import org.eclipse.sirius.web.graphql.schema.MutationTypeProvider;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.projects.Project;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The data fetcher used to rename a {@link Document}.
 * <p>
 * It will be used to handle the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Mutation {
 *   renameDocument(input : RenameDocumentInput!): RenameDocumentPayload!
 * }
 * </pre>
 *
 * @author fbarbin
 */
@MutationDataFetcher(type = MutationTypeProvider.TYPE, field = MutationRenameDocumentDataFetcher.RENAME_DOCUMENT_FIELD)
public class MutationRenameDocumentDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    public static final String RENAME_DOCUMENT_FIELD = "renameDocument"; //$NON-NLS-1$

    private final ObjectMapper objectMapper;

    private final IDocumentService documentService;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IGraphQLMessageService messageService;

    public MutationRenameDocumentDataFetcher(ObjectMapper objectMapper, IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IDocumentService documentService,
            IGraphQLMessageService messageService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.documentService = Objects.requireNonNull(documentService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(MutationTypeProvider.INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, RenameDocumentInput.class);

        // @formatter:off
        return this.documentService.getDocument(input.getDocumentId())
                .map(Document::getProject)
                .map(Project::getId)
                .map(UUID::toString)
                .map(projectId -> this.editingContextEventProcessorRegistry.dispatchEvent(projectId, input))
                .orElse(Mono.empty())
                .defaultIfEmpty(new ErrorPayload(input.getId(), this.messageService.unexpectedError()))
                .toFuture();
        // @formatter:on
    }

}
