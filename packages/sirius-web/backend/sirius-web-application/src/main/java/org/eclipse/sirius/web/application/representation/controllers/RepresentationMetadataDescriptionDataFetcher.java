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
package org.eclipse.sirius.web.application.representation.controllers;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionInput;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.representations.IRepresentationDescription;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Data fetcher for the field RepresentationMetadata#description.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "RepresentationMetadata", field = "description")
public class RepresentationMetadataDescriptionDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IRepresentationDescription>> {

    private final IEditingContextDispatcher editingContextDispatcher;

    public RepresentationMetadataDescriptionDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<IRepresentationDescription> get(DataFetchingEnvironment environment) throws Exception {
        CompletableFuture<IRepresentationDescription> result = Mono.<IRepresentationDescription>empty().toFuture();
        Map<String, Object> localContext = environment.getLocalContext();

        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        if (editingContextId != null && representationId != null) {
            GetRepresentationDescriptionInput input = new GetRepresentationDescriptionInput(UUID.randomUUID(), editingContextId, representationId);
            result = this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                    .filter(GetRepresentationDescriptionPayload.class::isInstance)
                    .map(GetRepresentationDescriptionPayload.class::cast)
                    .map(GetRepresentationDescriptionPayload::representationDescription)
                    .toFuture();
        }
        return result;
    }
}
