/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;

/**
 * Project stored using a binary zip format.
 *
 * @author Arthur Daussy
 */
public record ProjectZipContent(
        @NotNull String projectName,
        @NotNull Map<String, ByteArrayOutputStream> files,
        @NotNull Map<String, Object> manifest
) {

    public static final String NATURES = "natures";

    public static final String REPRESENTATIONS = "representations";

    public static final String TARGET_OBJECT_URI = "targetObjectURI";

    public static final String DESCRIPTION_URI = "descriptionURI";

    public static final String MIGRATION_VERSION = "migrationVersion";

    public static final String LATEST_MIGRATION_PERFORMED = "latestMigrationPerformed";

    public ProjectZipContent {
        Objects.requireNonNull(projectName);
        Objects.requireNonNull(files);
        Objects.requireNonNull(manifest);
    }
}
