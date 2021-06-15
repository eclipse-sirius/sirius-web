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
package org.eclipse.sirius.web.graphql.datafetchers.diagram;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.tools.ToolSection;
import org.eclipse.sirius.web.graphql.schema.DiagramTypesProvider;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve tool sections relevant to a given diagram.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Diagram {
 *   toolSections(diagramId: ID!): [ToolSection!]!
 * }
 * </pre>
 *
 * @author hmarchadour
 */
@QueryDataFetcher(type = DiagramTypesProvider.DIAGRAM_TYPE, field = DiagramTypesProvider.TOOL_SECTIONS_FIELD)
public class DiagramToolsDataFetcher implements IDataFetcherWithFieldCoordinates<List<ToolSection>> {

    private final IToolService toolService;

    public DiagramToolsDataFetcher(IToolService toolService) {
        this.toolService = Objects.requireNonNull(toolService);
    }

    @Override
    public List<ToolSection> get(DataFetchingEnvironment environment) throws Exception {
        Diagram diagram = environment.getSource();
        return this.toolService.getToolSections(diagram);
    }
}
