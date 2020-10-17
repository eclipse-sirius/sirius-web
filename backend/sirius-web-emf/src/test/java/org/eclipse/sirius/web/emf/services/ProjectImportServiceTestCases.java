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
package org.eclipse.sirius.web.emf.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.collaborative.api.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessorRegistry;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.tests.TestDiagramBuilder;
import org.eclipse.sirius.web.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.web.emf.services.upload.ProjectImportService;
import org.eclipse.sirius.web.persistence.repositories.IIdMappingRepository;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.accounts.Profile;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.UploadDocumentInput;
import org.eclipse.sirius.web.services.api.document.UploadDocumentSuccessPayload;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.services.api.dto.IInput;
import org.eclipse.sirius.web.services.api.dto.IPayload;
import org.eclipse.sirius.web.services.api.projects.CreateProjectInput;
import org.eclipse.sirius.web.services.api.projects.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.UploadProjectSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.Visibility;
import org.eclipse.sirius.web.spring.collaborative.diagrams.INodeStyleDeserializer;
import org.eclipse.sirius.web.spring.collaborative.projects.ProjectEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.representations.IRepresentationDeserializer;
import org.eclipse.sirius.web.spring.graphql.api.UploadFile;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Unit tests of the project import service.
 *
 * @author gcoutable
 */
public class ProjectImportServiceTestCases {

    private static final String PATH_TO_ZIPPED_PROJECT = "test_import/Test_Project.zip"; //$NON-NLS-1$

    private static final String PROJECT_NAME = "Test Project"; //$NON-NLS-1$

    private static final String PROJECT_NAME_COPY = "Test Project Copy"; //$NON-NLS-1$

    private final Diagram diagram = new TestDiagramBuilder().getDiagram(UUID.randomUUID());

    private final Project project = new Project(UUID.randomUUID(), PROJECT_NAME, new Profile(UUID.randomUUID(), "username"), Visibility.PUBLIC); //$NON-NLS-1$

    private final Project projectCopy = new Project(UUID.randomUUID(), PROJECT_NAME_COPY, new Profile(UUID.randomUUID(), "username"), Visibility.PUBLIC); //$NON-NLS-1$

    private final Document document = new Document(UUID.randomUUID(), this.project, "flow", null); //$NON-NLS-1$

    private final Document documentCopy = new Document(UUID.randomUUID(), this.projectCopy, "flow", null); //$NON-NLS-1$

    private final IIdMappingRepository idMappingRepository = new NoOpIdMappingRepository();

    @Test
    public void testImportProject() {

        ProjectImportService projectImportService = this.createProjectImportService(this.project, this.document, this.diagram);

        String filePath = ProjectImportServiceTestCases.class.getClassLoader().getResource(PATH_TO_ZIPPED_PROJECT).getFile();
        assertThat(new File(filePath).exists()).isTrue();

        try (var inputStream = new FileInputStream(filePath)) {
            UploadFile file = new UploadFile(PROJECT_NAME, inputStream);
            var context = new Context(new UsernamePasswordAuthenticationToken(null, null));
            IPayload importedProjectPayload = projectImportService.importProject(file, context);
            assertThat(importedProjectPayload).isInstanceOf(UploadProjectSuccessPayload.class);
            assertThat(((UploadProjectSuccessPayload) importedProjectPayload).getProject().getName()).isEqualTo(PROJECT_NAME);
        } catch (IOException exception) {
            fail(exception.getMessage(), exception);
        }
    }

