/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramToolbar;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Datafetcher to retrieve the toolbar diagram property.
 *
 * @author tgiraudet
 */
@QueryDataFetcher(type = "DiagramDescription", field = "toolbar")
public class DiagramDescriptionToolbarDataFetcher implements IDataFetcherWithFieldCoordinates<DiagramToolbar> {

    @Override
    public DiagramToolbar get(DataFetchingEnvironment environment) throws Exception {
        return new DiagramToolbar();
    }
}
