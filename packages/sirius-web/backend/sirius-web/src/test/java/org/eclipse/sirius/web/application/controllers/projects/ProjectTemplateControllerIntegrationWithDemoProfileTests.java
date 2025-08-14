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

import java.util.List;
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
 * Integration tests of the project templates controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.enabled=*" })
@ActiveProfiles(value = "demo")
public class ProjectTemplateControllerIntegrationWithDemoProfileTests extends AbstractIntegrationTests {

    @Autowired
    private ProjectTemplatesQueryRunner projectTemplatesQueryRunner;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of project templates with the demo profile, when a query asking default templates is performed, then the result does not contain the default templates")
    public void givenSetOfProjectTemplatesWithDemoProfileWhenQueryAskingDefaultTemplatesIsPerformedThenTheResultDoesNotContainTheDefaultTemplates() {
        Map<String, Object> variables = Map.of("page", 0, "limit", 6, "context", ProjectTemplateContext.PROJECT_BROWSER);
        var result = this.projectTemplatesQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.hasNextPage");
        assertThat(hasNextPage).isTrue();

        String startCursor = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.count");
        assertThat(count).isGreaterThan(0);

        List<String> projectTemplateIds = JsonPath.read(result, "$.data.viewer.projectTemplates.edges[-3:].node.id");
        assertThat(projectTemplateIds).doesNotContain("create-project", "upload-project", "browse-all-project-templates");
    }
}
