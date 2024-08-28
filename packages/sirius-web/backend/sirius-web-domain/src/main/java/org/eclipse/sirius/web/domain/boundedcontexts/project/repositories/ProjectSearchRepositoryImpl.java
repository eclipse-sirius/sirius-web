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
package org.eclipse.sirius.web.domain.boundedcontexts.project.repositories;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.api.IProjectSearchRepositoryDelegate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Project> findAll(Pageable pageable) {
        return this.projectSearchRepositoryDelegate.findAll(pageable);
    }
}
