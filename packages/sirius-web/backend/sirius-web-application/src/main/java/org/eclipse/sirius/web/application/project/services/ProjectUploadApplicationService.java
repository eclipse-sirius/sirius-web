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
package org.eclipse.sirius.web.application.project.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.dto.UploadProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectMapper;
import org.eclipse.sirius.web.application.project.services.api.IProjectUploadApplicationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectCreationService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service used to import a project.
 *
 * @author gcoutable
 */
@Service
public class ProjectUploadApplicationService implements IProjectUploadApplicationService {

    private final ProjectZipContentProvider projectZipContentProvider;

    private final IProjectCreationService projectCreationService;

    private final IProjectMapper projectMapper;

    private final IMessageService messageService;

    public ProjectUploadApplicationService(ProjectZipContentProvider projectZipContentProvider, IProjectCreationService projectCreationService, IProjectMapper projectMapper, IMessageService messageService) {
        this.projectZipContentProvider = Objects.requireNonNull(projectZipContentProvider);
        this.projectCreationService = Objects.requireNonNull(projectCreationService);
        this.projectMapper = Objects.requireNonNull(projectMapper);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    @Transactional
    public IPayload uploadProject(UploadProjectInput input) {
        IPayload payload = new ErrorPayload(input.id(), "");
        Optional<ProjectZipContent> optionalProjectZipContent = this.projectZipContentProvider.buildFromZip(input.file().getInputStream());
        if (optionalProjectZipContent.isPresent()) {
            ProjectZipContent projectZipContent = optionalProjectZipContent.get();

            var natures = this.getNatures(projectZipContent.manifest().get(ProjectZipContent.NATURES));
            IResult<Project> result = this.projectCreationService.createProject(new InitializeProjectInput(input.id(), input, projectZipContent), projectZipContent.projectName(), natures);
            if (result instanceof Success<Project> success) {
                var project = success.data();
                payload = new UploadProjectSuccessPayload(input.id(), this.projectMapper.toDTO(project));
            }
        } else {
            payload = new ErrorPayload(input.id(), this.messageService.unexpectedError());
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
