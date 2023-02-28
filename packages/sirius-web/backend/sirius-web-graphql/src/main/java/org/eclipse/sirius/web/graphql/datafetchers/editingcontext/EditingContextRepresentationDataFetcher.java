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
package org.eclipse.sirius.web.graphql.datafetchers.editingcontext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve a representation of an editing context.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   representation(representationId: ID!): Representation
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "EditingContext", field = "representation")
public class EditingContextRepresentationDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<RepresentationMetadata>> {

    private static final String REPRESENTATION_ID_ARGUMENT = "representationId";

    private final IRepresentationService representationService;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextRepresentationDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IRepresentationService representationService) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.representationService = Objects.requireNonNull(representationService);
    }

    @Override
    public DataFetcherResult<RepresentationMetadata> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String representationId = environment.getArgument(REPRESENTATION_ID_ARGUMENT);

        Map<String, Object> localContext = new HashMap<>(environment.getLocalContext());
        localContext.put(LocalContextConstants.REPRESENTATION_ID, representationId);

        // Search among the active representations first. They are already loaded in memory and include transient
        // representations.
        // @formatter:off
        var representationMetadata = this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                    .flatMap(editingContextEventProcessor -> editingContextEventProcessor.getRepresentationEventProcessors().stream())
                    .filter(editingContextEventProcessor -> editingContextEventProcessor.getRepresentation().getId().equals(representationId))
                    .map(IRepresentationEventProcessor::getRepresentation)
                    .filter(ISemanticRepresentation.class::isInstance)
                    .map(ISemanticRepresentation.class::cast)
                    .map((ISemanticRepresentation representation) -> {
                        return new RepresentationMetadata(representation.getId(),
                                                          representation.getKind(),
                                                          representation.getLabel(),
                                                          representation.getDescriptionId(),
                                                          representation.getTargetObjectId());
                    })
                    .findFirst();
        // @formatter:on

        // @formatter:off
        if (representationMetadata.isEmpty()) {
            representationMetadata = this.representationService.getRepresentationDescriptorForProjectId(editingContextId, representationId)
                    .map(RepresentationDescriptor::getRepresentation)
                    .map(this::toRepresentationMetadata);
        }

        return DataFetcherResult.<RepresentationMetadata>newResult()
                .data(representationMetadata.orElse(null))
                .localContext(localContext)
                .build();
        // @formatter:on
    }

    private RepresentationMetadata toRepresentationMetadata(IRepresentation representation) {
        // @formatter:off
        String targetObjectId = Optional.of(representation)
                .filter(ISemanticRepresentation.class::isInstance)
                .map(ISemanticRepresentation.class::cast)
                .map(ISemanticRepresentation::getTargetObjectId)
                .orElse(null);
        // @formatter:on
        return new RepresentationMetadata(representation.getId(), representation.getKind(), representation.getLabel(), representation.getDescriptionId(), targetObjectId);
    }

}
