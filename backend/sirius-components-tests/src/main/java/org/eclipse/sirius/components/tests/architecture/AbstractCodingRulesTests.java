/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import static com.tngtech.archunit.core.domain.JavaModifier.ABSTRACT;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.GeneralCodingRules;

import java.lang.annotation.Target;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.sirius.components.annotations.Immutable;
import org.junit.jupiter.api.Test;

/**
 * Superclass of all the coding rules test cases.
 *
 * @author sbegaudeau
 */
public abstract class AbstractCodingRulesTests {
    private static final String GUAVA_ANNOTATIONS = "com.google.common.annotations.."; //$NON-NLS-1$

    private static final String GUAVA_BASE = "com.google.common.base.."; //$NON-NLS-1$

    private static final String GUAVA_COLLECT = "com.google.common.collect.."; //$NON-NLS-1$

    private static final String GUAVA_ESCAPE = "com.google.common.escape.."; //$NON-NLS-1$

    private static final String GUAVA_EVENTBUS = "com.google.common.eventbus.."; //$NON-NLS-1$

    private static final String GUAVA_HASH = "com.google.common.hash.."; //$NON-NLS-1$

    private static final String GUAVA_HTML = "com.google.common.html.."; //$NON-NLS-1$

    private static final String GUAVA_IO = "com.google.common.io.."; //$NON-NLS-1$

    private static final String GUAVA_MATH = "com.google.common.math.."; //$NON-NLS-1$

    private static final String GUAVA_NET = "com.google.common.net.."; //$NON-NLS-1$

    private static final String GUAVA_PRIMITIVES = "com.google.common.primitives.."; //$NON-NLS-1$

    private static final String GUAVA_REFLECT = "com.google.common.reflect.."; //$NON-NLS-1$

    private static final String GUAVA_UTIL = "com.google.common.util.."; //$NON-NLS-1$

    private static final String GUAVA_XML = "com.google.common.xml.."; //$NON-NLS-1$

    private static final String GUAVA_THIRDPARTY = "com.google.thirdparty.."; //$NON-NLS-1$

    private static final String SPRING_STRINGUTILS = "org.springframework.util.StringUtils"; //$NON-NLS-1$

    private static final String TESTCASE_SUFFIX = "TestCases"; //$NON-NLS-1$

    private static final String EMF = "org.eclipse.emf.."; //$NON-NLS-1$

    private static final String EMFJSON = "org.eclipse.sirius.emfjson.."; //$NON-NLS-1$

    private static final String APACHE_COMMONS = "org.apache.commons.."; //$NON-NLS-1$

    private static final String IS = "is"; //$NON-NLS-1$

    private static final String GET = "get"; //$NON-NLS-1$

    protected abstract String getProjectRootPackage();

    protected abstract JavaClasses getClasses();

