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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
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
 * Used to test diagram layout.
 *
 * @author gcoutable
 */
public class DiagramLayoutTest {

    private TestLayoutObjectService objectService = new TestLayoutObjectService();

    private DefaultTestDiagramDescriptionProvider defaultTestDiagramDescriptionProvider = new DefaultTestDiagramDescriptionProvider(this.objectService);

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
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, UUID representationDescriptionId) {
                DiagramDescription diagramDescription = DiagramLayoutTest.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);
                return Optional.of(diagramDescription);
            }
        };

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider);

        LayoutService layoutService = new LayoutService(new IELKDiagramConverter.NoOp(), new IncrementalLayoutDiagramConverter(), new LayoutConfiguratorRegistry(List.of()),
                new ELKLayoutedDiagramProvider(), new IncrementalLayoutedDiagramProvider(), representationDescriptionSearchService, incrementalLayoutEngine);

        return new TestDiagramCreationService(this.objectService, representationDescriptionSearchService, layoutService);
    }

    @Test
    public void testSimpleDiagramLayout() throws IOException {
        String firstParentTargetObjectId = "First Parent"; //$NON-NLS-1$
        String secondParentTargetObjectId = "Second Parent"; //$NON-NLS-1$
        String firstChildTargetObjectId = "First child"; //$NON-NLS-1$
        String secondChildTargetObjectId = "Second child"; //$NON-NLS-1$

        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root") //$NON-NLS-1$
            .nodes()
                .rectangleNode(firstParentTargetObjectId).at(10, 10).of(200, 300)
                    .childNodes()
                        .rectangleNode(firstChildTargetObjectId).at(10, 10).of(50, 50).and()
                        .rectangleNode(secondChildTargetObjectId).at(70, 70).of(50, 50).and()
                        .and()
                    .and()
                .rectangleNode(secondParentTargetObjectId).at(300, 400).of(100, 100).and()
                .and()
            .edge("Link") //$NON-NLS-1$
                .from(firstParentTargetObjectId).at(0.5, 0.5)
                .to(secondParentTargetObjectId).at(0.8, 0.8)
                .and()
            .edge("Opposite Link") //$NON-NLS-1$
                .from(secondParentTargetObjectId).at(0.2, 0.7)
                .to(firstParentTargetObjectId).at(0.1, 0.9)
            .and()
        .build();
        // @formatter:on

        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testSimpleDiagramLayout"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$ //$NON-NLS-5$
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);

        Optional<Diagram> optionalRefreshedDiagram = diagramCreationService.performRefresh(editingContext, diagram);
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

        Optional<Node> optionalThirdParent = this.getNode(refreshedDiagram.getNodes(), "Third Parent"); //$NON-NLS-1$
        assertThat(optionalThirdParent).isPresent();
        Node thirdParent = optionalThirdParent.get();
        assertThat(thirdParent.getPosition()).isEqualTo(Position.UNDEFINED);

        IDiagramEvent diagramEvent = new SinglePositionEvent(Position.at(300, 100));
        Diagram layoutedDiagram = diagramCreationService.performLayout(editingContext, refreshedDiagram, diagramEvent);

        Optional<Node> optionalLayoutedThirdParent = this.getNode(layoutedDiagram.getNodes(), "Third Parent"); //$NON-NLS-1$
        assertThat(optionalLayoutedThirdParent).isPresent();
        Node layoutedThirdParent = optionalLayoutedThirdParent.get();
        assertThat(layoutedThirdParent.getPosition()).isEqualTo(Position.at(300, 100));
    }
}
