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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.ProjectImage;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.repositories.IProjectImageRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.services.api.IProjectImageSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Used to find images.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectImageSearchService implements IProjectImageSearchService {

    private final IProjectImageRepository projectImageRepository;

    public ProjectImageSearchService(IProjectImageRepository projectImageRepository) {
        this.projectImageRepository = Objects.requireNonNull(projectImageRepository);
    }

    @Override
    public Optional<ProjectImage> findById(UUID id) {
        return this.projectImageRepository.findById(id);
    }

    @Override
    public Page<ProjectImage> findAll(UUID projectId, Pageable pageable) {
        var projectImages = this.projectImageRepository.findAllByProjectId(projectId, pageable);
        var count = this.projectImageRepository.countByProjectId(projectId);

        return new PageImpl<>(projectImages, pageable, count);
    }

    @Override
    public List<ProjectImage> findAll(UUID projectId) {
        return this.projectImageRepository.findAllByProjectId(projectId);
    }
}
