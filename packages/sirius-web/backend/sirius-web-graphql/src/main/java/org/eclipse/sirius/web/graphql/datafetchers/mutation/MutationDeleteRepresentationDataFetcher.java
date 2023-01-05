/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.messages.IGraphQLMessageService;
import org.eclipse.sirius.web.graphql.schema.MutationTypeProvider;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The data fetcher used to delete a representation.
 * <p>
 * *It will be used to handle the following GraphQL field:*
 * </p>
 * **
 *
 * <pre>
 * type Mutation{
 *    deleteRepresentation(input:DeleteRepresentationInput!):DeleteRepresentationPayload!
 * }
 * </pre>
 *
 * @author lfasani
 */
@MutationDataFetcher(type = MutationTypeProvider.TYPE, field = MutationDeleteRepresentationDataFetcher.DELETE_REPRESENTATION_FIELD)
public class MutationDeleteRepresentationDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    public static final String DELETE_REPRESENTATION_FIELD = "deleteRepresentation";

    private final ObjectMapper objectMapper;

    private final IRepresentationService representationService;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IGraphQLMessageService messageService;

    public MutationDeleteRepresentationDataFetcher(ObjectMapper objectMapper, IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IRepresentationService representationService,
            IGraphQLMessageService messageService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.representationService = Objects.requireNonNull(representationService);
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(MutationTypeProvider.INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, DeleteRepresentationInput.class);
        // @formatter:off
        return new IDParser().parse(input.representationId())
                .flatMap(this.representationService::getRepresentation)
                .map(RepresentationDescriptor::getProjectId)
                .map(UUID::toString)
                .map(projectId -> this.editingContextEventProcessorRegistry.dispatchEvent(projectId, input))
                .orElse(Mono.empty())
                .defaultIfEmpty(new ErrorPayload(input.id(), this.messageService.unexpectedError()))
                .toFuture();
        // @formatter:on

    }
}
