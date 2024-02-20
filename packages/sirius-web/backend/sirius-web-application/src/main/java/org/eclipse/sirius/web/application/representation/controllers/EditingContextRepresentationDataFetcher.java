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
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationApplicationService;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field EditingContext#representation.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "EditingContext", field = "representation")
public class EditingContextRepresentationDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<RepresentationMetadata>> {

    private static final String REPRESENTATION_ID_ARGUMENT = "representationId";

    private final IRepresentationApplicationService representationApplicationService;

    public EditingContextRepresentationDataFetcher(IRepresentationApplicationService representationApplicationService) {
        this.representationApplicationService = Objects.requireNonNull(representationApplicationService);
    }

    @Override
    public DataFetcherResult<RepresentationMetadata> get(DataFetchingEnvironment environment) throws Exception {
        String representationId = environment.getArgument(REPRESENTATION_ID_ARGUMENT);

        Map<String, Object> localContext = new HashMap<>(environment.getLocalContext());
        localContext.put(LocalContextConstants.REPRESENTATION_ID, representationId);

        var representationMetadata = this.representationApplicationService.findRepresentationMetadataById(representationId).orElse(null);

        return DataFetcherResult.<RepresentationMetadata>newResult()
                .data(representationMetadata)
                .localContext(localContext)
                .build();
    }
}
