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
import java.util.Objects;

final public class GeneratorArgumentsParser {
    private final String defaultGenmodelPattern = "**/src/**.genmodel";
    private final String patternOptionPrefix = "--genmodel-pattern=";
    private final String formatOptionPrefix = "--format=";

    public GeneratorArguments parse(String[] args) {
        if (args == null || args.length < 1) {
            throw new IllegalArgumentException("Expected 1 argument: <repository-root>");
        }
        Path repositoryRoot = this.toPath(Objects.requireNonNull(args[0], "repository root path"));
        String genmodelPattern = this.defaultGenmodelPattern;
        boolean format = true;
        for (int index = 1; index < args.length; index++) {
            String argument = args[index];
            if (argument != null && argument.startsWith(this.patternOptionPrefix)) {
                genmodelPattern = argument.substring(this.patternOptionPrefix.length());
            } else if ("--format".equals(argument)) {
                throw new IllegalArgumentException("Format option requires a value: --format=true|false");
            } else if (argument != null && argument.startsWith(this.formatOptionPrefix)) {
                format = this.parseFormat(argument.substring(this.formatOptionPrefix.length()));
            }
        }
        if (!Files.isDirectory(repositoryRoot)) {
            throw new IllegalArgumentException("Repository root not found: " + repositoryRoot);
        }
        if (genmodelPattern == null || genmodelPattern.isBlank()) {
            throw new IllegalArgumentException("Genmodel pattern must not be blank.");
        }
        return new GeneratorArguments(repositoryRoot, genmodelPattern, format);
    }

    private Path toPath(String value) {
        if (value.startsWith("file:")) {
            return Path.of(java.net.URI.create(value));
        }
        return Path.of(value);
    }

    private boolean parseFormat(String value) {
        if ("true".equals(value)) {
            return true;
        }
        if ("false".equals(value)) {
            return false;
        }
        throw new IllegalArgumentException("Format option must be true or false: --format=" + value);
    }
}
