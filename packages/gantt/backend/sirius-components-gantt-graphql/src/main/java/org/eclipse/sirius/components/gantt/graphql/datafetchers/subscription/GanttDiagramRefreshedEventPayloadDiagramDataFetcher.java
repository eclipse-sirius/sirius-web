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
package org.eclipse.sirius.components.gantt.graphql.datafetchers.subscription;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.gantt.dto.GanttRefreshedEventPayload;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve a gantt diagram from its payload.
 *
 * @author lfasani
 */
@QueryDataFetcher(type = "DiagramRefreshedEventPayload", field = "gantt")
public class GanttDiagramRefreshedEventPayloadDiagramDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<Gantt>> {
    @Override
    public DataFetcherResult<Gantt> get(DataFetchingEnvironment environment) throws Exception {
        GanttRefreshedEventPayload payload = environment.getSource();
        return DataFetcherResult.<Gantt>newResult()
                .data(payload.gantt())
                .localContext(environment.getLocalContext())
                .build();
    }
}
