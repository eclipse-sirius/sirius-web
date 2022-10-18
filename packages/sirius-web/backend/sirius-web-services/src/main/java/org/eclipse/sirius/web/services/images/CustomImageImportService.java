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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.persistence.entities.CustomImageEntity;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.repositories.ICustomImageRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.images.ICustomImageImportService;
import org.eclipse.sirius.web.services.api.images.UploadImageSuccessPayload;
import org.springframework.stereotype.Service;

/**
 * Implementation of ICustomImageImportService.
 *
 * @author pcdavid
 */
@Service
public class CustomImageImportService implements ICustomImageImportService {
    private final IProjectRepository projectRepository;

    private final ICustomImageRepository customImageRepository;

    public CustomImageImportService(IProjectRepository projectRepository, ICustomImageRepository customImageRepository) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.customImageRepository = Objects.requireNonNull(customImageRepository);
    }

    @Override
    public IPayload importImage(UUID inputId, String editingContextId, String label, UploadFile file) {
        IPayload result = new ErrorPayload(inputId, "Error while uploading image " + file.getName()); //$NON-NLS-1$
        if (this.isImageFile(Path.of(file.getName()))) {
            try {
                CustomImageEntity customImageEntity = new CustomImageEntity();
                if (label != null && !label.isBlank()) {
                    customImageEntity.setLabel(label);
                } else {
                    customImageEntity.setLabel(this.trimFileExtension(file.getName()));
                }
                // @formatter:off
                Optional<ProjectEntity> optionalProject = Optional.ofNullable(editingContextId)
                        .flatMap(projectId -> new IDParser().parse(projectId))
                        .flatMap(this.projectRepository::findById);
                if (optionalProject.isPresent()) {
                    customImageEntity.setProject(optionalProject.get());
                }
                // @formatter:on
                customImageEntity.setContentType(Files.probeContentType(Path.of(file.getName())));
                customImageEntity.setContent(file.getInputStream().readAllBytes());
                customImageEntity.setId(UUID.randomUUID());
                customImageEntity = this.customImageRepository.save(customImageEntity);
                result = new UploadImageSuccessPayload(inputId, customImageEntity.getId());
            } catch (IOException e) {
                result = new ErrorPayload(inputId, String.format("Error while uploading image %s: %s", file.getName(), e.getMessage())); //$NON-NLS-1$
            }
        }
        return result;
    }

    private boolean isImageFile(Path path) {
        try {
            String probedType = Files.probeContentType(path);
            return probedType != null && probedType.startsWith("image/"); //$NON-NLS-1$
        } catch (IOException ioe) {
            return false;
        }
    }

    private String trimFileExtension(String fileName) {
        int extensionStart = fileName.lastIndexOf('.');
        if (extensionStart != -1) {
            return fileName.substring(0, extensionStart);
        } else {
            return fileName;
        }
    }
}
