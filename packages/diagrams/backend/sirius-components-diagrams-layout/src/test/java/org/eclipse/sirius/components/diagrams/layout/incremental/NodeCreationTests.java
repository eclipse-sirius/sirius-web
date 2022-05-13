/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.layout.ELKLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.IELKDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.LayoutService;
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

    @Test
    public void testNodeCreationNoResizeNeeded() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreation"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$ //$NON-NLS-5$
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(10, 10);
        Diagram diagram = this.createParentNotResizedDiagram();
        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        this.testThatChildIsInParentBounds(layoutedDiagram);
    }

    @Test
    public void testNodeCreationWithResizeNeeded() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreation"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$ //$NON-NLS-5$
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(200, 200);
        Diagram diagram = this.createParentNotResizedDiagram();
        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        this.testThatChildIsInParentBounds(layoutedDiagram);
    }

    @Test
    public void testNodeCreationInResizedParentNoResizeNeeded() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreation"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$ //$NON-NLS-5$
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(10, 10);
        Diagram diagram = this.createParentResizedDiagram();
        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        this.testThatChildIsInParentBounds(layoutedDiagram);
    }

    @Test
    public void testNodeCreationInResizedParentWithResizeNeeded() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreation"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$ //$NON-NLS-5$
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(200, 200);
        Diagram diagram = this.createParentResizedDiagram();
        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        this.testThatChildIsInParentBounds(layoutedDiagram);
    }

    @Test
    public void testNodeCreationOutsideItsParent() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreationOutsideItsParent"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$ //$NON-NLS-5$
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(100, 100);
        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root") //$NON-NLS-1$
                .nodes()
                    .rectangleNode("A").at(10, 10).of(200, 300).and() //$NON-NLS-1$
                    .rectangleNode("B").at(500, 500).of(200, 300).and() //$NON-NLS-1$
                .and()
            .build();
        // @formatter:on

        // we create a child of B by clicking inside of A
        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        Optional<Node> optionalA = this.getNode(layoutedDiagram.getNodes(), "A"); //$NON-NLS-1$
        assertThat(optionalA).isPresent();
        Optional<Node> optionalB = this.getNode(layoutedDiagram.getNodes(), "B"); //$NON-NLS-1$
        assertThat(optionalB).isPresent();
        Optional<Node> optionalChild = this.getNode(layoutedDiagram.getNodes(), "Child"); //$NON-NLS-1$
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
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testChildlessNodeCreation"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$ //$NON-NLS-5$
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(100, 100);
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root").nodes().and().build(); //$NON-NLS-1$

        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        // parent node should still be positioned at the eventCreationPosition
        Optional<Node> optionalParent = this.getNode(layoutedDiagram.getNodes(), "Parent"); //$NON-NLS-1$
        assertThat(optionalParent).isPresent();
        Node parent = optionalParent.get();
        Position parentPosition = parent.getPosition();
        assertThat(parentPosition).isEqualTo(Position.at(100, 100));
    }

    @Test
    public void testMultiNodeCreation() {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreation"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$ //$NON-NLS-5$
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        Position eventCreationPosition = Position.at(100, 100);
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root").nodes().and().build(); //$NON-NLS-1$

        Diagram layoutedDiagram = this.createNewNode(diagram, editingContext, eventCreationPosition);

        // parent node should still be positioned at the eventCreationPosition
        Optional<Node> optionalParent = this.getNode(layoutedDiagram.getNodes(), "Parent"); //$NON-NLS-1$
        assertThat(optionalParent).isPresent();
        Node parent = optionalParent.get();
        Position parentPosition = parent.getPosition();
        assertThat(parentPosition).isEqualTo(Position.at(100, 100));

        // since the parent was not positioned yet, its children should be at a default position
        Optional<Node> optionalChild = this.getNode(layoutedDiagram.getNodes(), "Child"); //$NON-NLS-1$
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
        double defaultLabelPadding = 2 * LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING;
        double defaultYPosition = LayoutOptionValues.DEFAULT_ELK_PADDING;
        double labelPaddings = 0;
        if (this.isLabelOfType(parent, "inside")) { //$NON-NLS-1$
            double parentLabelHeight = parent.getLabel().getSize().getHeight();
            labelPaddings += parentLabelHeight + defaultLabelPadding;
        }
        if (this.isLabelOfType(child, "outside")) { //$NON-NLS-1$
            double nodeLabelHeight = child.getLabel().getSize().getHeight();
            labelPaddings += nodeLabelHeight + defaultLabelPadding;
        }
        double yPosition = Math.max(labelPaddings, defaultYPosition);

        return Position.at(LayoutOptionValues.DEFAULT_ELK_PADDING, yPosition);
    }

    private boolean isLabelOfType(Node node, String labelType) {
        Label label = node.getLabel();
        if (label != null) {
            return label.getType().contains(labelType);
        }
        return false;
    }

    private Diagram createParentNotResizedDiagram() {
        // @formatter:off
        return TestLayoutDiagramBuilder.diagram("Root") //$NON-NLS-1$
                .nodes()
                    .rectangleNode("Parent").at(10, 10).of(200, 200) //$NON-NLS-1$
                    .and()
                .and()
            .build();
        // @formatter:on
    }

    private void testThatChildIsInParentBounds(Diagram diagram) {
        Optional<Node> optionalParent = this.getNode(diagram.getNodes(), "Parent"); //$NON-NLS-1$
        assertThat(optionalParent).isPresent();
        Optional<Node> optionalChild = this.getNode(diagram.getNodes(), "Child"); //$NON-NLS-1$
        assertThat(optionalChild).isPresent();

        Node layoutedParent = optionalParent.get();
        assertThat(layoutedParent).hasEveryChildWithinItsBounds();
    }

    private Diagram createParentResizedDiagram() {
        // @formatter:off
        return TestLayoutDiagramBuilder.diagram("Root") //$NON-NLS-1$
                .nodes()
                    .rectangleNode("Parent").at(10, 10).of(200, 200).customizedProperties(Set.of(CustomizableProperties.Size)) //$NON-NLS-1$
                    .and()
                .and()
            .build();
        // @formatter:on
    }

    private Diagram createNewNode(Diagram diagram, IEditingContext editingContext, Position eventCreationPosition) {
        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        IDiagramEvent diagramEvent = new SinglePositionEvent(eventCreationPosition);

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

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider, List.of());

        LayoutService layoutService = new LayoutService(new IELKDiagramConverter.NoOp(), new IncrementalLayoutDiagramConverter(), new LayoutConfiguratorRegistry(List.of()),
                new ELKLayoutedDiagramProvider(List.of()), new IncrementalLayoutedDiagramProvider(), representationDescriptionSearchService, incrementalLayoutEngine);

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
