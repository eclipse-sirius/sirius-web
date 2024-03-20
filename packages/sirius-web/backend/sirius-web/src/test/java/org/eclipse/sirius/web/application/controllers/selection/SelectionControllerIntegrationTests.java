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
package org.eclipse.sirius.web.application.controllers.selection;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionEventInput;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionRefreshedEventPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.TestIdentifiers;
import org.eclipse.sirius.web.services.api.IGraphQLRequestor;
import org.eclipse.sirius.web.services.selection.SelectionDescriptionProvider;
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
 * Integration tests of the selection controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SelectionControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String GET_SELECTION_EVENT_SUBSCRIPTION = """
            subscription selectionEvent($input: SelectionEventInput!) {
              selectionEvent(input: $input) {
                __typename
              }
            }
            """;

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @BeforeEach
    public void beforeEach() {
        this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                .map(IEditingContextEventProcessor::getEditingContextId)
                .forEach(this.editingContextEventProcessorRegistry::disposeEditingContextEventProcessor);
    }

    @Test
    @DisplayName("Given a semantic object, when we subscribe to its selection events, then the selection is sent")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSemanticObjectWhenWeSubscribeToItsSelectionEventsThenTheSelectionIsSent() {
        var input = new SelectionEventInput(UUID.randomUUID(), TestIdentifiers.PAPAYA_PROJECT.toString(), SelectionDescriptionProvider.REPRESENTATION_DESCRIPTION_ID, TestIdentifiers.PAPAYA_ROOT_OBJECT.toString());
        var flux = this.graphQLRequestor.subscribe(GET_SELECTION_EVENT_SUBSCRIPTION, input);

        Predicate<Object> selectionContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(SelectionRefreshedEventPayload.class::isInstance)
                .map(SelectionRefreshedEventPayload.class::cast)
                .map(SelectionRefreshedEventPayload::selection)
                .filter(selection -> {
                    assertThat(selection.getObjects()).hasSizeGreaterThanOrEqualTo(5);
                    return true;
                })
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(selectionContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
