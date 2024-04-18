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

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.application.images.dto.DeleteImageInput;
import org.eclipse.sirius.web.application.images.dto.ImageMetadata;
import org.eclipse.sirius.web.application.images.dto.RenameImageInput;
import org.eclipse.sirius.web.application.images.dto.UploadImageInput;
import org.eclipse.sirius.web.application.images.dto.UploadImageSuccessPayload;
import org.eclipse.sirius.web.application.images.services.api.IImageApplicationService;
import org.eclipse.sirius.web.application.images.services.api.IImageMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.image.Image;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageDeletionService;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application services used to manipulate images.
 *
 * @author sbegaudeau
 */
@Service
public class ImageApplicationService implements IImageApplicationService {

    private final IProjectSearchService projectSearchService;

    private final IImageSearchService imageSearchService;

    private final IImageCreationService imageCreationService;

    private final IImageUpdateService imageUpdateService;

    private final IImageDeletionService imageDeletionService;

    private final IImageMapper imageMapper;

    private final IMessageService messageService;

    public ImageApplicationService(IProjectSearchService projectSearchService, IImageSearchService imageSearchService, IImageCreationService imageCreationService, IImageUpdateService imageUpdateService, IImageDeletionService imageDeletionService, IImageMapper imageMapper, IMessageService messageService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.imageSearchService = Objects.requireNonNull(imageSearchService);
        this.imageCreationService = Objects.requireNonNull(imageCreationService);
        this.imageUpdateService = Objects.requireNonNull(imageUpdateService);
        this.imageDeletionService = Objects.requireNonNull(imageDeletionService);
        this.imageMapper = Objects.requireNonNull(imageMapper);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Image> findById(UUID id) {
        return this.imageSearchService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImageMetadata> findAll(UUID projectId, Pageable pageable) {
        return this.imageSearchService.findAll(projectId, pageable).map(this.imageMapper::toDTO);
    }

    @Override
    @Transactional
    public IPayload uploadImage(UploadImageInput input) {
        var result = this.imageCreationService.createImage(input.projectId(), input.label(), input.file().getName(), input.file().getInputStream());

        IPayload payload = null;
        if (result instanceof Failure<Image> failure) {
            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<Image> success) {
            payload = new UploadImageSuccessPayload(input.id(), success.data().getId());
        }
        return payload;
    }

    @Override
    @Transactional
    public IPayload renameImage(RenameImageInput input) {
        var result = this.imageUpdateService.renameImage(input.imageId(), input.newLabel());

        IPayload payload = null;
        if (result instanceof Failure<Void> failure) {
            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<Void>) {
            payload = new SuccessPayload(input.id());
        }
        return payload;
    }

    @Override
    @Transactional
    public IPayload deleteImage(DeleteImageInput input) {
        var result = this.imageDeletionService.deleteImage(input.imageId());

        IPayload payload = null;
        if (result instanceof Failure<Void> failure) {
            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<Void>) {
            payload = new SuccessPayload(input.id());
        }
        return payload;
    }
}
