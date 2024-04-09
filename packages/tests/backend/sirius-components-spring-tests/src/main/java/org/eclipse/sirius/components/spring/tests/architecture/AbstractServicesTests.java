/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import org.eclipse.sirius.components.annotations.Audited;
import org.junit.jupiter.api.Test;

/**
 * Superclass of the Spring service tests.
 *
 * @author sbegaudeau
 */
public abstract class AbstractServicesTests {

    protected abstract String getProjectRootPackage();

    protected abstract JavaClasses getClasses();

    @Test
    public void serviceClassShouldOnlyUseAuditedRepositoryMethods() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .should()
                .callMethodWhere(this.nonAuditedRepositoryMethod())
                .allowEmptyShould(true);

        rule.check(this.getClasses());
    }

    private DescribedPredicate<JavaMethodCall> nonAuditedRepositoryMethod() {
        return new DescribedPredicate<>("the repository method called has not been audited") {
            @Override
            public boolean test(JavaMethodCall javaMethodCall) {
                JavaClass targetJavaClass = javaMethodCall.getTargetOwner();
                String fullName = targetJavaClass.getFullName();

                boolean isRepository = fullName.endsWith("Repository");
                boolean hasBeenAudited = javaMethodCall.getTarget().isAnnotatedWith(Audited.class);

                return isRepository && !hasBeenAudited;
            }
        };
    }
}
