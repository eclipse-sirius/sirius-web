/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.emf.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.persistence.entities.IdMappingEntity;
import org.eclipse.sirius.web.persistence.repositories.IIdMappingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * The implementation of the IdMapping repository which does nothing.
 *
 * @author gcoutable
 */
public class NoOpIdMappingRepository implements IIdMappingRepository {

    @Override
    public Iterable<IdMappingEntity> findAll(Sort sort) {
        return new ArrayList<>();
    }

    @Override
    public Page<IdMappingEntity> findAll(Pageable pageable) {
        return Page.empty();
    }

    @Override
    public <S extends IdMappingEntity> S save(S entity) {
        entity.setId(UUID.randomUUID());
        return entity;
    }

    @Override
    public <S extends IdMappingEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return new ArrayList<>();
    }

    @Override
    public Optional<IdMappingEntity> findById(UUID id) {
        if ("54356295-8d4a-4eae-a818-b964eb5f1ccb".equals(id.toString())) { //$NON-NLS-1$
            IdMappingEntity idMappingEntity = new IdMappingEntity();
            idMappingEntity.setExternalId("flow.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']"); //$NON-NLS-1$
            idMappingEntity.setId(id);
            return Optional.of(idMappingEntity);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public Iterable<IdMappingEntity> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Iterable<IdMappingEntity> findAllById(Iterable<UUID> ids) {
        return new ArrayList<>();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID id) {
    }

    @Override
    public void delete(IdMappingEntity entity) {
    }

    @Override
    public void deleteAll(Iterable<? extends IdMappingEntity> entities) {
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public Optional<IdMappingEntity> findByExternalId(String externalId) {
        Optional<IdMappingEntity> optional = Optional.empty();
        if (externalId.equals("flow.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']")) { //$NON-NLS-1$
            IdMappingEntity idMappingEntity = new IdMappingEntity();
            idMappingEntity.setExternalId(externalId);
            idMappingEntity.setId(UUID.randomUUID());
            optional = Optional.of(idMappingEntity);
        } else if (externalId.equals("flow.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Dependencies%20Control']")) { //$NON-NLS-1$
            IdMappingEntity idMappingEntity = new IdMappingEntity();
            idMappingEntity.setExternalId(externalId);
            idMappingEntity.setId(UUID.randomUUID());
            optional = Optional.of(idMappingEntity);
        } else if (externalId.equals("flow.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Simplified%20Topography']")) { //$NON-NLS-1$
            IdMappingEntity idMappingEntity = new IdMappingEntity();
            idMappingEntity.setExternalId(externalId);
            idMappingEntity.setId(UUID.randomUUID());
            optional = Optional.of(idMappingEntity);
        }
        return optional;
    }

}
