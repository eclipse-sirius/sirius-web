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
package org.eclipse.sirius.web.application.controllers.impactanalysis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.datatree.DataTreeNode;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.components.view.emf.diagram.tools.DeleteOneDiagramElementToolHandler;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.ModelOperationDiagramDescriptionProvider;
import org.eclipse.sirius.web.services.diagrams.UndoRedoDiagramDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.DiagramImpactAnalysisReportExecutor;
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
 * Integration tests of the impact analysis for diagram tools.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ImpactAnalysisDiagramToolControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private ModelOperationDiagramDescriptionProvider modelOperationDiagramDescriptionProvider;

    @Autowired
    private DiagramImpactAnalysisReportExecutor diagramImpactAnalysisReportExecutor;

    @Autowired
    private UndoRedoDiagramDescriptionProvider undoRedoDiagramDescriptionProvider;

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
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a tool, when asking for impact analysis on tool, then the report return is correct")
    public void givenDiagramWithToolWhenAskingForImpactAnalysisOnToolThenTheReportIsCorrect() {
        var flux = this.givenSubscriptionToModelOperationDiagram();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
        });

        Runnable invokeImpactAnalysisReport = () -> this.diagramImpactAnalysisReportExecutor.execute(
                        PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                        diagramId.get(),
                        this.modelOperationDiagramDescriptionProvider.getCreateNodeToolId(),
                        diagramId.get(),
                        List.of())
                .isSuccess()
                .hasElementsDeleted(0)
                .hasElementsCreated(2)
                .hasElementsModified(3)
                .assertImpactTree(dataTree -> {
                    assertThat(dataTree.id()).isEqualTo("impact_tree");
                    assertThat(dataTree.nodes().stream().filter(node -> node.parentId() == null)).hasSize(1);
                    assertThat(dataTree.nodes()).anySatisfy(node -> {
                        assertThat(node.label().toString()).isEqualTo("elements: a");
                        assertThat(node.endIconsURLs()).hasSize(1);
                        List<String> endIconsURL = node.endIconsURLs().get(0);
                        assertThat(endIconsURL).anyMatch(endIconURL -> endIconURL.contains("FeatureAddition.svg"));
                        Optional<DataTreeNode> parentNode = dataTree.nodes().stream().filter(dataTreeNode -> Objects.equals(dataTreeNode.id(), node.parentId()))
                                .findFirst();
                        assertThat(parentNode).isPresent()
                                .get()
                                .returns("Sirius Web", dataTreeNode -> dataTreeNode.label().toString());
                    });
                    assertThat(dataTree.nodes()).anySatisfy(node -> {
                        assertThat(node.label().toString()).isEqualTo("elements: c");
                        assertThat(node.endIconsURLs()).hasSize(1);
                        List<String> endIconsURL = node.endIconsURLs().get(0);
                        assertThat(endIconsURL).anyMatch(endIconURL -> endIconURL.contains("FeatureAddition.svg"));
                        Optional<DataTreeNode> parentNode = dataTree.nodes().stream().filter(dataTreeNode -> Objects.equals(dataTreeNode.id(), node.parentId()))
                                .findFirst();
                        assertThat(parentNode).isPresent()
                                .get()
                                .returns("Sirius Web", dataTreeNode -> dataTreeNode.label().toString());
                    });
                    assertThat(dataTree.nodes()).anySatisfy(node -> {
                        assertThat(node.label().toString()).isEqualTo("name: a");
                        assertThat(node.endIconsURLs()).hasSize(1);
                        List<String> endIconsURL = node.endIconsURLs().get(0);
                        assertThat(endIconsURL).anyMatch(endIconURL -> endIconURL.contains("FeatureAddition.svg"));
                        Optional<DataTreeNode> parentNode = dataTree.nodes().stream().filter(dataTreeNode -> Objects.equals(dataTreeNode.id(), node.parentId()))
                                .findFirst();
                        assertThat(parentNode).isPresent()
                                .get()
                                .returns("a", dataTreeNode -> dataTreeNode.label().toString());
                    });
                    assertThat(dataTree.nodes()).anySatisfy(node -> {
                        assertThat(node.label().toString()).isEqualTo("name: c");
                        assertThat(node.endIconsURLs()).hasSize(1);
                        List<String> endIconsURL = node.endIconsURLs().get(0);
                        assertThat(endIconsURL).anyMatch(endIconURL -> endIconURL.contains("FeatureAddition.svg"));
                        Optional<DataTreeNode> parentNode = dataTree.nodes().stream().filter(dataTreeNode -> Objects.equals(dataTreeNode.id(), node.parentId()))
                                .findFirst();
                        assertThat(parentNode).isPresent()
                                .get()
                                .returns("c", dataTreeNode -> dataTreeNode.label().toString());
                    });
                });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(invokeImpactAnalysisReport)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a tool that fails, when asking for impact analysis on the tool, then the report contains an error message")
    public void givenDiagramWithToolThatFailsWhenAskingForImpactAnalysisOnToolThenReportContainsErrorMessage() {
        var flux = this.givenSubscriptionToModelOperationDiagram();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
        });

        Runnable invokeImpactAnalysisReport = () -> this.diagramImpactAnalysisReportExecutor.execute(
                        PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                        diagramId.get(),
                        "failingNodeToolId",
                        diagramId.get(),
                        List.of())
                .hasExecutionFailure()
                .hasElementsDeleted(0)
                .hasElementsCreated(0)
                .hasElementsModified(0)
                .assertImpactTree(dataTree -> {
                    assertThat(dataTree.id()).isEqualTo("impact_tree");
                    assertThat(dataTree.nodes()).isEmpty();
                });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(invokeImpactAnalysisReport)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a delete tool, when asking for impact analysis, then the deletion is reported")
    public void givenDiagramWithDeleteToolWhenAskingForImpactAnalysisThenDeletionIsReported() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.undoRedoDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "DeleteImpactAnalysis"
        );
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
        var diagramId = new AtomicReference<String>();
        var nodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            nodeId.set(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode().getId());
        });

        Runnable invokeImpactAnalysisReport = () -> this.diagramImpactAnalysisReportExecutor.execute(
                        PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                        diagramId.get(),
                        DeleteOneDiagramElementToolHandler.DELETE_ELEMENT_TOOL_ID,
                        nodeId.get(),
                        List.of())
                .isSuccess()
                .hasElementsDeleted(1)
                .hasElementsCreated(0)
                .hasElementsModified(1)
                .assertImpactTree(dataTree -> assertThat(dataTree.nodes()).anySatisfy(node -> {
                    assertThat(node.label().toString()).isEqualTo("elements: sirius-web-infrastructure");
                    assertThat(node.endIconsURLs()).anySatisfy(icons ->
                            assertThat(icons).anyMatch(icon -> icon.contains("FeatureDeletion.svg")));
                }));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(invokeImpactAnalysisReport)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