    @Test
    public void noClassesShouldUseGuava() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        GUAVA_ANNOTATIONS,
                        GUAVA_BASE,
                        GUAVA_COLLECT,
                        GUAVA_ESCAPE,
                        GUAVA_EVENTBUS,
                        GUAVA_HASH,
                        GUAVA_HTML,
                        GUAVA_IO,
                        GUAVA_MATH,
                        GUAVA_NET,
                        GUAVA_PRIMITIVES,
                        GUAVA_REFLECT,
                        GUAVA_UTIL,
                        GUAVA_XML,
                        GUAVA_THIRDPARTY
                );
        // @formatter:on

        rule.check(this.getClasses());
    }

    public void noClassesShouldUseEMF() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .should()
                .dependOnClassesThat()
                .resideInAPackage(EMF)
                .orShould()
                .dependOnClassesThat()
                .resideInAPackage(EMFJSON);
        // @formatter:on

        rule.check(this.getClasses());
    }

    public void noClassesShouldUseApacheCommons() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .should()
                .dependOnClassesThat()
                .resideInAPackage(APACHE_COMMONS);
        // @formatter:on

        rule.check(this.getClasses());
    }

    @Test
    public void noClassesShouldUseSpringStringUtils() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .should()
                .dependOnClassesThat()
                .areAssignableTo(SPRING_STRINGUTILS);
        // @formatter:on

        rule.check(this.getClasses());
    }

    @Test
    public void noClassesShouldUseStandardStreams() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .should(GeneralCodingRules.ACCESS_STANDARD_STREAMS);
        // @formatter:on

        rule.check(this.getClasses());
    }

    @Test
    public void noClassesShouldThrowGenericExceptions() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .should(GeneralCodingRules.THROW_GENERIC_EXCEPTIONS);
        // @formatter:on

        rule.check(this.getClasses());
    }

    @Test
    public void noClassesShouldUseJavaLogging() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .should(GeneralCodingRules.USE_JAVA_UTIL_LOGGING);
        // @formatter:on

        rule.check(this.getClasses());
    }

    @Test
    public void classesShouldUsePublicVisibility() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .should().bePackagePrivate()
                .andShould().bePrivate()
                .andShould().beProtected();
        // @formatter:on
        rule.check(this.getClasses());
    }

    @Test
    public void classesShouldHavePublicOrPrivateConstructors() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .should(this.haveProtectedOrPackageConstructor());
        // @formatter:on
        rule.check(this.getClasses());
    }

    private ArchCondition<JavaClass> haveProtectedOrPackageConstructor() {
        return new ArchCondition<>("have a package private constructor") { //$NON-NLS-1$
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                // @formatter:off
                boolean hasProtectedOrPackageConstructor = javaClass.getAllConstructors().stream()
                        .filter(javaConstructor -> {
                            return javaConstructor.reflect().getDeclaringClass().equals(javaClass.reflect());
                        })
                        .anyMatch(javaConstructor -> {
                            boolean isProtected  = javaConstructor.getModifiers().contains(JavaModifier.PROTECTED);
                            boolean isPackage = !javaConstructor.getModifiers().contains(JavaModifier.PRIVATE) && !javaConstructor.getModifiers().contains(JavaModifier.PROTECTED) && !javaConstructor.getModifiers().contains(JavaModifier.PUBLIC);
                            return isProtected || isPackage;
                        });
                // @formatter:on

                boolean isAnonymousClass = javaClass.isAnonymousClass();
                boolean isEnum = javaClass.isEnum();

                boolean conditionSatisfied = hasProtectedOrPackageConstructor && !isAnonymousClass && !isEnum;

                String pattern = "The class {0} has a constructor with a protected or package visibility"; //$NON-NLS-1$
                String message = MessageFormat.format(pattern, javaClass.getSimpleName());
                events.add(new SimpleConditionEvent(javaClass, conditionSatisfied, message));
            }
        };
    }

    @Test
    public void interfacesShouldRespectNamingConventions() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areInterfaces()
                .and()
                .areNotAnnotatedWith(Target.class)
                .should()
                .haveSimpleNameStartingWith("I"); //$NON-NLS-1$
        // @formatter:on

        rule.check(this.getClasses());
    }

    @Test
    public void abstractClassesShouldRespectNamingConventions() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areNotInterfaces()
                .and()
                .haveModifier(ABSTRACT)
                .should()
                .haveSimpleNameStartingWith("Abstract"); //$NON-NLS-1$
        // @formatter:on

        rule.check(this.getClasses());
    }

    /**
     * Composition should be used instead of inheritance to share business code since it is way easier to test and it is
     * easier to determine and thus maintain the dependencies of a piece of business code.
     */
    @Test
    public void abstractClassesShouldNotContainBusinessCode() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areNotInterfaces()
                .and()
                .haveModifier(ABSTRACT)
                .should(this.notContainBusinessCode());
        // @formatter:on

        rule.check(this.getClasses());
    }

    /**
     * This predicate will be used to identify business code in a class.
     *
     * <p>
     * For that it will look for the following patterns in a Java class:
     * <p>
     * <ul>
     * <li>methods which are not getters with a field matching the name of the method</li>
     * </ul>
     *
     * @return A predicate which can be used to identify business code in a class
     */
    private ArchCondition<JavaClass> notContainBusinessCode() {
        return new ArchCondition<>("not contain business code") { //$NON-NLS-1$
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                boolean isConditionSatisfied = true;

                Set<JavaMethod> methods = javaClass.getMethods();
                Iterator<JavaMethod> iterator = methods.iterator();
                while (isConditionSatisfied && iterator.hasNext()) {
                    JavaMethod javaMethod = iterator.next();
                    String name = javaMethod.getName();
                    if (name.startsWith(IS) && javaMethod.getRawReturnType().isAssignableTo(Boolean.class)) {
                        name = name.substring(IS.length());
                    } else if (name.startsWith(GET)) {
                        name = name.substring(GET.length());
                    }

                    if (!name.isBlank()) {
                        name = name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
                        isConditionSatisfied = javaClass.tryGetField(name).isPresent();
                    } else {
                        isConditionSatisfied = false;
                    }
                }

                String message = "The abstract class does not have any business code"; //$NON-NLS-1$
                if (!isConditionSatisfied) {
                    String pattern = "The abstract class {0} does contain business code, please favor composition over inheritance to share business code"; //$NON-NLS-1$
                    message = MessageFormat.format(pattern, javaClass.getSimpleName());
                }
                events.add(new SimpleConditionEvent(javaClass, isConditionSatisfied, message));
            }
        };
    }

    /**
     * Static methods make the lifecycle of the code complex. They make the code harder to reason about and they can
     * very easily become either:
     *
     * <ul>
     * <li>A problem to unit tests a class since they can't be mocked</li>
     * <li>God classes with a collection of unrelated utility methods</li>
     * </ul>
     *
     * With the introduction of this test, it appears that apart from some utility constructors like in the @Immutable
     * classes, we do not have a single static method with a real behavior. Thus nothing of value will be lost.
     *
     * In this test, we will ignore the following use cases:
     *
     * <ul>
     * <li>Enum since Java enum are considered as extending java.lang.Enum which comes with static methods</li>
     * <li>Java 8+ lambdas which are compiled to hidden static methods (to make it short)</li>
     * <li>@Parameters methods used by JUnit test cases</li>
     * <li>@Immutable classes which are using a static method to create the builder</li>
     * <li>@Constructor methods which are used as alternate constructors</li>
     * </ul>
     */
    @Test
    public void noMethodsShouldBeStatic() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noMethods()
                .that()
                .areDeclaredInClassesThat()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areDeclaredInClassesThat()
                .areNotAnnotatedWith(Immutable.class)
                .and()
                .areDeclaredInClassesThat()
                .areNotAssignableTo(Enum.class)
                .and()
                .areDeclaredInClassesThat(this.isNotTestCase())
                .and(this.isNotLambda())
                .and(this.isNotSwitchTable())
                .should()
                .beStatic();
        // @formatter:on

        rule.check(this.getClasses());
    }

    private DescribedPredicate<JavaClass> isNotTestCase() {
        return new DescribedPredicate<>("is not a test case") { //$NON-NLS-1$
            @Override
            public boolean apply(JavaClass javaClass) {
                return !javaClass.getName().endsWith(TESTCASE_SUFFIX);
            }
        };
    }

    /**
     * Lambda are compiled as hidden static methods named lambda$XXX. This method will help detect them and ignore them.
     *
     * @return A predicate which will help us ignore lambda methods
     */
    private DescribedPredicate<JavaMethod> isNotLambda() {
        return new DescribedPredicate<>("is not a lambda") { //$NON-NLS-1$
            @Override
            public boolean apply(JavaMethod javaMethod) {
                return !javaMethod.getName().startsWith("lambda$"); //$NON-NLS-1$
            }
        };
    }

    /**
     * Some switch can be compiled as hidden static methods named $SWITCH_TABLE$. This predicate will help detect them
     * and ignore them.
     *
     * @return A predicate which help us ignore switch expressions
     */
    private DescribedPredicate<JavaMethod> isNotSwitchTable() {
        return new DescribedPredicate<>("is not a switch table (whatever that is...)") { //$NON-NLS-1$
            @Override
            public boolean apply(JavaMethod javaMethod) {
                return !javaMethod.getFullName().contains("$SWITCH_TABLE$"); //$NON-NLS-1$
            }
        };
    }
}
