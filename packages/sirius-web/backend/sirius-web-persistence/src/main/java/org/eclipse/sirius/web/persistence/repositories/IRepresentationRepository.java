/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.web.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Audited;
import org.eclipse.sirius.web.persistence.entities.RepresentationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer used to manipulate representations.
 *
 * @author sbegaudeau
 */
@Repository
public interface IRepresentationRepository extends PagingAndSortingRepository<RepresentationEntity, UUID>, ListCrudRepository<RepresentationEntity, UUID> {
    @Audited
    @Override
    Optional<RepresentationEntity> findById(UUID id);

    @Audited
    Optional<RepresentationEntity> findByIdAndProjectId(UUID id, UUID projectId);

    @Audited
    List<RepresentationEntity> findAllByTargetObjectId(String objectId);

    @Audited
    List<RepresentationEntity> findAllByProjectId(UUID projectId);

    @Audited
    @Query(value = """
            SELECT CASE WHEN COUNT(representation)> 0 THEN true ELSE false END
            FROM RepresentationEntity representation
            WHERE representation.targetObjectId=?1
            """)
    boolean hasRepresentations(String objectId);

    @Audited
    @Override
    <S extends RepresentationEntity> S save(S representationEntity);

    @Audited
    @Override
    void deleteById(UUID id);
}
