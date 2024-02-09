/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public boolean existsById(UUID projectId) {
        return this.projectRepository.existsById(projectId);
    }

    @Override
    public Optional<Project> findById(UUID projectId) {
        return this.projectRepository.findById(projectId);
    }

    @Override
    public Page<Project> findAll(Pageable pageable) {
        return this.projectRepository.findAll(pageable);
    }
}
