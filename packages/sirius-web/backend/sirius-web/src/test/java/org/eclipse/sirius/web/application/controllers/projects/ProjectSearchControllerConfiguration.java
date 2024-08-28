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
package org.eclipse.sirius.web.application.controllers.projects;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.api.IProjectSearchRepositoryDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.simple.JdbcClient;

/**
 * Configuration used to change the project search strategy.
 *
 * @author sbegaudeau
 */
public class ProjectSearchControllerConfiguration {

    @Bean
    @Primary
    public IProjectSearchRepositoryDelegate testProjectSearchRepositoryDelegate(JdbcClient jdbcClient) {
        return new IProjectSearchRepositoryDelegate() {
            @Override
            public boolean existsById(UUID projectId) {
                return false;
            }

            @Override
            public Optional<Project> findById(UUID projectId) {
                return Optional.empty();
            }

            @Override
            public Page<Project> findAll(Pageable pageable) {
                var query = """
                        SELECT project.* FROM project
                        JOIN nature ON project.id = nature.project_id
                        WHERE nature.name = 'ecore'
                        """;
                var projects = jdbcClient.sql(query)
                        .query(Project.class)
                        .list();
                return new PageImpl<>(projects, pageable, projects.size());
            }
        };
    }
}
