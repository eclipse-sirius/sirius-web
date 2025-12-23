/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.ReconnectEdgeInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeType;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.diagrams.tests.graphql.ReconnectEdgeMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.EdgeDiagramDescriptionProvider;
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
 * Integration tests for the manipulation of edge styles.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class EdgeStyleControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EdgeDiagramDescriptionProvider edgeDiagramDescriptionProvider;

    @Autowired
    private ReconnectEdgeMutationRunner reconnectEdgeMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToLabelEditableDiagramDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.edgeDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "EdgeDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with edges, when the diagram is rendered, then the edge styles are returned")
    public void givenDiagramWithEdgesWhenDiagramIsRenderedThenEdgeStylesAreReturned() {
        var flux = this.givenSubscriptionToLabelEditableDiagramDiagram();


        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getEdges()).hasSize(1);
            var edgeStyle = diagram.getEdges().get(0).getStyle();
            assertThat(edgeStyle.getColor()).isEqualTo("black");
            assertThat(edgeStyle.getSize()).isEqualTo(1);
            assertThat(edgeStyle.getLineStyle()).isEqualTo(LineStyle.Solid);
            assertThat(edgeStyle.getSourceArrow()).isEqualTo(ArrowStyle.None);
            assertThat(edgeStyle.getTargetArrow()).isEqualTo(ArrowStyle.InputArrow);
            assertThat(edgeStyle.getEdgeType()).isEqualTo(EdgeType.Manhattan);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with edge with conditional style, when the condition is true, then the good edge style is returned")
    public void givenDiagramWithEdgeWithConditionalStyleWhenConditionIsTrueThenGoodEdgeStyleIsReturned() {
        var flux = this.givenSubscriptionToLabelEditableDiagramDiagram();

        var diagramId = new AtomicReference<String>();
        var edgeId = new AtomicReference<String>();
        var siriusWebInfrastructureNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebInfrastructureNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode();
            siriusWebInfrastructureNodeId.set(siriusWebInfrastructureNode.getId());

            assertThat(diagram.getEdges()).hasSize(1);
            var edge = diagram.getEdges().get(0);
            edgeId.set(edge.getId());
            var edgeStyle = edge.getStyle();
            assertThat(edgeStyle.getColor()).isEqualTo("black");
            assertThat(edgeStyle.getSize()).isEqualTo(1);
            assertThat(edgeStyle.getLineStyle()).isEqualTo(LineStyle.Solid);
            assertThat(edgeStyle.getSourceArrow()).isEqualTo(ArrowStyle.None);
            assertThat(edgeStyle.getTargetArrow()).isEqualTo(ArrowStyle.InputArrow);
            assertThat(edgeStyle.getEdgeType()).isEqualTo(EdgeType.Manhattan);
        });

        Runnable reconnectSource = () -> {
            var input = new ReconnectEdgeInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    edgeId.get(),
                    siriusWebInfrastructureNodeId.get(),
                    ReconnectEdgeKind.TARGET
            );
            var result = this.reconnectEdgeMutationRunner.run(input);
            String typename = JsonPath.read(result.data(), "$.data.reconnectEdge.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var edgeStyle = diagram.getEdges().stream()
                    .filter(edge -> edge.getCenterLabel().text().equals("sirius-web-application -> sirius-web-infrastructure"))
                    .findFirst()
                    .map(Edge::getStyle)
                    .orElse(null);
            assertThat(edgeStyle).isNotNull();
            assertThat(edgeStyle.getSize()).isEqualTo(2);
            assertThat(edgeStyle.getLineStyle()).isEqualTo(LineStyle.Dash);
            assertThat(edgeStyle.getSourceArrow()).isEqualTo(ArrowStyle.Circle);
            assertThat(edgeStyle.getTargetArrow()).isEqualTo(ArrowStyle.ClosedArrowWithVerticalBar);
            assertThat(edgeStyle.getEdgeType()).isEqualTo(EdgeType.SmartManhattan);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(reconnectSource)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
