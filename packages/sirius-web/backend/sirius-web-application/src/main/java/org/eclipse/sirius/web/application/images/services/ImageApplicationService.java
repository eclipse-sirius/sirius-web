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
package org.eclipse.sirius.web.application.images.services;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.application.images.services.api.IImageApplicationService;
import org.eclipse.sirius.web.domain.boundedcontexts.image.Image;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application services used to manipulate images.
 *
 * @author sbegaudeau
 */
@Service
public class ImageApplicationService implements IImageApplicationService {

    private final IImageSearchService imageSearchService;

    public ImageApplicationService(IImageSearchService imageSearchService) {
        this.imageSearchService = Objects.requireNonNull(imageSearchService);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Image> findById(UUID id) {
        return this.imageSearchService.findById(id);
    }

}
