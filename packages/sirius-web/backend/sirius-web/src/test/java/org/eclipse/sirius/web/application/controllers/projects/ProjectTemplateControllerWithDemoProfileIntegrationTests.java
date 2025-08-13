/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.projects;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.Map;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.ProjectTemplateContext;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ProjectTemplatesQueryRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the project templates controllers with demo profile activated.
 *
 * @author gcoutbale
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.enabled=*" })
@ActiveProfiles("demo")
public class ProjectTemplateControllerWithDemoProfileIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private ProjectTemplatesQueryRunner projectTemplatesQueryRunner;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a query to request project templates is performed, then it returns an empty list")
    public void givenDemoProfileWhenQueryToRequestProjectTemplatesIsPerformedThenItReturnsAnEmptyList() {
        Map<String, Object> variables = Map.of("page", 0, "limit", 10, "context", ProjectTemplateContext.PROJECT_TEMPLATE_MODAL);
        var result = this.projectTemplatesQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.startCursor");
        assertThat(startCursor).isBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.endCursor");
        assertThat(endCursor).isBlank();

        int count = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.count");
        assertThat(count).isZero();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a query to request default templates is performed, then it returns an empty list")
    public void givenDemoProfileWhenQueryToRequestDefaultTemplatesIsPerformedThenItReturnsAnEmptyList() {
        Map<String, Object> variables = Map.of("page", 0, "limit", 6, "context", ProjectTemplateContext.PROJECT_BROWSER);
        var result = this.projectTemplatesQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.startCursor");
        assertThat(startCursor).isBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.endCursor");
        assertThat(endCursor).isBlank();

        int count = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.count");
        assertThat(count).isZero();
    }
}
