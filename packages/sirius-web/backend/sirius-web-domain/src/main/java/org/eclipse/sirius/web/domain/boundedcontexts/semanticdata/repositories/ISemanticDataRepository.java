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
package org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.repositories;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to persist the semantic data aggregate.
 *
 * @author sbegaudeau
 */
@Repository
public interface ISemanticDataRepository extends ListPagingAndSortingRepository<SemanticData, UUID>, ListCrudRepository<SemanticData, UUID> {

    @Query("""
        SELECT semanticData.*
        FROM semantic_data semanticData
        JOIN semantic_data_domain semanticDataDomain
        ON semanticData.id = semanticDataDomain.semantic_data_id
        WHERE semanticDataDomain.uri IN (:domainUris)
        GROUP BY semanticData.id
        """)
    List<SemanticData> findAllByDomains(List<String> domainUris);

    @Query("""
            SELECT CASE WHEN COUNT(semanticDataDomain.*) > 0 THEN true ELSE false END
            FROM semantic_data semanticData
            JOIN semantic_data_domain semanticDataDomain
            ON semanticData.id = semanticDataDomain.semantic_data_id
            WHERE semanticData.id = :id
            AND semanticDataDomain.uri IN (:domainUris)
            """)
    boolean isUsingDomains(UUID id, List<String> domainUris);

    @Query("""
        SELECT dependency_semantic_data_id FROM semantic_data_dependency semanticDataDependency
        WHERE semanticDataDependency.semantic_data_id = :id
        """)
    List<UUID> findAllDependenciesById(UUID id);

    @Query("""
        WITH RECURSIVE dependencies AS (
          SELECT semanticDataDependency.*
          FROM semantic_data_dependency semanticDataDependency
          WHERE semanticDataDependency.semantic_data_id = :id

          UNION

          SELECT childSemanticDataDependency.*
          FROM semantic_data_dependency childSemanticDataDependency
          INNER JOIN dependencies dependency ON dependency.dependency_semantic_data_id = childSemanticDataDependency.semantic_data_id
        )
        SELECT dependency_semantic_data_id FROM dependencies
        """)
    List<UUID> findAllDependenciesRecursivelyById(UUID id);
}
