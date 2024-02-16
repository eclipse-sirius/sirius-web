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
package org.eclipse.sirius.web.application.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#deleteProject.
 *
 * @author sbegaudeau
 */
@MutationDataFetcher(type = "Mutation", field = "deleteProject")
public class MutationDeleteProjectDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IProjectApplicationService projectApplicationService;

    public MutationDeleteProjectDataFetcher(ObjectMapper objectMapper, IProjectApplicationService projectApplicationService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, DeleteProjectInput.class);
        return this.projectApplicationService.deleteProject(input);
    }
}
