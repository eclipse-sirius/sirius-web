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
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectDeletionApplicationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectDeletionService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service used to delete projects.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectDeletionApplicationService implements IProjectDeletionApplicationService {

    private final IProjectDeletionService projectDeletionService;

    public ProjectDeletionApplicationService(IProjectDeletionService projectDeletionService) {
        this.projectDeletionService = Objects.requireNonNull(projectDeletionService);
    }

    @Override
    @Transactional
    public IPayload deleteProject(DeleteProjectInput input) {
        var result = this.projectDeletionService.deleteProject(input, input.projectId());

        IPayload payload = null;
        if (result instanceof Failure<Void> failure) {
            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<Void>) {
            payload = new SuccessPayload(input.id());
        }
        return payload;
    }
}
