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
package org.eclipse.sirius.web.spring.collaborative;

import org.eclipse.sirius.web.spring.collaborative.architecture.CodingRulesTestCases;
import org.eclipse.sirius.web.spring.collaborative.architecture.SpringCodingRulesTestCases;
import org.eclipse.sirius.web.spring.collaborative.handlers.CreateChildEventHandlerTestCases;
import org.eclipse.sirius.web.spring.collaborative.handlers.CreateRootObjectEventHandlerTestCases;
import org.eclipse.sirius.web.spring.collaborative.handlers.DeleteObjectEventHandlerTestCases;
import org.eclipse.sirius.web.spring.collaborative.handlers.DeleteRepresentationEventHandlerTestCases;
import org.eclipse.sirius.web.spring.collaborative.handlers.RenameObjectEventHandlerTestCases;
import org.eclipse.sirius.web.spring.collaborative.handlers.RenameProjectEventHandlerTestCases;
import org.eclipse.sirius.web.spring.collaborative.projects.ProjectServiceTestCases;
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
    CreateChildEventHandlerTestCases.class,
    CreateRootObjectEventHandlerTestCases.class,
    DeleteObjectEventHandlerTestCases.class,
    DeleteRepresentationEventHandlerTestCases.class,
    RenameObjectEventHandlerTestCases.class,
    ProjectServiceTestCases.class,
    RenameProjectEventHandlerTestCases.class
})
//@formatter:on
public final class AllSiriusWebSpringCollaborativeTests {
    private AllSiriusWebSpringCollaborativeTests() {
        // Prevent instantiation
    }
}
