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

import java.util.Map;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ViewerCapabilitiesQueryRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of viewer capabilities.
 *
 * @author gcoutable
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewerCapabilitiesControllerTests extends AbstractIntegrationTests {

    @Autowired
    private ViewerCapabilitiesQueryRunner viewerCapabilitiesQueryRunner;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a server, when a query to retrieve viewer capabilities is executed, then it return the right value")
    public void givenAServerWhenQueryToRetrieveViewerCapabilitiesIsExecutedThenItReturnsTrueByDefault() {
        var result = this.viewerCapabilitiesQueryRunner.run(Map.of());
        boolean canCreateProjects = JsonPath.read(result, "$.data.viewer.capabilities.projects.canCreate");
        boolean canUploadProjects = JsonPath.read(result, "$.data.viewer.capabilities.projects.canUpload");
        boolean canListLibraries = JsonPath.read(result, "$.data.viewer.capabilities.libraries.canList");
        assertThat(canCreateProjects).isTrue();
        assertThat(canUploadProjects).isFalse();
        assertThat(canListLibraries).isTrue();
    }

}
