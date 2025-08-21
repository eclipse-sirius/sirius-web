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

import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.library.services.api.ILibraryEditingContextService;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to convert library id into editing context id and vice versa.
 *
 * @author gcoutable
 */
@Service
public class LibraryEditingContextService implements ILibraryEditingContextService {

    private final ILibrarySearchService librarySearchService;

    public LibraryEditingContextService(ILibrarySearchService librarySearchService) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
    }

    @Override
    public Optional<String> getLibraryIdentifier(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .flatMap(uuid -> this.librarySearchService.findBySemanticData(AggregateReference.to(uuid)))
                .map(library -> String.format("%s:%s:%s", library.getNamespace(), library.getName(), library.getVersion()));
    }
}
