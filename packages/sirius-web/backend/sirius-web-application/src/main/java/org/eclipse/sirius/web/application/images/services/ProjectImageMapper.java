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

import org.eclipse.sirius.web.application.images.dto.ImageMetadata;
import org.eclipse.sirius.web.application.images.services.api.IProjectImageMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.ProjectImage;
import org.springframework.stereotype.Service;

/**
 * Used to convert a project image to a DTO.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectImageMapper implements IProjectImageMapper {
    @Override
    public ImageMetadata toDTO(ProjectImage projectImage) {
        return new ImageMetadata(projectImage.getId(), projectImage.getLabel(), projectImage.getId().toString());
    }
}
