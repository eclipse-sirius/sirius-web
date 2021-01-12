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
package org.eclipse.sirius.web.graphql.datafetchers.mutation;

import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLMutationTypes;
import org.eclipse.sirius.web.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.graphql.datafetchers.IDataFetchingEnvironmentService;
import org.eclipse.sirius.web.graphql.schema.MutationTypeProvider;
import org.eclipse.sirius.web.services.api.projects.CreateProjectInput;
import org.eclipse.sirius.web.services.api.projects.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to create a project.
 * <p>
 * It will be used to handle the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Mutation {
 *   createProject(input: CreateProjectInput!): CreateProjectPayload!
 * }
 * </pre>
 *
 * @author hmarchadour
 */
// @formatter:off
@GraphQLMutationTypes(
    input = CreateProjectInput.class,
    payloads = {
        CreateProjectSuccessPayload.class
    }
)
@MutationDataFetcher(type = MutationTypeProvider.TYPE, field = MutationCreateProjectDataFetcher.CREATE_PROJECT_FIELD)
// @formatter:on
public class MutationCreateProjectDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    public static final String CREATE_PROJECT_FIELD = "createProject"; //$NON-NLS-1$

    private final IDataFetchingEnvironmentService dataFetchingEnvironmentService;

    private final IProjectService projectService;

    public MutationCreateProjectDataFetcher(IDataFetchingEnvironmentService dataFetchingEnvironmentService, IProjectService projectService) {
        this.dataFetchingEnvironmentService = Objects.requireNonNull(dataFetchingEnvironmentService);
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        var input = this.dataFetchingEnvironmentService.getInput(environment, CreateProjectInput.class);
        return this.projectService.createProject(input);
    }

}
