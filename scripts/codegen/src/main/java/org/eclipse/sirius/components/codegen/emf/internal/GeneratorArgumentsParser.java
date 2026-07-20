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

    public GeneratorArguments parse(String[] args) {
        if (args == null || args.length < 1) {
            throw new IllegalArgumentException("Expected 1 argument: <repository-root>");
        }
        Path repositoryRoot = this.toPath(Objects.requireNonNull(args[0], "repository root path"));
        String genmodelPattern = this.defaultGenmodelPattern;
        for (int index = 1; index < args.length; index++) {
            String argument = args[index];
            if (argument != null && argument.startsWith(this.patternOptionPrefix)) {
                genmodelPattern = argument.substring(this.patternOptionPrefix.length());
            }
        }
        if (!Files.isDirectory(repositoryRoot)) {
            throw new IllegalArgumentException("Repository root not found: " + repositoryRoot);
        }
        if (genmodelPattern == null || genmodelPattern.isBlank()) {
            throw new IllegalArgumentException("Genmodel pattern must not be blank.");
        }
        return new GeneratorArguments(repositoryRoot, genmodelPattern);
    }

    private Path toPath(String value) {
        if (value.startsWith("file:")) {
            return Path.of(java.net.URI.create(value));
        }
        return Path.of(value);
    }
}
