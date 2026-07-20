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

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.sirius.components.codegen.emf.internal.GenmodelFinder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GenmodelFinderTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void findsMatchingGenmodelsInStableOrder() throws Exception {
        Path root = this.temporaryFolder.newFolder("repository").toPath();
        Path source = Files.createDirectories(root.resolve("module/src/main"));
        Files.writeString(source.resolve("b.genmodel"), "");
        Files.writeString(source.resolve("a.genmodel"), "");
        Files.writeString(root.resolve("ignored.genmodel"), "");

        List<Path> found = new GenmodelFinder().find(root, "**/src/**.genmodel");

        assertEquals(List.of(source.resolve("a.genmodel"), source.resolve("b.genmodel")), found);
    }
}
