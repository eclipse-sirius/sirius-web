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
package org.eclipse.sirius.web.forms;

import org.eclipse.sirius.web.forms.architecture.CodingRulesTestCases;
import org.eclipse.sirius.web.forms.architecture.ImmutableTestCases;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite used to run all the unit tests of the forms.
 *
 * @author sbegaudeau
 */
@RunWith(Suite.class)
@SuiteClasses({ CodingRulesTestCases.class, ImmutableTestCases.class })
public final class AllSiriusWebFormsTests {
    private AllSiriusWebFormsTests() {
        // Prevent instantiation
    }
}
