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
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.OmniboxCommandsQueryRunner;
import org.eclipse.sirius.components.graphql.tests.OmniboxSearchQueryRunner;
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
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OmniboxControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private OmniboxCommandsQueryRunner omniboxCommandsQueryRunner;

    @Autowired
    private OmniboxSearchQueryRunner omniboxSearchQueryRunner;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given context entries and a query, when a query is performed, then omnibox commands are returned")
    public void givenContextEntriesWhenAQueryIsPerformedThenCommandsAreReturned() {
        var omniboxContextEntries = List.of(Map.of("id", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, "kind", "EditingContext"));

        Map<String, Object> firstQueryVariables = Map.of("contextEntries", omniboxContextEntries, "query", "");
        var firstQueryResult = this.omniboxCommandsQueryRunner.run(firstQueryVariables);
        List<String> allCommandLabels = JsonPath.read(firstQueryResult, "$.data.viewer.omniboxCommands.edges[*].node.label");
        assertThat(allCommandLabels).hasSize(1).anyMatch(label -> Objects.equals(label, "Search"));

        Map<String, Object> secondQueryVariables = Map.of("contextEntries", omniboxContextEntries, "query", "Sea");
        var secondQueryResult = this.omniboxCommandsQueryRunner.run(secondQueryVariables);
        List<String> seaFilteredCommandsLabels = JsonPath.read(secondQueryResult, "$.data.viewer.omniboxCommands.edges[*].node.label");
        assertThat(seaFilteredCommandsLabels).hasSize(1).anyMatch(label -> Objects.equals(label, "Search"));

        Map<String, Object> thirdQueryVariables = Map.of("contextEntries", omniboxContextEntries, "query", "yello");
        var thirdQueryResult = this.omniboxCommandsQueryRunner.run(thirdQueryVariables);
        List<String> yelloFilteredCommandsLabels = JsonPath.read(thirdQueryResult, "$.data.viewer.omniboxCommands.edges[*].node.label");
        assertThat(yelloFilteredCommandsLabels).isEmpty();
    }



    @Test
    @GivenSiriusWebServer
    @DisplayName("Given context entries and a query, when the text-based objects are queried, then the objects are returned")
    public void givenContextEntriesAndQueryWhenTextBasedObjectsAreQueriedThenObjectsAreReturned() {
        var omniboxContextEntries = List.of(Map.of("id", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, "kind", "EditingContext"));

        Map<String, Object> emptyQueryVariables = Map.of("contextEntries", omniboxContextEntries, "query", "");
        var emptyQueryResult = this.omniboxSearchQueryRunner.run(emptyQueryVariables);
        List<String> emptyQueryObjectLabels = JsonPath.read(emptyQueryResult, "$.data.viewer.omniboxSearch[*].label");
        assertThat(emptyQueryObjectLabels).isNotEmpty();

        String filterQuery = "yello";
        Map<String, Object> filterQueryVariables = Map.of("contextEntries", omniboxContextEntries, "query", filterQuery);
        var filterQueryResult = this.omniboxSearchQueryRunner.run(filterQueryVariables);
        List<String> filterQueryObjectLabels = JsonPath.read(filterQueryResult, "$.data.viewer.omniboxSearch[*].label");
        assertThat(filterQueryObjectLabels).isNotEmpty().allMatch(label -> label.toLowerCase().contains(filterQuery.toLowerCase()));
    }

}
