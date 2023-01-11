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
package org.eclipse.sirius.web.graphql.datafetchers.project;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.web.services.api.projects.ProjectTemplate;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to concatenate the server image URL to the project template image path.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "ProjectTemplate", field = "imageURL")
public class ProjectTemplateImageURLDataFetcher implements IDataFetcherWithFieldCoordinates<String> {

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        ProjectTemplate projectTemplate = environment.getSource();
        return URLConstants.IMAGE_BASE_PATH + projectTemplate.getImageURL();
    }
}
