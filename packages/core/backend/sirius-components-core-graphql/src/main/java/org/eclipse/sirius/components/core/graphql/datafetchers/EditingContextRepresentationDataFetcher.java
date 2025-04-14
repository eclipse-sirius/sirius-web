/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.core.graphql.datafetchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.core.graphql.dto.RepresentationMetadataDTO;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field EditingContext#representation.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "EditingContext", field = "representation")
public class EditingContextRepresentationDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<RepresentationMetadataDTO>> {

    private static final String REPRESENTATION_ID_ARGUMENT = "representationId";

    private final List<IRepresentationMetadataProvider> representationMetadataProviders;

    private final List<IRepresentationImageProvider> representationImageProviders;

    public EditingContextRepresentationDataFetcher(List<IRepresentationMetadataProvider> representationMetadataProviders, List<IRepresentationImageProvider> representationImageProviders) {
        this.representationMetadataProviders = Objects.requireNonNull(representationMetadataProviders);
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
    }

    @Override
    public DataFetcherResult<RepresentationMetadataDTO> get(DataFetchingEnvironment environment) throws Exception {
        String representationId = environment.getArgument(REPRESENTATION_ID_ARGUMENT);

        Map<String, Object> localContext = new HashMap<>(environment.getLocalContext());
        localContext.put(LocalContextConstants.REPRESENTATION_ID, representationId);

        var representationMetadata = this.representationMetadataProviders.stream()
                .flatMap(provider -> provider.getMetadata(representationId).stream())
                .map(this::toDTO)
                .findFirst()
                .orElse(null);

        return DataFetcherResult.<RepresentationMetadataDTO>newResult()
                .data(representationMetadata)
                .localContext(localContext)
                .build();
    }

    private RepresentationMetadataDTO toDTO(RepresentationMetadata representationMetadata) {
        List<String> icons = new ArrayList<>();
        if (representationMetadata.iconURLs().isEmpty()) {
            icons = this.representationImageProviders.stream()
                    .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.kind()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } else {
            icons = representationMetadata.iconURLs();
        }

        return new RepresentationMetadataDTO(representationMetadata.id(), representationMetadata.label(), representationMetadata.kind(), representationMetadata.descriptionId(), icons);
    }
}
