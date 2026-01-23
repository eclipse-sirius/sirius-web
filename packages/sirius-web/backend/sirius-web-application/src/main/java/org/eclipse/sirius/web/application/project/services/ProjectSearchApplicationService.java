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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.application.project.services.api.IProjectMapper;
import org.eclipse.sirius.web.application.project.services.api.IProjectSearchApplicationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service used to search projects.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectSearchApplicationService implements IProjectSearchApplicationService {

    private final IProjectSearchService projectSearchService;

    private final IProjectMapper projectMapper;

    public ProjectSearchApplicationService(IProjectSearchService projectSearchService, IProjectMapper projectMapper) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectMapper = Objects.requireNonNull(projectMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectDTO> findById(String projectId) {
        return this.projectSearchService.findById(projectId).map(this.projectMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Window<ProjectDTO> findAll(KeysetScrollPosition position, int limit, Map<String, Object> filter) {
        var window = this.projectSearchService.findAll(position, limit, filter);
        return new Window<>(window.map(this.projectMapper::toDTO), window.hasPrevious());
    }
}
