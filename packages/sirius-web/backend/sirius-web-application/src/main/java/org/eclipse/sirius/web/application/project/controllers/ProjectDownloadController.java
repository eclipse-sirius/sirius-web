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
package org.eclipse.sirius.web.application.project.controllers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.eclipse.sirius.web.application.project.services.api.IProjectExportService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.core.io.ByteArrayResource;
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
 * @author gcoutable
 */
@Controller
@RequestMapping("/api/projects")
public class ProjectDownloadController {

    private final List<ICapabilityVoter> capabilityVoters;

    private final IProjectSearchService projectSearchService;

    private final IProjectExportService projectExportService;

    public ProjectDownloadController(List<ICapabilityVoter> capabilityVoters, IProjectSearchService projectSearchService, IProjectExportService projectExportService) {
        this.capabilityVoters = Objects.requireNonNull(capabilityVoters);
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectExportService = Objects.requireNonNull(projectExportService);
    }

    @ResponseBody
    @GetMapping(path = "/{projectId}")
    public ResponseEntity<Resource> downloadProject(@PathVariable String projectId) {
        ResponseEntity<Resource> response = new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);

        var hasCapability = this.capabilityVoters.stream().allMatch(voter -> voter.vote(SiriusWebCapabilities.PROJECT, projectId, SiriusWebCapabilities.Project.DOWNLOAD) == CapabilityVote.GRANTED);

        var optionalProject = this.projectSearchService.findById(projectId);
        if (hasCapability && optionalProject.isPresent()) {
            var project = optionalProject.get();
            byte[] content = this.projectExportService.export(project);

            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(project.getName() + ".zip")
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);
            headers.setContentType(MediaType.parseMediaType("application/zip"));
            headers.setContentLength(content.length);
            var resource = new ByteArrayResource(content);

            response = new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        return response;
    }
}
