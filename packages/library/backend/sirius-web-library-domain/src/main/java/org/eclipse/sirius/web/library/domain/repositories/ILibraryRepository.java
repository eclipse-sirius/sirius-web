/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.library.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.library.domain.Library;
import org.eclipse.sirius.web.library.domain.LibraryId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to persist the library aggregate.
 *
 * @author gdaniel
 */
@Repository
public interface ILibraryRepository extends ListPagingAndSortingRepository<Library, LibraryId>, ListCrudRepository<Library, LibraryId> {

    Page<Library> findAllByNamespaceAndName(String namespace, String name, Pageable pageable);

    @Query("""
        SELECT * FROM library
        WHERE library.semantic_data_id = :semanticDataId
        """)
    Optional<Library> findBySemanticDataId(UUID semanticDataId);
}
