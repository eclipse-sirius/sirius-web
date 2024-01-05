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
package org.eclipse.sirius.components.portals.graphql.datafetchers.query;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.portals.dto.RepresentationBreadcrumbsSuccessPayload;
import org.eclipse.sirius.components.collaborative.portals.dto.RepresentationBreadcumbsInput;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The data fetcher to get the breadcrumb leading from the project's root to a representation.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "RepresentationMetadata", field = "breadcrumbs")
public class RepresentationMetadataBreadcrumbsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<WorkbenchSelectionEntry>>> {
    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public RepresentationMetadataBreadcrumbsDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<WorkbenchSelectionEntry>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        var editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString);
        var representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString);
        RepresentationMetadata representationMetadata = environment.getSource();

        if (editingContextId.isPresent() && representationId.isPresent() && representationMetadata != null) {
            var input = new RepresentationBreadcumbsInput(UUID.randomUUID(), editingContextId.get(), representationId.get(), representationMetadata.getId());
            return this.editingContextEventProcessorRegistry.dispatchEvent(input.editingContextId(), input)
                    .filter(RepresentationBreadcrumbsSuccessPayload.class::isInstance)
                    .map(RepresentationBreadcrumbsSuccessPayload.class::cast)
                    .map(RepresentationBreadcrumbsSuccessPayload::path)
                    .toFuture();
        } else {
            return Mono.<List<WorkbenchSelectionEntry>>empty().toFuture();
        }
    }

}
