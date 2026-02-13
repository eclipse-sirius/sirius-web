/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
import org.eclipse.sirius.web.application.project.api.IUploadProjectInput;
import org.eclipse.sirius.web.application.project.dto.UploadProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectUploadApplicationService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#uploadProject.
 *
 * @author gcoutable
 */
@MutationDataFetcher(type = "Mutation", field = "uploadProject")
public class MutationUploadProjectDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final ICapabilityEvaluator capabilityEvaluator;

    private final IProjectUploadApplicationService projectUploadApplicationService;

    private final IMessageService messageService;

    public MutationUploadProjectDataFetcher(ObjectMapper objectMapper, ICapabilityEvaluator capabilityEvaluator, IProjectUploadApplicationService projectUploadApplicationService, IMessageService messageService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
        this.projectUploadApplicationService = Objects.requireNonNull(projectUploadApplicationService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, IUploadProjectInput.class);

        var hasCapability = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.PROJECT, null, SiriusWebCapabilities.Project.UPLOAD);
        if (!hasCapability) {
            return new ErrorPayload(input.id(), this.messageService.unauthorized());
        }

        return this.projectUploadApplicationService.uploadProject(new UploadProjectInput(input.id(), input.file()));
    }

}
