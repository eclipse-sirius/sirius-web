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
package org.eclipse.sirius.web.tests.architecture;

import static com.tngtech.archunit.core.domain.JavaModifier.FINAL;
import static com.tngtech.archunit.core.domain.JavaModifier.PUBLIC;
import static com.tngtech.archunit.core.domain.JavaModifier.STATIC;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import java.text.MessageFormat;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.junit.Test;

/**
 * Superclass of the immutable test cases.
 *
 * @author sbegaudeau
 */
public abstract class AbstractImmutableTestCases {

    protected abstract String getProjectRootPackage();

    protected abstract JavaClasses getClasses();

    @Test
    public void immutableClassesShouldBeFinal() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Immutable.class)
                .should()
                .haveModifier(FINAL);
        // @formatter:on

        rule.check(this.getClasses());
    }

    @Test
    public void immutableClassesShouldHaveAPrivateConstructor() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Immutable.class)
                .and()
                .areNotAnnotatedWith(GraphQLInputObjectType.class)
                .should()
                .haveOnlyPrivateConstructors();
        // @formatter:on

        rule.check(this.getClasses());
    }

    @Test
    public void immutableClassesShouldHavePublicGetters() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.fields()
                .that()
                .areDeclaredInClassesThat()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areDeclaredInClassesThat()
                .areAnnotatedWith(Immutable.class)
                .should(this.haveAPublicGetter());
        // @formatter:on

        rule.check(this.getClasses());
    }

    private ArchCondition<JavaField> haveAPublicGetter() {
        return new ArchCondition<>("have a public getter") { //$NON-NLS-1$
            @Override
            public void check(JavaField javaField, ConditionEvents events) {
                if (!javaField.getModifiers().contains(STATIC)) {
                    JavaClass javaClass = javaField.getOwner();

                    String getterName = "get" + javaField.getName().substring(0, 1).toUpperCase() + javaField.getName().substring(1); //$NON-NLS-1$
                    if (javaField.getRawType().getFullName().equals("java.lang.Boolean") || javaField.getRawType().getFullName().equals("boolean")) { //$NON-NLS-1$ //$NON-NLS-2$
                        getterName = "is" + javaField.getName().substring(0, 1).toUpperCase() + javaField.getName().substring(1); //$NON-NLS-1$
                    }

                    boolean isConditionSatisfied = false;
                    try {
                        JavaMethod method = javaClass.getMethod(getterName);
                        isConditionSatisfied = method != null && method.getModifiers().contains(PUBLIC);
                    } catch (IllegalArgumentException exception) {
                        // Getter not found
                        isConditionSatisfied = false;
                    }

                    String message = "The field has a getter"; //$NON-NLS-1$
                    if (!isConditionSatisfied) {
                        message = "The field " + javaField.getFullName() + " does not have a getter"; //$NON-NLS-1$ //$NON-NLS-2$
                    }
                    events.add(new SimpleConditionEvent(javaField, isConditionSatisfied, message));
                }
            }
        };
    }

    @Test
    public void immutableClassesShouldNotHaveSetters() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Immutable.class)
                .should(this.notHaveSetters());
        // @formatter:on

        rule.check(this.getClasses());
    }

    private ArchCondition<JavaClass> notHaveSetters() {
        return new ArchCondition<>("not have setters") { //$NON-NLS-1$
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                // @formatter:off
                long settersCount = javaClass.getMethods().stream()
                        .filter(javaMethod -> javaMethod.getName().startsWith("set")) //$NON-NLS-1$
                        .count();
                // @formatter:on

                boolean isConditionSatisfied = settersCount == 0;
                String message = "The class does not have any setters"; //$NON-NLS-1$
                if (!isConditionSatisfied) {
                    message = "The class " + javaClass.getName() + " does have setters"; //$NON-NLS-1$//$NON-NLS-2$
                }
                events.add(new SimpleConditionEvent(javaClass, isConditionSatisfied, message));
            }
        };
    }

    @Test
    public void immutableClassesShouldHaveABuilderMethod() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Immutable.class)
                .should(this.haveABuilderMethod());
        // @formatter:on

        rule.check(this.getClasses());
    }

    private ArchCondition<JavaClass> haveABuilderMethod() {
        return new ArchCondition<>("have a builder method") { //$NON-NLS-1$
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                // @formatter:off
                long count = javaClass.getMethods().stream()
                        .filter(method -> method.getName().equals("new" + javaClass.getSimpleName())) //$NON-NLS-1$
                        .filter(method -> "Builder".equals(method.getRawReturnType().getSimpleName())) //$NON-NLS-1$
                        .count();
                // @formatter:on

                boolean isConditionSatisfied = count > 0;
                String message = "The class has a builder method"; //$NON-NLS-1$
                if (!isConditionSatisfied) {
                    message = MessageFormat.format("The class {0} does not have a builder method", javaClass.getSimpleName()); //$NON-NLS-1$
                }
                events.add(new SimpleConditionEvent(javaClass, isConditionSatisfied, message));
            }
        };
    }

    @Test
    public void immutableClassesShouldHaveANestedBuilder() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Immutable.class)
                .should(new HaveAValidBuilderCondition(this.getClasses()));
        // @formatter:on

        rule.check(this.getClasses());
    }
}
