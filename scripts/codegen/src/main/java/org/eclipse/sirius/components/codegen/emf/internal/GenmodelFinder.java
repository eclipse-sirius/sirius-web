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
import java.nio.file.PathMatcher;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GenmodelFinder {
    private final String genmodelExtension = ".genmodel";

    public List<Path> find(Path repositoryRoot, String genmodelPattern) {
        Path normalizedRoot = repositoryRoot.toAbsolutePath().normalize();
        PathMatcher matcher = repositoryRoot.getFileSystem().getPathMatcher("glob:" + genmodelPattern);
        try (Stream<Path> paths = Files.walk(repositoryRoot)) {
            return paths.filter(Files::isRegularFile)
                    .filter(path -> !this.isBuildOutput(normalizedRoot, path))
                    .filter(path -> path.getFileName().toString().endsWith(this.genmodelExtension))
                    .filter(path -> this.matches(normalizedRoot, matcher, path))
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toList());
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to scan repository: " + repositoryRoot, exception);
        }
    }

    private boolean matches(Path repositoryRoot, PathMatcher matcher, Path path) {
        Path normalizedPath = path.toAbsolutePath().normalize();
        return normalizedPath.startsWith(repositoryRoot) && matcher.matches(repositoryRoot.relativize(normalizedPath));
    }

    private boolean isBuildOutput(Path repositoryRoot, Path path) {
        Path normalizedPath = path.toAbsolutePath().normalize();
        if (!normalizedPath.startsWith(repositoryRoot)) {
            return false;
        }
        for (Path segment : repositoryRoot.relativize(normalizedPath)) {
            if ("target".equals(segment.toString())) {
                return true;
            }
        }
        return false;
    }
}
