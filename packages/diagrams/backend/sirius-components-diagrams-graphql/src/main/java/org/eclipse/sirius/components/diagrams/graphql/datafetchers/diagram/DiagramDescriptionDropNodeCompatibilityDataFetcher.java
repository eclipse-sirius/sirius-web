/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodeCompatibilityEntry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetDropNodeCompatibilitySuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetDropNodeCompatibiliyInput;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
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

    private final IExceptionWrapper exceptionWrapper;
    private final IEditingContextDispatcher editingContextDispatcher;

    public DiagramDescriptionDropNodeCompatibilityDataFetcher(IExceptionWrapper exceptionWrapper, IEditingContextDispatcher editingContextDispatcher) {
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<DropNodeCompatibilityEntry>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        var editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString);
        var representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString);

        if (editingContextId.isPresent() && representationId.isPresent()) {
            GetDropNodeCompatibiliyInput input = new GetDropNodeCompatibiliyInput(UUID.randomUUID(), editingContextId.get(), representationId.get());
            return this.exceptionWrapper.wrapMono(() -> this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input), input)
                    .filter(GetDropNodeCompatibilitySuccessPayload.class::isInstance)
                    .map(GetDropNodeCompatibilitySuccessPayload.class::cast)
                    .map(GetDropNodeCompatibilitySuccessPayload::entries)
                    .toFuture();
        }
        return Mono.<List<DropNodeCompatibilityEntry>> empty().toFuture();
    }

}
