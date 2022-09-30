/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import java.util.Optional;

import org.eclipse.sirius.components.annotations.Audited;
import org.eclipse.sirius.web.persistence.entities.IdMappingEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer used to manipulate ID mappings.
 *
 * @author gcoutable
 */
@Repository
public interface IIdMappingRepository extends PagingAndSortingRepository<IdMappingEntity, String> {

    @Audited
    @Override
    Iterable<IdMappingEntity> findAll();

    @Audited
    @Override
    Optional<IdMappingEntity> findById(String id);

    @Audited
    Optional<IdMappingEntity> findByExternalId(String externalId);

    @Audited
    @Override
    <S extends IdMappingEntity> S save(S entity);
}
