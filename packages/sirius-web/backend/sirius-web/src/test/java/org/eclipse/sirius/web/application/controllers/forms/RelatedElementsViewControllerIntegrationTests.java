/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.PropertiesEventInput;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.GroupDisplayMode;
import org.eclipse.sirius.components.forms.TreeWidget;
import org.eclipse.sirius.components.forms.tests.graphql.RelatedElementsEventSubscriptionRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.test.StepVerifier;

/**
 * Integration tests of the related elements view.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RelatedElementsViewControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private RelatedElementsEventSubscriptionRunner relatedElementsEventSubscriptionRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given an entity, when we subscribe to its related elements events, then the form is sent")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnEntityWhenWeSubscribeToItsRelatedElementsEventsThenTheFormIsSent() {
        var input = new PropertiesEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), List.of(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString()));
        var flux = this.relatedElementsEventSubscriptionRunner.run(input);

        Predicate<Form> formPredicate = form -> {
            var groupNavigator = new FormNavigator(form).page("Human").group("Related Elements");
            assertThat(groupNavigator.getGroup()).hasDisplayMode(GroupDisplayMode.TOGGLEABLE_AREAS);

            var incomingTreeWidget = groupNavigator.findWidget("Incoming", TreeWidget.class);
            assertThat(incomingTreeWidget)
                    .hasTreeItemWithLabel("humans")
                    .hasTreeItemWithLabel("buck");

            var currentTreeWidget = groupNavigator.findWidget("Current", TreeWidget.class);
            assertThat(currentTreeWidget)
                    .hasTreeItemWithLabel("description")
                    .hasTreeItemWithLabel("promoted");

            var outgoingTreeWidget = groupNavigator.findWidget("Outgoing", TreeWidget.class);
            assertThat(outgoingTreeWidget)
                    .hasTreeItemWithLabel("NamedElement");

            return true;
        };

        Predicate<Object> formContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .filter(formPredicate)
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(formContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a node description, when we subscribe to its related elements events, then the form is sent")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenNodeDescriptionWhenWeSubscribeToItsRelatedElementsEventsThenTheFormIsSent() {
        var input = new PropertiesEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), List.of(StudioIdentifiers.HUMAN_NODE_DESCRIPTION_OBJECT.toString()));
        var flux = this.relatedElementsEventSubscriptionRunner.run(input);

        Predicate<Form> formPredicate = form -> {
            var groupNavigator = new FormNavigator(form).page("Human Node").group("Related Elements");
            assertThat(groupNavigator.getGroup()).hasDisplayMode(GroupDisplayMode.TOGGLEABLE_AREAS);

            var incomingTreeWidget = groupNavigator.findWidget("Incoming", TreeWidget.class);
            assertThat(incomingTreeWidget)
                    .hasTreeItemWithLabel("Root Diagram")
                    .hasTreeItemWithLabel("Node Descriptions");

            var currentTreeWidget = groupNavigator.findWidget("Current", TreeWidget.class);
            assertThat(currentTreeWidget)
                    .hasTreeItemWithLabel("Inside Label")
                    .hasTreeItemWithLabel("aql:self.name")
                    .hasTreeItemWithLabel("Style");

            var outgoingTreeWidget = groupNavigator.findWidget("Outgoing", TreeWidget.class);
            assertThat(outgoingTreeWidget.getNodes()).isEmpty();

            return true;
        };

        Predicate<Object> formContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .filter(formPredicate)
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(formContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
