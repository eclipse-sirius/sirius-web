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

import static com.tngtech.archunit.core.domain.JavaModifier.FINAL;
import static com.tngtech.archunit.core.domain.JavaModifier.PUBLIC;
import static com.tngtech.archunit.core.domain.JavaModifier.STATIC;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Architectural condition used to test that Java classes have a valid builder.
 *
 * @author sbegaudeau
 */
public class HaveAValidBuilderCondition extends ArchCondition<JavaClass> {

    private static final String BUILD_METHOD_NAME = "build";

    private static final String LAMBDA = "lambda$";

    private final JavaClasses javaClasses;

    public HaveAValidBuilderCondition(JavaClasses javaClasses) {
        super("have a builder");
        this.javaClasses = Objects.requireNonNull(javaClasses);
    }

    @Override
    public void check(JavaClass javaClass, ConditionEvents events) {
        String fullName = javaClass.getFullName();
        JavaClass builderJavaClass = this.javaClasses.get(fullName + "$Builder");

        boolean isValidBuilder = builderJavaClass.getModifiers().contains(FINAL);
        isValidBuilder = isValidBuilder && builderJavaClass.getModifiers().contains(PUBLIC);

        // @formatter:off
        List<JavaField> javaFields = javaClass.getAllFields().stream()
                .filter(field -> !field.getModifiers().contains(STATIC))
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on

        for (JavaField javaField : javaFields) {
            JavaField builderField = builderJavaClass.getField(javaField.getName());
            isValidBuilder = isValidBuilder && builderField != null && builderField.getRawType().getName().equals(javaField.getRawType().getName());
        }

        for (JavaMethod javaMethod : builderJavaClass.getMethods()) {
            if (!BUILD_METHOD_NAME.equals(javaMethod.getName()) && !javaMethod.getName().contains(LAMBDA)) {
                isValidBuilder = isValidBuilder && javaMethod.getRawReturnType().equals(builderJavaClass);

                JavaField javaField = builderJavaClass.getField(javaMethod.getName());
                isValidBuilder = isValidBuilder && javaField != null;
                isValidBuilder = isValidBuilder && javaMethod.getRawParameterTypes().size() == 1;
            }
        }

        // @formatter:off
        long buildMethodCount = builderJavaClass.getMethods().stream()
                .filter(method -> BUILD_METHOD_NAME.equals(method.getName()))
                .filter(method -> javaClass.getSimpleName().equals(method.getRawReturnType()
                        .getSimpleName())).count();
        // @formatter:on

        isValidBuilder = isValidBuilder && buildMethodCount == 1;

        String message = "The builder is valid";
        if (!isValidBuilder) {
            message = MessageFormat.format("The builder of the class {0} is not valid", javaClass.getSimpleName());
        }
        events.add(new SimpleConditionEvent(builderJavaClass, isValidBuilder, message));
    }
}
