/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.services.projects;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.persistence.entities.ProjectNatureEntity;
import org.eclipse.sirius.web.persistence.repositories.IProjectNatureRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *Implementation of the project nature repository which does nothing.
 *
 * @author frouene
 */
public class NoOpProjectNatureRepository implements IProjectNatureRepository {

    @Override
    public Iterable<ProjectNatureEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ProjectNatureEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ProjectNatureEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<ProjectNatureEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public List<ProjectNatureEntity> findAll() {
        return List.of();
    }

    @Override
    public List<ProjectNatureEntity> findAllById(Iterable<UUID> ids) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID id) {
    }

    @Override
    public void delete(ProjectNatureEntity entity) {
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> ids) {
    }

    @Override
    public void deleteAll(Iterable<? extends ProjectNatureEntity> entities) {
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public <S extends ProjectNatureEntity> S save(S projectNatureEntity) {
        return null;
    }

    @Override
    public List<ProjectNatureEntity> findAllByProjectId(UUID projectId) {
        return null;
    }

}
