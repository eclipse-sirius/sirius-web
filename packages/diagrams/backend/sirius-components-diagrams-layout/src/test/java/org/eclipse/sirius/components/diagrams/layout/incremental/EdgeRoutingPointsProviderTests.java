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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.UpdateEdgeRoutingPointsEvent;
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
        List<Position> routingPoints = edgeRoutingPointsProvider.getRoutingPoints(Optional.empty(), Optional.empty(), edgeLayoutData);
        assertThat(routingPoints).hasSize(0);

        assertThat(edgeLayoutData.getSourceAnchorRelativePosition()).isEqualTo(Ratio.of(0.5, 0.5));
        assertThat(edgeLayoutData.getTargetAnchorRelativePosition()).isEqualTo(Ratio.of(0.5, 0.5));
    }

    @Test
    public void testSelfLoopEdgeRoutingPoints() {
        EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        EdgeLayoutData edgeLayoutData = this.createSelfLoopEdgeLayoutData(diagramLayoutData, List.of());
        List<Position> routingPoints = edgeRoutingPointsProvider.getRoutingPoints(Optional.empty(), Optional.empty(), edgeLayoutData);
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

    @Test
    public void testSelfLoopEdgeRoutingPointsFollowTheMove() {
        EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        // @formatter:off
        List<Position> routingPoints = List.of(
            Position.at(100.0 / 3.0, -10),
            Position.at(200.0 / 3.0, -10)
        );
        // @formatter:on
        EdgeLayoutData edgeLayoutData = this.createSelfLoopEdgeLayoutData(diagramLayoutData, routingPoints);

        // The node has been moved
        Position newNodePosition = Position.at(0, 10);
        MoveEvent moveEvent = new MoveEvent(edgeLayoutData.getSource().getId(), newNodePosition);
        edgeLayoutData.getSource().setPosition(newNodePosition);

        List<Position> newRoutingPoints = edgeRoutingPointsProvider.getRoutingPoints(Optional.of(moveEvent), Optional.empty(), edgeLayoutData);
        assertThat(newRoutingPoints).hasSize(2);

        Position firstRoutingPoint = newRoutingPoints.get(0);
        assertThat(firstRoutingPoint).extracting(Position::getX).isEqualTo(routingPoints.get(0).getX());
        assertThat(firstRoutingPoint).extracting(Position::getY).isEqualTo(0d);

        Position secondRoutingPoint = newRoutingPoints.get(1);
        assertThat(secondRoutingPoint).extracting(Position::getX).isEqualTo(routingPoints.get(1).getX());
        assertThat(secondRoutingPoint).extracting(Position::getY).isEqualTo(0d);
    }

    @Test
    public void testUpdateEdgeRoutingPoints() {
        EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        EdgeLayoutData edgeLayoutData = this.createEdgeLayoutData(diagramLayoutData);
        List<Position> newEdgeRoutingPoints = List.of(Position.at(100, 100));
        UpdateEdgeRoutingPointsEvent edgeRoutingPointsEvent = new UpdateEdgeRoutingPointsEvent(edgeLayoutData.getId(), newEdgeRoutingPoints);

        List<Position> updatedRoutingPoints = edgeRoutingPointsProvider.getRoutingPoints(Optional.of(edgeRoutingPointsEvent), Optional.empty(), edgeLayoutData);
        assertThat(updatedRoutingPoints).hasSize(1);

        Position routingPoint = updatedRoutingPoints.get(0);
        assertThat(routingPoint).extracting(Position::getX).isEqualTo(100.0);
        assertThat(routingPoint).extracting(Position::getY).isEqualTo(100.0);
    }

    private DiagramLayoutData createDiagramLayoutData() {
        DiagramLayoutData diagramLayoutData = new DiagramLayoutData();
        diagramLayoutData.setId(UUID.randomUUID().toString());
        diagramLayoutData.setPosition(Position.at(0, 0));
        diagramLayoutData.setSize(Size.of(1000, 1000));

        return diagramLayoutData;
    }

    private EdgeLayoutData createSelfLoopEdgeLayoutData(DiagramLayoutData diagramLayoutData, List<Position> routingPoints) {
        EdgeLayoutData edgeLayoutData = new EdgeLayoutData();
        edgeLayoutData.setId(UUID.randomUUID().toString());
        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.at(0, 0), Size.of(100, 50), diagramLayoutData);
        edgeLayoutData.setSource(nodeLayoutData);
        edgeLayoutData.setTarget(nodeLayoutData);
        edgeLayoutData.setRoutingPoints(routingPoints);
        return edgeLayoutData;
    }

    private EdgeLayoutData createEdgeLayoutData(DiagramLayoutData diagramLayoutData) {
        EdgeLayoutData edgeLayoutData = new EdgeLayoutData();
        edgeLayoutData.setId(UUID.randomUUID().toString());
        edgeLayoutData.setSource(this.createNodeLayoutData(Position.at(0, 0), Size.of(100, 50), diagramLayoutData));
        edgeLayoutData.setTarget(this.createNodeLayoutData(Position.at(200, 200), Size.of(100, 50), diagramLayoutData));
        edgeLayoutData.setRoutingPoints(List.of());
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
