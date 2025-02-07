/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to persist the semantic data aggregate.
 *
 * @author mcharfadi
 */
@Repository
public interface IProjectSemanticDataRepository extends ListPagingAndSortingRepository<ProjectSemanticData, UUID>, ListCrudRepository<ProjectSemanticData, UUID> {

    @Query("""
        SELECT *
        FROM project_semantic_data projectSemanticData
        WHERE projectSemanticData.project_id = :projectId
        AND projectSemanticData.name = :name
        """)
    Optional<ProjectSemanticData> findByProjectIdAndName(String projectId, String name);

    @Query("""
        SELECT *
        FROM project_semantic_data projectSemanticData
        WHERE projectSemanticData.semantic_data_id = :semanticDataId
        """)
    Optional<ProjectSemanticData> findBySemanticDataId(UUID semanticDataId);

    @Query("""
        SELECT *
        FROM project_semantic_data projectSemanticData
        WHERE projectSemanticData.project_id = :projectId
        """)
    List<ProjectSemanticData> findAllByProjectId(String projectId);
}
