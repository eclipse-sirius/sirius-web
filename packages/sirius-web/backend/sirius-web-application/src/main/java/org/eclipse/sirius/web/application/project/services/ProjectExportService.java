/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.sirius.web.application.project.services.api.IProjectExportParticipant;
import org.eclipse.sirius.web.application.project.services.api.IProjectExportService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to download a project as a zip.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectExportService implements IProjectExportService {

    private final Logger logger = LoggerFactory.getLogger(ProjectExportService.class);

    private final List<IProjectExportParticipant> projectExportParticipants;

    private final ObjectMapper objectMapper;

    public ProjectExportService(List<IProjectExportParticipant> projectExportParticipants, ObjectMapper objectMapper) {
        this.projectExportParticipants = Objects.requireNonNull(projectExportParticipants);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] export(Project project) {
        byte[] zip = new byte[0];

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (var zipOutputStream = new ZipOutputStream(outputStream)) {
            var manifestEntries = this.projectExportParticipants.stream()
                    .map(projectExportParticipant -> projectExportParticipant.exportData(project, zipOutputStream))
                    .map(Map::entrySet)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            this.addManifest(project, manifestEntries, zipOutputStream);
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
            outputStream.reset();
        }

        if (outputStream.size() > 0) {
            zip = outputStream.toByteArray();
        }

        return zip;
    }

    private void addManifest(Project project, Map<String, Object> manifestEntries, ZipOutputStream outputStream) {
        try {
            byte[] manifestContent = this.objectMapper.writeValueAsBytes(manifestEntries);

            ZipEntry zipEntry = new ZipEntry(project.getName() + "/manifest.json");
            outputStream.putNextEntry(zipEntry);
            outputStream.write(manifestContent);
            outputStream.closeEntry();
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
    }
}
