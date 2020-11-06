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
package org.eclipse.sirius.web.spring.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.sirius.web.services.api.IPathService;
import org.junit.Test;

/**
 * Tests of the path service.
 *
 * @author hmarchadour
 */
public class PathServiceTestObfuscateAndResolveCases {

    @Test
    public void testStandardJarPath() {
        IPathService pathService = new PathService();

        String firstPath = "/Users/jdoe/.m2/repository/sample1/sample1-6.1.0.202006290907.jar!/icons/full/obj16/Relationship.png"; //$NON-NLS-1$
        String secondPath = "/Users/jdoe/.m2/repository/sample2/sample2-6.1.0.202006290907.jar!/icons/full/obj16/Relationship.png"; //$NON-NLS-1$

        String firstHash = pathService.obfuscatePath(firstPath);
        String secondHash = pathService.obfuscatePath(secondPath);

        assertThat(pathService.isObfuscated(firstHash)).isTrue();
        assertThat(pathService.isObfuscated(secondHash)).isTrue();

        assertThat(firstHash).isNotEqualTo(secondHash);

        assertThat(pathService.resolvePath(firstHash)).isEqualTo(firstPath);
        assertThat(pathService.resolvePath(secondHash)).isEqualTo(secondPath);
    }

    @Test
    public void testInvalidJarPaths() {
        // @formatter:off
        List<String> invalidJarPaths = List.of(
                "", //$NON-NLS-1$
                "/Users/jdoe/.m2/repository/sample2", //$NON-NLS-1$
                "/Users/jdoe/.m2/repository/sample1/sample1-6.1.0.202006290907.jar", //$NON-NLS-1$
                "/Users/jdoe/.m2/repository/sample1/sample1-6.1.0.202006290907.jar!", //$NON-NLS-1$
                "/Users/jdoe/.m2/repository/sample1/sample1-6.1.0.202006290907.jar!/"//$NON-NLS-1$
        );
        // @formatter:on
        IPathService pathService = new PathService();
        for (String invalidJarPath : invalidJarPaths) {
            String hash = pathService.obfuscatePath(invalidJarPath);
            assertThat(pathService.isObfuscated(hash)).isFalse();
            assertThat(hash).isEqualTo(invalidJarPath);
        }
    }

    @Test
    public void testNullJarPath() {
        IPathService pathService = new PathService();
        String invalidJarPath = null;
        String hash = pathService.obfuscatePath(invalidJarPath);
        assertThat(pathService.isObfuscated(hash)).isFalse();
        assertThat(hash).isEqualTo(invalidJarPath);

    }
}
