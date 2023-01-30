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
package org.eclipse.sirius.components.diagrams.graphql.datafetchers.subscription;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve a diagram from its payload.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "DiagramRefreshedEventPayload", field = "diagram")
public class DiagramRefreshedEventPayloadDiagramDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<Diagram>> {

    @Override
    public DataFetcherResult<Diagram> get(DataFetchingEnvironment environment) throws Exception {
        DiagramRefreshedEventPayload payload = environment.getSource();
        // @formatter:off
        return DataFetcherResult.<Diagram>newResult()
                .data(payload.getDiagram())
                .localContext(environment.getLocalContext())
                .build();
        // @formatter:on
    }

}
