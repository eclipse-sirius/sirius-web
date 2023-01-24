/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramAssertions.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.CustomizableProperties;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeEvent;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.diagrams.layout.ELKLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.ELKPropertiesService;
import org.eclipse.sirius.components.diagrams.layout.IELKDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.ILayoutEngineHandlerSwitchProvider;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.LayoutService;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageNodeStyleSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.services.DefaultTestDiagramDescriptionProvider;
import org.eclipse.sirius.components.diagrams.layout.services.TestDiagramCreationService;
import org.eclipse.sirius.components.diagrams.layout.services.TestLayoutObjectService;
import org.eclipse.sirius.components.diagrams.tests.builder.JsonBasedEditingContext;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Test;

/**
 * Used to test the refresh and the layout of a diagram after an edge reconnection.
 *
 * @author gcoutable
 */
public class ReconnectEdgeTests {

    private static final String EDGE_TO_RECONNECT_LABEL = "Edge to reconnect";

    private static final String DIAGRAM_ROOT = "Root";

    private static final String THIRD_NODE = "Third";

    private static final String SECOND_NODE = "Second";

    private static final String FIRST_NODE = "First";

    private static final Path PATH_TO_EDITING_CONTEXTS = Paths.get("src", "test", "resources", "editing-contexts");

    private TestLayoutObjectService objectService = new TestLayoutObjectService();

    private DefaultTestDiagramDescriptionProvider defaultTestDiagramDescriptionProvider = new DefaultTestDiagramDescriptionProvider(this.objectService);

    private ELKPropertiesService elkPropertiesService = new ELKPropertiesService();

