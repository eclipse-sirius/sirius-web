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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class GeneratedEditProjectCleaner {
    private final PluginPropertiesMerger propertiesMerger;
    private final SvgIconInitializer svgIconInitializer;

    public GeneratedEditProjectCleaner(PluginPropertiesMerger propertiesMerger, SvgIconInitializer svgIconInitializer) {
        this.propertiesMerger = propertiesMerger;
        this.svgIconInitializer = svgIconInitializer;
    }

    public void cleanup(Path projectRoot) {
        Path generatedProperties = projectRoot.resolve("plugin.properties");
        this.propertiesMerger.merge(generatedProperties, projectRoot.resolve("src/main/resources/plugin.properties"));
        this.svgIconInitializer.initialize(projectRoot);
        this.delete(projectRoot.resolve("build.properties"));
        this.delete(projectRoot.resolve("plugin.xml"));
        this.delete(generatedProperties);
    }

    private void delete(Path path) {
        try {
            if (Files.deleteIfExists(path)) {
                System.out.println("Cleaned generated file: " + path);
            }
        } catch (IOException exception) {
            System.err.println("Failed to remove generated file: " + path + " (" + exception.getMessage() + ")");
        }
    }
}
