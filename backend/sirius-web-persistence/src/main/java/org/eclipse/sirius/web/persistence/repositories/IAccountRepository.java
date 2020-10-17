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
package org.eclipse.sirius.web.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Audited;
import org.eclipse.sirius.web.persistence.entities.AccountEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer used to manipulate accounts.
 *
 * @author sbegaudeau
 */
@Repository
public interface IAccountRepository extends PagingAndSortingRepository<AccountEntity, UUID> {

    @Audited
    Optional<AccountEntity> findByUsername(String username);

    @Audited
    @Override
    List<AccountEntity> findAll();
}
