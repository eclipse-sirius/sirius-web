/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field RepresentationMetadata#isViewBased.
 *
 * @author mcharfadi
 */
@QueryDataFetcher(type = "RepresentationMetadata", field = "isViewBased")
public class RepresentationMetadataIsVewBasedDataFetcher implements IDataFetcherWithFieldCoordinates<Boolean> {

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IEditingContextSearchService editingContextSearchService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IURLParser urlParser;

    private final IObjectService objectService;

    public RepresentationMetadataIsVewBasedDataFetcher(IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IEditingContextSearchService editingContextSearchService, IRepresentationMetadataSearchService representationMetadataSearchService, IURLParser urlParser, IObjectService objectService) {
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);

        if (editingContextId != null && representationId != null) {
            var editingContext = this.editingContextSearchService.findById(editingContextId);
            var representationMetadata = representationMetadataSearchService.findMetadataById(UUID.fromString(representationId));

            if (representationMetadata.isPresent() && editingContext.isPresent()) {
                var sourceId = getSourceId(representationMetadata.get().getDescriptionId());
                var sourceElementId = getSourceElementId(representationMetadata.get().getDescriptionId());

                if (sourceId.isPresent() && sourceElementId.isPresent()) {
                    return this.viewRepresentationDescriptionSearchService.findViewsBySourceId(editingContext.get(), sourceId.get()).stream()
                            .flatMap(view -> view.getDescriptions().stream())
                            .anyMatch(description -> objectService.getId(description).equals(sourceElementId.get()));
                }
            }

        }

        return false;
    }

    private Optional<String> getSourceId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }
}