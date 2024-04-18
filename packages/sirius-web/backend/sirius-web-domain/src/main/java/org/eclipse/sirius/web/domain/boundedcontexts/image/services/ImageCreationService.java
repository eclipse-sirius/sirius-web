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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.image.Image;
import org.eclipse.sirius.web.domain.boundedcontexts.image.repositories.IImageRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageCreationService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to create images.
 *
 * @author sbegaudeau
 */
@Service
public class ImageCreationService implements IImageCreationService {

    private final IImageRepository imageRepository;

    private final IMessageService messageService;

    public ImageCreationService(IImageRepository imageRepository, IMessageService messageService) {
        this.imageRepository = Objects.requireNonNull(imageRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Image> createImage(UUID projectId, String label, String fileName, InputStream inputStream) {
        IResult<Image> result = null;

        var optionalContent = this.getContent(inputStream);
        var optionalContentType = this.getContentType(Path.of(fileName));

        if (optionalContent.isPresent() && optionalContentType.isPresent()) {
            var content = optionalContent.get();
            var contentType = optionalContentType.get();

            var realLabel = label;
            if (realLabel.isBlank()) {
                realLabel = fileName;
            }

            var image = Image.newImage()
                    .project(AggregateReference.to(projectId))
                    .label(realLabel)
                    .contentType(contentType)
                    .content(content)
                    .build();

            this.imageRepository.save(image);

            result = new Success<>(image);
        } else {
            result = new Failure<>(this.messageService.unexpectedError());
        }
        return result;
    }

    private Optional<String> getContentType(Path path) {
        Optional<String> optionalContentType = Optional.empty();
        try {
            String probedType = Files.probeContentType(path);
            optionalContentType = Optional.ofNullable(probedType);
        } catch (IOException exception) {
            // Do nothing on purpose
        }
        return optionalContentType;
    }

    private Optional<byte[]> getContent(InputStream inputStream) {
        Optional<byte[]> optionalContent = Optional.empty();

        try {
            optionalContent = Optional.of(inputStream.readAllBytes());
        } catch (IOException exception) {
            // Do nothing on purpose
        }

        return optionalContent;
    }
}
