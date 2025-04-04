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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.api.IProjectSearchRepositoryDelegate;
import org.springframework.stereotype.Repository;

/**
 * Fragment repository used to customize the search of projects.
 *
 * @author sbegaudeau
 */
@Repository
public class ProjectSearchRepositoryImpl implements ProjectSearchRepository<Project, String> {

    private final IProjectSearchRepositoryDelegate projectSearchRepositoryDelegate;

    public ProjectSearchRepositoryImpl(IProjectSearchRepositoryDelegate projectSearchRepositoryDelegate) {
        this.projectSearchRepositoryDelegate = Objects.requireNonNull(projectSearchRepositoryDelegate);
    }

    @Override
    public boolean existsById(String projectId) {
        return this.projectSearchRepositoryDelegate.existsById(projectId);
    }

    @Override
    public Optional<Project> findById(String projectId) {
        return this.projectSearchRepositoryDelegate.findById(projectId);
    }

    @Override
    public List<Project> findAllAfter(String cursorProjectId, int limit, Map<String, Object> filter) {
        return this.projectSearchRepositoryDelegate.findAllAfter(cursorProjectId, limit, filter);
    }

    @Override
    public List<Project> findAllBefore(String cursorProjectId, int limit, Map<String, Object> filter) {
        return this.projectSearchRepositoryDelegate.findAllBefore(cursorProjectId, limit, filter);
    }
}
