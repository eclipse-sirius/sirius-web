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

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import org.eclipse.sirius.web.annotations.Audited;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.stereotype.Service;

/**
 * Superclass of the Spring service tests.
 *
 * @author sbegaudeau
 */
public abstract class AbstractServicesTestCases {

    private static final String SERVICES_PACKAGE = "..services.."; //$NON-NLS-1$

    protected abstract String getProjectRootPackage();

    protected abstract JavaClasses getClasses();

    @Test
    @Ignore
    public void servicesClassShouldBeInAServicesPackage() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Service.class)
                .should()
                .resideInAPackage(SERVICES_PACKAGE);
        // @formatter:on

        rule.check(this.getClasses());
    }

    @Test
    public void serviceClassShouldOnlyUseAuditedRepositoryMethods() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .should()
                .callMethodWhere(this.nonAuditedRepositoryMethod());
        // @formatter:on

        rule.check(this.getClasses());
    }

    private DescribedPredicate<JavaMethodCall> nonAuditedRepositoryMethod() {
        return new DescribedPredicate<>("the repository method called has not been audited") { //$NON-NLS-1$
            @Override
            public boolean apply(JavaMethodCall javaMethodCall) {
                JavaClass targetJavaClass = javaMethodCall.getTargetOwner();
                String fullName = targetJavaClass.getFullName();

                boolean isRepository = fullName.endsWith("Repository"); //$NON-NLS-1$
                boolean hasBeenAudited = javaMethodCall.getTarget().isAnnotatedWith(Audited.class);

                return isRepository && !hasBeenAudited;
            }
        };
    }
}
