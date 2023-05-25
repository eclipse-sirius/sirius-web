/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import static org.eclipse.sirius.components.diagrams.tests.DiagramAssertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.elk.graph.ElkBendPoint;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkEdgeSection;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.layout.ELKConvertedDiagram;
import org.eclipse.sirius.components.diagrams.layout.ELKLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.ELKPropertiesService;
import org.eclipse.sirius.components.diagrams.layout.SiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.tests.IdPolicy;
import org.eclipse.sirius.components.diagrams.tests.LayoutPolicy;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the layouted diagram provider.
 *
 * @author sbegaudeau
 */
public class LayoutedDiagramProviderTests {

    private static final double NODE_WIDTH = 100;

    private static final double NODE_HEIGHT = 50;

    private static final double NODE_X = 500;

    private static final double NODE_Y = 900;

    private static final double LABEL_WIDTH = 50;

    private static final double LABEL_HEIGHT = 10;

    private static final double LABEL_X = 550;

    private static final double LABEL_Y = 940;

    private static final double EDGE_BENDPOINT_X = 300;

    private static final double EDGE_BENDPOINT_Y = 600;

    private static final String DIAGRAM_ID = UUID.randomUUID().toString();

    private static final String FIRST_NODE_ID = UUID.randomUUID().toString();

    private static final String FIRST_EDGE_ID = UUID.randomUUID().toString();

    private ELKPropertiesService elkPropertiesService = new ELKPropertiesService();

    @BeforeAll
    public static void initTest() {
        LayoutMetaDataService.getInstance().registerLayoutMetaDataProviders(new LayeredOptions());
    }

    @Test
    public void testLayoutedDiagramProvider() {
        // @formatter:off
        TestDiagramBuilder diagramBuilder = new TestDiagramBuilder();

        Node node = Node.newNode(diagramBuilder.getNode(FIRST_NODE_ID, true))
                .build();

        Edge edge = Edge.newEdge(diagramBuilder.getEdge(FIRST_EDGE_ID, node.getId(), node.getId()))
                .build();

        Diagram originalDiagram = Diagram.newDiagram(diagramBuilder.getDiagram(DIAGRAM_ID))
                .nodes(List.of(node))
                .edges(List.of(edge))
                .build();
        // @formatter:on

        ELKConvertedDiagram convertedDiagram = this.getConvertedDiagram(originalDiagram);

        Diagram layoutedDiagram = new ELKLayoutedDiagramProvider(List.of(), this.elkPropertiesService).getLayoutedDiagram(originalDiagram, convertedDiagram.getElkDiagram(),
                convertedDiagram.getId2ElkGraphElements(), new SiriusWebLayoutConfigurator());
        assertThat(layoutedDiagram).hasBounds(0, 0, 0, 0);
        assertThat(layoutedDiagram.getNodes()).hasSizeGreaterThan(0);
        assertThat(layoutedDiagram.getNodes().get(0)).hasBounds(NODE_X, NODE_Y, NODE_WIDTH, NODE_HEIGHT);
        assertThat(layoutedDiagram).matchesRecursively(originalDiagram, IdPolicy.WITH_ID, LayoutPolicy.WITHOUT_LAYOUT);
    }

    private ELKConvertedDiagram getConvertedDiagram(Diagram originalDiagram) {
        Map<String, ElkGraphElement> id2ElkGraphElements = new HashMap<>();

        ElkNode elkDiagram = ElkGraphFactory.eINSTANCE.createElkNode();
        elkDiagram.setIdentifier(originalDiagram.getId().toString());

        Node node = originalDiagram.getNodes().get(0);
        ElkNode elkNode = ElkGraphFactory.eINSTANCE.createElkNode();
        elkNode.setIdentifier(node.getId().toString());
        elkNode.setDimensions(NODE_WIDTH, NODE_HEIGHT);
        elkNode.setX(NODE_X);
        elkNode.setY(NODE_Y);
        elkNode.setParent(elkDiagram);

        ElkLabel elkLabel = ElkGraphFactory.eINSTANCE.createElkLabel();
        elkLabel.setIdentifier(node.getInsideLabel().getId().toString());
        elkLabel.setDimensions(LABEL_WIDTH, LABEL_HEIGHT);
        elkLabel.setX(LABEL_X);
        elkLabel.setY(LABEL_Y);
        elkLabel.setParent(elkNode);
        id2ElkGraphElements.put(elkLabel.getIdentifier(), elkLabel);

        id2ElkGraphElements.put(elkNode.getIdentifier(), elkNode);

        String edgeId = originalDiagram.getEdges().get(0).getId();

        ElkEdge elkEdge = ElkGraphFactory.eINSTANCE.createElkEdge();
        elkEdge.setIdentifier(edgeId.toString());
        elkEdge.getSources().add(elkNode);
        elkEdge.getTargets().add(elkNode);
        elkEdge.setContainingNode(elkDiagram);

        ElkEdgeSection section = ElkGraphFactory.eINSTANCE.createElkEdgeSection();

        section.setStartX(NODE_X);
        section.setStartY(NODE_Y);

        ElkBendPoint bendPoint = ElkGraphFactory.eINSTANCE.createElkBendPoint();
        bendPoint.setX(EDGE_BENDPOINT_X);
        bendPoint.setY(EDGE_BENDPOINT_Y);
        section.getBendPoints().add(bendPoint);

        section.setEndX(NODE_X);
        section.setEndY(NODE_Y);

        elkEdge.getSections().add(section);

        id2ElkGraphElements.put(elkEdge.getIdentifier(), elkEdge);

        return new ELKConvertedDiagram(elkDiagram, id2ElkGraphElements);
    }

}
