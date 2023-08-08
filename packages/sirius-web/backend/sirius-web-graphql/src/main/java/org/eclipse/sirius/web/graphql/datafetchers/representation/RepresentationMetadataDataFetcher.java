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
package org.eclipse.sirius.web.graphql.datafetchers.representation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.representations.IRepresentation;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.FieldCoordinates;

/**
 * The data fetcher used to retrieve the metadata from the representation.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Representation", field = "metadata")
public class RepresentationMetadataDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<RepresentationMetadata>> {

    private static final String METADATA_FIELD = "metadata";

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    public RepresentationMetadataDataFetcher(IRepresentationMetadataSearchService representationMetadataSearchService) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
    }

    @Override
    public List<FieldCoordinates> getFieldCoordinates() {
        // @formatter:off
        return List.of(
                FieldCoordinates.coordinates("Diagram", METADATA_FIELD),
                FieldCoordinates.coordinates("Form", METADATA_FIELD),
                FieldCoordinates.coordinates("FormDescriptionEditor", METADATA_FIELD),
                FieldCoordinates.coordinates("BarChart", METADATA_FIELD),
                FieldCoordinates.coordinates("PieChart", METADATA_FIELD),
                FieldCoordinates.coordinates("Tree", METADATA_FIELD),
                FieldCoordinates.coordinates("Selection", METADATA_FIELD),
                FieldCoordinates.coordinates("Validation", METADATA_FIELD),
                FieldCoordinates.coordinates("Gantt", METADATA_FIELD)
        );
        // @formatter:on
    }

    @Override
    public DataFetcherResult<RepresentationMetadata> get(DataFetchingEnvironment environment) throws Exception {
        IRepresentation representation = environment.getSource();
        var metadata = this.representationMetadataSearchService.findByRepresentation(representation).orElse(null);

        Map<String, Object> localContext = new HashMap<>(environment.getLocalContext());

        // @formatter:off
        return DataFetcherResult.<RepresentationMetadata>newResult()
                .data(metadata)
                .localContext(localContext)
                .build();
        // @formatter:on
    }

}
