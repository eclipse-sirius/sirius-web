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
package org.eclipse.sirius.web.persistence;

import org.eclipse.sirius.web.persistence.architecture.CodingRulesTestCases;
import org.eclipse.sirius.web.persistence.architecture.ConfigurationTestCases;
import org.eclipse.sirius.web.persistence.architecture.RepositoryTestCases;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite of the sirius-web-persistence project.
 *
 * @author sbegaudeau
 */
@RunWith(Suite.class)
@SuiteClasses({ CodingRulesTestCases.class, ConfigurationTestCases.class, RepositoryTestCases.class })
public final class AllSiriusWebPersistenceTests {
    private AllSiriusWebPersistenceTests() {
        // Prevent instantiation
    }
}
