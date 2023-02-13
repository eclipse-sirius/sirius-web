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
package org.eclipse.sirius.components.diagrams.graphql.datafetchers.diagram;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the {@link DiagramDescription} for a given diagram.
 *
 * <pre>
 * type Diagram {
 *   autoLayout: boolean!
 * }
 * </pre>
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "Diagram", field = "autoLayout")
public class DiagramAutoLayoutDataFetcher implements IDataFetcherWithFieldCoordinates<Boolean> {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public DiagramAutoLayoutDataFetcher(IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        Diagram diagram = environment.getSource();
        // @formatter:off
        return this.representationDescriptionSearchService
                   .findById(null, diagram.getDescriptionId()) // It's OK to pass null for now, the whole class will disappear soon.
                   .filter(DiagramDescription.class::isInstance)
                   .map(DiagramDescription.class::cast)
                   .map(DiagramDescription::isAutoLayout)
                   .orElse(false);
        // @formatter:on
    }
}
