/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.images.services.api;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.images.dto.DeleteImageInput;
import org.eclipse.sirius.web.application.images.dto.ImageMetadata;
import org.eclipse.sirius.web.application.images.dto.RenameImageInput;
import org.eclipse.sirius.web.application.images.dto.UploadImageInput;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.ProjectImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Application services used to manipulate project images.
 *
 * @author sbegaudeau
 */
public interface IProjectImageApplicationService {

    Optional<ProjectImage> findById(UUID id);

    Page<ImageMetadata> findAll(String projectId, Pageable pageable);

    IPayload uploadImage(UploadImageInput input);

    IPayload renameImage(RenameImageInput input);

    IPayload deleteImage(DeleteImageInput input);
}
