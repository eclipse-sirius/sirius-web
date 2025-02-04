/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.editingcontext.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextApplicationService;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Project#currentEditingContext.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Project", field = "currentEditingContext")
public class ProjectCurrentEditingContextDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<String>> {

    private final IEditingContextApplicationService editingContextApplicationService;

    public ProjectCurrentEditingContextDataFetcher(IEditingContextApplicationService editingContextApplicationService) {
        this.editingContextApplicationService = Objects.requireNonNull(editingContextApplicationService);
    }

    @Override
    public DataFetcherResult<String> get(DataFetchingEnvironment environment) throws Exception {
        ProjectDTO project = environment.getSource();
        String editingContextId = this.editingContextApplicationService.getCurrentEditingContextId(project.id());

        Map<String, Object> localContext = new HashMap<>();
        localContext.put(LocalContextConstants.EDITING_CONTEXT_ID, editingContextId);

        return DataFetcherResult.<String>newResult()
                .data(editingContextId)
                .localContext(localContext)
                .build();
    }
}
