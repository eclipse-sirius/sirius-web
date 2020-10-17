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

import org.eclipse.sirius.web.persistence.repositories.AccountRepositoryIntegrationTestCases;
import org.eclipse.sirius.web.persistence.repositories.DocumentRepositoryIntegrationTestCases;
import org.eclipse.sirius.web.persistence.repositories.ProjectRepositoryIntegrationTestCases;
import org.eclipse.sirius.web.persistence.repositories.RepresentationRepositoryIntegrationTestCases;
import org.eclipse.sirius.web.persistence.repositories.TestPostgreSQLContainer;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Integration test suite of the sirius-web-persistence project.
 * <p>
 * The test suite will start by creating a Docker container from the official Docker PostgreSQL image. Then it will run
 * all the integration tests one after the other. Individual tests methods cannot make any hypothesis regarding the
 * state of the database when they will start running as the database will be empty. For example, one should not assume
 * that a test will run before another one and thus that the database will contain any data created by a previous test.
 * </p>
 * <p>
 * For that, every single test should be annotated with both @Test and @Transactional to ensure that the changes made by
 * the test are not committed after the test. Spring will ensure that a rollback occurs after each test execution. When
 * the test suite will start, it will do so using a clean database freshly started and provisioned thanks to Liquibase.
 * </p>
 *
 * @author sbegaudeau
 */
@RunWith(Suite.class)
// @formatter:off
@SuiteClasses({
    AccountRepositoryIntegrationTestCases.class,
    DocumentRepositoryIntegrationTestCases.class,
    ProjectRepositoryIntegrationTestCases.class,
    RepresentationRepositoryIntegrationTestCases.class
})
// @formatter:on
public final class AllSiriusWebPersistenceIntegrationTestSuite {

    /**
     * Starts a Docker container for PostgreSQL before any tests and shut it down after all tests have been executed.
     */
    @ClassRule
    public static TestPostgreSQLContainer postgreSQLContainer = new TestPostgreSQLContainer();

    private AllSiriusWebPersistenceIntegrationTestSuite() {
        // Prevent instantiation
    }
}
