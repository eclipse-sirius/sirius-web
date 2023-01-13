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
import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.RemoveEdgeEvent;
import org.eclipse.sirius.components.diagrams.layout.ELKLayoutedDiagramProvider;
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
 * Used to test the layout of edges.
 *
 * @author gcoutable
 */
public class EdgeLayoutTests {
    private static final Path PATH_TO_EDITING_CONTEXTS = Paths.get("src", "test", "resources", "editing-contexts");

    private TestLayoutObjectService objectService = new TestLayoutObjectService();

    private DefaultTestDiagramDescriptionProvider defaultTestDiagramDescriptionProvider = new DefaultTestDiagramDescriptionProvider(this.objectService);

    private TestDiagramCreationService createDiagramCreationService(Diagram diagram) {
        IRepresentationDescriptionSearchService.NoOp representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                DiagramDescription diagramDescription = EdgeLayoutTests.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);
                return Optional.of(diagramDescription);
            }
        };

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        LayoutService layoutService = new LayoutService(new IELKDiagramConverter.NoOp(), new IncrementalLayoutDiagramConverter(), new LayoutConfiguratorRegistry(List.of()),
                new ELKLayoutedDiagramProvider(List.of()), new IncrementalLayoutedDiagramProvider(), representationDescriptionSearchService, incrementalLayoutEngine);

        return new TestDiagramCreationService(this.objectService, representationDescriptionSearchService, layoutService);
    }

    @Test
    public void testRemoveEdgesBetweenTwoSameElements() {
        String firstNode = "First";
        String secondNode = "Second";

        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root")
                .nodes()
                    .rectangleNode(firstNode).at(10, 10).of(40, 210).and()
                    .rectangleNode(secondNode).at(100, 10).of(40, 210).and()
                    .and()
                .edge("E1 - removed")
                    .from(firstNode).at(0.75, 1.0 / 7.0)
                    .to(secondNode).at(0.25, 1.0 / 7.0)
                    .and()
                .edge("E2")
                    .from(firstNode).at(0.75, 2.0 / 7.0)
                    .to(secondNode).at(0.25, 2.0 / 7.0)
                    .and()
                .edge("E3 - removed")
                    .from(firstNode).at(0.75, 3.0 / 7.0)
                    .to(secondNode).at(0.25, 3.0 / 7.0)
                    .and()
                .edge("E4")
                    .from(firstNode).at(0.75, 4.0 / 7.0)
                    .to(secondNode).at(0.25, 4.0 / 7.0)
                    .and()
                .edge("E5 - removed")
                    .from(firstNode).at(0.75, 5.0 / 7.0)
                    .to(secondNode).at(0.25, 5.0 / 7.0)
                    .and()
                .edge("E6")
                    .from(firstNode).at(0.75, 6.0 / 7.0)
                    .to(secondNode).at(0.25, 6.0 / 7.0)
                    .and()
                .build();
        // @formatter:on

        Path path = Paths.get(PATH_TO_EDITING_CONTEXTS.toString(), "testRemoveEdgeWithMultipleEdgeBetweenSameElement");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        List<String> removedEdgeIds = new ArrayList<>();
        // @formatter:off
        diagram.getEdges().stream()
                .filter(edge -> edge.getCenterLabel().getText().contains(" - removed"))
                .map(Edge::getId)
                .forEach(removedEdgeIds::add);
        // @formatter:on
        RemoveEdgeEvent removeEdgeEvent = new RemoveEdgeEvent(removedEdgeIds);

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        Optional<Diagram> optionalRefreshedDiagram = diagramCreationService.performRefresh(editingContext, diagram, removeEdgeEvent);
        assertThat(optionalRefreshedDiagram).isPresent();
        Diagram refreshedDiagram = optionalRefreshedDiagram.get();
        List<Edge> refreshedEdges = refreshedDiagram.getEdges();
        assertThat(refreshedEdges).hasSize(3);

        // Each remaining edge has the id for index 0, 1 and 2 but keeps its position
        // @formatter:off
        assertThat(refreshedEdges.get(0))
                .hasId(diagram.getEdges().get(0).getId())
                .hasSourceAnchorRelativePositionRatio(Ratio.of(0.75, 2.0 / 7.0))
                .hasTargetAnchorRelativePositionRatio(Ratio.of(0.25, 2.0 / 7.0));
        assertThat(refreshedEdges.get(1))
                .hasId(diagram.getEdges().get(1).getId())
                .hasSourceAnchorRelativePositionRatio(Ratio.of(0.75, 4.0 / 7.0))
                .hasTargetAnchorRelativePositionRatio(Ratio.of(0.25, 4.0 / 7.0));
        assertThat(refreshedEdges.get(2))
                .hasId(diagram.getEdges().get(2).getId())
                .hasSourceAnchorRelativePositionRatio(Ratio.of(0.75, 6.0 / 7.0))
                .hasTargetAnchorRelativePositionRatio(Ratio.of(0.25, 6.0 / 7.0));
        // @formatter:on
    }
}
