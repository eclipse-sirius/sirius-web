/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.libraries;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.Map;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.LibrariesQueryRunner;
import org.eclipse.sirius.web.tests.graphql.LibraryQueryRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the library controllers when the demo profile is active.
 *
 * @author gcoutable
 */
@Transactional
@ActiveProfiles("demo")
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.enabled=studio" })
public class LibraryControllerWithDemoProfileIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private LibrariesQueryRunner librariesQueryRunner;

    @Autowired
    private ILibrarySearchService librarySearchService;

    @Autowired
    private LibraryQueryRunner libraryQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a query to list libraries is performed, then it returns an empty list")
    public void givenDemoProfileWhenQueryToListLibrariesIsPerformedThenItReturnsAnEmptyList() {
        Map<String, Object> variables = Map.of("page", 0, "limit", 10);
        var result = this.librariesQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result.data(), "$.data.viewer.libraries.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result.data(), "$.data.viewer.libraries.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result.data(), "$.data.viewer.libraries.pageInfo.startCursor");
        assertThat(startCursor).isBlank();

        String endCursor = JsonPath.read(result.data(), "$.data.viewer.libraries.pageInfo.endCursor");
        assertThat(endCursor).isBlank();

        int count = JsonPath.read(result.data(), "$.data.viewer.libraries.pageInfo.count");
        assertThat(count).isZero();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a query to retrieve a library is performed, then it returns null")
    public void givenDemoProfileWhenQueryToRetrieveALibraryIsPerformedThenItReturnsNull() {
        var namespace = "papaya";
        var name = "java";
        var version = "1.0.0";

        var itExists = this.librarySearchService.existsByNamespaceAndNameAndVersion(namespace, name, version);
        assertThat(itExists).isTrue();

        Map<String, Object> variables = Map.of("namespace", namespace, "name", name, "version", version);
        var result = this.libraryQueryRunner.run(variables);

        var library = JsonPath.read(result.data(), "$.data.viewer.library");
        assertThat(library).isNull();
    }

}
