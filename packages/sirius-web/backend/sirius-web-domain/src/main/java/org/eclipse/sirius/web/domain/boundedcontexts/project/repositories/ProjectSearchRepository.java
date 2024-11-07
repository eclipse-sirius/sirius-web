/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.project.repositories;

import java.util.Optional;

import org.eclipse.sirius.components.annotations.RepositoryFragment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Fragment interface used to search projects.
 *
 * @author sbegaudeau
 *
 * @param <T> The type of entity
 * @param <ID> The type of the identifier
 */
@RepositoryFragment
public interface ProjectSearchRepository<T, ID> {
    boolean existsById(ID id);

    Optional<T> findById(ID id);

    Page<T> findAll(Pageable pageable);
}
