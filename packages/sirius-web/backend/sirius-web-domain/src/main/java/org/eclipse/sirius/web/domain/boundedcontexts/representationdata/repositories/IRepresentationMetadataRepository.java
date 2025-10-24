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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories;

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        WHERE representationMetadata.semantic_data_id = :semanticDataId
        """)
    List<RepresentationMetadata> findAllRepresentationMetadataBySemanticDataId(UUID semanticDataId);

    @Query("""
        WITH
        filtered_representation_metadata as (
            SELECT row_number() over ( ORDER BY representationMetadata.created_on desc, representationMetadata.label asc, representationMetadata.id asc), representationMetadata.*
            FROM representation_metadata representationMetadata
            WHERE representationMetadata.semantic_data_id = :semanticDataId
            ORDER BY representationMetadata.created_on desc, representationMetadata.label asc, representationMetadata.id asc
        ),
        cursor_index as (
            SELECT coalesce(
                (
                    SELECT representationMetadata.row_number
                    FROM filtered_representation_metadata representationMetadata
                    WHERE representationMetadata.id = :cursorId
                    LIMIT 1
                ),
                (
                    SELECT max(representationMetadata.row_number) + 1
                    FROM filtered_representation_metadata representationMetadata
                )
            ) as row_number
        )
        SELECT representationMetadata.*
        FROM filtered_representation_metadata representationMetadata, cursor_index c
        WHERE representationMetadata.row_number >= greatest(0, c.row_number - :limit)
        AND representationMetadata.row_number < c.row_number
        """)
    List<RepresentationMetadata> findAllRepresentationMetadataBySemanticDataIdBefore(UUID semanticDataId, UUID cursorId, int limit);

    @Query("""
        WITH
        filtered_representation_metadata as (
            SELECT row_number() over ( ORDER BY representationMetadata.created_on desc, representationMetadata.label asc, representationMetadata.id asc), representationMetadata.*
            FROM representation_metadata representationMetadata
            WHERE representationMetadata.semantic_data_id = :semanticDataId
            ORDER BY representationMetadata.created_on desc, representationMetadata.label asc, representationMetadata.id asc
        ),
        cursor_index as (
            SELECT coalesce(
                (
                    SELECT representationMetadata.row_number
                    FROM filtered_representation_metadata representationMetadata
                    WHERE representationMetadata.id = :cursorId
                    LIMIT 1
                ),
                0
            ) as row_number
        )
        SELECT representationMetadata.*
        FROM filtered_representation_metadata representationMetadata, cursor_index c
        WHERE
        CASE WHEN cast(:cursorId AS UUID) IS NOT null THEN
            representationMetadata.row_number <= c.row_number + :limit AND representationMetadata.row_number > c.row_number
        ELSE
            representationMetadata.row_number <= :limit
        END
        """)
    List<RepresentationMetadata> findAllRepresentationMetadataBySemanticDataIdAfter(UUID semanticDataId, UUID cursorId, int limit);

    @Query("""
        SELECT representationMetadata.*
        FROM representation_metadata representationMetadata
        WHERE representationMetadata.semantic_data_id = :semanticDataId
        AND representationMetadata.target_object_id = :targetObjectId
        """)
    List<RepresentationMetadata> findAllRepresentationMetadataBySemanticDataIdAndTargetObjectId(UUID semanticDataId, String targetObjectId);

    @Query("""
        SELECT representationMetadata.semantic_data_id
        FROM representation_metadata representationMetadata
        WHERE representationMetadata.id = :representationId
        """)
    Optional<UUID> findSemanticDataIdFromRepresentationId(UUID representationId);

    @Query("""
        SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
        FROM representation_metadata representationMetadata
        WHERE representationMetadata.semantic_data_id = :semanticDataId AND representationMetadata.target_object_id = :targetObjectId
        """)
    boolean existAnyRepresentationMetadataForSemanticDataIdAndTargetObjectId(UUID semanticDataId, String targetObjectId);

    @Query("""
        SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
        FROM representation_metadata representationMetadata
        WHERE representationMetadata.id = :representationId
        AND representationMetadata.kind IN (:kinds)
        """)
    boolean existsByIdAndKind(UUID representationId, List<String> kinds);
}
