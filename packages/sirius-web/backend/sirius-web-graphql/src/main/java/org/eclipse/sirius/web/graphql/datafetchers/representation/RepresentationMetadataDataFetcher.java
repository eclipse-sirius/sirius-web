/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.representations.IRepresentation;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.FieldCoordinates;

/**
 * The data fetcher used to retrieve the metadata from the representation.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Representation", field = "metadata")
public class RepresentationMetadataDataFetcher implements IDataFetcherWithFieldCoordinates<RepresentationMetadata> {

    private static final String METADATA_FIELD = "metadata"; //$NON-NLS-1$

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    public RepresentationMetadataDataFetcher(IRepresentationMetadataSearchService representationMetadataSearchService) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
    }

    @Override
    public List<FieldCoordinates> getFieldCoordinates() {
        // @formatter:off
        return List.of(
                FieldCoordinates.coordinates("Diagram", METADATA_FIELD), //$NON-NLS-1$
                FieldCoordinates.coordinates("Form", METADATA_FIELD), //$NON-NLS-1$
                FieldCoordinates.coordinates("FormDescriptionEditor", METADATA_FIELD), //$NON-NLS-1$
                FieldCoordinates.coordinates("BarChart", METADATA_FIELD), //$NON-NLS-1$
                FieldCoordinates.coordinates("PieChart", METADATA_FIELD), //$NON-NLS-1$
                FieldCoordinates.coordinates("Tree", METADATA_FIELD), //$NON-NLS-1$
                FieldCoordinates.coordinates("Selection", METADATA_FIELD), //$NON-NLS-1$
                FieldCoordinates.coordinates("Validation", METADATA_FIELD) //$NON-NLS-1$
        );
        // @formatter:on
    }

    @Override
    public RepresentationMetadata get(DataFetchingEnvironment environment) throws Exception {
        IRepresentation representation = environment.getSource();
        return this.representationMetadataSearchService.findByRepresentation(representation).orElse(null);
    }

}
