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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests of the path service.
 *
 * @author hmarchadour
 */
@RunWith(Parameterized.class)
public class PathServiceIsObfuscatedTestCases {

    private final String imagePath;

    private final boolean obfuscated;

    public PathServiceIsObfuscatedTestCases(String imagePath, boolean obfuscated) {
        this.imagePath = imagePath;
        this.obfuscated = obfuscated;
    }

    @Parameters(name = "{index} - imagePath: {0}")
    public static List<Object[]> getImagePaths() {
        // @formatter:off
        return List.of(
                new Object[] {"/Users/jdoe/.m2/repository/sample1/sample1-6.1.0.202006290907.jar!/icons/full/obj16/Relationship.png", false}, //$NON-NLS-1$
                new Object[] {"/Users/jdoe/.m2/repository/sample2/sample2-6.1.0.202006290907.jar!/icons/full/obj16/Relationship.png", false}, //$NON-NLS-1$
                new Object[] {"/hash/588d4998-b86a-30a6-a9b9-9ffe2ff7d142!/icons/full/obj16/Relationship.gif", true}, //$NON-NLS-1$
                new Object[] {"/hash/588d4998-b86a-30a6-a9b9-9ffe2ff7d142!a", true}, //$NON-NLS-1$
                new Object[] {"/hash/588d4998-b86a-30a6-a9b9-9ffe2ff7d142!", false}, //$NON-NLS-1$
                new Object[] {"/hash/!", false}, //$NON-NLS-1$
                new Object[] {"/hash/", false}, //$NON-NLS-1$
                new Object[] {"", false}, //$NON-NLS-1$
                new Object[] {null, false}
        );
        // @formatter:on
    }

    @Test
    public void test() {
        IPathService pathService = new PathService();
        assertThat(pathService.isObfuscated(this.imagePath)).isEqualTo(this.obfuscated);
    }

}
