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
package org.eclipse.sirius.web.domain.boundedcontexts.projectimage.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.ProjectImage;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.repositories.IProjectImageRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.services.api.IProjectImageCreationService;
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
public class ProjectImageCreationService implements IProjectImageCreationService {

    private final IProjectImageRepository projectImageRepository;

    private final IMessageService messageService;

    public ProjectImageCreationService(IProjectImageRepository projectImageRepository, IMessageService messageService) {
        this.projectImageRepository = Objects.requireNonNull(projectImageRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<ProjectImage> createProjectImage(UUID projectId, String label, String fileName, InputStream inputStream) {
        IResult<ProjectImage> result = null;

        var optionalContent = this.getContent(inputStream);
        var optionalContentType = this.getContentType(Path.of(fileName));

        if (optionalContent.isPresent() && optionalContentType.isPresent()) {
            var content = optionalContent.get();
            var contentType = optionalContentType.get();

            var realLabel = label;
            if (realLabel.isBlank()) {
                realLabel = fileName;
            }

            var projectImage = ProjectImage.newProjectImage()
                    .project(AggregateReference.to(projectId))
                    .label(realLabel)
                    .contentType(contentType)
                    .content(content)
                    .build();

            this.projectImageRepository.save(projectImage);

            result = new Success<>(projectImage);
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
