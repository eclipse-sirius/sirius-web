/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.components.codegen.emf.internal;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;

public final class EditProjectLocationResolver {
    public EditProjectLocation resolve(ProjectLocation projectLocation, GenModel genModel) {
        if (projectLocation == null || projectLocation.projectRoot().getParent() == null) {
            return null;
        }
        Path parentDir = projectLocation.projectRoot().getParent();
        EditProjectLocation resolved = this.fromDirectory(genModel.getEditProjectDirectory(), parentDir);
        if (resolved != null) {
            return resolved;
        }
        String pluginId = genModel.getEditPluginID();
        return pluginId == null || pluginId.isBlank() ? null : new EditProjectLocation(pluginId, parentDir.resolve(pluginId));
    }

    private EditProjectLocation fromDirectory(String directory, Path parentDir) {
        if (directory == null || directory.isBlank()) {
            return null;
        }
        Path directoryPath = Path.of(directory);
        if (directoryPath.isAbsolute() && Files.exists(directoryPath)) {
            Path projectRoot = this.projectRoot(directoryPath);
            return projectRoot == null ? null : new EditProjectLocation(projectRoot.getFileName().toString(), projectRoot);
        }
        String normalized = directory.replace('\\', '/').trim();
        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        if (normalized.isEmpty()) {
            return null;
        }
        Path relative = Path.of(normalized);
        return relative.getNameCount() == 0 ? null : new EditProjectLocation(relative.getName(0).toString(), parentDir.resolve(relative.getName(0)));
    }

    private Path projectRoot(Path path) {
        for (int index = 0; index < path.getNameCount(); index++) {
            if ("src".equals(path.getName(index).toString()) && index > 0) {
                Path prefix = path.subpath(0, index);
                return path.getRoot() == null ? prefix : path.getRoot().resolve(prefix);
            }
        }
        return path;
    }
}
