/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.persistence.architecture;

import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import org.junit.Test;
import org.springframework.data.repository.Repository;

/**
 * Spring repository architectural tests.
 *
 * @author sbegaudeau
 */
public class RepositoryTests {
    private static final String REPOSITORY_SUFFIX = "Repository"; //$NON-NLS-1$

    private static final String REPOSITORIES_PACKAGE = "..repositories.."; //$NON-NLS-1$

    private static final String TEST = "Test"; //$NON-NLS-1$

    @Test
    public void onlyRepositoriesShouldBeInARepositoryPackage() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(ArchitectureConstants.SIRIUS_WEB_PERSISTENCE_ROOT_PACKAGE)
                .and()
                .resideInAPackage(REPOSITORIES_PACKAGE)
                .and()
                .haveSimpleNameNotContaining(TEST)
                .should()
                .beAssignableTo(Repository.class);
        // @formatter:on

        rule.check(ArchitectureConstants.CLASSES);
    }

    @Test
    public void repositoriesShouldBeNamedRepository() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(ArchitectureConstants.SIRIUS_WEB_PERSISTENCE_ROOT_PACKAGE)
                .and()
                .areAssignableTo(Repository.class)
                .should()
                .haveSimpleNameEndingWith(REPOSITORY_SUFFIX);
        // @formatter:on

        rule.check(ArchitectureConstants.CLASSES);
    }

    @Test
    public void repositoriesShouldOnlyBeInterfaces() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(ArchitectureConstants.SIRIUS_WEB_PERSISTENCE_ROOT_PACKAGE)
                .and()
                .areAssignableTo(Repository.class)
                .should()
                .beInterfaces();
        // @formatter:on

        rule.check(ArchitectureConstants.CLASSES);
    }

    @Test
    public void repositoriesShouldBeAnnotatedWithRepository() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(ArchitectureConstants.SIRIUS_WEB_PERSISTENCE_ROOT_PACKAGE)
                .and()
                .areAssignableTo(Repository.class)
                .should()
                .beAnnotatedWith(org.springframework.stereotype.Repository.class);
        // @formatter:on

        rule.check(ArchitectureConstants.CLASSES);
    }
}
