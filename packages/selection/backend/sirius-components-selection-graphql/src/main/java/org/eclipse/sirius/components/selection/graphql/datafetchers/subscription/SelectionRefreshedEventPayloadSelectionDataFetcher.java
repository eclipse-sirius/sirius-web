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
package org.eclipse.sirius.components.selection.graphql.datafetchers.subscription;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionRefreshedEventPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.selection.Selection;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve the selection from its payload.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "SelectionRefreshedEventPayload", field = "selection")
public class SelectionRefreshedEventPayloadSelectionDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<Selection>> {
    @Override
    public DataFetcherResult<Selection> get(DataFetchingEnvironment environment) throws Exception {
        SelectionRefreshedEventPayload payload = environment.getSource();
        return DataFetcherResult.<Selection>newResult()
                .data(payload.getSelection())
                .localContext(environment.getLocalContext())
                .build();
    }
}
