/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import org.eclipse.sirius.web.persistence.entities.CustomImageMetadataEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer for custom images.
 *
 * @author pcdavid
 */
@Repository
public interface ICustomImageMetadataRepository extends PagingAndSortingRepository<CustomImageMetadataEntity, UUID> {
    @Audited
    @Override
    Optional<CustomImageMetadataEntity> findById(UUID id);

    @Audited
    @Override
    void deleteById(UUID id);

    @Audited
    @Override
    <S extends CustomImageMetadataEntity> S save(S entity);

    @Audited
    @Override
    List<CustomImageMetadataEntity> findAll();

    @Audited
    List<CustomImageMetadataEntity> findAllByProjectId(UUID projectId);

}
