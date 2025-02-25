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

import org.eclipse.sirius.web.application.library.services.api.ILibraryEditingContextApplicationService;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to interact with the editing context of libraries.
 *
 * @author sbegaudeau
 */
@Service
public class LibraryEditingContextApplicationService implements ILibraryEditingContextApplicationService {

    private final ILibrarySearchService librarySearchService;

    public LibraryEditingContextApplicationService(ILibrarySearchService librarySearchService) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
    }

    @Override
    @Transactional(readOnly = true)
    public String getCurrentEditingContextId(String namespace, String name, String version) {
        return this.librarySearchService.findByNamespaceAndNameAndVersion(namespace, name, version)
                .map(Library::getSemanticData)
                .map(AggregateReference::getId)
                .map(Object::toString)
                .orElse("");
    }
}
