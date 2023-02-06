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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.DoublePositionEvent;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.layout.ELKLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.ELKPropertiesService;
import org.eclipse.sirius.components.diagrams.layout.IELKDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.ILayoutEngineHandlerSwitchProvider;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.LayoutService;
import org.eclipse.sirius.components.diagrams.layout.TextBoundsService;
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
 * Used to test diagram layout.
 *
 * @author gcoutable
 */
public class DiagramLayoutTests {

    private static final String DIAGRAM_ROOT_ID = "Root";

    private static final String THIRD_TARGET_OBJECT_ID = "Third";

    private static final String SECOND_TARGET_OBJECT_ID = "Second";

    private static final String FIRST_TARGET_OBJECT_ID = "First";

    private static final Path PATH_TO_EDITING_CONTEXTS = Paths.get("src", "test", "resources", "editing-contexts");

    private TestLayoutObjectService objectService = new TestLayoutObjectService();

    private DefaultTestDiagramDescriptionProvider defaultTestDiagramDescriptionProvider = new DefaultTestDiagramDescriptionProvider(this.objectService);

    private TextBoundsService textBoundsService = new TextBoundsService();

    private ELKPropertiesService elkPropertiesService = new ELKPropertiesService();

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

    private TestDiagramCreationService createDiagramCreationService(Diagram diagram) {
        IRepresentationDescriptionSearchService.NoOp representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                DiagramDescription diagramDescription = DiagramLayoutTests.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);
                return Optional.of(diagramDescription);
            }
        };

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        LayoutService layoutService = new LayoutService(new IELKDiagramConverter.NoOp(), new IncrementalLayoutDiagramConverter(this.textBoundsService, this.elkPropertiesService),
                new LayoutConfiguratorRegistry(List.of()), new ELKLayoutedDiagramProvider(List.of(), this.elkPropertiesService), new IncrementalLayoutedDiagramProvider(),
                representationDescriptionSearchService, incrementalLayoutEngine);

        return new TestDiagramCreationService(this.objectService, representationDescriptionSearchService, layoutService);
    }

    @Test
    public void testRemoveEdgeDoesNotAffectUnrelated() {

        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram(DIAGRAM_ROOT_ID)
            .nodes()
                .rectangleNode(FIRST_TARGET_OBJECT_ID).at(10, 10).of(20, 20).and()
                .rectangleNode(SECOND_TARGET_OBJECT_ID).at(50, 10).of(20, 20).and()
                .rectangleNode(THIRD_TARGET_OBJECT_ID).at(50, 40).of(20, 20).and()
                .and()
            .edge("one")
                .from(FIRST_TARGET_OBJECT_ID).at(0.75, 0.5)
                .to(SECOND_TARGET_OBJECT_ID).at(0.25, 0.5)
                .and()
            .edge("two")
                .from(FIRST_TARGET_OBJECT_ID).at(0.5, 0.75)
                .to(THIRD_TARGET_OBJECT_ID).at(0.25, 0.5)
                .goingThrough(20, 50)
                .and()
            .build();
        // @formatter:on

        Path path = Paths.get(PATH_TO_EDITING_CONTEXTS.toString(), "testRemoveEdgeDoesNotAffectUnrelated");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        Optional<Diagram> optionalRefreshedDiagram = diagramCreationService.performRefresh(editingContext, diagram, null);
        assertThat(optionalRefreshedDiagram).isPresent();
        Diagram refreshedDiagram = optionalRefreshedDiagram.get();
        assertThat(refreshedDiagram.getNodes()).hasSize(2);

        Optional<Node> optionalFirstNode = this.getNode(refreshedDiagram.getNodes(), FIRST_TARGET_OBJECT_ID);
        assertThat(optionalFirstNode).isPresent();
        Node firstNode = optionalFirstNode.get();
        assertThat(firstNode.getPosition()).isEqualTo(Position.at(10, 10));
        assertThat(firstNode.getSize()).isEqualTo(Size.of(20, 20));

        Optional<Node> optionalSecondNode = this.getNode(refreshedDiagram.getNodes(), SECOND_TARGET_OBJECT_ID);
        assertThat(optionalSecondNode).isNotPresent();

        Optional<Node> optionalThirdNode = this.getNode(refreshedDiagram.getNodes(), THIRD_TARGET_OBJECT_ID);
        assertThat(optionalThirdNode).isPresent();
        Node thirdNode = optionalThirdNode.get();
        assertThat(thirdNode.getPosition()).isEqualTo(Position.at(50, 40));
        assertThat(thirdNode.getSize()).isEqualTo(Size.of(20, 20));

        assertThat(refreshedDiagram.getEdges()).hasSize(1);
        Edge edge = refreshedDiagram.getEdges().get(0);
        assertThat(edge.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(0.5, 0.75));
        assertThat(edge.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(0.25, 0.5));
        assertThat(edge.getRoutingPoints()).hasSize(1);
        assertThat(edge.getRoutingPoints().get(0)).isEqualTo(Position.at(20, 50));
    }

    @Test
    public void testSimpleDiagramLayout() throws IOException {
        String firstParentTargetObjectId = "First Parent";
        String secondParentTargetObjectId = "Second Parent";
        String firstChildTargetObjectId = "First child";
        String secondChildTargetObjectId = "Second child";

        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram(DIAGRAM_ROOT_ID)
            .nodes()
                .rectangleNode(firstParentTargetObjectId).at(10, 10).of(200, 300)
                    .childNodes(new FreeFormLayoutStrategy())
                        .rectangleNode(firstChildTargetObjectId).at(10, 10).of(50, 50).and()
                        .rectangleNode(secondChildTargetObjectId).at(70, 70).of(50, 50).and()
                        .and()
                    .and()
                .rectangleNode(secondParentTargetObjectId).at(300, 400).of(100, 100).and()
                .and()
            .edge("Link")
                .from(firstParentTargetObjectId).at(0.5, 0.5)
                .to(secondParentTargetObjectId).at(0.8, 0.8)
                .and()
            .edge("Opposite Link")
                .from(secondParentTargetObjectId).at(0.2, 0.7)
                .to(firstParentTargetObjectId).at(0.1, 0.9)
            .and()
            .build();
        // @formatter:on

        Path path = Paths.get(PATH_TO_EDITING_CONTEXTS.toString(), "testSimpleDiagramLayout");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        IDiagramEvent diagramEvent = new SinglePositionEvent(Position.at(300, 100));

        Optional<Diagram> optionalRefreshedDiagram = diagramCreationService.performRefresh(editingContext, diagram, diagramEvent);
        assertThat(optionalRefreshedDiagram).isNotEmpty();
        Diagram refreshedDiagram = optionalRefreshedDiagram.get();

        Optional<Node> optionalFirstParent = this.getNode(refreshedDiagram.getNodes(), firstParentTargetObjectId);
        assertThat(optionalFirstParent).isPresent();
        Node firstParent = optionalFirstParent.get();
        assertThat(firstParent.getPosition()).isEqualTo(Position.at(10, 10));
        assertThat(firstParent.getSize()).isEqualTo(Size.of(200, 300));

        Optional<Node> optionalFirstChild = this.getNode(refreshedDiagram.getNodes(), firstChildTargetObjectId);
        assertThat(optionalFirstChild).isPresent();
        Node firstChild = optionalFirstChild.get();
        assertThat(firstChild.getPosition()).isEqualTo(Position.at(10, 10));
        assertThat(firstChild.getSize()).isEqualTo(Size.of(50, 50));

        Optional<Node> optionalThirdParent = this.getNode(refreshedDiagram.getNodes(), "Third Parent");
        assertThat(optionalThirdParent).isPresent();
        Node thirdParent = optionalThirdParent.get();
        assertThat(thirdParent.getPosition()).isEqualTo(Position.UNDEFINED);

        assertThat(refreshedDiagram.getEdges()).hasSize(2);
        Edge firstToSecondEdge = refreshedDiagram.getEdges().get(0);
        assertThat(firstToSecondEdge.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(0.5, 0.5));
        assertThat(firstToSecondEdge.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(0.8, 0.8));
        assertThat(firstToSecondEdge.getRoutingPoints()).isEmpty();

        Edge secondToFirstEdge = refreshedDiagram.getEdges().get(1);
        assertThat(secondToFirstEdge.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(0.2, 0.7));
        assertThat(secondToFirstEdge.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(0.1, 0.9));
        assertThat(secondToFirstEdge.getRoutingPoints()).isEmpty();

        Diagram layoutedDiagram = diagramCreationService.performLayout(editingContext, refreshedDiagram, diagramEvent);

        Optional<Node> optionalLayoutedThirdParent = this.getNode(layoutedDiagram.getNodes(), "Third Parent");
        assertThat(optionalLayoutedThirdParent).isPresent();
        Node layoutedThirdParent = optionalLayoutedThirdParent.get();
        assertThat(layoutedThirdParent.getPosition()).isEqualTo(Position.at(300, 100));
    }

    @Test
    public void testCreateEdgeDoesNotAffectOtherEdges() {
        ListLayoutStrategy columnListLayoutStrategy = new ListLayoutStrategy();

        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram(DIAGRAM_ROOT_ID)
            .nodes()
                .rectangleNode(FIRST_TARGET_OBJECT_ID)
                    .at(10, 10)
                    .of(-1, -1)
                    .withHeader(true)
                    .childNodes(columnListLayoutStrategy)
                        .iconLabelNode("Child").at(-1, -1).of(-1, -1).and()
                        .and()
                    .and()
                .rectangleNode(SECOND_TARGET_OBJECT_ID)
                    .at(200, 200)
                    .of(-1, -1)
                    .withHeader(true)
                    .childNodes(columnListLayoutStrategy).and()
                    .and()
            .and()
            .edge(SECOND_TARGET_OBJECT_ID)
                .from(FIRST_TARGET_OBJECT_ID).at(0.75, 0.25)
                .to(SECOND_TARGET_OBJECT_ID).at(0.25, 0.25)
                .and()
            .build();
        // @formatter:on
        TestDiagramCreationService layoutDiagramInitialDiagram = this.createDiagramCreationService(diagram);
        Diagram initialLayoutedDiagram = layoutDiagramInitialDiagram.performLayout(new IEditingContext.NoOp(), diagram, null);
        assertThat(initialLayoutedDiagram.getEdges()).hasSize(1);
        Edge initialEdge = initialLayoutedDiagram.getEdges().get(0);
        assertThat(initialEdge.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(0.75, 0.25));
        assertThat(initialEdge.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(0.25, 0.25));

        Path path = Paths.get(PATH_TO_EDITING_CONTEXTS.toString(), "testCreateEdgeDoesNotAffectOtherEdges");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(initialLayoutedDiagram);
        DoublePositionEvent diagramEvent = new DoublePositionEvent(Position.at(20, 20), Position.at(60, 30));
        Optional<Diagram> optionalRefreshedDiagram = diagramCreationService.performRefresh(editingContext, initialLayoutedDiagram, diagramEvent);
        assertThat(optionalRefreshedDiagram).isPresent();
        Diagram refreshedDiagram = optionalRefreshedDiagram.get();
        assertThat(refreshedDiagram.getEdges()).hasSize(2);

        Optional<Node> first = this.getNode(refreshedDiagram.getNodes(), FIRST_TARGET_OBJECT_ID);
        assertThat(refreshedDiagram.getEdges().get(1).getSourceId()).isEqualTo(first.get().getId());
        assertThat(refreshedDiagram.getEdges().get(1).getTargetId()).isEqualTo(first.get().getId());

        Diagram layoutedDiagram = diagramCreationService.performLayout(editingContext, refreshedDiagram, diagramEvent);
        assertThat(layoutedDiagram.getEdges().get(1).getSourceAnchorRelativePosition().getX()).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(1);
        assertThat(layoutedDiagram.getEdges().get(1).getSourceAnchorRelativePosition().getY()).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(1);
        assertThat(layoutedDiagram.getEdges().get(1).getTargetAnchorRelativePosition().getX()).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(1);
        assertThat(layoutedDiagram.getEdges().get(1).getTargetAnchorRelativePosition().getX()).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(1);

        assertThat(layoutedDiagram.getEdges().get(0).getSourceAnchorRelativePosition()).isEqualTo(refreshedDiagram.getEdges().get(0).getSourceAnchorRelativePosition());
        assertThat(layoutedDiagram.getEdges().get(0).getTargetAnchorRelativePosition()).isEqualTo(refreshedDiagram.getEdges().get(0).getTargetAnchorRelativePosition());

    }
}
