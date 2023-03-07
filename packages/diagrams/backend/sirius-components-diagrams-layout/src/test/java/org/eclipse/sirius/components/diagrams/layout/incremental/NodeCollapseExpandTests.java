/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.events.UpdateCollapsingStateEvent;
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
 * Used to test the collapse and expand diagram event.
 *
 * @author gcoutable
 */
public class NodeCollapseExpandTests {

    private static final String CHILD_ID = "Child";

    private static final String CONTAINER_ID = "Container";

    private static final String CONTAINER_SIBLING_ID = "Container-sibling";

    private static final String DIAGRAM_ROOT_ID = "Root";

    private static final Path PATH_TO_EDITING_CONTEXTS = Paths.get("src", "test", "resources", "editing-contexts");

    private TestLayoutObjectService objectService = new TestLayoutObjectService();

    private DefaultTestDiagramDescriptionProvider defaultTestDiagramDescriptionProvider = new DefaultTestDiagramDescriptionProvider(this.objectService);

    private TestDiagramCreationService createDiagramCreationService(Diagram diagram) {
        IRepresentationDescriptionSearchService.NoOp representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                DiagramDescription diagramDescription = NodeCollapseExpandTests.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);

                // @formatter:off
                var nodeDescriptions = diagramDescription.getNodeDescriptions().stream()
                        .map(nodeDescription -> NodeDescription.newNodeDescription(nodeDescription).collapsible(true).build())
                        .toList();
                diagramDescription = DiagramDescription.newDiagramDescription(diagramDescription)
                        .nodeDescriptions(nodeDescriptions)
                        .build();
                // @formatter:on
                return Optional.of(diagramDescription);
            }
        };

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        LayoutService layoutService = new LayoutService(new IELKDiagramConverter.NoOp(), new IncrementalLayoutDiagramConverter(new TextBoundsService(), new ELKPropertiesService()),
                new LayoutConfiguratorRegistry(List.of()), new ELKLayoutedDiagramProvider(List.of(), new ELKPropertiesService()), new IncrementalLayoutedDiagramProvider(),
                representationDescriptionSearchService, incrementalLayoutEngine);

        return new TestDiagramCreationService(this.objectService, representationDescriptionSearchService, layoutService);
    }

    @Test
    public void testCollapseExpandedNode() {
        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram(DIAGRAM_ROOT_ID)
            .nodes()
                .rectangleNode(CONTAINER_SIBLING_ID).at(300, 150).of(150, 70).and()
                .rectangleNode(CONTAINER_ID).at(10, 10).of(200, 100)
                    .childNodes(new FreeFormLayoutStrategy())
                        .rectangleNode(CHILD_ID).at(10, 10).of(150, 70).and()
                        .and()
                    .and()
                .and()
            .edge("edge")
                .from(CHILD_ID).at(0.5, 0.5)
                .to(CONTAINER_SIBLING_ID).at(0.5, 0.5)
                .and()
            .build();
        // @formatter:on

        Path path = Paths.get(PATH_TO_EDITING_CONTEXTS.toString(), "testCollapseExpandNode");
        JsonBasedEditingContext jsonBasedEditingContext = new JsonBasedEditingContext(path);

        var updateCollapsingStateEvent = new UpdateCollapsingStateEvent(diagram.getNodes().get(1).getId(), CollapsingState.COLLAPSED);
        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);

        var optionalRefreshedDiagram = diagramCreationService.performRefresh(jsonBasedEditingContext, diagram, updateCollapsingStateEvent);
        assertThat(optionalRefreshedDiagram).isPresent();
        Diagram refreshedDiagram = optionalRefreshedDiagram.get();

        assertThat(refreshedDiagram.getNodes()).hasSize(2);
        Node container = refreshedDiagram.getNodes().get(1);
        assertThat(container.getCollapsingState()).isEqualByComparingTo(CollapsingState.COLLAPSED);
        assertThat(container.getChildNodes()).allMatch(node -> node.getState() == ViewModifier.Hidden);
        assertThat(refreshedDiagram.getEdges()).allMatch(edge -> edge.getState() == ViewModifier.Hidden);

        Diagram laidOutDiagram = diagramCreationService.performLayout(jsonBasedEditingContext, refreshedDiagram, updateCollapsingStateEvent);
        assertThat(laidOutDiagram.getNodes()).hasSize(2);
        Node laidOutContainer = laidOutDiagram.getNodes().get(1);
        assertThat(laidOutContainer.getPosition()).isEqualTo(Position.at(10, 10));
        assertThat(laidOutContainer.getSize()).isEqualTo(Size.of(200, 70));
    }

    @Test
    public void testExpandCollapsedNode() {
        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram(DIAGRAM_ROOT_ID)
            .nodes()
                .rectangleNode(CONTAINER_SIBLING_ID).at(300, 150).of(150, 70).and()
                .rectangleNode(CONTAINER_ID).at(10, 10).of(200, 100)
                    .collapsingState(CollapsingState.COLLAPSED)
                    .childNodes(new FreeFormLayoutStrategy()).and()
                    .and()
                .and()
            .build();
        // @formatter:on

        Path path = Paths.get(PATH_TO_EDITING_CONTEXTS.toString(), "testCollapseExpandNode");
        JsonBasedEditingContext jsonBasedEditingContext = new JsonBasedEditingContext(path);

        var updateCollapsingStateEvent = new UpdateCollapsingStateEvent(diagram.getNodes().get(1).getId(), CollapsingState.EXPANDED);
        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);

        var optionalRefreshedDiagram = diagramCreationService.performRefresh(jsonBasedEditingContext, diagram, updateCollapsingStateEvent);
        assertThat(optionalRefreshedDiagram).isPresent();
        Diagram refreshedDiagram = optionalRefreshedDiagram.get();

        assertThat(refreshedDiagram.getNodes()).hasSize(2);
        Node containerSibling = refreshedDiagram.getNodes().get(0);
        Node container = refreshedDiagram.getNodes().get(1);
        assertThat(container.getCollapsingState()).isEqualByComparingTo(CollapsingState.EXPANDED);
        assertThat(container.getChildNodes()).hasSize(1);

        Node child = container.getChildNodes().get(0);
        assertThat(child.getPosition()).isEqualTo(Position.UNDEFINED);
        assertThat(child.getSize()).isEqualTo(Size.UNDEFINED);

        assertThat(refreshedDiagram.getEdges()).hasSize(1);
        Edge edge = refreshedDiagram.getEdges().get(0);
        assertThat(edge.getSourceId()).isEqualTo(child.getId());
        assertThat(edge.getTargetId()).isEqualTo(containerSibling.getId());
        assertThat(edge.getSourceAnchorRelativePosition()).isEqualTo(Ratio.UNDEFINED);
        assertThat(edge.getTargetAnchorRelativePosition()).isEqualTo(Ratio.UNDEFINED);

        Diagram laidOutDiagram = diagramCreationService.performLayout(jsonBasedEditingContext, refreshedDiagram, updateCollapsingStateEvent);
        assertThat(laidOutDiagram.getNodes()).hasSize(2);
        Node laidOutContainer = laidOutDiagram.getNodes().get(1);
        assertThat(laidOutContainer.getPosition()).isEqualTo(Position.at(10, 10));
        assertThat(laidOutContainer.getSize().getWidth()).isEqualTo(200);
        assertThat(laidOutContainer.getSize().getHeight()).isBetween(118.0, 119.0);
        assertThat(laidOutContainer.getChildNodes()).hasSize(1);

        Node laidOutChild = laidOutContainer.getChildNodes().get(0);
        assertThat(laidOutChild.getPosition().getX()).isEqualTo(12);
        assertThat(laidOutChild.getPosition().getY()).isBetween(36.0, 37.0);
        assertThat(laidOutChild.getSize()).isEqualTo(Size.of(150, 70));

        Edge laidOutEdge = laidOutDiagram.getEdges().get(0);
        assertThat(laidOutEdge.getSourceId()).isEqualTo(child.getId());
        assertThat(laidOutEdge.getTargetId()).isEqualTo(containerSibling.getId());
        assertThat(laidOutEdge.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(0.5, 0.5));
        assertThat(laidOutEdge.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(0.5, 0.5));
    }

}
