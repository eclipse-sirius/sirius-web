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

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.UpdateProjectStyleCustomizationStateInput;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IProjectStyleCustomizationApplicationService;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.repositories.ProjectStyleCustomizationStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to manipulate project style customizations.
 *
 * @author gcoutable
 */
@Service
public class ProjectStyleCustomizationApplicationService implements IProjectStyleCustomizationApplicationService {

    private final ProjectStyleCustomizationStore projectStyleCustomizationStore;

    private final Logger logger = LoggerFactory.getLogger(ProjectStyleCustomizationApplicationService.class);

    public ProjectStyleCustomizationApplicationService(ProjectStyleCustomizationStore projectStyleCustomizationStore) {
        this.projectStyleCustomizationStore = Objects.requireNonNull(projectStyleCustomizationStore);
    }

    @Override
    public IPayload updateProjectStyleCustomizationState(UpdateProjectStyleCustomizationStateInput input) {
        if (input.enable()) {
            this.projectStyleCustomizationStore.createProjectStyleCustomization(input.projectId(), input.styleCustomizationDescriptionId());
        } else {
            this.projectStyleCustomizationStore.deleteByProjectIdAndStyleDescriptionId(input.projectId(), input.styleCustomizationDescriptionId());
        }

        this.logger.atInfo()
                .setMessage("Project style customization {} updated in project {}")
                .addArgument(input.styleCustomizationDescriptionId())
                .addArgument(input.projectId())
                .addKeyValue("styleCustomizationId", input.styleCustomizationDescriptionId())
                .addKeyValue("projectId", input.projectId())
                .log();
        return new SuccessPayload(input.id());
    }
}
