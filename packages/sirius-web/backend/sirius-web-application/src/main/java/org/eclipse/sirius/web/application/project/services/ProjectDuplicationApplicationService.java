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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.application.project.dto.DuplicateProjectInput;
import org.eclipse.sirius.web.application.project.dto.DuplicateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectDuplicationApplicationService;
import org.eclipse.sirius.web.application.project.services.api.IProjectExportService;
import org.eclipse.sirius.web.application.project.services.api.IProjectMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
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

    private final IProjectExportService exportService;

    private final ProjectZipContentProvider projectZipContentProvider;

    private final IProjectCreationService projectCreationService;

    private final IProjectMapper projectMapper;

    private final IProjectSearchService projectSearchService;

    private final IMessageService messageService;

    private final Logger logger = LoggerFactory.getLogger(ProjectDuplicationApplicationService.class);

    public ProjectDuplicationApplicationService(IProjectExportService exportService, ProjectZipContentProvider projectZipContentProvider, IProjectCreationService projectCreationService, IProjectMapper projectMapper, IProjectSearchService projectSearchService, IMessageService messageService) {
        this.exportService = Objects.requireNonNull(exportService);
        this.projectZipContentProvider = Objects.requireNonNull(projectZipContentProvider);
        this.projectCreationService = Objects.requireNonNull(projectCreationService);
        this.projectMapper = Objects.requireNonNull(projectMapper);
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    @Transactional
    public IPayload duplicateProject(DuplicateProjectInput input) {
        IPayload payload = new ErrorPayload(input.id(), "");
        var optionalProject = this.projectSearchService.findById(input.projectId());
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            byte[] content = this.exportService.export(project);
            UploadFile zipFile = new UploadFile(project.getName() + ".zip", new ByteArrayInputStream(content));

            Optional<ProjectZipContent> optionalProjectZipContent = this.projectZipContentProvider.buildFromZip(zipFile.getInputStream());
            if (optionalProjectZipContent.isPresent()) {
                ProjectZipContent projectZipContent = optionalProjectZipContent.get();

                InitializeProjectInput initializeProjectInput = new InitializeProjectInput(input.id(), input, projectZipContent);
                var natures = this.getNatures(projectZipContent.manifest().get(ProjectZipContent.NATURES));
                IResult<Project> result = this.projectCreationService.createProject(initializeProjectInput, projectZipContent.projectName() + " - Copy", natures);
                if (result instanceof Success<Project> success) {
                    payload = new DuplicateProjectSuccessPayload(input.id(), projectMapper.toDTO(success.data()));
                    this.logger.trace("The project {} has been duplicated in project {}", project.getId(), success.data().getId());
                }
            } else {
                payload = new ErrorPayload(input.id(), this.messageService.unexpectedError());
            }
        } else {
            payload = new ErrorPayload(input.id(), this.messageService.notFound());
        }

        return payload;
    }

    private List<String> getNatures(Object object) {
        return Optional.of(object).filter(List.class::isInstance)
                .map(List.class::cast)
                .stream()
                .flatMap(Collection::stream)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();
    }

}
