/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.persistence.entities.CustomImageMetadataEntity;
import org.eclipse.sirius.web.persistence.repositories.ICustomImageMetadataRepository;
import org.eclipse.sirius.web.services.api.images.ICustomImageEditService;
import org.eclipse.sirius.web.services.api.images.RenameImageSuccessPayload;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ICustomImageEditService}.
 *
 * @author pcdavid
 */
@Service
public class CustomImageEditService implements ICustomImageEditService {

    private final ICustomImageMetadataRepository customImageMetadataRepository;

    public CustomImageEditService(ICustomImageMetadataRepository customImageMetadataRepository) {
        this.customImageMetadataRepository = Objects.requireNonNull(customImageMetadataRepository);
    }

    @Override
    public IPayload renameImage(UUID inputId, UUID imageId, String newLabel) {
        IPayload result = new ErrorPayload(inputId, "Unable to rename the image");
        Optional<CustomImageMetadataEntity> optionalImageEntity = this.customImageMetadataRepository.findById(imageId);
        if (optionalImageEntity.isPresent()) {
            CustomImageMetadataEntity imageEntity = optionalImageEntity.get();
            imageEntity.setLabel(newLabel);
            this.customImageMetadataRepository.save(imageEntity);
            result = new RenameImageSuccessPayload(inputId);
        }
        return result;
    }

    @Override
    public void delete(UUID imageId) {
        this.customImageMetadataRepository.deleteById(imageId);
    }

}
