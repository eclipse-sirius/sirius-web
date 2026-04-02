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
package org.eclipse.sirius.web.application.project.services;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.eclipse.sirius.web.application.project.dto.RenameProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectUpdateApplicationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectUpdateService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service used to update projects.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectUpdateApplicationService implements IProjectUpdateApplicationService {

    private final IProjectUpdateService projectUpdateService;

    private final Logger logger = LoggerFactory.getLogger(ProjectUpdateApplicationService.class);

    public ProjectUpdateApplicationService(IProjectUpdateService projectUpdateService) {
        this.projectUpdateService = Objects.requireNonNull(projectUpdateService);
    }

    @Override
    @Transactional
    public IPayload renameProject(RenameProjectInput input) {
        var result = this.projectUpdateService.renameProject(input, input.projectId(), input.newName());

        IPayload payload = null;
        if (result instanceof Failure<Void> failure) {
            this.logger.atWarn()
                    .setMessage("Rename of project {} failed")
                    .addArgument(input.projectId())
                    .addKeyValue("projectId", input.projectId())
                    .log();

            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<Void>) {
            this.logger.atInfo()
                    .setMessage("Project {} renamed")
                    .addArgument(input.projectId())
                    .addKeyValue("projectId", input.projectId())
                    .log();

            payload = new RenameProjectSuccessPayload(input.id());
        }
        return payload;
    }
}
