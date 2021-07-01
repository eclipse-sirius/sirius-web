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
package org.eclipse.sirius.web.persistence.repositories;

/**
 * Superclass of the integration test classes used to adopt the singleton container pattern from testcontainers.
 *
 * @author sbegaudeau
 */
public abstract class AbstractIntegrationTests {
    static final TestPostgreSQLContainer POSTGRESQL_CONTAINER;

    static {
        POSTGRESQL_CONTAINER = new TestPostgreSQLContainer();
        POSTGRESQL_CONTAINER.start();
    }
}
