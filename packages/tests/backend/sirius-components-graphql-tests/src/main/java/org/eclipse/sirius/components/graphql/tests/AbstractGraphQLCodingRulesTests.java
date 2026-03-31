/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.components.graphql.tests;

import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import java.text.MessageFormat;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.annotations.spring.graphql.SubscriptionDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.junit.jupiter.api.Test;

/**
 * Superclass of all GraphQL coding rules test cases.
 *
 * @author tgiraudet
 */
public abstract class AbstractGraphQLCodingRulesTests {

    protected abstract String getProjectRootPackage();

    protected abstract JavaClasses getClasses();

    @Test
    public void dataFetcherWithFieldCoordinatesShouldHaveDataFetcherAnnotation() {
        ArchRule rule = ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(this.getProjectRootPackage())
            .and()
            .areAssignableTo(IDataFetcherWithFieldCoordinates.class)
            .should(this.haveDataFetcherAnnotation());

        rule.check(this.getClasses());
    }

    @Test
    public void dataFetcherShouldRespectNamingConventions() {
        ArchRule rule = ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(this.getProjectRootPackage())
            .and()
            .areAssignableTo(IDataFetcherWithFieldCoordinates.class)
            .should(this.nameMatchesTypeFieldDataFetcher());

        rule.check(this.getClasses());
    }

    private ArchCondition<JavaClass> haveDataFetcherAnnotation() {
        return new ArchCondition<>("have a data fetcher annotation") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                var optionalDataFetcherAnnotation = AbstractGraphQLCodingRulesTests.this.getDataFetcherAnnotation(javaClass);
                var pattern = "The data fetcher class {0} should have a data fetcher annotation among: QueryDataFetcher, MutationDataFetcher, SubscriptionDataFetcher";
                String message = MessageFormat.format(pattern, javaClass.getSimpleName());
                events.add(new SimpleConditionEvent(javaClass, optionalDataFetcherAnnotation.isPresent(), message));
            }
        };
    }

    private ArchCondition<JavaClass> nameMatchesTypeFieldDataFetcher() {
        return new ArchCondition<>("name matches TypeFieldDataFetcher") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                var optionalDataFetcherAnnotation = AbstractGraphQLCodingRulesTests.this.getDataFetcherAnnotation(javaClass);
                boolean hasCorrectName = true;
                var expectedName = javaClass.getSimpleName();

                if (optionalDataFetcherAnnotation.isPresent()) {
                    var dataFetcherAnnotation = optionalDataFetcherAnnotation.get();
                    var type = dataFetcherAnnotation.get("type").orElse("").toString();
                    var field = dataFetcherAnnotation.get("field").orElse("").toString();
                    if (field.length() > 0) {
                        field = field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
                    }

                    expectedName = MessageFormat.format("{0}{1}DataFetcher", type, field);
                    hasCorrectName = javaClass.getSimpleName().equals(expectedName);
                }
                var pattern = "The data fetcher class {0} should have a name matching ''<Type><Field>DataFetcher'': expecting ''{1}''";
                String message = MessageFormat.format(pattern, javaClass.getSimpleName(), expectedName);
                events.add(new SimpleConditionEvent(javaClass, hasCorrectName, message));
            }
        };
    }

    private Optional<JavaAnnotation<JavaClass>> getDataFetcherAnnotation(JavaClass javaClass) {
        return javaClass.tryGetAnnotationOfType(QueryDataFetcher.class.getTypeName())
            .or(() -> javaClass.tryGetAnnotationOfType(MutationDataFetcher.class.getTypeName()))
            .or(() -> javaClass.tryGetAnnotationOfType(SubscriptionDataFetcher.class.getTypeName()));
    }

}
