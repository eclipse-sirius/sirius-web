/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.api.architecture;

import static org.junit.jupiter.api.Assertions.fail;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.eclipse.sirius.web.annotations.PublicApi;
import org.junit.jupiter.api.Test;

/**
 * API coding rules tests.
 *
 * @author sbegaudeau
 */
public class APICodingRulesTests {
    @Test
    public void apiTypesShouldBeMarkedAsPublicApi() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage("org.eclipse.sirius.web.api.services..") //$NON-NLS-1$
                .or()
                .resideInAPackage("org.eclipse.sirius.web.api.configuration..") //$NON-NLS-1$
                .should()
                .beAnnotatedWith(PublicApi.class);
        // @formatter:on

        rule.check(ArchitectureConstants.CLASSES);
    }

    @Test
    public void publicClassesShouldNotDependOnNonPublicOnes() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .areAnnotatedWith(PublicApi.class)
                .should()
                .dependOnClassesThat(this.areNonApi());
        // @formatter:on

        rule.check(ArchitectureConstants.CLASSES);
    }

    private DescribedPredicate<JavaClass> areNonApi() {
        return new DescribedPredicate<>("are non API") { //$NON-NLS-1$
            @Override
            public boolean apply(JavaClass javaClass) {
                if (javaClass.getFullName().equals(PublicApi.class.getName())) {
                    return false;
                }

                boolean isPublicApi = javaClass.isAnnotatedWith(PublicApi.class);
                boolean isStandardJavaType = javaClass.getPackageName().startsWith("java."); //$NON-NLS-1$

                if (!isStandardJavaType && !isPublicApi) {
                    // Fallback for import from other jars, ArchUnit does not seem to find the annotations
                    try {
                        Class<?> aClass = Class.forName(javaClass.getFullName());
                        // @formatter:off
                        isPublicApi = Arrays.stream(aClass.getAnnotations())
                                .map(Annotation::annotationType)
                                .map(Class::getName)
                                .anyMatch(name -> PublicApi.class.getName().equals(name));
                        // @formatter:on
                    } catch (ClassNotFoundException e) {
                        fail(e.getMessage());
                    }
                }
                return !isPublicApi && !isStandardJavaType;
            }
        };
    }
}
