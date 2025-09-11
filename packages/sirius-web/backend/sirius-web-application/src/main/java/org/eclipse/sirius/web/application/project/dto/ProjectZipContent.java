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
package org.eclipse.sirius.web.application.project.dto;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Project stored using a binary zip format.
 *
 * @author Arthur Daussy
 */
public class ProjectZipContent implements IProjectBinaryContent {

    private static final String DOCUMENTS_FOLDER = "documents";

    private static final String ZIP_FOLDER_SEPARATOR = "/";

    private static final String MANIFEST_JSON_FILE = "manifest.json";

    private static final String REPRESENTATIONS_FOLDER = "representations";

    private final Logger logger = LoggerFactory.getLogger(ProjectZipContent.class);

    private final String projectName;

    private final Map<String, ByteArrayOutputStream> fileContent;

    private final Map<String, Object> manifest;


    public ProjectZipContent(String projectName, Map<String, ByteArrayOutputStream> fileContent, Map<String, Object> manifest) {
        this.projectName = projectName;
        this.fileContent = fileContent;
        this.manifest = manifest;
    }

    @Override
    public String getName() {
        return projectName;
    }

    @Override
    public Map<String, ByteArrayOutputStream> getFileContent() {
        return fileContent;
    }

    public Map<String, Object> getManifest() {
        return manifest;
    }

    public List<String> getNatures() {
        return (List<String>) getManifest().get("natures");
    }

}
