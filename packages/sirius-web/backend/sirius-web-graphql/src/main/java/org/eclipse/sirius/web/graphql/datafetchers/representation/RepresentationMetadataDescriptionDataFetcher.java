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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.forms.PropertiesEventProcessorFactory;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the description from the representation metadata.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "RepresentationMetadata", field = "description")
public class RepresentationMetadataDescriptionDataFetcher implements IDataFetcherWithFieldCoordinates<IRepresentationDescription> {

    // @formatter:off
    private static final FormDescription FAKE_DETAILS_DESCRIPTION = FormDescription.newFormDescription(PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
            .label(PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
            .idProvider(new GetOrCreateRandomIdProvider())
            .labelProvider(variableManager -> PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
            .targetObjectIdProvider(variableManager -> PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
            .canCreatePredicate(variableManager -> true)
            .groupDescriptions(List.of())
            .pageDescriptions(List.of())
            .build();
    // @formatter:on

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public RepresentationMetadataDescriptionDataFetcher(IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public IRepresentationDescription get(DataFetchingEnvironment environment) throws Exception {
        RepresentationMetadata representationMetadata = environment.getSource();
        if (Objects.equals(PropertiesEventProcessorFactory.DETAILS_VIEW_ID, representationMetadata.getDescriptionId())) {
            /*
             * The FormDescription used for the details view can not be found by
             * IRepresentationDescriptionSearchService, but we can get away by returning a fake one with the same id as
             * no GraphQL query actually needs to see its content. We need to return *something* with the correct id
             * only to allow GraphQL resolution to continue on queries like "completionProposals" defined on
             * FormDescription.
             */
            return FAKE_DETAILS_DESCRIPTION;
        }
        return this.representationDescriptionSearchService.findById(null, representationMetadata.getDescriptionId()).orElse(null);
    }

}
