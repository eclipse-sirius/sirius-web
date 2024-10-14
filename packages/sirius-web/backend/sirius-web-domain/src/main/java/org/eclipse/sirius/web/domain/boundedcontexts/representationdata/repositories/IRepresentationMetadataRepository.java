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

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to persist the representation metadata aggregate.
 *
 * @author sbegaudeau
 */
@Repository
public interface IRepresentationMetadataRepository extends ListPagingAndSortingRepository<RepresentationMetadata, UUID>, ListCrudRepository<RepresentationMetadata, UUID> {

    @Query("""
        SELECT representationMetadata.*
        FROM representation_metadata representationMetadata
        WHERE representationMetadata.id = :id
        """)
    Optional<RepresentationMetadata> findMetadataById(UUID id);

    @Query("""
        SELECT representationMetadata.*
        FROM representation_metadata representationMetadata
        WHERE representationMetadata.project_id = :projectId
        """)
    List<RepresentationMetadata> findAllMetadataByProjectId(UUID projectId);

    @Query("""
        SELECT representationMetadata.*
        FROM representation_metadata representationMetadata
        WHERE representationMetadata.target_object_id = :targetObjectId
        AND representationMetadata.project_id = :projectId
        """)
    List<RepresentationMetadata> findAllMetadataByProjectAndTargetObjectId(UUID projectId, String targetObjectId);

    @Query("""
        SELECT representationMetadata.project_id
        FROM representation_metadata representationMetadata
        WHERE representationMetadata.id = :representationId
        """)
    Optional<UUID> findProjectIdFromRepresentationId(UUID representationId);

    @Query("""
        SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
        FROM representation_metadata representationMetadata
        WHERE representationMetadata.target_object_id = :targetObjectId
        """)
    boolean existAnyRepresentationForTargetObjectId(String targetObjectId);

    @Query("""
        SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
        FROM representation_metadata representationMetadata
        WHERE representationMetadata.id = :representationId
        AND representationMetadata.kind IN (:kinds)
        """)
    boolean existsByIdAndKind(UUID representationId, List<String> kinds);
}
