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
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramEventInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.TestIdentifiers;
import org.eclipse.sirius.web.services.api.IGraphQLRequestor;
import org.eclipse.sirius.web.services.diagrams.UnsynchronizedDiagramDescriptionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.test.StepVerifier;

/**
 * Integration tests of the unsynchronized diagrams.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class UnsynchronizedDiagramControllerTests extends AbstractIntegrationTests {

    private static final String GET_DIAGRAM_EVENT_SUBSCRIPTION = """
            subscription diagramEvent($input: DiagramEventInput!) {
              diagramEvent(input: $input) {
                __typename
              }
            }
            """;

    private static final String CREATE_REPRESENTATION_MUTATION = """
            mutation createRepresentation($input: CreateRepresentationInput!) {
              createRepresentation(input: $input) {
                __typename
                ... on CreateRepresentationSuccessPayload {
                  representation {
                    id
                  }
                }
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
    @DisplayName("Given an unsynchronous diagram, when it is opened, then unsynchronized nodes should not appear")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenUnsynchronousDiagramWhenItIsOpenedThenUnsynchronizedNodesShouldNotAppear() {
        this.commitInitializeStateBeforeThreadSwitching();

        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.PAPAYA_PROJECT.toString(),
                UnsynchronizedDiagramDescriptionProvider.REPRESENTATION_DESCRIPTION_ID,
                TestIdentifiers.PAPAYA_ROOT_OBJECT.toString(),
                "UnsynchronizedDiagram"
        );
        var result = this.graphQLRequestor.execute(CREATE_REPRESENTATION_MUTATION, input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        String typename = JsonPath.read(result, "$.data.createRepresentation.__typename");
        assertThat(typename).isEqualTo(CreateRepresentationSuccessPayload.class.getSimpleName());

        String representationId = JsonPath.read(result, "$.data.createRepresentation.representation.id");
        assertThat(representationId).isNotNull();

        var diagramEventInput = new DiagramEventInput(UUID.randomUUID(), TestIdentifiers.PAPAYA_PROJECT.toString(), representationId);
        var flux = this.graphQLRequestor.subscribe(GET_DIAGRAM_EVENT_SUBSCRIPTION, diagramEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        Predicate<Object> initialDiagramContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .filter(diagram -> {
                    assertThat(diagram.getNodes()).isEmpty();
                    return true;
                })
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(initialDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    /**
     * Used to commit the state of the transaction after its initialization by the @Sql annotation
     * in order to make the state persisted in the database. Without this, the initialized state
     * will not be visible by the various repositories when the test will switch threads to use
     * the thread of the editing context.
     *
     * This should not be used every single time but only in the couple integrations tests that are
     * required to interact with repositories while inside an editing context event handler or a
     * representation event handler for example.
     */
    private void commitInitializeStateBeforeThreadSwitching() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }
}
