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

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.api.IProjectSearchRepositoryDelegate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Used to execute the queries for the project search repository.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectSearchRepositoryDelegate implements IProjectSearchRepositoryDelegate {

    private final JdbcAggregateOperations jdbcAggregateOperations;

    public ProjectSearchRepositoryDelegate(JdbcAggregateOperations jdbcAggregateOperations) {
        this.jdbcAggregateOperations = Objects.requireNonNull(jdbcAggregateOperations);
    }

    @Override
    public boolean existsById(UUID projectId) {
        Query query = query(where("id").is(projectId));
        return this.jdbcAggregateOperations.exists(query, Project.class);
    }

    @Override
    public Optional<Project> findById(UUID projectId) {
        Query query = query(where("id").is(projectId));
        return this.jdbcAggregateOperations.findOne(query, Project.class);
    }

    @Override
    public Page<Project> findAll(Pageable pageable) {
        return this.jdbcAggregateOperations.findAll(Project.class, pageable);
    }
}
