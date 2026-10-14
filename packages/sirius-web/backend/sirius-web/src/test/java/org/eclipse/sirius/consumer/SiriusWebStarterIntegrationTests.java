/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.consumer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

/**
 * Verifies that the starter boots a downstream application with its project services.
 *
 * @author gcoutable
 */
@Testcontainers
@SpringBootTest(classes = SiriusWebConsumerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "spring.liquibase.change-log=classpath:db/db.changelog-master.xml",
    "spring.mvc.pathmatch.matching-strategy=ANT_PATH_MATCHER",
    "spring.data.elasticsearch.repositories.enabled=false"
})
public class SiriusWebStarterIntegrationTests {

    @Container
    public static final PostgreSQLContainer POSTGRESQL_CONTAINER = new PostgreSQLContainer("postgres:latest");

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @Test
    @DisplayName("Given a downstream application, when it starts, then nothing happen")
    public void givenDownstreamApplicationWhenItStartsThenProjectServicesAreAvailable() {
        // This integration tests that downstream projects will not fail to start because a package is missing in the component scan of sirius-web-starter
    }
}
