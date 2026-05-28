/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ProjectImageApplicationService implements IProjectImageApplicationService {

    private final IProjectImageSearchService projectImageSearchService;

    private final IProjectImageCreationService projectImageCreationService;

    private final IProjectImageUpdateService projectImageUpdateService;

    private final IProjectImageDeletionService projectImageDeletionService;

    private final IProjectImageMapper projectImageMapper;

    private final Logger logger = LoggerFactory.getLogger(ProjectImageApplicationService.class);

    public ProjectImageApplicationService(IProjectImageSearchService projectImageSearchService, IProjectImageCreationService projectImageCreationService,
            IProjectImageUpdateService projectImageUpdateService, IProjectImageDeletionService projectImageDeletionService, IProjectImageMapper projectImageMapper) {
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
    public Page<ImageMetadata> findAll(String projectId, Pageable pageable) {
        return this.projectImageSearchService.findAll(projectId, pageable).map(this.projectImageMapper::toDTO);
    }

    @Override
    @Transactional
    public IPayload uploadImage(UploadImageInput input) {
        var result = this.projectImageCreationService.createProjectImage(input, input.projectId(), input.label(), input.file().getName(), input.file().getInputStream());

        return switch (result) {
            case Failure<ProjectImage>(var message) -> {
                this.logger.atWarn()
                        .setMessage("Image upload failed")
                        .addKeyValue("projectId", input.projectId())
                        .log();

                yield new ErrorPayload(input.id(), message);
            }
            case Success<ProjectImage>(var data) -> {
                this.logger.atInfo()
                        .setMessage("Image {} uploaded")
                        .addArgument(data.getId())
                        .addKeyValue("projectId", input.projectId())
                        .addKeyValue("imageId", data.getId())
                        .log();

                yield new UploadImageSuccessPayload(input.id(), data.getId());
            }
        };
    }

    @Override
    @Transactional
    public IPayload renameImage(RenameImageInput input) {
        var result = this.projectImageUpdateService.renameProjectImage(input, input.imageId(), input.newLabel());

        return switch (result) {
            case Failure<Void>(var message) -> {
                this.logger.atWarn()
                        .setMessage("Rename of the image {} failed")
                        .addArgument(input.imageId())
                        .addKeyValue("imageId", input.imageId())
                        .log();

                yield new ErrorPayload(input.id(), message);
            }
            case Success<Void> success -> {
                this.logger.atInfo()
                        .setMessage("Image {} renamed")
                        .addArgument(input.imageId())
                        .addKeyValue("imageId", input.imageId())
                        .log();

                yield new SuccessPayload(input.id());
            }
        };
    }

    @Override
    @Transactional
    public IPayload deleteImage(DeleteImageInput input) {
        var result = this.projectImageDeletionService.deleteProjectImage(input, input.imageId());

        return switch (result) {
            case Failure<Void>(var message) -> {
                this.logger.atWarn()
                        .setMessage("Deletion of the image {} failed")
                        .addArgument(input.imageId())
                        .addKeyValue("imageId", input.imageId())
                        .log();

                yield new ErrorPayload(input.id(), message);
            }
            case Success<Void> success -> {
                this.logger.atInfo()
                        .setMessage("Image {} deleted")
                        .addArgument(input.imageId())
                        .addKeyValue("imageId", input.imageId())
                        .log();

                yield new SuccessPayload(input.id());
            }
        };
    }
}
