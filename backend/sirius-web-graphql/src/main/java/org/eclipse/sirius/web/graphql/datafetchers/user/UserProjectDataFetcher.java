/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.user;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.graphql.schema.ViewerTypeProvider;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve a project for a viewer.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Viewer {
 *   project(projectId: String!): Project
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = ViewerTypeProvider.USER_TYPE, field = ViewerTypeProvider.PROJECT_FIELD)
public class UserProjectDataFetcher implements IDataFetcherWithFieldCoordinates<Project> {

    private final IProjectService projectService;

    private final Logger logger = LoggerFactory.getLogger(UserProjectDataFetcher.class);

    public UserProjectDataFetcher(IProjectService projectService) {
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public Project get(DataFetchingEnvironment environment) throws Exception {
        String projectIdArgument = environment.getArgument(ViewerTypeProvider.PROJECT_ID_ARGUMENT);
        try {
            UUID projectId = UUID.fromString(projectIdArgument);
            return this.projectService.getProject(projectId).orElse(null);
        } catch (IllegalArgumentException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return null;
    }

}
