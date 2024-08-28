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
package org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.repositories;

import java.util.List;
import java.util.Optional;
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
        SELECT *
        FROM semantic_data semanticData
        WHERE semanticData.project_id = :projectId
        """)
    Optional<SemanticData> findByProjectId(UUID projectId);

    @Query("""
        SELECT semanticData.*
        FROM semantic_data semanticData
        JOIN semantic_data_domain semanticDataDomain
        ON semanticData.id = semanticDataDomain.semantic_data_id
        WHERE semanticDataDomain.uri IN (:domainUris)
        GROUP BY semanticData.id
        """)
    List<SemanticData> findAllByDomains(List<String> domainUris);
}
