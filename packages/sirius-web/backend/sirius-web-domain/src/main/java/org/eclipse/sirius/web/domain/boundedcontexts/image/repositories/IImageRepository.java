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
package org.eclipse.sirius.web.domain.boundedcontexts.image.repositories;

import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.image.Image;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to persist the image aggregate.
 *
 * @author sbegaudeau
 */
@Repository
public interface IImageRepository extends ListPagingAndSortingRepository<Image, UUID>, ListCrudRepository<Image, UUID> {
    @Query("""
        SELECT
          CASE WHEN count(*) > 0 THEN true ELSE false END
        FROM image image
        WHERE image.label = :label
        """)
    boolean existsByLabel(String label);
}
