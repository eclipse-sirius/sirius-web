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
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageDeletionService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to delete images.
 *
 * @author sbegaudeau
 */
@Service
public class ImageDeletionService implements IImageDeletionService {

    private final IImageRepository imageRepository;

    private final IMessageService messageService;

    public ImageDeletionService(IImageRepository imageRepository, IMessageService messageService) {
        this.imageRepository = Objects.requireNonNull(imageRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> deleteImage(UUID imageId) {
        IResult<Void> result = null;

        var optionalImage = this.imageRepository.findById(imageId);
        if (optionalImage.isPresent()) {
            var image = optionalImage.get();
            image.dispose();

            this.imageRepository.delete(image);
            result = new Success<>(null);
        } else {
            result = new Failure<>(this.messageService.notFound());
        }

        return result;
    }
}
