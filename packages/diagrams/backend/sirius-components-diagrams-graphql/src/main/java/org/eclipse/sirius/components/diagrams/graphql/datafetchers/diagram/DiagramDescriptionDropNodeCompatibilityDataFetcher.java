/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodeCompatibilityEntry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetDropNodeCompatibilitySuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetDropNodeCompatibiliyInput;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The data fetcher for the {@code DiagramDescription { dropNodeCompatibility }} query.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "DiagramDescription", field = "dropNodeCompatibility")
public class DiagramDescriptionDropNodeCompatibilityDataFetcher  implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<DropNodeCompatibilityEntry>>> {
    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public DiagramDescriptionDropNodeCompatibilityDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<DropNodeCompatibilityEntry>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        if (editingContextId != null && representationId != null) {
            GetDropNodeCompatibiliyInput input = new GetDropNodeCompatibiliyInput(UUID.randomUUID(), editingContextId, representationId);

            return this.editingContextEventProcessorRegistry.dispatchEvent(input.editingContextId(), input)
                    .filter(GetDropNodeCompatibilitySuccessPayload.class::isInstance)
                    .map(GetDropNodeCompatibilitySuccessPayload.class::cast)
                    .map(GetDropNodeCompatibilitySuccessPayload::entries)
                    .toFuture();
        }
        return Mono.<List<DropNodeCompatibilityEntry>> empty().toFuture();
    }

}
