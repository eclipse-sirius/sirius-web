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
package org.eclipse.sirius.web.domain.boundedcontexts.image.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.image.Image;
import org.eclipse.sirius.web.domain.boundedcontexts.image.repositories.IImageRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to find images.
 *
 * @author sbegaudeau
 */
@Service
public class ImageSearchService implements IImageSearchService {

    private final IImageRepository imageRepository;

    public ImageSearchService(IImageRepository imageRepository) {
        this.imageRepository = Objects.requireNonNull(imageRepository);
    }

    @Override
    public boolean existsByLabel(String label) {
        return this.imageRepository.existsByLabel(label);
    }

    @Override
    public List<Image> findAll() {
        return this.imageRepository.findAll();
    }
}
