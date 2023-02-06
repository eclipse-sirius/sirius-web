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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.CustomizableProperties;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
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
 * Tests the manual resize of a node.
 *
 * @author rpage
 */
public class NodeResizeTests {
    private TestLayoutObjectService objectService = new TestLayoutObjectService();

    private DefaultTestDiagramDescriptionProvider defaultTestDiagramDescriptionProvider = new DefaultTestDiagramDescriptionProvider(this.objectService);

    private TextBoundsService textBoundsService = new TextBoundsService();

    private ELKPropertiesService elkPropertiesService = new ELKPropertiesService();

    @Test
    public void testDownsizeFromN() {
        Position positionDelta = Position.at(0, -100);
        Size newSize = Size.of(200, 200);
        this.testResizeNode(positionDelta, Position.at(100, 200), newSize);
    }

    @Test
    public void testDownsizeFromNE() {
        Position positionDelta = Position.at(0, -100);
        Size newSize = Size.of(150, 200);
        this.testResizeNode(positionDelta, Position.at(100, 200), newSize);
    }

    @Test
    public void testDownsizeFromE() {
        Position positionDelta = Position.at(0, 0);
        Size newSize = Size.of(150, 300);
        this.testResizeNode(positionDelta, Position.at(100, 100), newSize);
    }

    @Test
    public void testDownsizeFromSE() {
        Position positionDelta = Position.at(0, 0);
        Size newSize = Size.of(150, 200);
        this.testResizeNode(positionDelta, Position.at(100, 100), newSize);
    }

    @Test
    public void testDownsizeFromS() {
        Position positionDelta = Position.at(0, 0);
        Size newSize = Size.of(200, 200);
        this.testResizeNode(positionDelta, Position.at(100, 100), newSize);
    }

    @Test
    public void testDownsizeFromSW() {
        Position positionDelta = Position.at(-100, 0);
        Size newSize = Size.of(150, 200);
        this.testResizeNode(positionDelta, Position.at(200, 100), newSize);
    }

    @Test
    public void testDownsizeFromW() {
        Position positionDelta = Position.at(-100, 0);
        Size newSize = Size.of(150, 300);
        this.testResizeNode(positionDelta, Position.at(200, 100), newSize);
    }

    @Test
    public void testDownsizeFromNW() {
        Position positionDelta = Position.at(-100, -100);
        Size newSize = Size.of(150, 200);
        this.testResizeNode(positionDelta, Position.at(200, 200), newSize);
    }

    @Test
    public void testExpandFromN() {
        Position positionDelta = Position.at(0, 100);
        Size newSize = Size.of(200, 400);
        this.testResizeNode(positionDelta, Position.at(100, 0), newSize);
    }

    @Test
    public void testExpandFromNE() {
        Position positionDelta = Position.at(0, 100);
        Size newSize = Size.of(300, 400);
        this.testResizeNode(positionDelta, Position.at(100, 0), newSize);
    }

    @Test
    public void testExpandFromE() {
        Position positionDelta = Position.at(0, 0);
        Size newSize = Size.of(300, 300);
        this.testResizeNode(positionDelta, Position.at(100, 100), newSize);
    }

    @Test
    public void testExpandFromSE() {
        Position positionDelta = Position.at(0, 0);
        Size newSize = Size.of(300, 400);
        this.testResizeNode(positionDelta, Position.at(100, 100), newSize);
    }

    @Test
    public void testExpandFromS() {
        Position positionDelta = Position.at(0, 0);
        Size newSize = Size.of(200, 400);
        this.testResizeNode(positionDelta, Position.at(100, 100), newSize);
    }

    @Test
    public void testExpandFromSW() {
        Position positionDelta = Position.at(100, 0);
        Size newSize = Size.of(300, 400);
        this.testResizeNode(positionDelta, Position.at(0, 100), newSize);
    }

    @Test
    public void testExpandFromW() {
        Position positionDelta = Position.at(100, 0);
        Size newSize = Size.of(300, 300);
        this.testResizeNode(positionDelta, Position.at(0, 100), newSize);
    }

    @Test
    public void testExpandFromNW() {
        Position positionDelta = Position.at(100, 100);
        Size newSize = Size.of(300, 400);
        this.testResizeNode(positionDelta, Position.at(0, 0), newSize);
    }

    private void testResizeNode(Position positionDelta, Position expectedPosition, Size newSize) {
        String firstParentTargetObjectId = "First Parent";

        Diagram diagram = this.createDiagramWithOneNode(firstParentTargetObjectId);

        Optional<Node> optionalResizedFirstParent = this.resizeNode(firstParentTargetObjectId, diagram, positionDelta, newSize);

        Node resizedFirstParent = optionalResizedFirstParent.get();
        assertTrue(newSize.equals(resizedFirstParent.getSize()));
        assertTrue(expectedPosition.equals(resizedFirstParent.getPosition()));
        assertTrue(resizedFirstParent.getCustomizedProperties().stream().anyMatch(prop -> prop.equals(CustomizableProperties.Size)));
    }

    private Diagram createDiagramWithOneNode(String firstParentTargetObjectId) {
        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root")
                .nodes()
                    .rectangleNode(firstParentTargetObjectId).at(100, 100).of(200, 300)
                    .and()
                .and()
            .build();
        // @formatter:on
        return diagram;
    }

    private Optional<Node> resizeNode(String objectId, Diagram diagram, Position positionDelta, Size newSize) {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testResizeWithNoPositionDelta");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);

        Optional<Node> optionalFirstParent = this.getNode(diagram.getNodes(), objectId);
        assertThat(optionalFirstParent).isPresent();
        Node firstParent = optionalFirstParent.get();

        IDiagramEvent resizeEvent = new ResizeEvent(firstParent.getId(), positionDelta, newSize);
        Optional<Diagram> newDiagram = diagramCreationService.performRefresh(editingContext, diagram, resizeEvent);
        Diagram layoutedDiagram = diagramCreationService.performLayout(editingContext, newDiagram.get(), resizeEvent);

        Optional<Node> optionalResizedFirstParent = this.getNode(layoutedDiagram.getNodes(), objectId);
        assertThat(optionalResizedFirstParent).isPresent();
        return optionalResizedFirstParent;
    }

    private TestDiagramCreationService createDiagramCreationService(Diagram diagram) {
        IRepresentationDescriptionSearchService.NoOp representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                DiagramDescription diagramDescription = NodeResizeTests.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);
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
}
