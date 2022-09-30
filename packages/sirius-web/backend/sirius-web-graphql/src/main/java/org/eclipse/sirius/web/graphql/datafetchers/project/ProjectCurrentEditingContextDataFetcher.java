/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.project;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.web.graphql.schema.ProjectTypeProvider;
import org.eclipse.sirius.web.services.api.projects.Project;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the current editing context of a project.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Project {
 *   currentEditingContext: EditingContext!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = ProjectTypeProvider.TYPE, field = ProjectTypeProvider.CURRENT_EDITING_CONTEXT_FIELD)
public class ProjectCurrentEditingContextDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<String>> {

    @Override
    public DataFetcherResult<String> get(DataFetchingEnvironment environment) throws Exception {
        Project project = environment.getSource();
        String editingContextId = project.getId().toString();
        Map<String, Object> localContext = new HashMap<>();
        localContext.put(LocalContextConstants.EDITING_CONTEXT_ID, editingContextId);

        // @formatter:off
        return DataFetcherResult.<String>newResult()
                .data(editingContextId)
                .localContext(localContext)
                .build();
        // @formatter:on
    }

}
