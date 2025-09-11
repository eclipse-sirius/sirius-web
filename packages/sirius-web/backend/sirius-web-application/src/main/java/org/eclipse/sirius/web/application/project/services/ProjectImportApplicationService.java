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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.dto.InitProjectContentInput;
import org.eclipse.sirius.web.application.project.dto.ProjectZipContent;
import org.eclipse.sirius.web.application.project.dto.UploadProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectImportApplicationService;
import org.eclipse.sirius.web.application.project.services.api.IProjectMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectCreationService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service used to import a project.
 *
 * @author gcoutable
 */
@Service
public class ProjectImportApplicationService implements IProjectImportApplicationService {

    private static final String ZIP_FOLDER_SEPARATOR = "/";

    private static final String MANIFEST_JSON_FILE = "manifest.json";

    private static final String REPRESENTATIONS_FOLDER = "representations";

    private static final String DOCUMENTS_FOLDER = "documents";

    private final Logger logger = LoggerFactory.getLogger(ProjectImportApplicationService.class);

    private final ZipProjectContentBuilder importProjectContentBuilder;

    private final IProjectCreationService projectCreationService;

    private final IProjectMapper projectMapper;

    public ProjectImportApplicationService(ZipProjectContentBuilder importProjectContentBuilder, IProjectCreationService projectCreationService, IProjectMapper projectMapper) {
        this.importProjectContentBuilder = importProjectContentBuilder;
        this.projectCreationService = Objects.requireNonNull(projectCreationService);
        this.projectMapper = Objects.requireNonNull(projectMapper);
    }

    /**
     * Returns {@link UploadProjectSuccessPayload} if the project import has been successful, {@link ErrorPayload}
     * otherwise.
     *
     * @param input
     *         the source {@link UploadProjectInput}
     * @return {@link UploadProjectSuccessPayload} whether the project import has been successful, {@link ErrorPayload}
     * otherwise
     */
    @Override
    @Transactional
    public IPayload importProject(UploadProjectInput input) {
        IPayload payload = new ErrorPayload(input.id(), "");
        Optional<ProjectZipContent> optProjectStructure = importProjectContentBuilder.buildFromZip(input.file().getInputStream());
        if (optProjectStructure.isPresent()) {
            ProjectZipContent projectStructure = optProjectStructure.get();

            IResult<Project> result = this.projectCreationService.createProject(new InitProjectContentInput(input.id(), input, projectStructure), projectStructure.getName(),
                    projectStructure.getNatures());
            if (result instanceof Success<Project> success) {
                var project = success.data();
                payload = new UploadProjectSuccessPayload(input.id(), projectMapper.toDTO(project));
            }
        } else {
            payload = new ErrorPayload(input.id(), "Unable to import project : Faile to read project structure");
        }

        return payload;
    }
}
