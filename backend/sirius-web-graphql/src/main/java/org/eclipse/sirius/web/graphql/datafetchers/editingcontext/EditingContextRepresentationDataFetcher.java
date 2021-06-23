/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.graphql.schema.EditingContextTypeProvider;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@QueryDataFetcher(type = EditingContextTypeProvider.TYPE, field = EditingContextTypeProvider.REPRESENTATION_FIELD)
public class EditingContextRepresentationDataFetcher implements IDataFetcherWithFieldCoordinates<IRepresentation> {

    private final IRepresentationService representationService;

    private final Logger logger = LoggerFactory.getLogger(EditingContextRepresentationDataFetcher.class);

    public EditingContextRepresentationDataFetcher(IRepresentationService representationService) {
        this.representationService = Objects.requireNonNull(representationService);
    }

    @Override
    public IRepresentation get(DataFetchingEnvironment environment) throws Exception {
        UUID editingContextId = environment.getSource();
        String representationIdArgument = environment.getArgument(EditingContextTypeProvider.REPRESENTATION_ID_ARGUMENT);
        try {
            UUID representationId = UUID.fromString(representationIdArgument);

            // @formatter:off
            return this.representationService.getRepresentationDescriptorForProjectId(editingContextId, representationId)
                    .map(RepresentationDescriptor::getRepresentation)
                    .orElse(null);
            // @formatter:on
        } catch (IllegalArgumentException exception) {
            this.logger.error(exception.getMessage(), exception);
        }
        return null;
    }

}
