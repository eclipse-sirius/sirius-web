/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.persistence.entities.CustomImageMetadataEntity;
import org.eclipse.sirius.web.persistence.repositories.ICustomImageMetadataRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.images.CustomImageMetadata;
import org.eclipse.sirius.web.services.api.images.IProjectCustomImageMetadataSearchService;
import org.springframework.stereotype.Service;

/**
 * Implementation of the service used to find existing custom images metadata for a given project.
 *
 * @author pcdavid
 */
@Service
public class ProjectCustomImageMetadataSearchService implements IProjectCustomImageMetadataSearchService {
    private final ICustomImageMetadataRepository customImageMetadataRepository;

    public ProjectCustomImageMetadataSearchService(ICustomImageMetadataRepository customImageMetadataRepository) {
        this.customImageMetadataRepository = Objects.requireNonNull(customImageMetadataRepository);
    }

    @Override
    public List<CustomImageMetadata> getProjectImages(String editingContextId) {
        // @formatter:off
        return new IDParser().parse(editingContextId)
                .map(this.customImageMetadataRepository::findAllByProjectId)
                .orElse(List.of())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        // @formatter:on
    }

    private CustomImageMetadata toDTO(CustomImageMetadataEntity customImageMetadataEntity) {
        String url = String.format("/custom/%s", customImageMetadataEntity.getId().toString()); //$NON-NLS-1$
        return new CustomImageMetadata(customImageMetadataEntity.getId(), customImageMetadataEntity.getLabel(), url, customImageMetadataEntity.getContentType());
    }

}
