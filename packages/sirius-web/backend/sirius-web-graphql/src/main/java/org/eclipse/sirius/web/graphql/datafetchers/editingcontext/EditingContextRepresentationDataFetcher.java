/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.editingcontext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve a representation of an editing context.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   representation(representationId: ID!): Representation
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "EditingContext", field = "representation")
public class EditingContextRepresentationDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<RepresentationMetadata>> {

    private static final String REPRESENTATION_ID_ARGUMENT = "representationId";

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    public EditingContextRepresentationDataFetcher(IRepresentationMetadataSearchService representationMetadataSearchService) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
    }

    @Override
    public DataFetcherResult<RepresentationMetadata> get(DataFetchingEnvironment environment) throws Exception {
        String representationId = environment.getArgument(REPRESENTATION_ID_ARGUMENT);

        Map<String, Object> localContext = new HashMap<>(environment.getLocalContext());
        localContext.put(LocalContextConstants.REPRESENTATION_ID, representationId);
        var representationMetadata = this.representationMetadataSearchService.findByRepresentationId(representationId);

        return DataFetcherResult.<RepresentationMetadata>newResult()
                .data(representationMetadata.orElse(null))
                .localContext(localContext)
                .build();
    }
}
