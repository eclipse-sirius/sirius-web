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
package org.eclipse.sirius.web.domain.boundedcontexts.library.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.repositories.ILibraryRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve libraries.
 *
 * @author gdaniel
 */
@Service
public class LibrarySearchService implements ILibrarySearchService {

    private final ILibraryRepository libraryRepository;

    public LibrarySearchService(ILibraryRepository libraryRepository) {
        this.libraryRepository = Objects.requireNonNull(libraryRepository);
    }

    @Override
    public Page<Library> findAll(Pageable pageable) {
        return this.libraryRepository.findAll(pageable);
    }

    @Override
    public boolean existsByNamespaceAndNameAndVersion(String namespace, String name, String version) {
        return this.libraryRepository.existsByNamespaceAndNameAndVersion(namespace, name, version);
    }

    @Override
    public Optional<Library> findByNamespaceAndNameAndVersion(String namespace, String name, String version) {
        return this.libraryRepository.findByNamespaceAndNameAndVersion(namespace, name, version);
    }

    @Override
    public Optional<Library> findBySemanticData(AggregateReference<SemanticData, UUID> semanticData) {
        return this.libraryRepository.findBySemanticDataId(semanticData.getId());
    }

    @Override
    public Page<Library> findAllByNamespaceAndName(String namespace, String name, Pageable pageable) {
        return this.libraryRepository.findAllByNamespaceAndName(namespace, name, pageable);
    }

    @Override
    public List<Library> findAllById(Iterable<UUID> ids) {
        return this.libraryRepository.findAllById(ids);
    }

    @Override
    public Optional<Library> findById(UUID id) {
        return this.libraryRepository.findById(id);
    }
}
