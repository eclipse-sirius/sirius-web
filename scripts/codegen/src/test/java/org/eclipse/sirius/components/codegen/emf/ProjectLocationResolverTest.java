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
import static org.junit.Assert.assertNull;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.sirius.components.codegen.emf.internal.ProjectLocation;
import org.eclipse.sirius.components.codegen.emf.internal.ProjectLocationResolver;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ProjectLocationResolverTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void resolvesBackendProjectLocation() throws Exception {
        Path root = this.temporaryFolder.newFolder("repository").toPath();
        Path genmodel = Files.createDirectories(root.resolve("packages/view/backend/my-project/src/main/resources"))
                .resolve("model.genmodel");

        ProjectLocation location = new ProjectLocationResolver().resolve(root, genmodel);

        assertEquals("my-project", location.projectName());
        assertEquals("src/main/resources/model.genmodel", location.projectRelativePath());
        assertEquals(root.resolve("packages/view/backend/my-project"), location.projectRoot());
    }

    @Test
    public void returnsNullOutsideRepository() throws Exception {
        Path root = this.temporaryFolder.newFolder("repository").toPath();
        assertNull(new ProjectLocationResolver().resolve(root, Path.of("C:/outside/model.genmodel")));
    }
}
