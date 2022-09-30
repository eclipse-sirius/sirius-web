/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.schema.ViewerTypeProvider;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;

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

    public UserProjectDataFetcher(IProjectService projectService) {
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public Project get(DataFetchingEnvironment environment) throws Exception {
        String projectIdArgument = environment.getArgument(ViewerTypeProvider.PROJECT_ID_ARGUMENT);
        return new IDParser().parse(projectIdArgument).flatMap(this.projectService::getProject).orElse(null);
    }

}
