/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.persistence.entities.CustomImageEntity;
import org.eclipse.sirius.web.persistence.repositories.ICustomImageRepository;
import org.eclipse.sirius.web.services.api.images.ICustomImageContentService;
import org.springframework.stereotype.Service;

/**
 * Implementation of the service used to retrieve the content of custom images.
 *
 * @author pcdavid
 */
@Service
public class CustomImageContentService implements ICustomImageContentService {
    private final ICustomImageRepository customImageRepository;

    public CustomImageContentService(ICustomImageRepository customImageRepository) {
        this.customImageRepository = Objects.requireNonNull(customImageRepository);
    }

    @Override
    public Optional<byte[]> getImageContentById(UUID editingContextId, UUID imageId) {
        return this.customImageRepository.findById(imageId).map(CustomImageEntity::getContent);
    }

    @Override
    public Optional<String> getImageContentTypeById(UUID editingContextId, UUID imageId) {
        return this.customImageRepository.findById(imageId).map(CustomImageEntity::getContentType);
    }
}
