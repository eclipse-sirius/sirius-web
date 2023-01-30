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
package org.eclipse.sirius.components.trees.graphql.datafetchers.subscription;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.trees.Tree;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve the tree from its payload.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "TreeRefreshedEventPayload", field = "tree")
public class TreeRefreshedEventPayloadTreeDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<Tree>> {
    @Override
    public DataFetcherResult<Tree> get(DataFetchingEnvironment environment) throws Exception {
        TreeRefreshedEventPayload payload = environment.getSource();
        return DataFetcherResult.<Tree>newResult()
                .data(payload.getTree())
                .localContext(environment.getLocalContext())
                .build();
    }
}