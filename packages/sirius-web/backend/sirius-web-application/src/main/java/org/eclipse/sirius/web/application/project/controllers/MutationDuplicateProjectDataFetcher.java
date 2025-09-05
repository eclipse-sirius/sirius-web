/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import java.util.Objects;
import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.eclipse.sirius.web.application.project.dto.DuplicateProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectDuplicationApplicationService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;

/**
 * Data fetcher for the field Mutation#duplicateProject.
 *
 * @author Arthur Daussy
 */
@MutationDataFetcher(type = "Mutation", field = "duplicateProject")
public class MutationDuplicateProjectDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IProjectDuplicationApplicationService projectDuplicateService;

    private final List<ICapabilityVoter> capabilityVoters;

    private final IMessageService messageService;


    public MutationDuplicateProjectDataFetcher(ObjectMapper objectMapper, List<ICapabilityVoter> capabilityVoters, IProjectDuplicationApplicationService projectDuplicateService, IMessageService messageService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.projectDuplicateService = Objects.requireNonNull(projectDuplicateService);
        this.capabilityVoters = Objects.requireNonNull(capabilityVoters);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, DuplicateProjectInput.class);

        var hasCapability = this.capabilityVoters.stream().allMatch(voter -> voter.vote(SiriusWebCapabilities.PROJECT, input.projectId(), SiriusWebCapabilities.Project.DUPLICATE) == CapabilityVote.GRANTED);
        if (!hasCapability) {
            return new ErrorPayload(input.id(), this.messageService.unauthorized());
        }

        return projectDuplicateService.duplicateProject(input);
    }
}
