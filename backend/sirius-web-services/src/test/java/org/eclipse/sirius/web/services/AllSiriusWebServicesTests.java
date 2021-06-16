/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.services;

import org.eclipse.sirius.web.services.architecture.CodingRulesTestCases;
import org.eclipse.sirius.web.services.architecture.ImmutableTestCases;
import org.eclipse.sirius.web.services.projects.ProjectServiceTestCases;
import org.eclipse.sirius.web.services.projects.RenameProjectEventHandlerTestCases;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite used to run all the unit tests of the forms.
 *
 * @author sbegaudeau
 */
@RunWith(Suite.class)
@SuiteClasses({ CodingRulesTestCases.class, ImmutableTestCases.class, ProjectServiceTestCases.class, RenameProjectEventHandlerTestCases.class })
public final class AllSiriusWebServicesTests {
    private AllSiriusWebServicesTests() {
        // Prevent instantiation
    }
}
