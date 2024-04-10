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

import static com.tngtech.archunit.library.modules.syntax.ModuleDependencyScope.consideringOnlyDependenciesBetweenModules;
import static com.tngtech.archunit.library.modules.syntax.ModuleRuleDefinition.modules;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

import org.eclipse.sirius.web.domain.annotations.Module;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Used to validate architectural constraints on our modules.
 *
 * @author sbegaudeau
 */
public class ModuleArchitectureTests {

    @Test
    @DisplayName("Given our architecture, when we verify the dependencies of our modules, then all requirements are satisfied")
    public void givenOurArchitectureWhenWeVerifyTheDependenciesOfOurModulesThenAllRequirementsAreSatisfied() {
        var classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.eclipse.sirius.web");

        var rule = modules()
                .definedByAnnotation(Module.class)
                .should()
                .respectTheirAllowedDependenciesDeclaredIn("allowedDependencies", consideringOnlyDependenciesBetweenModules())
                .andShould()
                .onlyDependOnEachOtherThroughPackagesDeclaredIn("exposedPackages");

        rule.check(classes);
    }

    @Test
    @DisplayName("Given our architecture, when we verify the dependencies of our modules, then no cycles are found")
    public void givenOurArchitectureWhenWeVerifyTheDependenciesOfOurModulesThenNoCyclesAreFound() {
        var classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.eclipse.sirius.web");

        var rule = modules()
                .definedByAnnotation(Module.class)
                .should()
                .beFreeOfCycles();

        rule.check(classes);
    }

}
