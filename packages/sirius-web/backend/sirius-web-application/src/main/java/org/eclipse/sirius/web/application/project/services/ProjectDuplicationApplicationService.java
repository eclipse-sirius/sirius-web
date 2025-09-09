/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.project.services;

import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.application.project.dto.DuplicateProjectInput;
import org.eclipse.sirius.web.application.project.dto.DuplicateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.InitProjectContentInput;
import org.eclipse.sirius.web.application.project.dto.ProjectZipContent;
import org.eclipse.sirius.web.application.project.services.api.IProjectDuplicationApplicationService;
import org.eclipse.sirius.web.application.project.services.api.IProjectExportService;
import org.eclipse.sirius.web.application.project.services.api.IProjectMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service used to duplicate a project.
 *
 * @author Arthur Daussy
 */
@Service
public class ProjectDuplicationApplicationService implements IProjectDuplicationApplicationService {

    private final Logger logger = LoggerFactory.getLogger(ProjectDuplicationApplicationService.class);

    private final IProjectExportService exportService;

    private final ZipProjectContentBuilder importProjectContentBuilder;

    private final IProjectCreationService projectCreationService;

    private final IProjectMapper projectMapper;

    private final IProjectSearchService projectSearchService;

    public ProjectDuplicationApplicationService(IProjectExportService exportService, ZipProjectContentBuilder importProjectContentBuilder, IProjectCreationService projectCreationService, IProjectMapper projectMapper, IProjectSearchService projectSearchService) {
        this.exportService = Objects.requireNonNull(exportService);
        this.importProjectContentBuilder = Objects.requireNonNull(importProjectContentBuilder);
        this.projectCreationService = Objects.requireNonNull(projectCreationService);
        this.projectMapper = Objects.requireNonNull(projectMapper);
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
    }

    @Override
    @Transactional
    public IPayload duplicateProject(DuplicateProjectInput input) {
        IPayload payload = new ErrorPayload(input.id(), "");
        var optProject = projectSearchService.findById(input.projectId());
        if (optProject.isPresent()) {
            Project project = optProject.get();
            byte[] content = exportService.export(project);
            UploadFile zipFile = new UploadFile(project.getName() + ".zip", new ByteArrayInputStream(content));

            Optional<ProjectZipContent> optProjectStructure = importProjectContentBuilder.buildFromZip(zipFile.getInputStream());
            if (optProjectStructure.isPresent()) {
                ProjectZipContent projectStructure = optProjectStructure.get();
                InitProjectContentInput projectContentInput = new InitProjectContentInput(UUID.randomUUID(), input, projectStructure);
                IResult<Project> result = this.projectCreationService.createProject(projectContentInput, projectStructure.getName() + " - Copy", projectStructure.getNatures());
                if (result instanceof Success<Project> success) {
                    payload = new DuplicateProjectSuccessPayload(input.id(), projectMapper.toDTO(success.data()));
                    logger.trace("Project {} duplicated in project {}", project.getId(), success.data().getId());
                }
            } else {
                payload = new ErrorPayload(input.id(), "Unable to import project : Faile to read project structure");
            }
        } else {
            payload = new ErrorPayload(input.id(), "Unable to project with id :" + input.projectId());
        }

        return payload;
    }

}
