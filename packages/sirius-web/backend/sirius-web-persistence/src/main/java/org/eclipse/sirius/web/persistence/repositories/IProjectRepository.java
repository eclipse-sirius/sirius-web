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

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Audited;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer used to manipulate projects.
 *
 * @author sbegaudeau
 */
@Repository
public interface IProjectRepository extends PagingAndSortingRepository<ProjectEntity, UUID> {

    @Override
    @Audited
    boolean existsById(UUID id);

    @Audited
    @Override
    Optional<ProjectEntity> findById(UUID id);

    @Audited
    @Override
    <S extends ProjectEntity> S save(S projectEntity);

    @Audited
    @Override
    void deleteById(UUID id);

}
