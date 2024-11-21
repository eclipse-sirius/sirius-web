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
package org.eclipse.sirius.web.domain.boundedcontexts.project.repositories;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.api.IProjectSearchRepositoryDelegate;
import org.springframework.stereotype.Repository;

/**
 * Fragment repository used to customize the search of projects.
 *
 * @author sbegaudeau
 */
@Repository
public class ProjectSearchRepositoryImpl implements ProjectSearchRepository<Project, UUID> {

    private final IProjectSearchRepositoryDelegate projectSearchRepositoryDelegate;

    public ProjectSearchRepositoryImpl(IProjectSearchRepositoryDelegate projectSearchRepositoryDelegate) {
        this.projectSearchRepositoryDelegate = Objects.requireNonNull(projectSearchRepositoryDelegate);
    }

    @Override
    public boolean existsById(UUID projectId) {
        return this.projectSearchRepositoryDelegate.existsById(projectId);
    }

    @Override
    public Optional<Project> findById(UUID projectId) {
        return this.projectSearchRepositoryDelegate.findById(projectId);
    }

    @Override
    public List<Project> findAllAfter(UUID cursorProjectId, int limit) {
        return this.projectSearchRepositoryDelegate.findAllAfter(cursorProjectId, limit);
    }

    @Override
    public List<Project> findAllBefore(UUID cursorProjectId, int limit) {
        return this.projectSearchRepositoryDelegate.findAllBefore(cursorProjectId, limit);
    }
}
