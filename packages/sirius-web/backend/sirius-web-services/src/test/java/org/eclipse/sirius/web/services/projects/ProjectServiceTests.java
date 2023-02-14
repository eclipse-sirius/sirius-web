/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.web.services.projects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.projects.CreateProjectInput;
import org.eclipse.sirius.web.services.api.projects.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateService;
import org.eclipse.sirius.web.services.messages.IServicesMessageService;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the project service.
 *
 * @author sbegaudeau
 */
public class ProjectServiceTests {

    private static final String NEW_PROJECT = "New Project";

    private IServicesMessageService noOpMessageService = new NoOpServicesMessageService();

    private IProjectRepository noOpProjectRepository = new NoOpProjectRepository() {
        @Override
        public <S extends ProjectEntity> S save(S entity) {
            entity.setId(UUID.randomUUID());
            return entity;
        }
    };

    private IEditingContextSearchService noOpEditingContextSearchService = new IEditingContextSearchService() {

        @Override
        public Optional<IEditingContext> findById(String editingContextId) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(String editingContextId) {
            return false;
        }
    };

    private ProjectService projectService = new ProjectService(this.noOpMessageService, this.noOpProjectRepository, new IProjectTemplateService.NoOp(), this.noOpEditingContextSearchService,
            new IEditingContextPersistenceService.NoOp());

    @Test
    public void testProjectCreationWithInvalidName() {
        CreateProjectInput input = new CreateProjectInput(UUID.randomUUID(), "");
        IPayload payload = this.projectService.createProject(input);
        assertThat(payload).isInstanceOf(ErrorPayload.class);
    }

    @Test
    public void testProjectCreationSuccess() {
        CreateProjectInput input = new CreateProjectInput(UUID.randomUUID(), NEW_PROJECT);
        IPayload payload = this.projectService.createProject(input);
        assertThat(payload).isInstanceOf(CreateProjectSuccessPayload.class);
    }

    @Test
    public void testCreateProjectsWithSameName() {
        CreateProjectInput firstInput = new CreateProjectInput(UUID.randomUUID(), NEW_PROJECT);
        IPayload payload = this.projectService.createProject(firstInput);
        assertThat(payload).isInstanceOf(CreateProjectSuccessPayload.class);

        CreateProjectInput secondInput = new CreateProjectInput(UUID.randomUUID(), NEW_PROJECT);
        IPayload secondPayload = this.projectService.createProject(secondInput);
        assertThat(secondPayload).isInstanceOf(CreateProjectSuccessPayload.class);
    }

}
