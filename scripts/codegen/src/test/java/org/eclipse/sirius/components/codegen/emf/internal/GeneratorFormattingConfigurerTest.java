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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Test;

public class GeneratorFormattingConfigurerTest {
    @Test
    public void enablesFormattingAndLoadsFormatterOptions() {
        Generator generator = new Generator();

        new GeneratorFormattingConfigurer().configure(generator, new ResourceSetImpl(), null, true);

        assertTrue(generator.getOptions().codeFormatting);
        assertTrue(generator.getOptions().commentFormatting);
        assertNotNull(generator.getOptions().codeFormatterOptions);
    }

    @Test
    public void disablesFormattingWithoutLoadingFormatterOptions() {
        Generator generator = new Generator();

        new GeneratorFormattingConfigurer().configure(generator, new ResourceSetImpl(), null, false);

        assertFalse(generator.getOptions().codeFormatting);
        assertFalse(generator.getOptions().commentFormatting);
        assertTrue(generator.getOptions().codeFormatterOptions == null || generator.getOptions().codeFormatterOptions.isEmpty());
    }
}
