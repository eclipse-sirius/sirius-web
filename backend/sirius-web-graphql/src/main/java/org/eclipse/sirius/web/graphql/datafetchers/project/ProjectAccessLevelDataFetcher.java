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
package org.eclipse.sirius.web.graphql.datafetchers.project;

import java.security.Principal;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.graphql.datafetchers.IDataFetchingEnvironmentService;
import org.eclipse.sirius.web.graphql.schema.ProjectTypeProvider;
import org.eclipse.sirius.web.services.api.projects.AccessLevel;
import org.eclipse.sirius.web.services.api.projects.IProjectAccessPolicy;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the current user's access level to a project.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Project {
 *   accessLevel: AccessLevel!
 * }
 * </pre>
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = ProjectTypeProvider.TYPE, field = ProjectTypeProvider.ACCESS_LEVEL_FIELD)
public class ProjectAccessLevelDataFetcher implements IDataFetcherWithFieldCoordinates<AccessLevel> {

    private final IDataFetchingEnvironmentService dataFetchingEnvironmentService;

    private final IProjectAccessPolicy projectAccessPolicy;

    public ProjectAccessLevelDataFetcher(IDataFetchingEnvironmentService dataFetchingEnvironmentService, IProjectAccessPolicy projectAccessPolicy) {
        this.dataFetchingEnvironmentService = Objects.requireNonNull(dataFetchingEnvironmentService);
        this.projectAccessPolicy = Objects.requireNonNull(projectAccessPolicy);
    }

    @Override
    public AccessLevel get(DataFetchingEnvironment environment) throws Exception {
        Project project = environment.getSource();
        var optionalUsername = this.dataFetchingEnvironmentService.getPrincipal(environment).map(Principal::getName);
        if (optionalUsername.isPresent()) {
            String username = optionalUsername.get();
            return this.projectAccessPolicy.getAccessLevel(username, project.getId()).orElse(null);
        }
        return AccessLevel.READ;
    }
}
