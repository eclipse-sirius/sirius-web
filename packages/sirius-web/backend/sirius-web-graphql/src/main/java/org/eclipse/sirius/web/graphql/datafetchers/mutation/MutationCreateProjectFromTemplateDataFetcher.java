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
package org.eclipse.sirius.web.graphql.datafetchers.mutation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.web.services.api.projects.CreateProjectFromTemplateInput;
import org.eclipse.sirius.web.services.api.projects.IProjectService;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to create a project from a template.
 *
 * @author pcdavid
 */
@MutationDataFetcher(type = "Mutation", field = "createProjectFromTemplate")
public class MutationCreateProjectFromTemplateDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IExceptionWrapper exceptionWrapper;

    private final IProjectService projectService;

    public MutationCreateProjectFromTemplateDataFetcher(ObjectMapper objectMapper, IExceptionWrapper exceptionWrapper, IProjectService projectService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, CreateProjectFromTemplateInput.class);

        return this.exceptionWrapper.wrap(() -> this.projectService.createProject(input), input);
    }

}
