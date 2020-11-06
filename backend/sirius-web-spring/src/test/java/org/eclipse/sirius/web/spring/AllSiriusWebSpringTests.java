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
package org.eclipse.sirius.web.spring;

import org.eclipse.sirius.web.spring.architecture.CodingRulesTestCases;
import org.eclipse.sirius.web.spring.architecture.SpringCodingRulesTestCases;
import org.eclipse.sirius.web.spring.controllers.DocumentControllerTestCases;
import org.eclipse.sirius.web.spring.controllers.ImagesControllerTestCases;
import org.eclipse.sirius.web.spring.controllers.ProjectControllerTestCases;
import org.eclipse.sirius.web.spring.services.PathServiceIsObfuscatedTestCases;
import org.eclipse.sirius.web.spring.services.PathServiceTestObfuscateAndResolveCases;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite of the project.
 *
 * @author sbegaudeau
 */
@RunWith(Suite.class)
//@formatter:off
@SuiteClasses({
    CodingRulesTestCases.class,
    SpringCodingRulesTestCases.class,
    DocumentControllerTestCases.class,
    ImagesControllerTestCases.class,
    ProjectControllerTestCases.class,
    PathServiceIsObfuscatedTestCases.class,
    PathServiceTestObfuscateAndResolveCases.class
})
//@formatter:on
public final class AllSiriusWebSpringTests {
    private AllSiriusWebSpringTests() {
        // Prevent instantiation
    }
}
