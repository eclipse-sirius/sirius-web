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

import org.eclipse.sirius.web.application.library.dto.LibraryDTO;
import org.eclipse.sirius.web.application.library.services.api.ILibraryMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.springframework.stereotype.Service;

/**
 * Used to convert a library to a DTO.
 *
 * @author gdaniel
 */
@Service
public class LibraryMapper implements ILibraryMapper {

    @Override
    public LibraryDTO toDTO(Library library) {
        return new LibraryDTO(
                library.getNamespace(),
                library.getName(),
                library.getVersion(),
                library.getDescription(),
                library.getCreatedOn()
        );
    }
}
