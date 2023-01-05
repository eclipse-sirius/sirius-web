/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.view.emf.CustomImageMetadata;
import org.eclipse.sirius.components.view.emf.ICustomImageMetadataSearchService;
import org.eclipse.sirius.web.persistence.entities.CustomImageMetadataEntity;
import org.eclipse.sirius.web.persistence.repositories.ICustomImageMetadataRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.projects.ProjectMapper;
import org.springframework.stereotype.Service;

/**
 * Implementation of the service used to find existing custom images metadata.
 *
 * @author pcdavid
 */
@Service
public class CustomImageMetadataSearchService implements ICustomImageMetadataSearchService {
    private final ICustomImageMetadataRepository customImageMetadataRepository;

    public CustomImageMetadataSearchService(ICustomImageMetadataRepository customImageMetadataRepository) {
        this.customImageMetadataRepository = Objects.requireNonNull(customImageMetadataRepository);
    }

    @Override
    public List<CustomImageMetadata> getAvailableImages(String editingContextId) {
        List<CustomImageMetadataEntity> allImages = new ArrayList<>();
        // Global images (not owned to a particular project)
        allImages.addAll(this.customImageMetadataRepository.findAllByProjectId(null));
        // Project-specific images
        allImages.addAll(new IDParser().parse(editingContextId).map(this.customImageMetadataRepository::findAllByProjectId).orElse(List.of()));
        return allImages.stream().map(this::toDTO).toList();
    }

    private CustomImageMetadata toDTO(CustomImageMetadataEntity customImageMetadataEntity) {
        // @formatter:off
        Optional<String> optionalProjectId = Optional.ofNullable(customImageMetadataEntity.getProject())
                .map(projectEntity -> new ProjectMapper().toDTO(projectEntity))
                .map(Project::getId)
                .map(UUID::toString);
        // @formatter:on
        return new CustomImageMetadata(customImageMetadataEntity.getId(), optionalProjectId, customImageMetadataEntity.getLabel(), customImageMetadataEntity.getContentType());
    }

}
