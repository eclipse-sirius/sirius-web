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

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.repositories.IProjectImageRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.services.api.IProjectImageDeletionService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to delete project images.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectImageDeletionService implements IProjectImageDeletionService {

    private final IProjectImageRepository projectImageRepository;

    private final IMessageService messageService;

    public ProjectImageDeletionService(IProjectImageRepository projectImageRepository, IMessageService messageService) {
        this.projectImageRepository = Objects.requireNonNull(projectImageRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> deleteProjectImage(UUID projectImageId) {
        IResult<Void> result = null;

        var optionalProjectImage = this.projectImageRepository.findById(projectImageId);
        if (optionalProjectImage.isPresent()) {
            var projectImage = optionalProjectImage.get();
            projectImage.dispose();

            this.projectImageRepository.delete(projectImage);
            result = new Success<>(null);
        } else {
            result = new Failure<>(this.messageService.notFound());
        }

        return result;
    }
}
