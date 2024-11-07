/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

import org.jmolecules.archunit.JMoleculesArchitectureRules;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Used to validate that we respect JMolecules Onion architecture constraints.
 *
 * @author sbegaudeau
 */
public class OnionArchitectureTests {

    @Test
    @DisplayName("Given our onion architecture, when we verify its constraints, then all requirements are satisfied")
    public void givenOurOnionArchitectureWhenWeVerifyItsConstraintsThenAllRequirementsAreSatisfied() {
        var classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.eclipse.sirius.web");

        JMoleculesArchitectureRules.ensureOnionSimple().check(classes);
    }
}
