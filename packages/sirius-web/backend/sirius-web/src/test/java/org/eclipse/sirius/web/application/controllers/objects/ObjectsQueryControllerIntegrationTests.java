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

import java.util.List;
import java.util.Map;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ObjectsLabelsQueryRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the objects query.
 *
 * @author pcdavid
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ObjectsQueryControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private ObjectsLabelsQueryRunner objectsLabelsQueryRunner;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with semantic data and representations, when the labels of semantic elements or documents are requested, then the default labels of these objects are returned")
    public void givenProjectWhenMultipleObjectLabelsQueriedThenDefaultLabelsAreReturned() {
        List<String> inputObjectIds = List.of(StudioIdentifiers.VIEW_DOCUMENT.toString(), StudioIdentifiers.DIAGRAM_DESCRIPTION_OBJECT.toString());
        Map<String, Object> variables = Map.of(
                "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                "objectIds", inputObjectIds
        );
        var result = this.objectsLabelsQueryRunner.run(variables);
        List<String> objectIds = JsonPath.read(result.data(), "$.data.viewer.editingContext.objects.*.id");
        List<String> labels = JsonPath.read(result.data(), "$.data.viewer.editingContext.objects.*.label");

        assertThat(objectIds).hasSameElementsAs(inputObjectIds);
        assertThat(labels).hasSameElementsAs(List.of("Form View", "Root Diagram"));
    }

}