    /**
     * Tests the behavior of the project import when the creation of a document fails.
     *
     * <p>
     * {@link NoOpProjectEventProcessor#handle(IInputEvent)} is overridden to return a ErrorPayload. Then it ensures the
     * {@link NoOpProjectEventProcessor} has been disposed.
     * </p>
     *
     * NOTE: Because the {@link ProjectEventProcessor} return a ErrorPayload this test logs errors.
     */
    @Test
    public void testImportProjectFailBecauseOfDocument() {
        IEMFMessageService messageService = new NoOpEMFMessageService();

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(INodeStyle.class, new INodeStyleDeserializer());
        module.addDeserializer(IRepresentation.class, new IRepresentationDeserializer());
        objectMapper.registerModule(module);

        IProjectEventProcessor projectEventProcessor = new NoOpProjectEventProcessor() {
            @Override
            public Optional<IPayload> handle(IInput input, Context context) {
                return Optional.of(new ErrorPayload("Test import failure")); //$NON-NLS-1$
            }
        };

        List<Object> hasBeenDisposed = new ArrayList<>();
        IProjectEventProcessorRegistry projectEventProcessorRegistry = new NoOpProjectEventProcessorRegistry() {
            @Override
            public Optional<IProjectEventProcessor> getOrCreateProjectEventProcessor(UUID projectId) {
                return Optional.of(projectEventProcessor);
            }

            @Override
            public void dispose(UUID projectId) {
                hasBeenDisposed.add(projectId);
            }
        };

        List<Object> projectHasBeenDeleted = new ArrayList<>();
        IProjectService projectService = new NoOpProjectService() {
            @Override
            public IPayload createProject(CreateProjectInput input) {
                return new CreateProjectSuccessPayload(ProjectImportServiceTestCases.this.project);
            }

            @Override
            public void delete(UUID projectId) {
                projectHasBeenDeleted.add(projectId);
            }
        };

        ProjectImportService projectImportService = new ProjectImportService(projectService, projectEventProcessorRegistry, objectMapper, messageService, this.idMappingRepository);

        String filePath = ProjectImportServiceTestCases.class.getClassLoader().getResource(PATH_TO_ZIPPED_PROJECT).getFile();
        assertThat(new File(filePath).exists()).isTrue();

        try (var inputStream = new FileInputStream(filePath)) {
            UploadFile file = new UploadFile(PROJECT_NAME, inputStream);
            var context = new Context(new UsernamePasswordAuthenticationToken(null, null));
            IPayload importedProjectPayload = projectImportService.importProject(file, context);
            assertThat(importedProjectPayload).isInstanceOf(ErrorPayload.class);
            assertThat(((ErrorPayload) importedProjectPayload).getMessage()).isEqualTo(messageService.unexpectedError());
        } catch (IOException exception) {
            fail(exception.getMessage(), exception);
        }

        assertThat(hasBeenDisposed).isNotEmpty();
        assertThat(projectHasBeenDeleted).isNotEmpty();
    }

    /**
     * Tests the behavior of the project import when the creation of a representation fails.
     *
     * <p>
     * {@link NoOpProjectEventProcessor#handle(IInputEvent)} is overridden to return an ErrorPayload. Then it ensures
     * the {@link NoOpProjectEventProcessor} has been disposed.
     * </p>
     *
     * NOTE: Because the {@link ProjectEventProcessor} return an ErrorPayload, this test logs errors.
     */
    @Test
    public void testImportProjectFailBecauseOfRepresentation() {
        IEMFMessageService messageService = new NoOpEMFMessageService();

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(INodeStyle.class, new INodeStyleDeserializer());
        module.addDeserializer(IRepresentation.class, new IRepresentationDeserializer());
        objectMapper.registerModule(module);

        IProjectEventProcessor projectEventProcessor = new NoOpProjectEventProcessor() {
            @Override
            public Optional<IPayload> handle(IInput input, Context context) {
                Optional<IPayload> optional = Optional.empty();
                if (input instanceof UploadDocumentInput) {
                    optional = Optional.of(new UploadDocumentSuccessPayload(ProjectImportServiceTestCases.this.document));
                } else if (input instanceof CreateRepresentationInput) {
                    optional = Optional.of(new ErrorPayload("An error has occured")); //$NON-NLS-1$
                }
                return optional;
            }
        };

        List<Object> hasBeenDisposed = new ArrayList<>();
        IProjectEventProcessorRegistry projectEventProcessorRegistry = new NoOpProjectEventProcessorRegistry() {
            @Override
            public Optional<IProjectEventProcessor> getOrCreateProjectEventProcessor(UUID projectId) {
                return Optional.of(projectEventProcessor);
            }

            @Override
            public void dispose(UUID projectId) {
                hasBeenDisposed.add(projectId);
            }
        };

        List<Object> projectHasBeenDeleted = new ArrayList<>();
        IProjectService projectService = new NoOpProjectService() {
            @Override
            public IPayload createProject(CreateProjectInput input) {
                return new CreateProjectSuccessPayload(ProjectImportServiceTestCases.this.project);
            }

            @Override
            public void delete(UUID projectId) {
                projectHasBeenDeleted.add(projectId);
            }
        };

        ProjectImportService projectImportService = new ProjectImportService(projectService, projectEventProcessorRegistry, objectMapper, messageService, this.idMappingRepository);

        String filePath = ProjectImportServiceTestCases.class.getClassLoader().getResource(PATH_TO_ZIPPED_PROJECT).getFile();
        assertThat(new File(filePath).exists()).isTrue();

        try (var inputStream = new FileInputStream(filePath)) {
            UploadFile file = new UploadFile(PROJECT_NAME, inputStream);
            var context = new Context(new UsernamePasswordAuthenticationToken(null, null));
            IPayload importedProjectPayload = projectImportService.importProject(file, context);
            assertThat(importedProjectPayload).isInstanceOf(ErrorPayload.class);
            assertThat(((ErrorPayload) importedProjectPayload).getMessage()).isEqualTo(messageService.unexpectedError());
        } catch (IOException exception) {
            fail(exception.getMessage(), exception);
        }

        assertThat(hasBeenDisposed).isNotEmpty();
        assertThat(projectHasBeenDeleted).isNotEmpty();
    }

