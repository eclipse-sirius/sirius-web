/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.web.view.fork.dto.GetRepresentationMetadataViewBasedInput;
import org.eclipse.sirius.web.view.fork.dto.GetRepresentationMetadataViewBasedPayload;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Data fetcher for the field RepresentationMetadata#isViewBased.
 *
 * @author mcharfadi
 */
@QueryDataFetcher(type = "RepresentationMetadata", field = "isViewBased")
public class RepresentationMetadataIsVewBasedDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Boolean>> {

    private final IEditingContextDispatcher editingContextDispatcher;

    public RepresentationMetadataIsVewBasedDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<Boolean> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);

        if (editingContextId != null && representationId != null) {
            var input = new GetRepresentationMetadataViewBasedInput(UUID.randomUUID(), editingContextId, representationId);

            return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                    .filter(GetRepresentationMetadataViewBasedPayload.class::isInstance)
                    .map(GetRepresentationMetadataViewBasedPayload.class::cast)
                    .map(GetRepresentationMetadataViewBasedPayload::viewBased)
                    .toFuture();

        }

        return Mono.<Boolean>empty().toFuture();
    }

}
