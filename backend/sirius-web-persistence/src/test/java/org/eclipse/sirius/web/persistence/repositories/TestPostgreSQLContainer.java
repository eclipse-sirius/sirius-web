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
package org.eclipse.sirius.web.persistence.repositories;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

/**
 * PostgreSQL Docker Container used to tests our repositories.
 *
 * @author sbegaudeau
 */
public final class TestPostgreSQLContainer extends PostgreSQLContainer<TestPostgreSQLContainer> {
    private static final String VERSION = "postgres:latest"; //$NON-NLS-1$

    private static final String ENV_DB_URL = "SIRIUS_WEB_TEST_DB_URL"; //$NON-NLS-1$

    private static final String ENV_DB_USERNAME = "SIRIUS_WEB_TEST_DB_USERNAME"; //$NON-NLS-1$

    private static final String ENV_DB_PASSWORD = "SIRIUS_WEB_TEST_DB_PASSWORD"; //$NON-NLS-1$

    private final Logger logger = LoggerFactory.getLogger(TestPostgreSQLContainer.class);

    public TestPostgreSQLContainer() {
        super(VERSION);

        // @formatter:off
        this.waitStrategy = new LogMessageWaitStrategy()
                .withRegEx(".*database system is ready to accept connections.*\\s") //$NON-NLS-1$
                .withTimes(1)
                .withStartupTimeout(Duration.of(240, ChronoUnit.SECONDS));
        // @formatter:on
    }

    @Override
    public void start() {
        super.start();

        this.logger.info("Starting PostgreSQL Docker container"); //$NON-NLS-1$

        System.setProperty(ENV_DB_URL, this.getJdbcUrl());
        System.setProperty(ENV_DB_USERNAME, this.getUsername());
        System.setProperty(ENV_DB_PASSWORD, this.getPassword());
    }

    @Override
    public void stop() {
        this.logger.info("Stopping PostgreSQL Docker container"); //$NON-NLS-1$

        super.stop();
    }

}
