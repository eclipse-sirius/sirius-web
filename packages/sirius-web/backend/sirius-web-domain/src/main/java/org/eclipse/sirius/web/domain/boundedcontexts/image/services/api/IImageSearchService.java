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
package org.eclipse.sirius.web.domain.boundedcontexts.image.services.api;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.image.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Used to find images.
 *
 * @author sbegaudeau
 */
public interface IImageSearchService {

    Optional<Image> findById(UUID id);

    Page<Image> findAll(UUID projectId, Pageable pageable);
}
