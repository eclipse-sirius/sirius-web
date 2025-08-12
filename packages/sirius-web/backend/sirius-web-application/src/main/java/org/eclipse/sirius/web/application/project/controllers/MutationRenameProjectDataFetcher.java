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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#renameProject.
 *
 * @author sbegaudeau
 */
@MutationDataFetcher(type = "Mutation", field = "renameProject")
public class MutationRenameProjectDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final List<ICapabilityVoter> capabilityVoters;

    private final IProjectApplicationService projectApplicationService;

    private final IMessageService messageService;

    public MutationRenameProjectDataFetcher(ObjectMapper objectMapper, List<ICapabilityVoter> capabilityVoters, IProjectApplicationService projectApplicationService, IMessageService messageService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.capabilityVoters = Objects.requireNonNull(capabilityVoters);
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, RenameProjectInput.class);

        var hasCapability = this.capabilityVoters.stream().allMatch(voter -> voter.vote(SiriusWebCapabilities.PROJECT, input.projectId(), SiriusWebCapabilities.Project.RENAME) == CapabilityVote.GRANTED);
        if (!hasCapability) {
            return new ErrorPayload(input.id(), this.messageService.unauthorized());
        }

        return this.projectApplicationService.renameProject(input);
    }
}
