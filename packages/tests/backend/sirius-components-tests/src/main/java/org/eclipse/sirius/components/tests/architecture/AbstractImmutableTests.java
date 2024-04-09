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
package org.eclipse.sirius.components.tests.architecture;

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

import org.eclipse.sirius.components.annotations.Immutable;
import org.junit.jupiter.api.Test;

/**
 * Superclass of the immutable test cases.
 *
 * @author sbegaudeau
 */
public abstract class AbstractImmutableTests {
    protected abstract String getProjectRootPackage();

    protected abstract JavaClasses getClasses();

    @Test
    public void immutableClassesShouldBeFinal() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Immutable.class)
                .should()
                .haveModifier(FINAL)
                .allowEmptyShould(true);

        rule.check(this.getClasses());
    }

    @Test
    public void immutableClassesShouldHaveAPrivateConstructor() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Immutable.class)
                .should()
                .haveOnlyPrivateConstructors()
                .allowEmptyShould(true);

        rule.check(this.getClasses());
    }

    @Test
    public void immutableClassesShouldHavePublicGetters() {
        ArchRule rule = ArchRuleDefinition.fields()
                .that()
                .areDeclaredInClassesThat()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areDeclaredInClassesThat()
                .areAnnotatedWith(Immutable.class)
                .should(this.haveAPublicGetter())
                .allowEmptyShould(true);

        rule.check(this.getClasses());
    }

    private ArchCondition<JavaField> haveAPublicGetter() {
        return new ArchCondition<>("have a public getter") {
            @Override
            public void check(JavaField javaField, ConditionEvents events) {
                if (!javaField.getModifiers().contains(STATIC)) {
                    JavaClass javaClass = javaField.getOwner();

                    String getterName = "get" + javaField.getName().substring(0, 1).toUpperCase() + javaField.getName().substring(1);
                    if (javaField.getRawType().getFullName().equals("java.lang.Boolean") || javaField.getRawType().getFullName().equals("boolean")) {
                        getterName = "is" + javaField.getName().substring(0, 1).toUpperCase() + javaField.getName().substring(1);
                    }

                    boolean isConditionSatisfied = false;
                    try {
                        JavaMethod method = javaClass.getMethod(getterName);
                        isConditionSatisfied = method != null && method.getModifiers().contains(PUBLIC);
                    } catch (IllegalArgumentException exception) {
                        // Getter not found
                        isConditionSatisfied = false;
                    }

                    String message = "The field has a getter";
                    if (!isConditionSatisfied) {
                        message = "The field " + javaField.getFullName() + " does not have a getter";
                    }
                    events.add(new SimpleConditionEvent(javaField, isConditionSatisfied, message));
                }
            }
        };
    }

    @Test
    public void immutableClassesShouldNotHaveSetters() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Immutable.class)
                .should(this.notHaveSetters())
                .allowEmptyShould(true);

        rule.check(this.getClasses());
    }

    private ArchCondition<JavaClass> notHaveSetters() {
        return new ArchCondition<>("not have setters") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                long settersCount = javaClass.getMethods().stream()
                        .filter(javaMethod -> javaMethod.getName().startsWith("set"))
                        .count();

                boolean isConditionSatisfied = settersCount == 0;
                String message = "The class does not have any setters";
                if (!isConditionSatisfied) {
                    message = "The class " + javaClass.getName() + " does have setters";
                }
                events.add(new SimpleConditionEvent(javaClass, isConditionSatisfied, message));
            }
        };
    }

    @Test
    public void immutableClassesShouldHaveABuilderMethod() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Immutable.class)
                .should(this.haveABuilderMethod())
                .allowEmptyShould(true);

        rule.check(this.getClasses());
    }

    private ArchCondition<JavaClass> haveABuilderMethod() {
        return new ArchCondition<>("have a builder method") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                long count = javaClass.getMethods().stream()
                        .filter(method -> method.getName().equals("new" + javaClass.getSimpleName()))
                        .filter(method -> "Builder".equals(method.getRawReturnType().getSimpleName()))
                        .count();

                boolean isConditionSatisfied = count > 0;
                String message = "The class has a builder method";
                if (!isConditionSatisfied) {
                    message = MessageFormat.format("The class {0} does not have a builder method", javaClass.getSimpleName());
                }
                events.add(new SimpleConditionEvent(javaClass, isConditionSatisfied, message));
            }
        };
    }

    @Test
    public void immutableClassesShouldHaveANestedBuilder() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areAnnotatedWith(Immutable.class)
                .should(new HaveAValidBuilderCondition(this.getClasses()))
                .allowEmptyShould(true);

        rule.check(this.getClasses());
    }
}
