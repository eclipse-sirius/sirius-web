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
import org.eclipse.sirius.web.application.images.services.api.IProjectImageApplicationService;
import org.eclipse.sirius.web.application.images.services.api.IProjectImageMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.ProjectImage;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.services.api.IProjectImageCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.services.api.IProjectImageDeletionService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.services.api.IProjectImageSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.services.api.IProjectImageUpdateService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application services used to manipulate project images.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectImageApplicationService implements IProjectImageApplicationService {

    private final IProjectImageSearchService projectImageSearchService;

    private final IProjectImageCreationService projectImageCreationService;

    private final IProjectImageUpdateService projectImageUpdateService;

    private final IProjectImageDeletionService projectImageDeletionService;

    private final IProjectImageMapper projectImageMapper;

    public ProjectImageApplicationService(IProjectImageSearchService projectImageSearchService, IProjectImageCreationService projectImageCreationService, IProjectImageUpdateService projectImageUpdateService, IProjectImageDeletionService projectImageDeletionService, IProjectImageMapper projectImageMapper) {
        this.projectImageSearchService = Objects.requireNonNull(projectImageSearchService);
        this.projectImageCreationService = Objects.requireNonNull(projectImageCreationService);
        this.projectImageUpdateService = Objects.requireNonNull(projectImageUpdateService);
        this.projectImageDeletionService = Objects.requireNonNull(projectImageDeletionService);
        this.projectImageMapper = Objects.requireNonNull(projectImageMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectImage> findById(UUID id) {
        return this.projectImageSearchService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImageMetadata> findAll(UUID projectId, Pageable pageable) {
        return this.projectImageSearchService.findAll(projectId, pageable).map(this.projectImageMapper::toDTO);
    }

    @Override
    @Transactional
    public IPayload uploadImage(UploadImageInput input) {
        var result = this.projectImageCreationService.createProjectImage(input.projectId(), input.label(), input.file().getName(), input.file().getInputStream());

        IPayload payload = null;
        if (result instanceof Failure<ProjectImage> failure) {
            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<ProjectImage> success) {
            payload = new UploadImageSuccessPayload(input.id(), success.data().getId());
        }
        return payload;
    }

    @Override
    @Transactional
    public IPayload renameImage(RenameImageInput input) {
        var result = this.projectImageUpdateService.renameProjectImage(input.imageId(), input.newLabel());

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
        var result = this.projectImageDeletionService.deleteProjectImage(input.imageId());

        IPayload payload = null;
        if (result instanceof Failure<Void> failure) {
            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<Void>) {
            payload = new SuccessPayload(input.id());
        }
        return payload;
    }
}
