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
package org.eclipse.sirius.web.projects.stylecustomizations.application.services;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.core.domain.results.Failure;
import org.eclipse.sirius.web.core.domain.results.IResult;
import org.eclipse.sirius.web.core.domain.results.Success;
import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.UpdateProjectStyleCustomizationStateInput;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IProjectStyleCustomizationApplicationService;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.services.api.IProjectStyleCustomizationCreationService;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.services.api.IProjectStyleCustomizationDeletionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to manipulate project style customizations.
 *
 * @author gcoutable
 */
@Service
public class ProjectStyleCustomizationApplicationService implements IProjectStyleCustomizationApplicationService {

    private final IProjectStyleCustomizationCreationService projectStyleCustomizationCreationService;

    private final IProjectStyleCustomizationDeletionService projectStyleCustomizationDeletionService;

    private final Logger logger = LoggerFactory.getLogger(ProjectStyleCustomizationApplicationService.class);

    public ProjectStyleCustomizationApplicationService(IProjectStyleCustomizationCreationService projectStyleCustomizationCreationService, IProjectStyleCustomizationDeletionService projectStyleCustomizationDeletionService) {
        this.projectStyleCustomizationCreationService = Objects.requireNonNull(projectStyleCustomizationCreationService);
        this.projectStyleCustomizationDeletionService = Objects.requireNonNull(projectStyleCustomizationDeletionService);
    }

    @Override
    @Transactional
    public IPayload updateProjectStyleCustomizationState(UpdateProjectStyleCustomizationStateInput input) {
        IResult<?> result;

        if (input.enable()) {
            result = this.projectStyleCustomizationCreationService.createProjectStyleCustomization(input, AggregateReference.to(input.projectId()), input.styleCustomizationDescriptionId());
        } else {
            result = this.projectStyleCustomizationDeletionService.deleteByProjectIdAndStyleDescriptionId(input, AggregateReference.to(input.projectId()), input.styleCustomizationDescriptionId());
        }

        return switch (result) {
            case Failure<?>(var message) -> {
                this.logger.atWarn()
                        .setMessage("Update of Project style customization {} failed")
                        .addArgument(input.styleCustomizationDescriptionId())
                        .addKeyValue("styleCustomizationId", input.styleCustomizationDescriptionId())
                        .addKeyValue("projectId", input.projectId())
                        .log();

                yield new ErrorPayload(input.id(), message);
            }
            case Success<?>(var data) -> {
                this.logger.atInfo()
                        .setMessage("Update the project style customization {}")
                        .addArgument(input.styleCustomizationDescriptionId())
                        .addKeyValue("styleCustomizationId", input.styleCustomizationDescriptionId())
                        .addKeyValue("projectId", input.projectId())
                        .log();

                yield new SuccessPayload(input.id());
            }
        };
    }
}
