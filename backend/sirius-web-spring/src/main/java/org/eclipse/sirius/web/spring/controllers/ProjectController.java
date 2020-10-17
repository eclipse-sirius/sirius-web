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
package org.eclipse.sirius.web.spring.controllers;

import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.services.api.projects.IProjectExportService;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The entry point of the HTTP API to download a project in zip.
 * <p>
 * This endpoint will be available on the API base path prefix with download segment and followed by the project Id used
 * as a suffix. As such, users will be able to send project download request to the following URL:
 * </p>
 *
 * <pre>
 * PROTOCOL://DOMAIN.TLD(:PORT)/API_BASE_PATH/projects/PROJECT_ID
 * </pre>
 *
 * <p>
 * In a development environment, the URL will most likely be:
 * </p>
 *
 * <pre>
 * http://localhost:8080/api/projects/PROJECT_ID
 * </pre>
 *
 * @author gcoutable
 */
@Controller
@RequestMapping(URLConstants.PROJECT_BASE_PATH)
public class ProjectController {

    private final IProjectService projectService;

    private final IProjectExportService projectExportService;

    public ProjectController(IProjectService projectService, IProjectExportService projectExportService) {
        this.projectService = Objects.requireNonNull(projectService);
        this.projectExportService = Objects.requireNonNull(projectExportService);
    }

    @GetMapping(path = "/{projectId}")
    @ResponseBody
    public ResponseEntity<Resource> getProject(@PathVariable UUID projectId) {
        Optional<Project> optionalProject = this.projectService.getProject(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            byte[] zip = this.projectExportService.exportProjectAsZip(projectId);

            // @formatter:off
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment") //$NON-NLS-1$
                    .filename(project.getName() + ".zip") //$NON-NLS-1$
                    .build();
            // @formatter:on

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);
            headers.setContentType(MediaType.parseMediaType("application/zip")); //$NON-NLS-1$
            headers.setContentLength(zip.length);
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(zip));
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
