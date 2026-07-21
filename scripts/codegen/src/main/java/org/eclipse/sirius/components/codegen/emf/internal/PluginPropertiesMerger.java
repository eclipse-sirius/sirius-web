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
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public final class PluginPropertiesMerger {
    void merge(Path generatedRoot, Path resourcesFile) {
        if (!Files.isRegularFile(generatedRoot)) {
            return;
        }
        Properties generated = this.load(generatedRoot);
        if (generated.isEmpty()) {
            return;
        }
        Set<String> existingKeys = new HashSet<>();
        if (Files.isRegularFile(resourcesFile)) {
            existingKeys.addAll(this.load(resourcesFile).stringPropertyNames());
        }
        List<String> missingEntries = new ArrayList<>();
        for (String key : generated.stringPropertyNames()) {
            if (!existingKeys.contains(key)) {
                missingEntries.add(this.format(key, generated.getProperty(key)));
            }
        }
        if (missingEntries.isEmpty()) {
            return;
        }
        try {
            Path parent = resourcesFile.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (java.io.BufferedWriter writer = Files.newBufferedWriter(resourcesFile,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                if (this.needsTrailingNewline(resourcesFile)) {
                    writer.newLine();
                }
                for (String line : missingEntries) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            System.out.println("Merged " + missingEntries.size() + " plugin.properties entries into " + resourcesFile);
        } catch (IOException exception) {
            System.err.println("Failed to merge plugin.properties entries into " + resourcesFile + " (" + exception.getMessage() + ")");
        }
    }

    private Properties load(Path path) {
        Properties properties = new Properties();
        try (java.io.InputStream stream = Files.newInputStream(path)) {
            properties.load(stream);
        } catch (IOException exception) {
            System.err.println("Failed to load properties from " + path + " (" + exception.getMessage() + ")");
        }
        return properties;
    }

    private String format(String key, String value) {
        Properties properties = new Properties();
        properties.setProperty(key, value == null ? "" : value);
        StringWriter writer = new StringWriter();
        try {
            properties.store(writer, null);
            for (String line : writer.toString().split("\\R")) {
                if (!line.startsWith("#") && !line.isBlank()) {
                    return line;
                }
            }
        } catch (IOException exception) {
            // StringWriter does not throw, but preserve the generated fallback.
        }
        return key + "=" + (value == null ? "" : value);
    }

    private boolean needsTrailingNewline(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            return false;
        }
        byte[] bytes = Files.readAllBytes(path);
        return bytes.length > 0 && bytes[bytes.length - 1] != '\n';
    }
}
