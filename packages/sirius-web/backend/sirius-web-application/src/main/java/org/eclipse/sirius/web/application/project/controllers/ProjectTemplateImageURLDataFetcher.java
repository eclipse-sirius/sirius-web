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

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.web.application.project.dto.ProjectTemplateDTO;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field ProjectTemplate#imageURL.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "ProjectTemplate", field = "imageURL")
public class ProjectTemplateImageURLDataFetcher implements IDataFetcherWithFieldCoordinates<String> {
    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        ProjectTemplateDTO projectTemplateDTO = environment.getSource();
        return URLConstants.IMAGE_BASE_PATH + projectTemplateDTO.imageURL();
    }
}
