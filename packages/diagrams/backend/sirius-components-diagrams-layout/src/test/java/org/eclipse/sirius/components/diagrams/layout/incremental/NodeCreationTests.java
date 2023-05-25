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
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.layout.ELKLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.ELKPropertiesService;
import org.eclipse.sirius.components.diagrams.layout.IELKDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.ILayoutEngineHandlerSwitchProvider;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.LayoutService;
import org.eclipse.sirius.components.diagrams.layout.TextBoundsService;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageNodeStyleSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodePositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.services.DefaultTestDiagramDescriptionProvider;
import org.eclipse.sirius.components.diagrams.layout.services.TestDiagramCreationService;
import org.eclipse.sirius.components.diagrams.layout.services.TestLayoutObjectService;
import org.eclipse.sirius.components.diagrams.tests.builder.JsonBasedEditingContext;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Test;

/**
 * Tests the creation of a node and the resizing of its parent.
 *
 * @author rpage
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class NodeCreationTests {
    private TestLayoutObjectService objectService = new TestLayoutObjectService();

    private DefaultTestDiagramDescriptionProvider defaultTestDiagramDescriptionProvider = new DefaultTestDiagramDescriptionProvider(this.objectService);

    private TextBoundsService textBoundsService = new TextBoundsService();

    private ELKPropertiesService elkPropertiesService = new ELKPropertiesService();

    @Test
    public void testNodeCreationNoResizeNeeded() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreation");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(10, 10);
        Diagram diagram = this.createParentNotResizedDiagram();
        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        this.testThatChildIsInParentBounds(layoutedDiagram);
    }

    @Test
    public void testNodeCreationWithResizeNeeded() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreation");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(200, 200);
        Diagram diagram = this.createParentNotResizedDiagram();
        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        this.testThatChildIsInParentBounds(layoutedDiagram);
    }

    @Test
    public void testNodeCreationInResizedParentNoResizeNeeded() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreation");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(10, 10);
        Diagram diagram = this.createParentResizedDiagram();
        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        this.testThatChildIsInParentBounds(layoutedDiagram);
    }

    @Test
    public void testNodeCreationInResizedParentWithResizeNeeded() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreation");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(200, 200);
        Diagram diagram = this.createParentResizedDiagram();
        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        this.testThatChildIsInParentBounds(layoutedDiagram);
    }

    @Test
    public void testNodeCreationOutsideItsParent() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreationOutsideItsParent");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(100, 100);
        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root")
                .nodes()
                    .rectangleNode("A").at(10, 10).of(200, 300).customizedProperties(Set.of(CustomizableProperties.Size)).and()
                    .rectangleNode("B").at(500, 500).of(200, 300).customizedProperties(Set.of(CustomizableProperties.Size)).and()
                .and()
            .build();
        // @formatter:on

        // we create a child of B by clicking inside of A
        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        Optional<Node> optionalA = this.getNode(layoutedDiagram.getNodes(), "A");
        assertThat(optionalA).isPresent();
        Optional<Node> optionalB = this.getNode(layoutedDiagram.getNodes(), "B");
        assertThat(optionalB).isPresent();
        Optional<Node> optionalChild = this.getNode(layoutedDiagram.getNodes(), "Child");
        assertThat(optionalChild).isPresent();

        // A should not have been resized
        Node aNode = optionalA.get();
        assertThat(aNode.getPosition()).isEqualTo(Position.at(10, 10));
        assertThat(aNode.getSize()).isEqualTo(Size.of(200, 300));

        Node bNode = optionalB.get();
        // Child should be within B
        assertThat(bNode).hasEveryChildWithinItsBounds();
        // B should not haven been resized
        assertThat(bNode.getPosition()).isEqualTo(Position.at(500, 500));
        assertThat(bNode.getSize()).isEqualTo(Size.of(200, 300));
    }

    @Test
    public void testChildlessNodeCreation() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testChildlessNodeCreation");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(100, 100);
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root").nodes().and().build();

        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        // parent node should still be positioned at the eventCreationPosition
        Optional<Node> optionalParent = this.getNode(layoutedDiagram.getNodes(), "Parent");
        assertThat(optionalParent).isPresent();
        Node parent = optionalParent.get();
        Position parentPosition = parent.getPosition();
        assertThat(parentPosition).isEqualTo(Position.at(100, 100));
    }

    @Test
    public void testMultiNodeCreation() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreation");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(100, 100);
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root").nodes().and().build();

        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        // parent node should still be positioned at the eventCreationPosition
        Optional<Node> optionalParent = this.getNode(layoutedDiagram.getNodes(), "Parent");
        assertThat(optionalParent).isPresent();
        Node parent = optionalParent.get();
        Position parentPosition = parent.getPosition();
        assertThat(parentPosition).isEqualTo(Position.at(100, 100));

        // since the parent was not positioned yet, its children should be at a default position
        Optional<Node> optionalChild = this.getNode(layoutedDiagram.getNodes(), "Child");
        assertThat(optionalChild).isPresent();
        Node child = optionalChild.get();
        assertThat(child.getPosition()).isEqualTo(this.getDefaultPosition(parent, child));
    }

    /**
     * The is the exact same method than {@link NodePositionProvider} except that it is for {@link Node}.
     *
     * @param node
     *            The node
     * @return its default position
     */
    private Position getDefaultPosition(Node parent, Node child) {
        double defaultLabelPadding = LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING;
        double defaultYPosition = LayoutOptionValues.DEFAULT_ELK_PADDING;
        double labelPaddings = 0;
        if (this.isLabelOfType(parent, "inside")) {
            // We consider here the label is also on TOP of the node
            double parentLabelHeight = parent.getInsideLabel().getSize().getHeight();
            labelPaddings += defaultLabelPadding + parentLabelHeight + defaultYPosition;
        }
        if (this.isLabelOfType(child, "outside")) {
            double nodeLabelHeight = child.getInsideLabel().getSize().getHeight();
            labelPaddings += nodeLabelHeight + defaultLabelPadding;
        }
        double yPosition = Math.max(labelPaddings, defaultYPosition);

        return Position.at(LayoutOptionValues.DEFAULT_ELK_PADDING, yPosition);
    }

    private boolean isLabelOfType(Node node, String labelType) {
        InsideLabel insideLabel = node.getInsideLabel();
        if (insideLabel != null) {
            return insideLabel.getType().contains(labelType);
        }
        return false;
    }

    private Diagram createParentNotResizedDiagram() {
        // @formatter:off
        return TestLayoutDiagramBuilder.diagram("Root")
                .nodes()
                    .rectangleNode("Parent").at(10, 10).of(200, 200)
                    .and()
                .and()
            .build();
        // @formatter:on
    }

    private void testThatChildIsInParentBounds(Diagram diagram) {
        Optional<Node> optionalParent = this.getNode(diagram.getNodes(), "Parent");
        assertThat(optionalParent).isPresent();
        Optional<Node> optionalChild = this.getNode(diagram.getNodes(), "Child");
        assertThat(optionalChild).isPresent();

        Node layoutedParent = optionalParent.get();
        assertThat(layoutedParent).hasEveryChildWithinItsBounds();
    }

    private Diagram createParentResizedDiagram() {
        // @formatter:off
        return TestLayoutDiagramBuilder.diagram("Root")
                .nodes()
                    .rectangleNode("Parent").at(10, 10).of(200, 200).customizedProperties(Set.of(CustomizableProperties.Size))
                    .and()
                .and()
            .build();
        // @formatter:on
    }

    private Diagram createNewNode(Diagram diagram, IEditingContext editingContext, Position eventCreationPosition) {
        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        IDiagramEvent diagramEvent = new SinglePositionEvent(diagram.getId(), eventCreationPosition);

        Optional<Diagram> optionalRefreshedDiagram = diagramCreationService.performRefresh(editingContext, diagram, diagramEvent);
        assertThat(optionalRefreshedDiagram).isNotEmpty();
        Diagram refreshedDiagram = optionalRefreshedDiagram.get();

        return diagramCreationService.performLayout(editingContext, refreshedDiagram, diagramEvent);
    }

    private TestDiagramCreationService createDiagramCreationService(Diagram diagram) {
        IRepresentationDescriptionSearchService.NoOp representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                DiagramDescription diagramDescription = NodeCreationTests.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);
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
