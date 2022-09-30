/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.web.services.images;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.view.emf.CustomImage;
import org.eclipse.sirius.components.view.emf.ICustomImageSearchService;
import org.eclipse.sirius.web.persistence.entities.CustomImageEntity;
import org.eclipse.sirius.web.persistence.repositories.ICustomImageRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation of the service used to find existing custom images.
 *
 * @author pcdavid
 */
@Service
public class CustomImageSearchService implements ICustomImageSearchService {
    private final ICustomImageRepository customImageRepository;

    public CustomImageSearchService(ICustomImageRepository customImageRepository) {
        this.customImageRepository = Objects.requireNonNull(customImageRepository);
    }

    @Override
    public List<CustomImage> getAvailableImages(String editingContextId) {
        return this.customImageRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CustomImage toDTO(CustomImageEntity customImageEntity) {
        return new CustomImage(customImageEntity.getId(), customImageEntity.getLabel(), customImageEntity.getContentType());
    }

}
