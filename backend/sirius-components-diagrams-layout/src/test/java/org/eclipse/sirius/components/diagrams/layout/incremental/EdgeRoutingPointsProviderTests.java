/*******************************************************************************
 * Copyright (c) 2021, 2022 THALES GLOBAL SERVICES.
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

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.EdgeRoutingPointsProvider;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link EdgeRoutingPointsProvider}.
 *
 * @author fbarbin
 */
public class EdgeRoutingPointsProviderTests {

    @Test
    public void testEdgeRoutingPoints() {
        EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        EdgeLayoutData edgeLayoutData = this.createEdgeLayoutData(diagramLayoutData);
        List<Position> routingPoints = edgeRoutingPointsProvider.getRoutingPoints(edgeLayoutData);
        assertThat(routingPoints).hasSize(0);

        assertThat(edgeLayoutData.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(0.5, 0.5));
        assertThat(edgeLayoutData.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(0.5, 0.5));
    }

    @Test
    public void testSelfLoopEdgeRoutingPoints() {
        EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        EdgeLayoutData edgeLayoutData = this.createSelfLoopEdgeLayoutData(diagramLayoutData);
        List<Position> routingPoints = edgeRoutingPointsProvider.getRoutingPoints(edgeLayoutData);
        assertThat(routingPoints).hasSize(2);

        Position firstRoutingPoint = routingPoints.get(0);
        assertThat(firstRoutingPoint).extracting(Position::getX).isEqualTo(100.0 / 3.0);
        assertThat(firstRoutingPoint).extracting(Position::getY).isEqualTo(-10.0);

        Position secondRoutingPoint = routingPoints.get(1);
        assertThat(secondRoutingPoint).extracting(Position::getX).isEqualTo(200.0 / 3.0);
        assertThat(secondRoutingPoint).extracting(Position::getY).isEqualTo(-10.0);

        assertThat(edgeLayoutData.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(0.5, 0.5));
        assertThat(edgeLayoutData.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(0.5, 0.5));
    }

    private DiagramLayoutData createDiagramLayoutData() {
        DiagramLayoutData diagramLayoutData = new DiagramLayoutData();
        diagramLayoutData.setId(UUID.randomUUID().toString());
        diagramLayoutData.setPosition(Position.at(0, 0));
        diagramLayoutData.setSize(Size.of(1000, 1000));

        return diagramLayoutData;
    }

    private EdgeLayoutData createSelfLoopEdgeLayoutData(DiagramLayoutData diagramLayoutData) {
        EdgeLayoutData edgeLayoutData = new EdgeLayoutData();
        edgeLayoutData.setId(UUID.randomUUID().toString());
        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.at(0, 0), Size.of(100, 50), diagramLayoutData);
        edgeLayoutData.setSource(nodeLayoutData);
        edgeLayoutData.setTarget(nodeLayoutData);
        return edgeLayoutData;
    }

    private EdgeLayoutData createEdgeLayoutData(DiagramLayoutData diagramLayoutData) {
        EdgeLayoutData edgeLayoutData = new EdgeLayoutData();
        edgeLayoutData.setId(UUID.randomUUID().toString());
        edgeLayoutData.setSource(this.createNodeLayoutData(Position.at(0, 0), Size.of(100, 50), diagramLayoutData));
        edgeLayoutData.setTarget(this.createNodeLayoutData(Position.at(200, 200), Size.of(100, 50), diagramLayoutData));
        return edgeLayoutData;
    }

    private NodeLayoutData createNodeLayoutData(Position position, Size size, IContainerLayoutData parent) {
        NodeLayoutData nodeLayoutData = new NodeLayoutData();
        nodeLayoutData.setId(UUID.randomUUID().toString());
        nodeLayoutData.setParent(parent);
        nodeLayoutData.setPosition(position);
        nodeLayoutData.setSize(size);
        return nodeLayoutData;
    }

}
