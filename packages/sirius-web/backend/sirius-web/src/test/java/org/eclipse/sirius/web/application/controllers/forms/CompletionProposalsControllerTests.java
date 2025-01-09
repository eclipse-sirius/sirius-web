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
package org.eclipse.sirius.web.application.controllers.forms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.tests.graphql.CompletionProposalsQueryRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.DetailsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.test.StepVerifier;

/**
 * Integration tests of the completion proposals.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompletionProposalsControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

    @Autowired
    private CompletionProposalsQueryRunner completionProposalsQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a textfield for a domain type, when we ask for its completion proposals, then the proposals are returned")
    public void givenTextfieldForDomainTypeWhenWeAskForItsCompletionProposalsThenTheProposalsAreReturned() {
        var detailRepresentationId =  "details://?objectIds=[" + String.join(",", StudioIdentifiers.DIAGRAM_DESCRIPTION_OBJECT.toString()) + "]";
        var input = new DetailsEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), detailRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(input);

        var formId = new AtomicReference<String>();
        var textareaId = new AtomicReference<String>();

        Consumer<Object> formContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    formId.set(form.getId());

                    var groupNavigator = new FormNavigator(form).page("Root Diagram").group("Core Properties");
                    var textarea = groupNavigator.findWidget("Domain Type", Textarea.class);
                    textareaId.set(textarea.getId());
                }, () -> fail("Missing form"));

        Runnable getCompletionProposals = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                    "formId", formId.get(),
                    "widgetId", textareaId.get(),
                    "currentText", "",
                    "cursorPosition", 0
            );
            var result = this.completionProposalsQueryRunner.run(variables);
            List<String> resultTextToInsert = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.completionProposals[*].textToInsert");
            assertThat(resultTextToInsert)
                    .isNotEmpty()
                    .containsAll(List.of(
                            "buck::Human",
                            "buck::NamedElement",
                            "buck::Root"
                    ));
        };

        StepVerifier.create(flux)
                .consumeNextWith(formContentMatcher)
                .then(getCompletionProposals)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a textfield for an expression, when we ask for its completion proposals, then the proposals are returned")
    public void givenTextfieldForAnExpressionWhenWeAskForItsCompletionProposalsThenTheProposalsAreReturned() {
        var detailRepresentationId =  "details://?objectIds=[" + String.join(",", StudioIdentifiers.HUMAN_NODE_DESCRIPTION_OBJECT.toString()) + "]";
        var input = new DetailsEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), detailRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(input);

        var formId = new AtomicReference<String>();
        var textareaId = new AtomicReference<String>();

        Consumer<Object> formContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    formId.set(form.getId());

                    var groupNavigator = new FormNavigator(form).page("Human Node").group("Core Properties");
                    var textarea = groupNavigator.findWidget("Semantic Candidates Expression", Textarea.class);
                    textareaId.set(textarea.getId());
                }, () -> fail("Missing form"));

        Runnable getCompletionProposals = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                    "formId", formId.get(),
                    "widgetId", textareaId.get(),
                    "currentText", "aql:self.humans",
                    "cursorPosition", "aql:self.".length()
            );
            var result = this.completionProposalsQueryRunner.run(variables);
            List<String> resultTextToInsert = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.completionProposals[*].textToInsert");
            assertThat(resultTextToInsert)
                    .isNotEmpty()
                    .containsAll(List.of(
                            "ancestors()",
                            "eAllContents()",
                            "eContents()",
                            "siblings()"
                    ));
        };

        StepVerifier.create(flux)
                .consumeNextWith(formContentMatcher)
                .then(getCompletionProposals)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
