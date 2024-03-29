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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to persist the representation data aggregate.
 *
 * @author sbegaudeau
 */
@Repository
public interface IRepresentationDataRepository extends ListPagingAndSortingRepository<RepresentationData, UUID>, ListCrudRepository<RepresentationData, UUID> {
    @Query("""
        SELECT *
        FROM representation_data representationData
        WHERE representationData.project_id = :projectId
        """)
    List<RepresentationData> findAllByProjectId(UUID projectId);

    @Query("""
        SELECT representationData.project_id
        FROM representation_data representationData
        WHERE representationData.id = :representationId
        """)
    Optional<UUID> findProjectIdFromRepresentationId(UUID representationId);

    @Query("""
        SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
        FROM representation_data representationData
        WHERE representationData.target_object_id = :targetObjectId
        """)
    boolean existAnyRepresentationForTargetObjectId(String targetObjectId);

    @Query("""
        SELECT *
        FROM representation_data representationData
        WHERE representationData.target_object_id = :targetObjectId
        """)
    List<RepresentationData> findAllByTargetObjectId(String targetObjectId);
}
