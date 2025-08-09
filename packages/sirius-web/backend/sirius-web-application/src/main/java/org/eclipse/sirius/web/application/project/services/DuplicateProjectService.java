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

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.dto.DuplicateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectDuplicateService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * Service used to duplicate a project.
 *
 * @author Arthur Daussy
 */
@Service
public class DuplicateProjectService implements IProjectDuplicateService {
    
    private final IProjectSearchService projectSearchService;
    private final ProjectMapper projectMapper;

    public DuplicateProjectService(IProjectSearchService projectSearchService, ProjectMapper projectMapper) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectMapper = projectMapper;
    }

    @Override
    public IPayload duplicateProject(UUID inputId, Project project) {
        // Mock project duplication
        // For now only return the source project itself.
        Project newProject = this.projectSearchService.findById(project.getId()).get();
        return new DuplicateProjectSuccessPayload(inputId, projectMapper.toDTO(project));
    }


}
