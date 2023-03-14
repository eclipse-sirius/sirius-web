/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.springframework.beans.factory.annotation.Value;

import graphql.schema.DataFetchingEnvironment;

/**
 * Datafetcher to retrieve the debug diagram propertie.
 *
 * @author mcharfadi
 */
@QueryDataFetcher(type = "DiagramDescription", field = "debug")
public class DiagramDescriptionDebugDataFetcher implements IDataFetcherWithFieldCoordinates<Boolean> {

    private final boolean debug;

    public DiagramDescriptionDebugDataFetcher(@Value("${sirius.components.diagram.debug:false}") boolean debug) {
        this.debug = debug;
    }

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        return this.debug;
    }
}
