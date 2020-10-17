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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.graphql.schema.ViewerTypeProvider;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve all the projects accessible to a given viewer.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Viewer {
 *   projects: [Project!]!
 * }
 * </pre>
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = ViewerTypeProvider.USER_TYPE, field = ViewerTypeProvider.PROJECTS_FIELD)
public class UserProjectsDataFetcher implements IDataFetcherWithFieldCoordinates<List<Project>> {
    private final IProjectService projectService;

    public UserProjectsDataFetcher(IProjectService projectService) {
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public List<Project> get(DataFetchingEnvironment environment) throws Exception {
        return this.projectService.getProjects();
    }

}
