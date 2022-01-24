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
package org.eclipse.sirius.web.spring.tests.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

/**
 * Superclass of all the Spring configuration tests.
 *
 * @author sbegaudeau
 */
public abstract class AbstractConfigurationTests {

    private static final String CONFIGURATION_PACKAGE = "..configuration.."; //$NON-NLS-1$

    protected abstract String getProjectRootPackage();

    protected abstract JavaClasses getClasses();

    @Test
    public void configurationClassesShouldBeInAConfigurationPackage() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Configuration.class)
                .should()
                .resideInAPackage(CONFIGURATION_PACKAGE);
        // @formatter:on

        rule.check(this.getClasses());
    }

    @Test
    public void configurationClassShouldOnlyHaveFinalFields() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Configuration.class)
                .should()
                .haveOnlyFinalFields();
        // @formatter:on

        rule.check(this.getClasses());
    }
}
