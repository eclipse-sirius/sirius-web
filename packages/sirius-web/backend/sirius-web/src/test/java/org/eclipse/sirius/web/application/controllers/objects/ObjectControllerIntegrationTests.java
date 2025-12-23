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
package org.eclipse.sirius.web.application.controllers.objects;



import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.Map;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ObjectLibraryQueryRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the object controllers.
 *
 * @author gdaniel
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ObjectControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private ObjectLibraryQueryRunner objectLibraryQueryRunner;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with library dependencies, when a library object is queried, then the object is returned with the library information")
    public void givenProjectWithLibraryDependenciesWhenLibraryObjectIsQueriedThenObjectIsReturnedWithLibraryInformation() {
        Map<String, Object> variables = Map.of(
                "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                "objectId", PapayaIdentifiers.PAPAYA_SIRIUS_WEB_TESTS_DATA_DOCUMENT.toString()
        );
        var result = this.objectLibraryQueryRunner.run(variables);
        String namespace = JsonPath.read(result.data(), "$.data.viewer.editingContext.object.library.namespace");
        assertThat(namespace).isEqualTo("papaya");

        String name = JsonPath.read(result.data(), "$.data.viewer.editingContext.object.library.name");
        assertThat(name).isEqualTo("sirius-web-tests-data");

        String version = JsonPath.read(result.data(), "$.data.viewer.editingContext.object.library.version");
        assertThat(version).isEqualTo("1.0.0");

    }

}
