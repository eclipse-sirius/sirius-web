/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.emf.services.upload;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessorRegistry;
import org.eclipse.sirius.web.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.web.persistence.repositories.IIdMappingRepository;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.services.api.dto.IPayload;
import org.eclipse.sirius.web.services.api.projects.CreateProjectInput;
import org.eclipse.sirius.web.services.api.projects.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.IProjectImportService;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.ProjectManifest;
import org.eclipse.sirius.web.services.api.projects.UnzippedProject;
import org.eclipse.sirius.web.services.api.projects.UploadProjectSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.Visibility;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.graphql.api.UploadFile;
import org.springframework.stereotype.Service;

/**
 * Service used to import a project.
 *
 * @author gcoutable
 */
@Service
public class ProjectImportService implements IProjectImportService {

    private final IProjectService projectService;

    private final IProjectEventProcessorRegistry projectEventProcessorRegistry;

    private final ObjectMapper objectMapper;

    private final IEMFMessageService messageService;

    private final IIdMappingRepository idMappingRepository;

    public ProjectImportService(IProjectService projectService, IProjectEventProcessorRegistry projectEventProcessorRegistry, ObjectMapper objectMapper, IEMFMessageService messageService,
            IIdMappingRepository repository) {
        this.idMappingRepository = Objects.requireNonNull(repository);
        this.projectService = Objects.requireNonNull(projectService);
        this.projectEventProcessorRegistry = Objects.requireNonNull(projectEventProcessorRegistry);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.messageService = Objects.requireNonNull(messageService);
    }

    /**
     * Returns {@link UploadProjectSuccessPayload} if the project import has been successful, {@link ErrorPayload}
     * otherwise.
     *
     * <p>
     * Unzip the given {@link UploadFile}, then creates a project with the name of the root directory in the zip file,
     * then use {@link ProjectImporter} to create documents and representations. If the project has not been imported,
     * it disposes the {@link IProjectEventProcessor} used to create documents and representations then delete the
     * created project in order to keep the server in the same state before the project upload attempt.
     * </p>
     *
     * @param file
     *            the file to upload
     * @param context
     *            the context in which the operation is performed
     * @return {@link UploadProjectSuccessPayload} whether the project import has been successful, {@link ErrorPayload}
     *         otherwise
     */
    @Override
    public IPayload importProject(UploadFile file, Context context) {
        IPayload payload = new ErrorPayload(this.messageService.unexpectedError());
        ProjectUnzipper unzipper = new ProjectUnzipper(file.getInputStream(), this.objectMapper);
        Optional<UnzippedProject> optionalUnzippedProject = unzipper.unzipProject();
        if (optionalUnzippedProject.isEmpty()) {
            return new ErrorPayload(this.messageService.unexpectedError());
        }
        UnzippedProject unzippedProject = optionalUnzippedProject.get();
        ProjectManifest manifest = unzippedProject.getManifest();
        String projectName = unzippedProject.getProjectName();

        CreateProjectInput createProjectInput = new CreateProjectInput(projectName, Visibility.PRIVATE);
        IPayload createProjectPayload = this.projectService.createProject(createProjectInput);
        if (createProjectPayload instanceof CreateProjectSuccessPayload) {
            Project project = ((CreateProjectSuccessPayload) createProjectPayload).getProject();
            Optional<IProjectEventProcessor> optionalProjectEventProcessor = this.projectEventProcessorRegistry.getOrCreateProjectEventProcessor(project.getId());
            if (optionalProjectEventProcessor.isPresent()) {
                IProjectEventProcessor projectEventProcessor = optionalProjectEventProcessor.get();
                Map<String, UploadFile> documents = unzippedProject.getDocumentIdToUploadFile();
                List<RepresentationDescriptor> representations = unzippedProject.getRepresentationDescriptors();

                ProjectImporter projectImporter = new ProjectImporter(project.getId(), projectEventProcessor, documents, representations, manifest, context, this.idMappingRepository);
                boolean hasBeenImported = projectImporter.importProject();

                if (!hasBeenImported) {
                    this.projectEventProcessorRegistry.dispose(project.getId());
                    this.projectService.delete(project.getId());
                } else {
                    payload = new UploadProjectSuccessPayload(project);
                }
            }
        }
        return payload;
    }
}
