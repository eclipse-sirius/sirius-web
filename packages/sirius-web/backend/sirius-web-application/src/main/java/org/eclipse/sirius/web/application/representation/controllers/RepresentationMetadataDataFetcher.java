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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.representations.IRepresentation;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.FieldCoordinates;

/**
 * Data fetcher for the field Representation#metadata.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Representation", field = "metadata")
public class RepresentationMetadataDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<RepresentationMetadata>> {
    private static final String METADATA_FIELD = "metadata";

    private final List<IRepresentationMetadataProvider> representationMetadataProviders;

    public RepresentationMetadataDataFetcher(List<IRepresentationMetadataProvider> representationMetadataProviders) {
        this.representationMetadataProviders = Objects.requireNonNull(representationMetadataProviders);
    }

    @Override
    public List<FieldCoordinates> getFieldCoordinates() {
        return List.of(
                FieldCoordinates.coordinates("Diagram", METADATA_FIELD),
                FieldCoordinates.coordinates("Form", METADATA_FIELD),
                FieldCoordinates.coordinates("FormDescriptionEditor", METADATA_FIELD),
                FieldCoordinates.coordinates("BarChart", METADATA_FIELD),
                FieldCoordinates.coordinates("PieChart", METADATA_FIELD),
                FieldCoordinates.coordinates("Tree", METADATA_FIELD),
                FieldCoordinates.coordinates("Selection", METADATA_FIELD),
                FieldCoordinates.coordinates("Validation", METADATA_FIELD),
                FieldCoordinates.coordinates("Gantt", METADATA_FIELD),
                FieldCoordinates.coordinates("Deck", METADATA_FIELD),
                FieldCoordinates.coordinates("Portal", METADATA_FIELD)
        );
    }

    @Override
    public DataFetcherResult<RepresentationMetadata> get(DataFetchingEnvironment environment) throws Exception {
        IRepresentation representation = environment.getSource();
        var metadata = this.representationMetadataProviders.stream()
                .flatMap(provider -> provider.getMetadata(representation.getId()).stream())
                .findFirst()
                .orElse(null);

        Map<String, Object> localContext = new HashMap<>(environment.getLocalContext());

        return DataFetcherResult.<RepresentationMetadata>newResult()
                .data(metadata)
                .localContext(localContext)
                .build();
    }
}
