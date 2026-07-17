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
package org.eclipse.sirius.components.tests.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

/**
 * EMF generation rules tests.
 *
 * @author lfasani
 */
public abstract class AbstractEMFGenerationRulesTests {
    protected abstract String getProjectRootPackage();

    protected abstract JavaClasses getClasses();

    @Test
    public void testClassesDoNotContainGeneratedNotTag() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(this.getProjectRootPackage())
                .and().areTopLevelClasses()
                .and().areNotInterfaces()
                .and().haveSimpleNameNotEndingWith("FactoryImpl")
                .and().haveSimpleNameNotEndingWith("ItemProviderAdapterFactory")
                .and().haveSimpleNameNotEndingWith("EditPlugin")
                .should(this.notContainJavadocTag("@generated NOT"));

        rule.check(this.getClasses());
    }

    /**
     * Custom ArchCondition to check for specific Javadoc content.
     * Note: Standard bytecode doesn't always preserve Javadocs unless compiled
     * with specific debug info or if you inspect the source. If ArchUnit is reading
     * compiled classes, you need a custom check or source-code parser.
     */
    private ArchCondition<JavaClass> notContainJavadocTag(String tag) {
        return new ArchCondition<JavaClass>("not contain Javadoc tag '" + tag + "' in class or methods") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                try {
                    // 1. Map the class package to a file system path sequence (e.g., com/example/myapp)
                    String packagePath = javaClass.getPackageName().replace('.', '/');
                    String fileName = javaClass.getSimpleName() + ".java";

                    // 2. Build the path directly to the project's source directory
                    Path sourcePath = Paths.get("src", "main", "java", packagePath, fileName);

                    // 3. Read the actual raw Java file content
                    String sourceCode = new String(Files.readAllBytes(sourcePath));

                    // 4. Direct check for the tag presence anywhere in the file
                    if (sourceCode.contains(tag)) {
                        String message = String.format("Class %s contains forbidden tag '%s'", javaClass.getName(), tag);
                        events.add(SimpleConditionEvent.violated(javaClass, message));
                    }
                } catch (IOException e) {
                    System.err.println("Could not read source file for " + javaClass.getName() + ": " + e.getMessage());
                }
            }
        };
    }
}
