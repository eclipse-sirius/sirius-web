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
package org.eclipse.sirius.web.graphql.datafetchers.mutation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.schema.MutationTypeProvider;
import org.eclipse.sirius.web.services.api.projects.CreateProjectInput;
import org.eclipse.sirius.web.services.api.projects.IProjectService;

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
@MutationDataFetcher(type = MutationTypeProvider.TYPE, field = MutationCreateProjectDataFetcher.CREATE_PROJECT_FIELD)
public class MutationCreateProjectDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    public static final String CREATE_PROJECT_FIELD = "createProject"; //$NON-NLS-1$

    private final ObjectMapper objectMapper;

    private final IProjectService projectService;

    public MutationCreateProjectDataFetcher(ObjectMapper objectMapper, IProjectService projectService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(MutationTypeProvider.INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, CreateProjectInput.class);
        return this.projectService.createProject(input);
    }

}
