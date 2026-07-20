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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;

public final class GeneratorFormattingConfigurer {
    private final String formatterPrefix = "org.eclipse.jdt.core.formatter.";
    private final String classpathPreferences = "/org.eclipse.jdt.core.prefs";

    void configure(Generator generator, ResourceSet resourceSet, ProjectLocation projectLocation) {
        Generator.Options options = generator.getOptions();
        options.codeFormatting = true;
        options.commentFormatting = true;
        options.importOrganizing = true;
        options.cleanup = true;
        options.resourceSet = resourceSet;
        Map<String, String> formatterOptions = DefaultCodeFormatterConstants.getEclipseDefaultSettings();
        formatterOptions.putAll(JavaCore.getOptions());
        formatterOptions.putAll(this.fromClasspath());
        formatterOptions.putAll(this.fromProject(projectLocation));
        options.codeFormatterOptions = formatterOptions;
    }

    private Map<String, String> fromClasspath() {
        try (java.io.InputStream stream = this.getClass().getResourceAsStream(this.classpathPreferences)) {
            if (stream == null) {
                System.err.println("Formatter preferences not found on classpath: " + this.classpathPreferences);
                return Map.of();
            }
            return this.formatterOptions(stream);
        } catch (IOException exception) {
            System.err.println("Failed to load formatter preferences: " + exception.getMessage());
            return Map.of();
        }
    }

    private Map<String, String> fromProject(ProjectLocation projectLocation) {
        if (projectLocation == null) {
            return Map.of();
        }
        Path preferences = projectLocation.projectRoot().resolve(".settings/org.eclipse.jdt.core.prefs");
        if (!Files.isRegularFile(preferences)) {
            return Map.of();
        }
        try (java.io.InputStream stream = Files.newInputStream(preferences)) {
            Map<String, String> result = this.formatterOptions(stream);
            System.out.println("Loaded formatter preferences from " + preferences);
            return result;
        } catch (IOException exception) {
            System.err.println("Failed to load formatter preferences: " + preferences + " (" + exception.getMessage() + ")");
            return Map.of();
        }
    }

    private Map<String, String> formatterOptions(java.io.InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);
        Map<String, String> result = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith(this.formatterPrefix)) {
                result.put(key, properties.getProperty(key));
            }
        }
        return result;
    }
}
