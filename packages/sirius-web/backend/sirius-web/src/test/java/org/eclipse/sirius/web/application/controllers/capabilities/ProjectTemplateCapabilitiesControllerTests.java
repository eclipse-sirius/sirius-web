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
package org.eclipse.sirius.web.application.controllers.capabilities;

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
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integrations tests for project template capabilities with no project template providers.
 *
 * @author gcoutable
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ProjectTemplateCapabilitiesControllerConfiguration.class)
public class ProjectTemplateCapabilitiesControllerTests extends AbstractIntegrationTests {

    @Autowired
    private ProjectTemplatesQueryRunner projectTemplatesQueryRunner;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a server without project template providers, when the query to retrieve project templates is performed, then project templates cannot be browsed event it is possible to create a project")
    public void givenAServerWithoutProjectTemplatesProviderWhenTheQueryToRetrieveProjectTemplatesIsPerformedThenProjectTemplatesCannotBeBrowsedEventIfIsPossibleToCreateAProject() {
        Map<String, Object> variables = Map.of("page", 0, "limit", 6, "context", ProjectTemplateContext.PROJECT_BROWSER);
        var result = this.projectTemplatesQueryRunner.run(variables);

        List<String> projectTemplateIds = JsonPath.read(result, "$.data.viewer.projectTemplates.edges[*].node.id");
        assertThat(projectTemplateIds).doesNotContain("browse-all-project-templates");
    }

}
