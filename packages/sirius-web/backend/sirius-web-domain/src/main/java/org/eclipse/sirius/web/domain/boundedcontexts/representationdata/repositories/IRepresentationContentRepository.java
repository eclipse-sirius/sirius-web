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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationContent;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to persist the representation content aggregate.
 *
 * @author gcoutable
 */
@Repository
public interface IRepresentationContentRepository extends ListPagingAndSortingRepository<RepresentationContent, UUID>, ListCrudRepository<RepresentationContent, UUID> {

    @Query("""
        SELECT representationContent.*
        FROM representation_content representationContent
        WHERE representationContent.representation_metadata_id = :representationMetadataId
        """)
    Optional<RepresentationContent> findContentByRepresentationMetadataId(UUID representationMetadataId);

}