    @Test
    public void testImportProjectTwice() {
        this.importProject(PROJECT_NAME, this.project, this.document, this.diagram);
        this.importProject(PROJECT_NAME_COPY, this.projectCopy, this.documentCopy, this.diagram);
    }

    private void importProject(String projectName, Project projectForPayload, Document documentForPayload, Diagram diagramForPayload) {
        ProjectImportService projectImportService = this.createProjectImportService(projectForPayload, documentForPayload, diagramForPayload);
        String filePath = ProjectImportServiceTestCases.class.getClassLoader().getResource(PATH_TO_ZIPPED_PROJECT).getFile();
        assertThat(new File(filePath).exists()).isTrue();

        try (var inputStream = new FileInputStream(filePath)) {
            UploadFile file = new UploadFile(projectName, inputStream);
            var context = new Context(new UsernamePasswordAuthenticationToken(null, null));
            IPayload importedProjectPayload = projectImportService.importProject(file, context);
            assertThat(importedProjectPayload).isInstanceOf(UploadProjectSuccessPayload.class);
            assertThat(((UploadProjectSuccessPayload) importedProjectPayload).getProject().getName()).isEqualTo(projectName);
        } catch (IOException exception) {
            fail(exception.getMessage(), exception);
        }
    }

    private ProjectImportService createProjectImportService(Project projectForPayload, Document documentForPayload, Diagram diagramForPayload) {
        IEMFMessageService messageService = new NoOpEMFMessageService();

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(INodeStyle.class, new INodeStyleDeserializer());
        module.addDeserializer(IRepresentation.class, new IRepresentationDeserializer());
        objectMapper.registerModule(module);

        IProjectEventProcessor projectEventProcessor = new NoOpProjectEventProcessor() {
            @Override
            public Optional<IPayload> handle(IInput input, Context context) {
                Optional<IPayload> optional = Optional.empty();
                if (input instanceof UploadDocumentInput) {
                    optional = Optional.of(new UploadDocumentSuccessPayload(documentForPayload));
                } else if (input instanceof CreateRepresentationInput) {
                    optional = Optional.of(new CreateRepresentationSuccessPayload(diagramForPayload));
                }
                return optional;
            }
        };

        IProjectEventProcessorRegistry projectEventProcessorRegistry = new NoOpProjectEventProcessorRegistry() {
            @Override
            public Optional<IProjectEventProcessor> getOrCreateProjectEventProcessor(UUID projectId) {
                return Optional.of(projectEventProcessor);
            }
        };

        IProjectService projectService = new NoOpProjectService() {
            @Override
            public IPayload createProject(CreateProjectInput input) {
                return new CreateProjectSuccessPayload(projectForPayload);
            }
        };

        return new ProjectImportService(projectService, projectEventProcessorRegistry, objectMapper, messageService, this.idMappingRepository);
    }
}
