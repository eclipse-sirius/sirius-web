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
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariableType;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolExecutor;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.ModelOperationDiagramDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    private InvokeSingleClickOnDiagramElementToolExecutor invokeSingleClickOnDiagramElementToolExecutor;

    @Autowired
    private ModelOperationDiagramDescriptionProvider modelOperationDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToModelOperationDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.modelOperationDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "ModelOperationDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram, when a tool with complex model operations is executed, then it works as expected")
    public void givenDiagramWhenToolWithComplexModelOperationsIsExecutedThenItWorksAsExpected() {
        var flux = this.givenSubscriptionToModelOperationDiagram();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getNodes())
                    .noneMatch(node -> node.getInsideLabel().getText().equals("a"))
                    .noneMatch(node -> node.getInsideLabel().getText().equals("c"));
        });

        var consumedWorkbenchSelection = new AtomicReference<WorkbenchSelection>();
        var expectedNodeSelected = new AtomicReference<Node>();

        Runnable createNode = () -> {
            this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), diagramId.get(), this.modelOperationDiagramDescriptionProvider.getCreateNodeToolId(), 0, 0, List.of())
                    .isSuccess()
                    .hasSelection(workbenchSelection -> {
                        assertThat(workbenchSelection.getEntries()).hasSize(1);
                        consumedWorkbenchSelection.set(workbenchSelection);
                    });
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .anyMatch(node -> node.getInsideLabel().getText().equals("a"))
                    .noneMatch(node -> node.getInsideLabel().getText().equals("b"))
                    .anyMatch(node -> node.getInsideLabel().getText().equals("c"));
            var diagramNavigator = new DiagramNavigator(diagram);
            var selectedNodeA = diagramNavigator.nodeWithLabel("c");
            expectedNodeSelected.set(selectedNodeA.getNode());
        });

        Runnable assertSelection = () -> {
            var consumedSelection = new WorkbenchSelection(List.of(new WorkbenchSelectionEntry(expectedNodeSelected.get().getTargetObjectId(), "")));
            assertThat(consumedSelection).isEqualTo(consumedWorkbenchSelection.get());
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(createNode)
                .consumeNextWith(updatedDiagramContentMatcher)
                .then(assertSelection)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram, when a tool with a custom elements to select expression is executed, then the returned new selection matches the expression")
    public void givenDiagramWhenToolWithExplicitSelectionIsExecutedThenItWorksAsExpected() {
        var flux = this.givenSubscriptionToModelOperationDiagram();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getNodes())
                    .noneMatch(node -> node.getInsideLabel().getText().equals("a"))
                    .noneMatch(node -> node.getInsideLabel().getText().equals("c"));
        });

        Runnable createNode = () -> {
            this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), diagramId.get(), this.modelOperationDiagramDescriptionProvider.getCreateNodeToolWithComputedNewSelectionId(), 0, 0, List.of())
                    .isSuccess()
                    .hasSelection(workbenchSelection -> assertThat(workbenchSelection.getEntries()).hasSize(2));
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .anyMatch(node -> node.getInsideLabel().getText().equals("Component1"))
                    .anyMatch(node -> node.getInsideLabel().getText().equals("Component2"))
                    .anyMatch(node -> node.getInsideLabel().getText().equals("Component3"));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(createNode)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram, when a tool with a selection dialog is executed, then it works as expected")
    public void givenDiagramWhenToolWithSelectionDialogIsExecutedThenItWorksAsExpected() {
        var flux = this.givenSubscriptionToModelOperationDiagram();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getNodes())
                .anyMatch(node -> node.getInsideLabel().getText().equals("sirius-web-domain"))
                .noneMatch(node -> node.getInsideLabel().getText().equals("componentRenamedAfterSelectedElement"));
        });

        Runnable createNode = () -> {
            var toolVariable = new ToolVariable("selectedObject", PapayaIdentifiers.SIRIUS_WEB_DOMAIN_OBJECT.toString(), ToolVariableType.OBJECT_ID);
            this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), diagramId.get(), this.modelOperationDiagramDescriptionProvider.getRenameElementToolId(), 0, 0, List.of(toolVariable))
                    .isSuccess();
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                .noneMatch(node -> node.getInsideLabel().getText().equals("sirius-web-domain"))
                .anyMatch(node -> node.getInsideLabel().getText().equals("componentRenamedAfterSelectedElement"));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(createNode)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
