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

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.image.Image;
import org.eclipse.sirius.web.domain.boundedcontexts.image.repositories.IImageRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Optional<Image> findById(UUID id) {
        return this.imageRepository.findById(id);
    }

    @Override
    public Page<Image> findAll(UUID projectId, Pageable pageable) {
        var images = this.imageRepository.findAllByProjectId(projectId, pageable);
        var count = this.imageRepository.countByProjectId(projectId);

        return new PageImpl<>(images, pageable, count);
    }
}
