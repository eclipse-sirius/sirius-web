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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.sirius.web.application.project.services.api.IProjectZipContentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Builder {@link ProjectZipContent} for zipped projects.
 *
 * @author Arthur Daussy
 */
@Service
public class ProjectZipContentProvider implements IProjectZipContentProvider {

    private static final String ZIP_FOLDER_SEPARATOR = "/";

    private static final String MANIFEST_JSON_FILE = "manifest.json";

    private final Logger logger = LoggerFactory.getLogger(ProjectZipContentProvider.class);

    private final ObjectMapper objectMapper;

    public ProjectZipContentProvider(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public Optional<ProjectZipContent> buildFromZip(InputStream zipData) {
        Map<String, ByteArrayOutputStream> fileContent = this.readZipFile(zipData);
        var optionalName = this.getName(fileContent);
        if (optionalName.isPresent()) {
            var name = optionalName.get();
            Map<String, Object> manifest = this.getManifest(fileContent, name);
            return Optional.of(new ProjectZipContent(name, fileContent, manifest));
        }
        return Optional.empty();
    }

    /**
     * Returns the project name if all zip entries represented by couple (zipEntry name -> OutputStream) in the given
     * {@link Map}, have their zip entry name starting by the project name, which should be the first segment of the
     * path of each zip entries.
     *
     * @return The name of the project
     */
    private Optional<String> getName(Map<String, ByteArrayOutputStream> zipEntryNameToContent) {
        Iterator<String> iterator = zipEntryNameToContent.keySet().iterator();
        if (iterator.hasNext()) {
            var entryName = iterator.next();
            var paths = entryName.split(ZIP_FOLDER_SEPARATOR);
            if (paths.length > 0) {
                String projectName = paths[0];
                if (!projectName.isBlank()) {
                    return Optional.of(projectName);
                }
            }

        }
        return Optional.empty();
    }

    private Map<String, Object> getManifest(Map<String, ByteArrayOutputStream> zipEntryNameToContent, String projectName) {
        String manifestPathInZip = projectName + ZIP_FOLDER_SEPARATOR + MANIFEST_JSON_FILE;

        byte[] manifestBytes = zipEntryNameToContent.entrySet().stream()
                .filter(entry -> entry.getKey().equals(manifestPathInZip))
                .map(Map.Entry::getValue)
                .map(ByteArrayOutputStream::toByteArray)
                .findFirst()
                .orElse(new byte[0]);

        try {
            return this.objectMapper.readValue(manifestBytes, HashMap.class);
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return Map.of();
    }

    /**
     * Reads the zip file and returns a map of zip entry name to zip entry content.
     *
     * @return The zip entry names mapped to its zip entry contents
     */
    private Map<String, ByteArrayOutputStream> readZipFile(InputStream inputStream) {
        Map<String, ByteArrayOutputStream> entries = new HashMap<>();
        try (var zipperProjectInputStream = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry = zipperProjectInputStream.getNextEntry();
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    zipperProjectInputStream.transferTo(byteArrayOutputStream);
                    entries.put(name, byteArrayOutputStream);
                }
                zipEntry = zipperProjectInputStream.getNextEntry();
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return entries;
    }
}
