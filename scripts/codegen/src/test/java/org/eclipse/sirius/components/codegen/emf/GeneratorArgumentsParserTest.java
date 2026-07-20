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

import java.nio.file.Path;

import org.eclipse.sirius.components.codegen.emf.internal.GeneratorArguments;
import org.eclipse.sirius.components.codegen.emf.internal.GeneratorArgumentsParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GeneratorArgumentsParserTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void parsesPlainPathAndPattern() throws Exception {
        Path root = this.temporaryFolder.newFolder("repository").toPath();

        GeneratorArguments arguments = new GeneratorArgumentsParser().parse(new String[] {
                root.toString(), "--genmodel-pattern=**/*.genmodel" });

        assertEquals(root, arguments.repositoryRoot());
        assertEquals("**/*.genmodel", arguments.genmodelPattern());
    }

    @Test
    public void parsesFileUri() throws Exception {
        Path root = this.temporaryFolder.newFolder("repository").toPath();

        GeneratorArguments arguments = new GeneratorArgumentsParser().parse(new String[] { root.toUri().toString() });

        assertEquals(root, arguments.repositoryRoot());
        assertEquals("**/src/**.genmodel", arguments.genmodelPattern());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsBlankPattern() throws Exception {
        Path root = this.temporaryFolder.newFolder("repository").toPath();
        new GeneratorArgumentsParser().parse(new String[] { root.toString(), "--genmodel-pattern=" });
    }
}
