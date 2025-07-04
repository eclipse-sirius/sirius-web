/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import java.util.Arrays;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.springframework.core.env.Environment;

import graphql.schema.DataFetchingEnvironment;

/**
 * DataFetcher to retrieve the disableFitView diagram property.
 *
 * @author frouene
 */
@QueryDataFetcher(type = "DiagramDescription", field = "disableFitView")
public class DiagramDescriptionDisabledFitViewDataFetcher implements IDataFetcherWithFieldCoordinates<Boolean> {

    private final Environment springEnvironment;

    public DiagramDescriptionDisabledFitViewDataFetcher(Environment environment) {
        this.springEnvironment = environment;
    }

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        return Arrays.asList(this.springEnvironment.getActiveProfiles()).contains("test");
    }
}
