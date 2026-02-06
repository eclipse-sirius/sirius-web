/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.application.controllers.projects;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.api.IProjectSearchRepositoryDelegate;
import org.springframework.jdbc.core.simple.JdbcClient;

/**
 * Configuration used to change the project search strategy.
 *
 * @author sbegaudeau
 */
public class CustomProjectSearchRepositoryDelegate implements IProjectSearchRepositoryDelegate {

    private final JdbcClient jdbcClient;

    public CustomProjectSearchRepositoryDelegate(JdbcClient jdbcClient) {
        this.jdbcClient = Objects.requireNonNull(jdbcClient);
    }

    @Override
    public boolean existsById(String projectId) {
        return false;
    }

    @Override
    public Optional<Project> findById(String projectId) {
        return Optional.empty();
    }

    @Override
    public List<Project> findAllBefore(String cursorProjectId, int limit, Map<String, Object> filter) {
        var query = """
                SELECT project.* FROM project
                JOIN nature ON project.id = nature.project_id
                WHERE nature.name = 'ecore'
                """;
        return jdbcClient.sql(query)
                .query(Project.class)
                .list();
    }

    @Override
    public List<Project> findAllAfter(String cursorProjectId, int limit, Map<String, Object> filter) {
        var query = """
                SELECT project.* FROM project
                JOIN nature ON project.id = nature.project_id
                WHERE nature.name = 'ecore'
                """;
        return jdbcClient.sql(query)
                .query(Project.class)
                .list();
    }

    @Override
    public List<Project> findAllBefore(List<String> projectIds, String cursorProjectId, int limit, Map<String, Object> filter) {
        return this.findAllBefore(cursorProjectId, limit, filter);
    }

    @Override
    public List<Project> findAllAfter(List<String> projectIds, String cursorProjectId, int limit, Map<String, Object> filter) {
        return this.findAllAfter(cursorProjectId, limit, filter);
    }
}
