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
package org.eclipse.sirius.components.diagrams.layout.services;

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
import org.eclipse.sirius.components.diagrams.layout.ELKDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.ELKLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.LayoutService;
import org.eclipse.sirius.components.diagrams.layout.TextBoundsService;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutEngine;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.eclipse.sirius.components.diagrams.tests.builder.JsonBasedEditingContext;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Test;

/**
 * Used to test the diagram full layout.
 *
 * @author lfasani
 */
public class DiagramELKLayoutTest {

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
                DiagramDescription diagramDescription = DiagramELKLayoutTest.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);
                return Optional.of(diagramDescription);
            }
        };

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider);

        LayoutService layoutService = new LayoutService(new ELKDiagramConverter(new TextBoundsService(), new ImageSizeProvider()), new IncrementalLayoutDiagramConverter(),
                new LayoutConfiguratorRegistry(List.of()), new ELKLayoutedDiagramProvider(), new IncrementalLayoutedDiagramProvider(), representationDescriptionSearchService, incrementalLayoutEngine);

        return new TestDiagramCreationService(this.objectService, representationDescriptionSearchService, layoutService);
    }

    @Test
    public void testNodeLayoutWithMultilineLabel() throws IOException {
        String nodeLabelWithMultiple = "First LineAAAAAAAA\nSecond LineBBBBBBBBB"; //$NON-NLS-1$
        String firstChildTargetObjectId = "First child"; //$NON-NLS-1$

        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root") //$NON-NLS-1$
            .nodes()
                .rectangleNode(nodeLabelWithMultiple).at(10, 10).of(10, 10)
                    .childNodes()
                        .rectangleNode(firstChildTargetObjectId).at(10, 10).of(50, 50).and()
                    .and()
                .and()
            .and()
        .build();
        // @formatter:on

        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeLayoutWithMultilineLabel"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$ //$NON-NLS-5$
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);

        Diagram layoutedDiagram = diagramCreationService.performElKLayout(editingContext, diagram);

        Node firstParent = layoutedDiagram.getNodes().get(0);

        // Check that the parent node and the label have the right size
        assertThat(firstParent.getSize()).isEqualTo(Size.of(195.8818359375, 131.197265625));
        assertThat(firstParent.getLabel().getSize()).isEqualTo(Size.of(161.8818359375, 32.197265625));

        // Check that the inner node is under the multi line label area
        assertThat(firstParent.getChildNodes().get(0).getPosition()).isEqualTo(Position.at(12, 49.197265625));
    }
}
