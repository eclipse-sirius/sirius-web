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
package org.eclipse.sirius.components.spring.tests.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Superclass of all the Spring coding rules test cases.
 *
 * @author sbegaudeau
 */
public abstract class AbstractSpringCodingRulesTests {

    protected abstract String getProjectRootPackage();

    protected abstract JavaClasses getClasses();

    @Test
    public void noClassesShouldUseAutowired() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noFields()
                .that()
                .areDeclaredInClassesThat()
                .resideInAPackage(this.getProjectRootPackage())
                .should()
                .beAnnotatedWith(Autowired.class);
        // @formatter:on

        rule.check(this.getClasses());
    }
}
