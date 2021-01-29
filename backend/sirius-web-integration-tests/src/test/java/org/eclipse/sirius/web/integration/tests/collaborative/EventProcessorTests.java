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
package org.eclipse.sirius.web.integration.tests.collaborative;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.web.integration.tests.PostgreSQLTestContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Class used to test the diagram event processor.
 *
 * @author sbegaudeau
 */
@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EventProcessorTests.class)
public class EventProcessorTests {
    @Container
    private static PostgreSQLTestContainer postgreSQLContainer = new PostgreSQLTestContainer();

    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl); //$NON-NLS-1$
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword); //$NON-NLS-1$
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername); //$NON-NLS-1$
    }

    @Test
    @Transactional
    public void testRetrieveDiagramEventProcessor() {
        assertThat(true).isTrue();
    }
}
