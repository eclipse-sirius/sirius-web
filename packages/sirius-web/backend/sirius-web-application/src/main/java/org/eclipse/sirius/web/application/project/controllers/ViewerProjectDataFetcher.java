/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.project.controllers;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Viewer#project.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "DefaultViewer", field = "project")
public class ViewerProjectDataFetcher implements IDataFetcherWithFieldCoordinates<ProjectDTO> {

    private static final String PROJECT_ID_ARGUMENT = "projectId";

    private final IProjectApplicationService projectApplicationService;

    public ViewerProjectDataFetcher(IProjectApplicationService projectApplicationService) {
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
    }

    @Override
    public ProjectDTO get(DataFetchingEnvironment environment) throws Exception {
        String projectId = environment.getArgument(PROJECT_ID_ARGUMENT);
        return new UUIDParser().parse(projectId).flatMap(this.projectApplicationService::findById).orElse(null);
    }
}
