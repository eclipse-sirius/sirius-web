/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.spring.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.services.api.projects.IProjectExportService;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Tests of the project controller.
 *
 * @author gcoutable
 */
public class ProjectControllerTests {

    @Test
    public void testProjectDoesNotExist() {
        IProjectService projectService = new IProjectService.NoOp();
        IProjectExportService projectExportService = new IProjectExportService.NoOp();

        ProjectController projectController = new ProjectController(projectService, projectExportService);
        String projectId = "631fcb2d-3463-4084-b5da-fd8022ebae53";
        ResponseEntity<Resource> responseEntity = projectController.getProject(UUID.fromString(projectId));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testProjectExists() {
        String projectFoundId = "631fcb2d-3463-4084-b5da-fd8022ebae53";

        IProjectService projectService = new IProjectService.NoOp() {
            @Override
            public Optional<Project> getProject(UUID projectId) {
                return Optional.of(new Project(UUID.fromString(projectFoundId), projectFoundId, List.of()));
            }
        };
        IProjectExportService projectExportService = new IProjectExportService.NoOp();

        ProjectController projectController = new ProjectController(projectService, projectExportService);
        ResponseEntity<Resource> responseEntity = projectController.getProject(UUID.fromString(projectFoundId));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        HttpHeaders headers = responseEntity.getHeaders();
        assertThat(headers.getContentType()).isEqualTo(MediaType.parseMediaType("application/zip"));
        assertThat(headers.getContentLength()).isEqualTo(0);
        assertThat(headers.getContentDisposition().getFilename()).isEqualTo(projectFoundId + ".zip");
    }

}
