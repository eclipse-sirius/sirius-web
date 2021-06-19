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
package org.eclipse.sirius.web.services.editingcontext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Implementation of the document repository which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpDocumentRepository implements IDocumentRepository {

    @Override
    public Iterable<DocumentEntity> findAll(Sort sort) {
        return new ArrayList<>();
    }

    @Override
    public Page<DocumentEntity> findAll(Pageable pageable) {
        return Page.empty();
    }

    @Override
    public <S extends DocumentEntity> S save(S entity) {
        return entity;
    }

    @Override
    public <S extends DocumentEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return entities;
    }

    @Override
    public Optional<DocumentEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public Iterable<DocumentEntity> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Iterable<DocumentEntity> findAllById(Iterable<UUID> ids) {
        return Collections.emptyList();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID id) {
    }

    @Override
    public void delete(DocumentEntity entity) {
    }

    @Override
    public void deleteAll(Iterable<? extends DocumentEntity> entities) {
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> ids) {
    }

    @Override
    public List<DocumentEntity> findAllByProjectId(UUID projectId) {
        return new ArrayList<>();
    }

    @Override
    public Optional<DocumentEntity> findByProjectIdAndId(UUID projectId, UUID documentId) {
        return Optional.empty();
    }

    @Override
    public Iterable<DocumentEntity> findAllByType(String name, String uri) {
        return List.of();
    }

}
