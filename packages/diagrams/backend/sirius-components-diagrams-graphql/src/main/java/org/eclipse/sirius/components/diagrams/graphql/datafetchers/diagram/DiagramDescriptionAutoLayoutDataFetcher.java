/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.graphql.datafetchers.diagram;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionInput;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionPayload;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The data fetcher used to retrieve the autolayout for a given diagram description.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "DiagramDescription", field = "autoLayout")
public class DiagramDescriptionAutoLayoutDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Boolean>> {

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public DiagramDescriptionAutoLayoutDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<Boolean> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);

        if (editingContextId != null && representationId != null) {
            GetRepresentationDescriptionInput input = new GetRepresentationDescriptionInput(UUID.randomUUID(), editingContextId, representationId);

            // @formatter:off
            return this.editingContextEventProcessorRegistry.dispatchEvent(input.editingContextId(), input)
                    .filter(GetRepresentationDescriptionPayload.class::isInstance)
                    .map(GetRepresentationDescriptionPayload.class::cast)
                    .map(payload -> Optional.ofNullable(payload.getRepresentationDescription())
                            .filter(DiagramDescription.class::isInstance)
                            .map(DiagramDescription.class::cast)
                            .map(DiagramDescription::isAutoLayout)
                            .orElse(false))
                    .toFuture();
            // @formatter:on
        }
        return Mono.just(false).toFuture();
    }
}
