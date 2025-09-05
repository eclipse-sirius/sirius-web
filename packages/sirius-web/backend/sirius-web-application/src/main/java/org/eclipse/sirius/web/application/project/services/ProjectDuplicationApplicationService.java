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
package org.eclipse.sirius.web.application.project.services;

import java.util.Objects;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.dto.DuplicateProjectInput;
import org.eclipse.sirius.web.application.project.dto.DuplicateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectDuplicationApplicationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service used to duplicate a project.
 *
 * @author Arthur Daussy
 */
@Service
public class ProjectDuplicationApplicationService implements IProjectDuplicationApplicationService {
    
    private final IProjectSearchService projectSearchService;

    private final ProjectMapper projectMapper;

    public ProjectDuplicationApplicationService(IProjectSearchService projectSearchService, ProjectMapper projectMapper) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectMapper = Objects.requireNonNull(projectMapper);
    }

    @Override
    @Transactional
    public IPayload duplicateProject(DuplicateProjectInput input) {
        // Mock project duplication
        // For now only return the source project itself.
        return projectSearchService.findById(input.projectId())
                .<IPayload>map(project -> new DuplicateProjectSuccessPayload(input.id(), projectMapper.toDTO(project)))
                .orElse(new ErrorPayload(input.id(), "Unable to find project with id " + input.projectId()));

    }
    
}
