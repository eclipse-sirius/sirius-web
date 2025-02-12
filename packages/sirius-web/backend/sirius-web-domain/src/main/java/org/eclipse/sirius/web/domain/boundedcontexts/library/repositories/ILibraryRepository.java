/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.library.repositories;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
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
public interface ILibraryRepository extends ListPagingAndSortingRepository<Library, UUID>, ListCrudRepository<Library, UUID> {

    @Query("""
        SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
        FROM library
        WHERE library.namespace = :namespace AND library.name = :name AND library.version = :version
        """)
    boolean existsByNamespaceAndNameAndVersion(String namespace, String name, String version);

    @Query("""
        SELECT * FROM library
        WHERE library.namespace = :namespace AND library.name = :name AND library.version = :version
        """)
    Optional<Library> findByNamespaceAndNameAndVersion(String namespace, String name, String version);
}
