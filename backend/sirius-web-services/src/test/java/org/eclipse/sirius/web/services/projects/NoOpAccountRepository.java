/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.persistence.entities.AccountEntity;
import org.eclipse.sirius.web.persistence.repositories.IAccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Implementation of the account repository which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpAccountRepository implements IAccountRepository {

    @Override
    public Iterable<AccountEntity> findAll(Sort sort) {
        return new ArrayList<>();
    }

    @Override
    public Page<AccountEntity> findAll(Pageable pageable) {
        return Page.empty();
    }

    @Override
    public <S extends AccountEntity> S save(S entity) {
        return entity;
    }

    @Override
    public <S extends AccountEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return new ArrayList<>();
    }

    @Override
    public Optional<AccountEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public List<AccountEntity> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Iterable<AccountEntity> findAllById(Iterable<UUID> ids) {
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
    public void delete(AccountEntity entity) {
    }

    @Override
    public void deleteAll(Iterable<? extends AccountEntity> entities) {
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> ids) {
    }

    @Override
    public Optional<AccountEntity> findByUsername(String userName) {
        return Optional.empty();
    }

}
