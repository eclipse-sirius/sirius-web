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

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.api.IProjectSearchRepositoryDelegate;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.query.Query;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

/**
 * Used to execute the queries for the project search repository.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectSearchRepositoryDelegate implements IProjectSearchRepositoryDelegate {

    private static final String ID = "id";

    private static final String CURSOR_PROJECT_ID = "cursorProjectId";

    private static final String LIMIT = "limit";

    private static final String FIND_ALL_BEFORE = """
            WITH
            filtered_projects as (
                SELECT row_number() over( ORDER BY p.created_on desc, p.name asc, p.id asc ), p.*
                FROM project p
                WHERE CASE WHEN :name <> '' THEN LOWER(p.name) LIKE CONCAT('%', CONCAT(LOWER(:name), '%')) ELSE true END
                ORDER BY p.created_on desc, p.name asc, p.id asc
            ),
            cursor_index as (
                SELECT coalesce(
                    (
                        SELECT p.row_number
                        FROM filtered_projects p
                        WHERE p.id = :cursorProjectId
                        LIMIT 1
                    ),
                    (
                        SELECT max(p.row_number) + 1
                        FROM filtered_projects p
                    )
                ) as row_number
            )
            SELECT p.id, p.name, p.created_on, p.last_modified_on
            FROM filtered_projects p, cursor_index c
            WHERE p.row_number >= greatest(0, c.row_number - :limit)
            AND p.row_number < c.row_number
            """;

    private static final String FIND_ALL_AFTER = """
            WITH
            filtered_projects as (
                SELECT row_number() over( ORDER BY p.created_on desc, p.name asc, p.id asc ), p.*
                FROM project p
                WHERE CASE WHEN :name <> '' THEN LOWER(p.name) LIKE CONCAT('%', CONCAT(LOWER(:name), '%')) ELSE true END
                ORDER BY p.created_on desc, p.name asc, p.id asc
            ),
            cursor_index as (
                SELECT coalesce(
                    (
                        SELECT p.row_number
                        FROM filtered_projects p
                        WHERE p.id = :cursorProjectId
                        LIMIT 1
                    ),
                    0
                ) as row_number
            )
            SELECT p.*
            FROM filtered_projects p, cursor_index c
            WHERE
            CASE WHEN :cursorProjectId <> '' THEN
                p.row_number <= c.row_number + :limit AND p.row_number > c.row_number
            ELSE
                p.row_number <= :limit
            END
            """;

    private final JdbcAggregateOperations jdbcAggregateOperations;

    private final JdbcClient jdbcClient;

    private final RelationalMappingContext context;

    private final JdbcConverter converter;

    public ProjectSearchRepositoryDelegate(JdbcAggregateOperations jdbcAggregateOperations, JdbcClient jdbcClient, RelationalMappingContext context, JdbcConverter converter) {
        this.jdbcAggregateOperations = Objects.requireNonNull(jdbcAggregateOperations);
        this.jdbcClient = Objects.requireNonNull(jdbcClient);
        this.context = Objects.requireNonNull(context);
        this.converter = Objects.requireNonNull(converter);
    }

    @Override
    public boolean existsById(String projectId) {
        Query query = query(where(ID).is(projectId));
        return this.jdbcAggregateOperations.exists(query, Project.class);
    }

    @Override
    public Optional<Project> findById(String projectId) {
        Query query = query(where(ID).is(projectId));
        return this.jdbcAggregateOperations.findOne(query, Project.class);
    }

    @Override
    public List<Project> findAllBefore(String cursorProjectId, int limit, Map<String, Object> filter) {
        if (limit > 0) {
            Map<String, Object>  parameters = new HashMap<>();
            parameters.put(CURSOR_PROJECT_ID, cursorProjectId);
            parameters.put(LIMIT, limit);
            this.handleFilter(parameters, filter, "name", "contains");
            return this.getAllProjectsQuery(FIND_ALL_BEFORE, parameters);
        }
        return List.of();
    }

    @Override
    public List<Project> findAllAfter(String cursorProjectId, int limit, Map<String, Object> filter) {
        if (limit > 0) {
            Map<String, Object>  parameters = new HashMap<>();
            parameters.put(CURSOR_PROJECT_ID, cursorProjectId);
            parameters.put(LIMIT, limit);
            this.handleFilter(parameters, filter, "name", "contains");
            return this.getAllProjectsQuery(FIND_ALL_AFTER, parameters);
        }
        return List.of();
    }

    private List<Project> getAllProjectsQuery(String sqlQuery, Map<String, ?>  parameters) {
        var entityRowMapper = this.getEntityRowMapper(Project.class);
        return this.jdbcClient.sql(sqlQuery)
                .params(parameters)
                .query(entityRowMapper)
                .list();
    }

    private void handleFilter(Map<String, Object> queryParameters, Map<String, Object> filter, String attributeName, String operation) {
        var value = "";
        var optionalFilterOperations = Optional.ofNullable(filter.get(attributeName));
        if (optionalFilterOperations.isPresent()) {
            if (optionalFilterOperations.get() instanceof Map<?, ?>) {
                Map<String, Object> filterOperations = (Map<String, Object>) optionalFilterOperations.get();
                if (filterOperations.containsKey(operation)) {
                    value = filterOperations.get(operation).toString();
                }
            }
        }
        queryParameters.put(attributeName, value);
    }

    private <T> EntityRowMapper<T> getEntityRowMapper(Class<T> domainType) {
        return new EntityRowMapper<>(this.getRequiredPersistentEntity(domainType), this.converter);
    }

    private <S> RelationalPersistentEntity<S> getRequiredPersistentEntity(Class<S> domainType) {
        return (RelationalPersistentEntity<S>) this.context.getRequiredPersistentEntity(domainType);
    }
}
