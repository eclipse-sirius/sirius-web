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

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to persist the project aggregate.
 *
 * @author sbegaudeau
 */
@Repository
public interface IProjectRepository extends ListPagingAndSortingRepository<Project, UUID>, ListCrudRepository<Project, UUID>, ProjectSearchRepository<Project, UUID> {

    @Query("""
        SELECT * FROM project
        WHERE project.id IN (:projectIds)
        ORDER BY project.name ASC
        OFFSET :offset
        LIMIT :limit
        """)
    List<Project> findAllById(List<UUID> projectIds, long offset, int limit);

    @Query("""
        SELECT count(*) FROM project
        WHERE project.id IN (:projectIds)
        """)
    long countAllById(List<UUID> projectIds);
}
