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
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.CustomizableProperties;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.NodeCreationEvent;
import org.eclipse.sirius.components.diagrams.layout.ELKLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.IELKDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.LayoutService;
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
        Position eventCreationPosition = Position.at(10, 10);
        Diagram diagram = this.createParentNotResizedDiagram();
        this.createNewNode(diagram, eventCreationPosition);
    }

    @Test
    public void testNodeCreationWithResizeNeeded() {
        Position eventCreationPosition = Position.at(199, 199);
        Diagram diagram = this.createParentNotResizedDiagram();
        this.createNewNode(diagram, eventCreationPosition);
    }

    @Test
    public void testNodeCreationInResizedParentNoResizeNeeded() {
        Position eventCreationPosition = Position.at(10, 10);
        Diagram diagram = this.createParentResizedDiagram();
        this.createNewNode(diagram, eventCreationPosition);
    }

    @Test
    public void testNodeCreationInResizedParentWithResizeNeeded() {
        Position eventCreationPosition = Position.at(199, 199);
        Diagram diagram = this.createParentResizedDiagram();
        this.createNewNode(diagram, eventCreationPosition);
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

    private void createNewNode(Diagram diagram, Position eventCreationPosition) {
        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeCreation"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$ //$NON-NLS-5$
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);

        Optional<Diagram> optionalRefreshedDiagram = diagramCreationService.performRefresh(editingContext, diagram);
        assertThat(optionalRefreshedDiagram).isNotEmpty();
        Diagram refreshedDiagram = optionalRefreshedDiagram.get();

        IDiagramEvent diagramEvent = new NodeCreationEvent(eventCreationPosition);
        Diagram layoutedDiagram = diagramCreationService.performLayout(editingContext, refreshedDiagram, diagramEvent);

        Optional<Node> optionalParent = this.getNode(layoutedDiagram.getNodes(), "Parent"); //$NON-NLS-1$
        assertThat(optionalParent).isPresent();
        Optional<Node> optionalChild = this.getNode(layoutedDiagram.getNodes(), "Child"); //$NON-NLS-1$
        assertThat(optionalChild).isPresent();

        Node layoutedParent = optionalParent.get();
        assertThat(layoutedParent).hasEveryChildWithinItsBounds();
    }

    private TestDiagramCreationService createDiagramCreationService(Diagram diagram) {
        IRepresentationDescriptionSearchService.NoOp representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, UUID representationDescriptionId) {
                DiagramDescription diagramDescription = NodeCreationTests.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);
                return Optional.of(diagramDescription);
            }
        };

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider);

        LayoutService layoutService = new LayoutService(new IELKDiagramConverter.NoOp(), new IncrementalLayoutDiagramConverter(), new LayoutConfiguratorRegistry(List.of()),
                new ELKLayoutedDiagramProvider(), new IncrementalLayoutedDiagramProvider(), representationDescriptionSearchService, incrementalLayoutEngine);

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
