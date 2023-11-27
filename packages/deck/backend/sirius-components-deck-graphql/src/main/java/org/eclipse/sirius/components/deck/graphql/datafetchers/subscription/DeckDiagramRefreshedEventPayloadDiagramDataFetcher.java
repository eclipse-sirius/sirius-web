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
package org.eclipse.sirius.components.deck.graphql.datafetchers.subscription;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.deck.dto.DeckRefreshedEventPayload;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve a deck diagram from its payload.
 *
 * @author fbarbin
 */
@QueryDataFetcher(type = "DeckRefreshedEventPayload", field = "deck")
public class DeckDiagramRefreshedEventPayloadDiagramDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<Deck>> {
    @Override
    public DataFetcherResult<Deck> get(DataFetchingEnvironment environment) throws Exception {
        DeckRefreshedEventPayload payload = environment.getSource();
        return DataFetcherResult.<Deck>newResult()
                .data(payload.deck())
                .localContext(environment.getLocalContext())
                .build();
    }
}
