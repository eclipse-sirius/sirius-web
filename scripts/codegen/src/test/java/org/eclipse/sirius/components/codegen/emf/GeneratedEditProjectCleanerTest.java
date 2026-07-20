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

package org.eclipse.sirius.components.codegen.emf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.sirius.components.codegen.emf.internal.GeneratedEditProjectCleaner;
import org.eclipse.sirius.components.codegen.emf.internal.GeneratedModelProjectCleaner;
import org.eclipse.sirius.components.codegen.emf.internal.PluginPropertiesMerger;
import org.eclipse.sirius.components.codegen.emf.internal.SvgIconInitializer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GeneratedEditProjectCleanerTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void mergesPropertiesInitializesIconsAndRemovesGeneratedMetadata() throws Exception {
        Path project = this.temporaryFolder.newFolder("edit").toPath();
        Files.writeString(project.resolve("plugin.properties"), "existing=generated\nadded=value\n");
        Files.createDirectories(project.resolve("src/main/resources/icons"));
        Files.writeString(project.resolve("src/main/resources/plugin.properties"), "existing=custom");
        Files.createDirectories(project.resolve("icons/nested"));
        Files.writeString(project.resolve("icons/nested/icon.gif"), "gif");
        Files.writeString(project.resolve("build.properties"), "");
        Files.writeString(project.resolve("plugin.xml"), "");

        new GeneratedEditProjectCleaner(new PluginPropertiesMerger(), new SvgIconInitializer()).cleanup(project);

        String properties = Files.readString(project.resolve("src/main/resources/plugin.properties"));
        assertTrue(properties.contains("existing=custom"));
        assertTrue(properties.contains("added=value"));
        assertTrue(Files.isRegularFile(project.resolve("src/main/resources/icons/nested/icon.svg")));
        assertFalse(Files.exists(project.resolve("icons")));
        assertFalse(Files.exists(project.resolve("build.properties")));
        assertFalse(Files.exists(project.resolve("plugin.xml")));
        assertFalse(Files.exists(project.resolve("plugin.properties")));
    }

    @Test
    public void removesGeneratedModelMetadata() throws Exception {
        Path project = this.temporaryFolder.newFolder("model").toPath();
        Files.writeString(project.resolve("build.properties"), "");
        Files.writeString(project.resolve("plugin.xml"), "");
        Files.writeString(project.resolve("plugin.properties"), "");

        new GeneratedModelProjectCleaner().cleanup(project);

        assertFalse(Files.exists(project.resolve("build.properties")));
        assertFalse(Files.exists(project.resolve("plugin.xml")));
        assertFalse(Files.exists(project.resolve("plugin.properties")));
    }
}
