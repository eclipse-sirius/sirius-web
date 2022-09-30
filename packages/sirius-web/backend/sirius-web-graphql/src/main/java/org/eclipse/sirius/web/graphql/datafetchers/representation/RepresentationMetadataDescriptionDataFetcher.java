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

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.representations.IRepresentationDescription;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the description from the representation metadata.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "RepresentationMetadata", field = "description")
public class RepresentationMetadataDescriptionDataFetcher implements IDataFetcherWithFieldCoordinates<IRepresentationDescription> {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public RepresentationMetadataDescriptionDataFetcher(IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public IRepresentationDescription get(DataFetchingEnvironment environment) throws Exception {
        RepresentationMetadata representationMetadata = environment.getSource();
        return this.representationDescriptionSearchService.findById(null, representationMetadata.getDescriptionId()).orElse(null);
    }

}
