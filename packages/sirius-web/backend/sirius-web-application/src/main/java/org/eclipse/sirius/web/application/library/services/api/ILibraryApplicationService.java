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
package org.eclipse.sirius.web.application.library.services.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.library.api.IPublishLibraryInput;
import org.eclipse.sirius.web.application.library.dto.LibraryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Application services used to manipulate libraries.
 *
 * @author gdaniel
 */
public interface ILibraryApplicationService {

    Page<LibraryDTO> findAll(Pageable pageable);

    Optional<LibraryDTO> findByNamespaceAndNameAndVersion(String namespace, String name, String version);

    Page<LibraryDTO> findByNamespaceAndName(String namespace, String name, Pageable pageable);

    IPayload publishLibraries(IPublishLibraryInput input);
}
