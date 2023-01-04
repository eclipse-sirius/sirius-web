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

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.layout.ELKLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.IELKDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.ILayoutEngineHandlerSwitchProvider;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.LayoutService;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.services.DefaultTestDiagramDescriptionProvider;
import org.eclipse.sirius.components.diagrams.layout.services.TestDiagramCreationService;
import org.eclipse.sirius.components.diagrams.layout.services.TestLayoutObjectService;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Test;

/**
 * Used to test the incremental layout on various kind of move event.
 *
 * @author gcoutable
 */
public class NodeMoveTests {

    private TestLayoutObjectService objectService = new TestLayoutObjectService();

    private DefaultTestDiagramDescriptionProvider defaultTestDiagramDescriptionProvider = new DefaultTestDiagramDescriptionProvider(this.objectService);

    /**
     * Tests that routing points of an edge between two nodes contained in a parent node are update according to the
     * move event that moves the parent node.
     */
    @Test
    public void testNodeMoveUpdatesEdgeRoutingPoints() {
        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root")
            .nodes()
                .rectangleNode("Parent").at(100, 100).of(500, 500)
                    .childNodes(new FreeFormLayoutStrategy())
                        .rectangleNode("First Child").at(100, 100).of(100, 100).and()
                        .rectangleNode("Second Child").at(300, 300).of(100, 100).and()
                    .and()
                .and()
            .and()
            .edge("Contained Edge")
                .from("First Child").at(0.5, 0.5)
                .to("Second Child").at(0.5, 0.5)
                .goingThrough(450, 250)
            .and()
            .build();
        // @formatter:on

        // Add 100px to the parent node position along the x-axis
        String parentId = diagram.getNodes().get(0).getId();
        MoveEvent moveEvent = new MoveEvent(parentId, Position.at(200, 100));
        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        Diagram layoutedDiagram = diagramCreationService.performLayout(new IEditingContext.NoOp(), diagram, moveEvent);
        assertThat(layoutedDiagram).isValid().hasNoOverflow();

        assertThat(layoutedDiagram.getNodes()).hasSize(1);
        Node parentNode = layoutedDiagram.getNodes().get(0);
        assertThat(parentNode).hasBounds(200, 100, 500, 500);

        assertThat(parentNode.getChildNodes()).hasSize(2);
        Node firstChildNode = parentNode.getChildNodes().get(0);
        assertThat(firstChildNode).hasBounds(100, 100, 100, 100);

        Node secondChildNode = parentNode.getChildNodes().get(1);
        assertThat(secondChildNode).hasBounds(300, 300, 100, 100);

        assertThat(layoutedDiagram.getEdges()).hasSize(1);
        Edge edge = layoutedDiagram.getEdges().get(0);
        assertThat(edge).goesThrough(List.of(Position.at(550, 250)));
    }

    private TestDiagramCreationService createDiagramCreationService(Diagram diagram) {
        IRepresentationDescriptionSearchService.NoOp representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                DiagramDescription diagramDescription = NodeMoveTests.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);
                return Optional.of(diagramDescription);
            }
        };

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider, List.of(), layoutEngineHandlerSwitchProvider, borderNodeLayoutEngine);

        LayoutService layoutService = new LayoutService(new IELKDiagramConverter.NoOp(), new IncrementalLayoutDiagramConverter(), new LayoutConfiguratorRegistry(List.of()),
                new ELKLayoutedDiagramProvider(List.of()), new IncrementalLayoutedDiagramProvider(), representationDescriptionSearchService, incrementalLayoutEngine);

        return new TestDiagramCreationService(this.objectService, representationDescriptionSearchService, layoutService);
    }

}
