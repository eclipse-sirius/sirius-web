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
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.image.repositories.IImageRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageUpdateService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to update images.
 *
 * @author sbegaudeau
 */
@Service
public class ImageUpdateService implements IImageUpdateService {

    private final IImageRepository imageRepository;

    private final IMessageService messageService;

    public ImageUpdateService(IImageRepository imageRepository, IMessageService messageService) {
        this.imageRepository = Objects.requireNonNull(imageRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> renameImage(UUID imageId, String newLabel) {
        IResult<Void> result = null;

        var optionalImage = this.imageRepository.findById(imageId);

        var sanitizedName = newLabel.trim();
        if (sanitizedName.isBlank()) {
            result = new Failure<>(this.messageService.invalidName());
        } else if (optionalImage.isEmpty()) {
            result = new Failure<>(this.messageService.notFound());
        } else {
            var image = optionalImage.get();
            image.updateLabel(newLabel);

            this.imageRepository.save(image);
            result = new Success<>(null);
        }

        return result;
    }
}
