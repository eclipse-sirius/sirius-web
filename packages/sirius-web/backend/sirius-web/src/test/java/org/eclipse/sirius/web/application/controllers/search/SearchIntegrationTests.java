/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.application.controllers.search;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.search.dto.SearchResult;
import org.eclipse.sirius.web.application.views.search.dto.SearchSuccessPayload;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.SearchQueryRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the Search view.
 *
 * @author pcdavid
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private SearchQueryRunner searchQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute a basic search, then all the matching semantic elements are returned")
    public void givenProjectWhenWeExecuteBasicSearchThenAllMatchingElementsAreReturned() {
        List<String> matches = this.search(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "sirius-web", false, false, false, false, false);
        assertThat(matches).containsExactlyInAnyOrder(
                "sirius-web-domain",
                "sirius-web-application",
                "sirius-web-infrastructure",
                "sirius-web-starter",
                "sirius-web"
        );
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute a whole word search, then only the matching semantic elements are returned")
    public void givenProjectWhenWeExecuteWholeWordSearchThenOnlyMatchingElementsAreReturned() {
        // "Command" appear multiple times as part of "Command1" and "Command2"
        List<String> matches = this.search(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "Command", false, /* mathcWholeWord */ false, false, false, false);
        assertThat(matches).containsExactly("Command1 over HTTP", "Command2 over null", "Command1", "Command2");
        // but never as a distinct whole word.
        matches = this.search(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "Command", false, /* mathcWholeWord */ true, false, false, false);
        assertThat(matches).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute a case insensitive search, then all the matching semantic elements are returned")
    public void givenProjectWhenWeExecuteCaseInsensitiveSearchThenAllMatchingElementsAreReturned() {
        List<String> matches = this.search(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "project creation", /* matchCase */ false, false, false, false, false);
        assertThat(matches).containsExactlyInAnyOrder("Project Creation");

        matches = this.search(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "project creation", /* matchCase */ true, false, false, false, false);
        assertThat(matches).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute a search including attributes, then all the matching semantic elements are returned")
    public void givenProjectWhenWeExecuteSearchIncludingAttributesThenAllMatchingElementsAreReturned() {
        // There are no matches in object names/labels
        List<String> matches = this.search(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "2024-02", false, false, false, /* searchInAttributes */ false, false);
        assertThat(matches).isEmpty();
        // but there are in several object attributes
        matches = this.search(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "2024-02", false, false, false, /* searchInAttributes */ true, false);
        // Note: the matches are in these object's attributes, but we return only the objects themselves
        assertThat(matches).containsExactlyInAnyOrder("2024.3.0", "2024.5.0");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute a search with regular expression, then all the matching semantic elements are returned")
    public void givenProjectWhenWeExecuteSearchWithRegexpThenAllMatchingElementsAreReturned() {
        List<String> matches = this.search(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "^sirius-web-([dt]|ap{2}).*[an]$", false, false, /* useRegularExpressions */ false, false, false);
        assertThat(matches).isEmpty();
        matches = this.search(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "^sirius-web-([dt]|ap{2}).*[an]$", false, false, /* useRegularExpressions */ true, false, false);
        assertThat(matches).containsExactlyInAnyOrder(
                "sirius-web-domain",
                "sirius-web-application"
        );
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute a search including libraries, then all the matching semantic elements are returned")
    public void givenProjectWhenWeExecuteSearchIncludingLibrariesThenAllMatchingElementsAreReturned() {
        List<String> matches = this.search(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "sirius-web", false, false, false, false, /* searchInLibraries */ false);
        assertThat(matches).containsExactlyInAnyOrder(
                "sirius-web-domain",
                "sirius-web-application",
                "sirius-web-infrastructure",
                "sirius-web-starter",
                "sirius-web"
        );
        matches = this.search(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "sirius-web", false, false, false, false, /* searchInLibraries */ true);
        assertThat(matches).containsExactlyInAnyOrder(
                "sirius-web-tests-data",
                "sirius-web-domain",
                "sirius-web-application",
                "sirius-web-infrastructure",
                "sirius-web-starter",
                "sirius-web"
        );
    }

    private List<String> search(UUID editingContextId, String text, boolean matchCase, boolean matchWholeWord, boolean useRegularExpressions, boolean searchInAttributes, boolean searchInLibraries) {
        // The SearchQuery object must be passed as a plain Map here
        var queryMap = Map.of(
                "text", text,
                "matchCase", matchCase,
                "matchWholeWord", matchWholeWord,
                "useRegularExpression", useRegularExpressions,
                "searchInAttributes", searchInAttributes,
                "searchInLibraries", searchInLibraries
        );
        Map<String, Object> variables = Map.of(
                "editingContextId", editingContextId.toString(),
                "query", queryMap
        );
        var result = this.searchQueryRunner.run(variables);

        String payloadTypename = JsonPath.read(result.data(), "$.data.viewer.editingContext.search.__typename");
        assertThat(payloadTypename).isEqualTo(SearchSuccessPayload.class.getSimpleName());

        String resultTypename = JsonPath.read(result.data(), "$.data.viewer.editingContext.search.result.__typename");
        assertThat(resultTypename).isEqualTo(SearchResult.class.getSimpleName());

        return JsonPath.read(result.data(), "$.data.viewer.editingContext.search.result.matches[*].label");
    }
}