    private TestDiagramCreationService createDiagramCreationService(Diagram diagram) {
        IRepresentationDescriptionSearchService.NoOp representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                DiagramDescription diagramDescription = ReconnectEdgeTests.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);
                return Optional.of(diagramDescription);
            }
        };

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        LayoutService layoutService = new LayoutService(new IELKDiagramConverter.NoOp(), new IncrementalLayoutDiagramConverter(this.elkPropertiesService), new LayoutConfiguratorRegistry(List.of()),
                new ELKLayoutedDiagramProvider(List.of(), this.elkPropertiesService), new IncrementalLayoutedDiagramProvider(), representationDescriptionSearchService, incrementalLayoutEngine);

        return new TestDiagramCreationService(this.objectService, representationDescriptionSearchService, layoutService);
    }

    private Optional<Node> getNode(List<Node> nodes, String targetObjectId) {
        Optional<Node> optionalNode = Optional.empty();
        List<Node> deeperNode = new ArrayList<>();

        Iterator<Node> nodeIt = nodes.iterator();
        while (optionalNode.isEmpty() && nodeIt.hasNext()) {
            Node node = nodeIt.next();
            if (targetObjectId.equals(node.getTargetObjectId())) {
                optionalNode = Optional.of(node);
            } else {
                deeperNode.addAll(node.getChildNodes());
            }
        }

        if (optionalNode.isEmpty() && !deeperNode.isEmpty()) {
            optionalNode = this.getNode(deeperNode, targetObjectId);
        }

        return optionalNode;
    }

    @Test
    public void testReconnectTargetEdgeEnd() {
        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram(DIAGRAM_ROOT)
            .nodes()
                .rectangleNode(FIRST_NODE).at(10, 10).of(200, 200).customizedProperties(Set.of(CustomizableProperties.Size)).and()
                .rectangleNode(SECOND_NODE).at(250, 10).of(200, 200).customizedProperties(Set.of(CustomizableProperties.Size)).and()
                .rectangleNode(THIRD_NODE).at(250, 250).of(200, 200).customizedProperties(Set.of(CustomizableProperties.Size)).and()
                .and()
            .edge(EDGE_TO_RECONNECT_LABEL)
                .from(FIRST_NODE).at(0.25, 0.25)
                .to(SECOND_NODE).at(0.5, 0.5)
                .and()
            .build();
        // @formatter:on

        Path path = Paths.get(PATH_TO_EDITING_CONTEXTS.toString(), "testReconnectTargetEdgeEnd");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Optional<Node> thirdNode = this.getNode(diagram.getNodes(), THIRD_NODE);
        assertThat(thirdNode).isPresent();

        Edge edge = diagram.getEdges().get(0);
        String edgeId = edge.getId();
        ReconnectEdgeEvent reconnectEdgeEvent = new ReconnectEdgeEvent(ReconnectEdgeKind.TARGET, edgeId, thirdNode.get().getId(), Position.at(300, 350));

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        Optional<Diagram> optionalRefreshedDiagram = diagramCreationService.performRefresh(editingContext, diagram, reconnectEdgeEvent);
        assertThat(optionalRefreshedDiagram).isPresent();

        Diagram refreshedDiagram = optionalRefreshedDiagram.get();
        assertThat(refreshedDiagram.getEdges()).hasSize(1);
        Edge refreshedEdge = refreshedDiagram.getEdges().get(0);
        assertThat(refreshedEdge.getId()).isNotEqualTo(edgeId);

        Optional<Node> optionalFirstRefreshedNode = this.getNode(refreshedDiagram.getNodes(), FIRST_NODE);
        Optional<Node> optionalThirdRefreshedNode = this.getNode(refreshedDiagram.getNodes(), THIRD_NODE);
        assertThat(refreshedEdge.getSourceId()).isEqualTo(optionalFirstRefreshedNode.get().getId());
        assertThat(refreshedEdge.getTargetId()).isEqualTo(optionalThirdRefreshedNode.get().getId());
        assertThat(refreshedEdge.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(0.25, 0.25));
        assertThat(refreshedEdge.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(-1, -1));

        Diagram layoutedDiagram = diagramCreationService.performLayout(editingContext, refreshedDiagram, reconnectEdgeEvent);

        assertThat(layoutedDiagram.getEdges()).hasSize(1);
        Edge layoutedEdge = layoutedDiagram.getEdges().get(0);
        assertThat(layoutedEdge.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(0.25, 0.25));
        assertThat(layoutedEdge.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(0.25, 0.5));
    }

    @Test
    public void testReconnectSourceEdgeEnd() {
        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram(DIAGRAM_ROOT)
            .nodes()
                .rectangleNode(FIRST_NODE).at(10, 10).of(200, 200).customizedProperties(Set.of(CustomizableProperties.Size)).and()
                .rectangleNode(SECOND_NODE).at(250, 10).of(200, 200).customizedProperties(Set.of(CustomizableProperties.Size)).and()
                .rectangleNode(THIRD_NODE).at(250, 250).of(200, 200).customizedProperties(Set.of(CustomizableProperties.Size)).and()
                .and()
            .edge(EDGE_TO_RECONNECT_LABEL)
                .from(FIRST_NODE).at(0.25, 0.25)
                .to(SECOND_NODE).at(0.75, 0.5)
                .goingThrough(20, 120)
                .and()
            .build();
        // @formatter:on

        Path path = Paths.get(PATH_TO_EDITING_CONTEXTS.toString(), "testReconnectSourceEdgeEnd");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Optional<Node> thirdNode = this.getNode(diagram.getNodes(), THIRD_NODE);
        assertThat(thirdNode).isPresent();

        Edge edge = diagram.getEdges().get(0);
        String edgeId = edge.getId();
        ReconnectEdgeEvent reconnectEdgeEvent = new ReconnectEdgeEvent(ReconnectEdgeKind.SOURCE, edgeId, thirdNode.get().getId(), Position.at(300, 350));

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        Optional<Diagram> optionalRefreshedDiagram = diagramCreationService.performRefresh(editingContext, diagram, reconnectEdgeEvent);
        assertThat(optionalRefreshedDiagram).isPresent();

        Diagram refreshedDiagram = optionalRefreshedDiagram.get();
        assertThat(refreshedDiagram.getEdges()).hasSize(1);
        Edge refreshedEdge = refreshedDiagram.getEdges().get(0);
        assertThat(refreshedEdge.getId()).isNotEqualTo(edgeId);

        Optional<Node> optionalSecondRefreshedNode = this.getNode(refreshedDiagram.getNodes(), SECOND_NODE);
        Optional<Node> optionalThirdRefreshedNode = this.getNode(refreshedDiagram.getNodes(), THIRD_NODE);
        assertThat(refreshedEdge.getSourceId()).isEqualTo(optionalThirdRefreshedNode.get().getId());
        assertThat(refreshedEdge.getTargetId()).isEqualTo(optionalSecondRefreshedNode.get().getId());
        assertThat(refreshedEdge.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(-1, -1));
        assertThat(refreshedEdge.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(0.75, 0.5));
        assertThat(refreshedEdge.getRoutingPoints()).hasSize(1);
        assertThat(refreshedEdge.getRoutingPoints().get(0)).isEqualTo(Position.at(20, 120));

        Diagram layoutedDiagram = diagramCreationService.performLayout(editingContext, refreshedDiagram, reconnectEdgeEvent);

        assertThat(layoutedDiagram.getEdges()).hasSize(1);
        Edge layoutedEdge = layoutedDiagram.getEdges().get(0);
        assertThat(layoutedEdge.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(0.25, 0.5));
        assertThat(layoutedEdge.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(0.75, 0.5));
        assertThat(layoutedEdge.getRoutingPoints()).hasSize(1);
        assertThat(layoutedEdge.getRoutingPoints().get(0)).isEqualTo(Position.at(20, 120));
    }

    @Test
    public void testReconnectOneTargetWithMultipleEdgeBetweenSameElements() {
        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram(DIAGRAM_ROOT)
            .nodes()
                .rectangleNode(FIRST_NODE).at(10, 10).of(200, 200).customizedProperties(Set.of(CustomizableProperties.Size)).and()
                .rectangleNode(SECOND_NODE).at(250, 10).of(200, 200).customizedProperties(Set.of(CustomizableProperties.Size)).and()
                .rectangleNode(THIRD_NODE).at(250, 250).of(200, 200).customizedProperties(Set.of(CustomizableProperties.Size)).and()
                .and()
            .edge("f -> s 0")
                .from(FIRST_NODE).at(0.75, 0.2)
                .to(SECOND_NODE).at(0.25, 0.2)
                .and()
            .edge(EDGE_TO_RECONNECT_LABEL)
                .from(FIRST_NODE).at(0.75, 0.4)
                .to(SECOND_NODE).at(0.25, 0.4)
                .and()
            .edge("f -> s 2")
                .from(FIRST_NODE).at(0.75, 0.6)
                .to(SECOND_NODE).at(0.25, 0.6)
                .and()
            .edge("f -> s 3")
                .from(FIRST_NODE).at(0.75, 0.8)
                .to(SECOND_NODE).at(0.25, 0.8)
                .and()
            .edge("f -> t 0")
                .from(FIRST_NODE).at(0.5, 0.75)
                .to(THIRD_NODE).at(0.25, 0.5)
                .goingThrough(110, 350)
                .and()
            .build();
        // @formatter:on

        Path path = Paths.get(PATH_TO_EDITING_CONTEXTS.toString(), "testReconnectOneTargetWithMultipleEdgeBetweenSameElements");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Optional<Node> secondNode = this.getNode(diagram.getNodes(), SECOND_NODE);
        assertThat(secondNode).isPresent();
        Optional<Node> thirdNode = this.getNode(diagram.getNodes(), THIRD_NODE);
        assertThat(thirdNode).isPresent();

        Edge edgeToReconnect = diagram.getEdges().get(1);
        String edgeToReconnectId = edgeToReconnect.getId();
        ReconnectEdgeEvent reconnectEdgeEvent = new ReconnectEdgeEvent(ReconnectEdgeKind.TARGET, edgeToReconnectId, thirdNode.get().getId(), Position.at(300, 300));

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        Optional<Diagram> optionalRefreshedDiagram = diagramCreationService.performRefresh(editingContext, diagram, reconnectEdgeEvent);
        assertThat(optionalRefreshedDiagram).isPresent();

        Diagram refreshedDiagram = optionalRefreshedDiagram.get();
        assertThat(reconnectEdgeEvent.getEdgeId()).isNotEqualTo(edgeToReconnectId);
        // @formatter:off
        assertThat(refreshedDiagram.getEdges())
            .hasSize(5)
            .anySatisfy(edge -> {
                assertThat(edge.getTargetId()).isEqualTo(thirdNode.get().getId());
                assertThat(edge)
                    .goesThrough(List.of(Position.at(110, 350)))
                    .hasSourceAnchorRelativePositionRatio(Ratio.of(0.5, 0.75))
                    .hasTargetAnchorRelativePositionRatio(Ratio.of(0.25, 0.5));
            })
            .anySatisfy(edge -> {
                assertThat(edge.getTargetId()).isEqualTo(thirdNode.get().getId());
                assertThat(edge)
                    .hasId(reconnectEdgeEvent.getEdgeId())
                    .hasSourceAnchorRelativePositionRatio(Ratio.of(0.75, 0.4))
                    .hasTargetAnchorRelativePositionRatio(Ratio.of(-1, -1));
            })
            .anySatisfy(edge -> {
                assertThat(edge.getTargetId()).isEqualTo(secondNode.get().getId());
                assertThat(edge)
                    .hasSourceAnchorRelativePositionRatio(Ratio.of(0.75, 0.2))
                    .hasTargetAnchorRelativePositionRatio(Ratio.of(0.25, 0.2));
            })
            .anySatisfy(edge -> {
                assertThat(edge.getTargetId()).isEqualTo(secondNode.get().getId());
                assertThat(edge)
                    .hasSourceAnchorRelativePositionRatio(Ratio.of(0.75, 0.6))
                    .hasTargetAnchorRelativePositionRatio(Ratio.of(0.25, 0.6));
            })
            .anySatisfy(edge -> {
                assertThat(edge.getTargetId()).isEqualTo(secondNode.get().getId());
                assertThat(edge)
                    .hasSourceAnchorRelativePositionRatio(Ratio.of(0.75, 0.8))
                    .hasTargetAnchorRelativePositionRatio(Ratio.of(0.25, 0.8));
            });
        // @formatter:on

        Diagram layoutedDiagram = diagramCreationService.performLayout(editingContext, refreshedDiagram, reconnectEdgeEvent);
        // @formatter:off
        assertThat(layoutedDiagram.getEdges())
            .hasSize(5)
            .anySatisfy(edge -> {
                assertThat(edge.getTargetId()).isEqualTo(thirdNode.get().getId());
                assertThat(edge)
                    .hasId(reconnectEdgeEvent.getEdgeId())
                    .hasSourceAnchorRelativePositionRatio(Ratio.of(0.75, 0.4))
                    .hasTargetAnchorRelativePositionRatio(Ratio.of(0.25, 0.25));
            });
        // @formatter:on
    }

}
