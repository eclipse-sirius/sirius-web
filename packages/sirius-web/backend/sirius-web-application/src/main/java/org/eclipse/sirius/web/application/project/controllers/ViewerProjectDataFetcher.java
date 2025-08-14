/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.SiriusWebLocalContextConstants;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Viewer#project.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Viewer", field = "project")
public class ViewerProjectDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<ProjectDTO>> {

    private static final String PROJECT_ID_ARGUMENT = "projectId";

    private final List<ICapabilityVoter> capabilityVoters;

    private final IProjectApplicationService projectApplicationService;

    public ViewerProjectDataFetcher(List<ICapabilityVoter> capabilityVoters, IProjectApplicationService projectApplicationService) {
        this.capabilityVoters = Objects.requireNonNull(capabilityVoters);
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
    }

    @Override
    public DataFetcherResult<ProjectDTO> get(DataFetchingEnvironment environment) throws Exception {
        String projectId = environment.getArgument(PROJECT_ID_ARGUMENT);
        var optionalProject = this.projectApplicationService.findById(projectId);

        var hasCapability = this.capabilityVoters.stream().allMatch(voter -> voter.vote(SiriusWebCapabilities.PROJECT, projectId, SiriusWebCapabilities.Project.VIEW) == CapabilityVote.GRANTED);
        if (!hasCapability || optionalProject.isEmpty()) {
            return null;
        }

        var project = optionalProject.get();

        Map<String, Object> localContext = new HashMap<>();
        localContext.put(SiriusWebLocalContextConstants.PROJECT_ID, projectId);

        return DataFetcherResult.<ProjectDTO> newResult()
                .data(project)
                .localContext(localContext)
                .build();
    }

}
