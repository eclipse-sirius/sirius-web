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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.library.dto.LibraryDTO;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.application.library.services.api.ILibraryApplicationService;
import org.eclipse.sirius.web.application.library.services.api.ILibraryMapper;
import org.eclipse.sirius.web.application.library.services.api.ILibraryPublicationHandler;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final List<ILibraryPublicationHandler> libraryPublicationHandlers;

    private final IMessageService messageService;

    private final Logger logger = LoggerFactory.getLogger(LibraryApplicationService.class);

    public LibraryApplicationService(ILibrarySearchService librarySearchService, ILibraryMapper libraryMapper, List<ILibraryPublicationHandler> libraryPublicationHandlers, IMessageService messageService) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.libraryMapper = Objects.requireNonNull(libraryMapper);
        this.libraryPublicationHandlers = Objects.requireNonNull(libraryPublicationHandlers);
        this.messageService = Objects.requireNonNull(messageService);
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

    @Override
    @Transactional(readOnly = true)
    public Page<LibraryDTO> findByNamespaceAndName(String namespace, String name, Pageable pageable) {
        return this.librarySearchService.findAllByNamespaceAndName(namespace, name, pageable).map(this.libraryMapper::toDTO);
    }

    @Override
    @Transactional
    public IPayload publishLibraries(PublishLibrariesInput input) {
        IPayload payload = new ErrorPayload(input.id(), this.messageService.unexpectedError());
        Optional<ILibraryPublicationHandler> optionalHandler = this.libraryPublicationHandlers.stream()
            .filter(handler -> handler.canHandle(input))
            .findFirst();
        if (optionalHandler.isPresent()) {
            payload = optionalHandler.get().handle(input);
        } else {
            this.logger.warn("No handler found for event: {}", input);
        }
        return payload;
    }
}
