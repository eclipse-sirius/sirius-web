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
package org.eclipse.sirius.web.application.library.services;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.application.library.dto.LibraryDTO;
import org.eclipse.sirius.web.application.library.services.api.ILibraryApplicationService;
import org.eclipse.sirius.web.application.library.services.api.ILibraryMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application services used to manipulate libraries.
 *
 * @author gdaniel
 */
@Service
public class LibraryApplicationService implements ILibraryApplicationService {

    private final ILibrarySearchService librarySearchService;

    private final ILibraryMapper libraryMapper;

    public LibraryApplicationService(ILibrarySearchService librarySearchService, ILibraryMapper libraryMapper) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.libraryMapper = Objects.requireNonNull(libraryMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LibraryDTO> findAll(Pageable pageable) {
        return this.librarySearchService.findAll(pageable).map(this.libraryMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LibraryDTO> findByNamespaceAndNameAndVersion(String namespace, String name, String version) {
        return this.librarySearchService.findByNamespaceAndNameAndVersion(namespace, name, version).map(this.libraryMapper::toDTO);
    }


}
