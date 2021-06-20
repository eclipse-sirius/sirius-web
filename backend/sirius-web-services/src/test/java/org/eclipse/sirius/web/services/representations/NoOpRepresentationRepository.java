/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.services.representations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.persistence.entities.RepresentationEntity;
import org.eclipse.sirius.web.persistence.repositories.IRepresentationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * Implementation of the representation repository which does nothing.
 *
 * @author gcoutable
 */
public class NoOpRepresentationRepository implements IRepresentationRepository {

    @Override
    public Iterable<RepresentationEntity> findAll(Sort sort) {
        return new ArrayList<>();
    }

    @Override
    public Page<RepresentationEntity> findAll(Pageable pageable) {
        return Page.empty();
    }

    @Override
    public <S extends RepresentationEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return entities;
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public Iterable<RepresentationEntity> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Iterable<RepresentationEntity> findAllById(Iterable<UUID> ids) {
        return new ArrayList<>();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(RepresentationEntity entity) {
    }

    @Override
    public void deleteAll(Iterable<? extends RepresentationEntity> entities) {
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> ids) {
    }

    @Override
    public Optional<RepresentationEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<RepresentationEntity> findByIdAndProjectId(UUID id, UUID projectId) {
        return Optional.empty();
    }

    @Override
    public List<RepresentationEntity> findAllByTargetObjectId(String objectId) {
        return new ArrayList<>();
    }

    @Override
    public List<RepresentationEntity> findAllByProjectId(UUID projectId) {
        return new ArrayList<>();
    }

    @Override
    public boolean hasRepresentations(String objectId) {
        return false;
    }

    @Override
    public <S extends RepresentationEntity> S save(S representationEntity) {
        return representationEntity;
    }

    @Override
    public void deleteById(UUID id) {
    }

    @Override
    public int deleteDanglingRepresentations(UUID projectId) {
        return 0;
    }

}
