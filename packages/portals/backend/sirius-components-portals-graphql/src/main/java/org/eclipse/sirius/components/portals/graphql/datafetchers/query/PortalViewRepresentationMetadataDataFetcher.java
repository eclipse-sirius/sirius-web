/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.portals.graphql.datafetchers.query;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.portals.PortalView;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher to get the (current) title for a PortalView from the corresponding representation.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "PortalView", field = "representationMetadata")
public class PortalViewRepresentationMetadataDataFetcher implements IDataFetcherWithFieldCoordinates<RepresentationMetadata> {
    private final List<IRepresentationMetadataProvider> representationMetadataProviders;

    public PortalViewRepresentationMetadataDataFetcher(List<IRepresentationMetadataProvider> representationMetadataProviders) {
        this.representationMetadataProviders = Objects.requireNonNull(representationMetadataProviders);
    }

    @Override
    public RepresentationMetadata get(DataFetchingEnvironment environment) throws Exception {
        PortalView portalView = environment.getSource();
        return this.representationMetadataProviders.stream()
                .flatMap(provider -> provider.getMetadata(portalView.getRepresentationId()).stream())
                .findFirst().orElse(null);
    }
}
