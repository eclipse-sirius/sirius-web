/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

/**
 * PostgreSQL Docker Container used to tests our repositories.
 *
 * @author sbegaudeau
 */
public final class TestPostgreSQLContainer extends PostgreSQLContainer<TestPostgreSQLContainer> {
    private static final String VERSION = "postgres:latest"; //$NON-NLS-1$

    public TestPostgreSQLContainer() {
        super(VERSION);

        // @formatter:off
        this.waitStrategy = new LogMessageWaitStrategy()
                .withRegEx(".*database system is ready to accept connections.*\\s") //$NON-NLS-1$
                .withTimes(1)
                .withStartupTimeout(Duration.of(240, ChronoUnit.SECONDS));
        // @formatter:on
    }

}
