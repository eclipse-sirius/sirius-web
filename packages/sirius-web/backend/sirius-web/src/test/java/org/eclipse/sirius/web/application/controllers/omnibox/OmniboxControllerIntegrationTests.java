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
package org.eclipse.sirius.web.application.controllers.omnibox;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.graphql.tests.OmniboxCommandsQueryRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the omnibox commands query.
 *
 * @author gcoutable
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OmniboxControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private OmniboxCommandsQueryRunner omniboxCommandsQueryRunner;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given context entries and a query, when a query is performed, then omnibox commands are returned")
    public void givenContextEntriesWhenAQueryIsPerformedThenCommandsAreReturned() {
        var omniboxContextEntries = List.of(Map.of("id", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, "kind", "EditingContext"));

        Map<String, Object> firstQueryVariables = Map.of("contextEntries", omniboxContextEntries, "query", "");
        var firstQueryResult = this.omniboxCommandsQueryRunner.run(firstQueryVariables);
        List<String> allCommandLabels = JsonPath.read(firstQueryResult, "$.data.viewer.omniboxCommands.edges[*].node.label");
        int allCommandLabelsNumber = allCommandLabels.size();

        String query = "yello";
        Map<String, Object> secondQueryVariables = Map.of("contextEntries", omniboxContextEntries, "query", query);
        var secondQueryResult = this.omniboxCommandsQueryRunner.run(secondQueryVariables);
        List<String> filteredCommandsLabels = JsonPath.read(secondQueryResult, "$.data.viewer.omniboxCommands.edges[*].node.label");
        assertThat(filteredCommandsLabels.size()).isNotEqualTo(allCommandLabelsNumber);
        assertThat(filteredCommandsLabels).allMatch(commandLabel -> commandLabel.contains(query));
    }

}
