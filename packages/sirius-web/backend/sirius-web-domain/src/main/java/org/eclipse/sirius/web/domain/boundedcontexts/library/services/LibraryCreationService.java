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

import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.repositories.ILibraryRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibraryCreationService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.stereotype.Service;

/**
 * Used to create libraries.
 *
 * @author sbegaudeau
 */
@Service
public class LibraryCreationService implements ILibraryCreationService {

    private final ILibraryRepository libraryRepository;

    public LibraryCreationService(ILibraryRepository libraryRepository) {
        this.libraryRepository = Objects.requireNonNull(libraryRepository);
    }

    @Override
    public IResult<Library> createLibrary(Library library) {
        this.libraryRepository.save(library);
        return new Success<>(library);
    }
}
