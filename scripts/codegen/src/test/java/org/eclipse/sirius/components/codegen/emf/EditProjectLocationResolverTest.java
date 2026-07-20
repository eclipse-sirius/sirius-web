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

import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.sirius.components.codegen.emf.internal.EditProjectLocation;
import org.eclipse.sirius.components.codegen.emf.internal.EditProjectLocationResolver;
import org.eclipse.sirius.components.codegen.emf.internal.ProjectLocation;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class EditProjectLocationResolverTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void resolvesRelativeEditProjectDirectory() throws Exception {
        Path backend = this.temporaryFolder.newFolder("backend").toPath();
        ProjectLocation model = new ProjectLocation("model", "src/model.genmodel", backend.resolve("model"));
        GenModel genModel = this.genModelWithDirectory("edit/src/main/java");

        EditProjectLocation edit = new EditProjectLocationResolver().resolve(model, genModel);

        assertEquals("edit", edit.projectName());
        assertEquals(backend.resolve("edit"), edit.projectRoot());
    }

    @Test
    public void resolvesAbsoluteEditSourceDirectory() throws Exception {
        Path backend = this.temporaryFolder.newFolder("backend").toPath();
        Path editSource = Files.createDirectories(backend.resolve("edit/src/main/java"));
        ProjectLocation model = new ProjectLocation("model", "src/model.genmodel", backend.resolve("model"));
        GenModel genModel = this.genModelWithDirectory(editSource.toString());

        EditProjectLocation edit = new EditProjectLocationResolver().resolve(model, genModel);

        assertEquals("edit", edit.projectName());
        assertEquals(backend.resolve("edit"), edit.projectRoot());
    }

    private GenModel genModelWithDirectory(String directory) {
        return (GenModel) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] { GenModel.class },
                (proxy, method, arguments) -> {
                    if ("getEditProjectDirectory".equals(method.getName())) {
                        return directory;
                    }
                    if ("getEditPluginID".equals(method.getName())) {
                        return null;
                    }
                    throw new UnsupportedOperationException(method.getName());
                });
    }
}
