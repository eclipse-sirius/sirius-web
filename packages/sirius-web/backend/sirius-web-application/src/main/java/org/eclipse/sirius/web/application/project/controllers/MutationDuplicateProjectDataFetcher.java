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

import java.util.Objects;

import graphql.schema.DataFetchingEnvironment;
import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
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

    private final IProjectDuplicationApplicationService projectDuplicationApplicationService;

    private final ICapabilityEvaluator capabilityEvaluator;

    private final IMessageService messageService;

    public MutationDuplicateProjectDataFetcher(ObjectMapper objectMapper, IProjectDuplicationApplicationService projectDuplicationApplicationService, ICapabilityEvaluator capabilityEvaluator, IMessageService messageService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.projectDuplicationApplicationService = Objects.requireNonNull(projectDuplicationApplicationService);
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, DuplicateProjectInput.class);

        var hasCapability = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.PROJECT, input.projectId(), SiriusWebCapabilities.Project.DUPLICATE);
        if (!hasCapability) {
            return new ErrorPayload(input.id(), this.messageService.unauthorized());
        }

        return this.projectDuplicationApplicationService.duplicateProject(input);
    }
}
