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
package org.eclipse.sirius.web.domain.boundedcontexts.library.services.api;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * Used to retrieve libraries.
 *
 * @author gdaniel
 */
public interface ILibrarySearchService {

    Page<Library> findAll(Pageable pageable);

    boolean existsByNamespaceAndNameAndVersion(String namespace, String name, String version);

    Optional<Library> findByNamespaceAndNameAndVersion(String namespace, String name, String version);

    Optional<Library> findBySemanticData(AggregateReference<SemanticData, UUID> semanticData);
}
