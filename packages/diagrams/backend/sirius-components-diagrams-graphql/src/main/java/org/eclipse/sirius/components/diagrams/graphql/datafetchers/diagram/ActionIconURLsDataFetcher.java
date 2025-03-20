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

import java.util.List;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Action;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to concatenate the server image URL to the Action iconURL.
 *
 * @author arichard
 */
@QueryDataFetcher(type = "Action", field = "iconURLs")
public class ActionIconURLsDataFetcher implements IDataFetcherWithFieldCoordinates<List<String>> {

    @Override
    public List<String> get(DataFetchingEnvironment environment) throws Exception {
        Action action = environment.getSource();
        return action.iconURLs().stream().map(url -> URLConstants.IMAGE_BASE_PATH + url).toList();
    }
}
