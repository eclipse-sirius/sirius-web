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
package org.eclipse.sirius.web.application.project.controllers;

import java.util.List;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.project.dto.ProjectSettingsDTO;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Project#projectSettings.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "Project", field = "projectSettings")
public class ProjectSettingsProjectSettingsDataFetcher implements IDataFetcherWithFieldCoordinates<ProjectSettingsDTO> {

    @Override
    public ProjectSettingsDTO get(DataFetchingEnvironment environment) throws Exception {
        // If we change `nothing` by `imageEnabled`, the tab to add an image to projects is back.
        return new ProjectSettingsDTO(List.of("nothing"));
    }
}
