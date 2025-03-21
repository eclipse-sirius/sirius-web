/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.project.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve projects.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectSearchService implements IProjectSearchService {

    private final IProjectRepository projectRepository;

    public ProjectSearchService(IProjectRepository projectRepository) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
    }

    @Override
    public boolean existsById(String projectId) {
        return this.projectRepository.existsById(projectId);
    }

    @Override
    public Optional<Project> findById(String projectId) {
        return this.projectRepository.findById(projectId);
    }

    @Override
    public Window<Project> findAll(KeysetScrollPosition position, int limit, Map<String, Object> filter) {
        Window<Project> window = new Window<>(List.of(), index -> position, false, false);
        if (limit > 0) {
            var cursorProjectKey = position.getKeys().get("id");
            if (cursorProjectKey instanceof String cursorProjectId) {
                if (this.existsById(cursorProjectId)) {
                    if (position.scrollsForward()) {
                        var projects = this.projectRepository.findAllAfter(cursorProjectId, limit + 1, filter);
                        boolean hasNext = projects.size() > limit;
                        boolean hasPrevious = !this.projectRepository.findAllBefore(cursorProjectId, 1, filter).isEmpty();
                        window = new Window<>(projects.subList(0, Math.min(projects.size(), limit)), index -> position, hasNext, hasPrevious);
                    } else if (position.scrollsBackward()) {
                        var projects = this.projectRepository.findAllBefore(cursorProjectId, limit + 1, filter);
                        boolean hasPrevious = projects.size() > limit;
                        boolean hasNext = !this.projectRepository.findAllAfter(cursorProjectId, 1, filter).isEmpty();
                        window = new Window<>(projects.subList(0, Math.min(projects.size(), limit)), index -> position, hasNext, hasPrevious);
                    }
                }
            } else {
                var projects = this.projectRepository.findAllAfter(null, limit + 1, filter);
                boolean hasNext = projects.size() > limit;
                boolean hasPrevious = false;
                window = new Window<>(projects.subList(0, Math.min(projects.size(), limit)), index -> position, hasNext, hasPrevious);
            }
        }
        return window;
    }

}
