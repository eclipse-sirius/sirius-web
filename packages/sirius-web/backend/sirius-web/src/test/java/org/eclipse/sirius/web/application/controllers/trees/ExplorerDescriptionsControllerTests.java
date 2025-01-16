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
package org.eclipse.sirius.web.application.controllers.trees;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ExplorerDescriptionsQueryRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the explorer descriptions controllers.
 *
 * @author Jerome Gout
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExplorerDescriptionsControllerTests extends AbstractIntegrationTests {

    @Autowired
    private ExplorerDescriptionsQueryRunner explorerDescriptionsQueryRunner;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an empty studio, when the explorer descriptions are requested, then only the default explorer description id is received")
    public void givenAnEmpyStudioWhenTheExplorerDescriptionsAreRequestedThenOnlyTheDefaultExplorerDescriptionIdIsReceived() {
        Map<String, Object> variables = Map.of(
                "editingContextId", StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString()
        );
        var result = this.explorerDescriptionsQueryRunner.run(variables);

        List<String> explorerIds = JsonPath.read(result, "$.data.viewer.editingContext.explorerDescriptions[*].id");
        assertThat(explorerIds).isNotEmpty().hasSize(1);
        assertThat(explorerIds.get(0)).isEqualTo(ExplorerDescriptionProvider.DESCRIPTION_ID);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the explorer descriptions are requested, then a view tree description is received in addition of the default explorer one")
    public void givenAStudioWhenTheExplorerDescriptionsAreRequestedThenAViewTreeDescriptionIsReceivedInAdditionOfTheDefaultExplorerOne() {
        Map<String, Object> variables = Map.of(
                "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString()
        );
        var result = this.explorerDescriptionsQueryRunner.run(variables);

        List<String> explorerIds = JsonPath.read(result, "$.data.viewer.editingContext.explorerDescriptions[*].id");
        assertThat(explorerIds).isNotEmpty().hasSize(2);
        assertThat(explorerIds.get(0)).isEqualTo(ExplorerDescriptionProvider.DESCRIPTION_ID);
        assertThat(explorerIds.get(1)).startsWith("siriusComponents://representationDescription?kind=treeDescription");
    }
}
