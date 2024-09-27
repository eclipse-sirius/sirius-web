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
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariableType;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.ModelOperationDiagramDescriptionProvider;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the model operations.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ModelOperationDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private InvokeSingleClickOnDiagramElementToolMutationRunner invokeSingleClickOnDiagramElementToolMutationRunner;

    @Autowired
    private ModelOperationDiagramDescriptionProvider modelOperationDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToModelOperationDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                this.modelOperationDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "ModelOperationDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a diagram, when a tool with complex model operations is executed, then it works as expected")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWhenToolWithComplexModelOperationsIsExecutedThenItWorksAsExpected() {
        var flux = this.givenSubscriptionToModelOperationDiagram();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    assertThat(diagram.getNodes())
                            .noneMatch(node -> node.getInsideLabel().getText().equals("a"))
                            .noneMatch(node -> node.getInsideLabel().getText().equals("c"));
                }, () -> fail("Missing diagram"));

        Runnable createNode = () -> {
            var createNodeToolId = this.modelOperationDiagramDescriptionProvider.getCreateNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), diagramId.get(), createNodeToolId, 0, 0, List.of());
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());

            List<String> newSelection = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.newSelection.entries[*].kind");
            assertThat(newSelection).hasSize(1);
            assertThat(newSelection.get(0)).isEqualTo("siriusComponents://semantic?domain=papaya&entity=Component");
        };

        Consumer<Object> updatedDiagramContentMatcher = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes())
                            .anyMatch(node -> node.getInsideLabel().getText().equals("a"))
                            .noneMatch(node -> node.getInsideLabel().getText().equals("b"))
                            .anyMatch(node -> node.getInsideLabel().getText().equals("c"));
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(createNode)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram, when a tool with a custom elements to select expression is executed, then the returned new selection matches the expression")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWhenToolWithExplicitSelectionIsExecutedThenItWorksAsExpected() {
        var flux = this.givenSubscriptionToModelOperationDiagram();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    assertThat(diagram.getNodes())
                            .noneMatch(node -> node.getInsideLabel().getText().equals("a"))
                            .noneMatch(node -> node.getInsideLabel().getText().equals("c"));
                }, () -> fail("Missing diagram"));

        Runnable createNode = () -> {
            var createNodeToolId = this.modelOperationDiagramDescriptionProvider.getCreateNodeToolWithComputedNewSelectionId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), diagramId.get(), createNodeToolId, 0, 0, List.of());
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());

            List<String> newSelection = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.newSelection.entries[*].kind");
            assertThat(newSelection).hasSize(2);
            assertThat(newSelection.get(0)).isEqualTo("siriusComponents://semantic?domain=papaya&entity=Component");
            assertThat(newSelection.get(1)).isEqualTo("siriusComponents://semantic?domain=papaya&entity=Component");
        };

        Consumer<Object> updatedDiagramContentMatcher = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes())
                            .anyMatch(node -> node.getInsideLabel().getText().equals("Component1"))
                            .anyMatch(node -> node.getInsideLabel().getText().equals("Component2"))
                            .anyMatch(node -> node.getInsideLabel().getText().equals("Component3"));
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(createNode)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram, when a tool with a selection dialog is executed, then it works as expected")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWhenToolWithSelectionDialogIsExecutedThenItWorksAsExpected() {
        var flux = this.givenSubscriptionToModelOperationDiagram();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    assertThat(diagram.getNodes())
                        .anyMatch(node -> node.getInsideLabel().getText().equals("sirius-web-domain"))
                        .noneMatch(node -> node.getInsideLabel().getText().equals("componentRenamedAfterSelectedElement"));
                }, () -> fail("Missing diagram"));

        Runnable createNode = () -> {
            var renameElementNodeToolId = this.modelOperationDiagramDescriptionProvider.getRenameElementToolId();
            var toolVariable = new ToolVariable("selectedObject", PapayaIdentifiers.SIRIUS_WEB_DOMAIN_OBJECT.toString(), ToolVariableType.OBJECT_ID);
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), diagramId.get(), renameElementNodeToolId, 0, 0, List.of(toolVariable));
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentMatcher = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes())
                        .noneMatch(node -> node.getInsideLabel().getText().equals("sirius-web-domain"))
                        .anyMatch(node -> node.getInsideLabel().getText().equals("componentRenamedAfterSelectedElement"));
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(createNode)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
