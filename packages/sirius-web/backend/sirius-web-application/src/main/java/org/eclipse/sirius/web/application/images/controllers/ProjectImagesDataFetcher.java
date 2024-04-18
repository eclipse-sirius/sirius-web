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
package org.eclipse.sirius.web.application.images.controllers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.images.dto.ImageMetadata;
import org.eclipse.sirius.web.application.images.services.api.IImageApplicationService;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.springframework.data.domain.PageRequest;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Project#images.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Project", field = "images")
public class ProjectImagesDataFetcher implements IDataFetcherWithFieldCoordinates<List<ImageMetadata>> {

    private final IImageApplicationService imageApplicationService;

    public ProjectImagesDataFetcher(IImageApplicationService imageApplicationService) {
        this.imageApplicationService = Objects.requireNonNull(imageApplicationService);
    }

    @Override
    public List<ImageMetadata> get(DataFetchingEnvironment environment) throws Exception {
        ProjectDTO project = environment.getSource();

        var pageable = PageRequest.of(0, 100);
        return this.imageApplicationService.findAll(project.id(), pageable).toList();
    }
}
