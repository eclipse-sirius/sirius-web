/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.services.api.images.CustomImageMetadata;
import org.eclipse.sirius.web.services.api.images.IProjectCustomImageMetadataSearchService;
import org.eclipse.sirius.web.services.api.projects.Project;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the available custom images for a project.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Project {
 *   images: [Image!]
 * }
 * </pre>
 * <p>
 * Note that this only returns the images' metadata (which matches the GraphQL Schema), and does not include the image's
 * content.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "Project", field = "images")
public class ProjectImagesDataFetcher implements IDataFetcherWithFieldCoordinates<List<CustomImageMetadata>> {
    private final IProjectCustomImageMetadataSearchService customImageMetadataSearchService;

    public ProjectImagesDataFetcher(IProjectCustomImageMetadataSearchService projectCustomImageMetadataSearchService) {
        this.customImageMetadataSearchService = Objects.requireNonNull(projectCustomImageMetadataSearchService);
    }

    @Override
    public List<CustomImageMetadata> get(DataFetchingEnvironment environment) throws Exception {
        Project project = environment.getSource();
        String editingContextId = project.getId().toString();
        return this.customImageMetadataSearchService.getProjectImages(editingContextId);
    }

}
