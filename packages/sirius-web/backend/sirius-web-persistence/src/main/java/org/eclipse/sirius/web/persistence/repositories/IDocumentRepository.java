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
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer used to manipulate documents.
 *
 * @author sbegaudeau
 */
@Repository
public interface IDocumentRepository extends PagingAndSortingRepository<DocumentEntity, UUID>, ListCrudRepository<DocumentEntity, UUID> {

    @Audited
    @Override
    Optional<DocumentEntity> findById(UUID id);

    @Audited
    @Override
    List<DocumentEntity> findAll();

    @Audited
    @Query(value = """
            SELECT * FROM Document document
            WHERE length(document.content) > 0
            AND document.content::::jsonb @> ('{ "ns": { "' || ?1 || '": "' || ?2 ||'" } }')::::jsonb
            """, nativeQuery = true)
    List<DocumentEntity> findAllByType(String name, String uri);

    @Audited
    List<DocumentEntity> findAllByProjectId(UUID projectId);

    @Audited
    @Query(value = """
            SELECT document FROM DocumentEntity document
            WHERE document.project.id=?1 AND document.id=?2
            """)
    Optional<DocumentEntity> findByProjectIdAndId(UUID projectId, UUID documentId);

    @Audited
    @Override
    void deleteById(UUID id);

    @Audited
    @Override
    <S extends DocumentEntity> S save(S entity);
}
