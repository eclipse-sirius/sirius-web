/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.projects.stylecustomizations.application.controllers;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.UpdateProjectStyleCustomizationStateInput;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IProjectStyleCustomizationApplicationService;

import graphql.schema.DataFetchingEnvironment;
import tools.jackson.databind.ObjectMapper;

/**
 * Data fetcher for the field Mutation#updateProjectStyleCustomizationState.
 *
 * @author gcoutable
 */
@MutationDataFetcher(type = "Mutation", field = "updateProjectStyleCustomizationState")
public class MutationUpdateProjectStyleCustomizationStateDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {
    private static final String INPUT_ARGUMENT = "input";

    private final IProjectStyleCustomizationApplicationService projectStyleCustomizationApplicationService;

    private final ObjectMapper objectMapper;

    private final ICapabilityEvaluator capabilityEvaluator;

    private final IMessageService messageService;

    public MutationUpdateProjectStyleCustomizationStateDataFetcher(IProjectStyleCustomizationApplicationService projectStyleCustomizationApplicationService, ObjectMapper objectMapper,
            ICapabilityEvaluator capabilityEvaluator, IMessageService messageService) {
        this.projectStyleCustomizationApplicationService = Objects.requireNonNull(projectStyleCustomizationApplicationService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, UpdateProjectStyleCustomizationStateInput.class);

        var hasCapability = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.PROJECT, input.projectId(), SiriusWebCapabilities.Project.EDIT);
        if (!hasCapability) {
            return new ErrorPayload(input.id(), this.messageService.unauthorized());
        }

        return this.projectStyleCustomizationApplicationService.updateProjectStyleCustomizationState(input);
    }
}
