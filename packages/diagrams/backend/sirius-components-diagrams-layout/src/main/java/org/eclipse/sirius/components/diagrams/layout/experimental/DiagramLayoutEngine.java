/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.experimental;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.layout.api.IDiagramLayoutConfigurationProvider;
import org.eclipse.sirius.components.diagrams.layout.api.IDiagramLayoutEngine;
import org.eclipse.sirius.components.diagrams.layout.api.configuration.DiagramLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.springframework.stereotype.Service;

/**
 * The new layout engine.
 *
 * @author pcdavid
 */
@Service
public class DiagramLayoutEngine implements IDiagramLayoutEngine {

    private final IDiagramLayoutConfigurationProvider diagramLayoutConfigurationProvider;

    public DiagramLayoutEngine() {
        this.diagramLayoutConfigurationProvider = new IDiagramLayoutConfigurationProvider.NoOp();
    }

    @Override
    public DiagramLayoutData layout(IEditingContext editingContext, Diagram diagram, DiagramLayoutData previousLayoutData, Optional<IDiagramEvent> optionalDiagramEvent) {
        DiagramLayoutConfiguration diagramLayoutConfiguration = this.diagramLayoutConfigurationProvider.getDiagramLayoutConfiguration(editingContext, diagram);
        return this.doLayout(diagram, diagramLayoutConfiguration, previousLayoutData, optionalDiagramEvent);
    }

    private DiagramLayoutData doLayout(Diagram diagram, DiagramLayoutConfiguration diagramLayoutConfiguration, DiagramLayoutData previousLayoutData, Optional<IDiagramEvent> optionalDiagramEvent) {
        Map<String, NodeLayoutData> nodeLayoutData = new HashMap<>();
        this.layoutNodes(diagram.getNodes(), previousLayoutData, nodeLayoutData);
        this.applyEvent(optionalDiagramEvent, nodeLayoutData);
        return new DiagramLayoutData(nodeLayoutData, previousLayoutData.edgeLayoutData(), previousLayoutData.labelLayoutData());
    }

    private void applyEvent(Optional<IDiagramEvent> optionalDiagramEvent, Map<String, NodeLayoutData> nodeLayoutData) {
        optionalDiagramEvent.filter(MoveEvent.class::isInstance).map(MoveEvent.class::cast).ifPresent(moveEvent -> {
            if (nodeLayoutData.containsKey(moveEvent.nodeId())) {
                Rectangle newBox = this.toRectangle(nodeLayoutData.get(moveEvent.nodeId())).moveTo(moveEvent.newPosition().getX(), moveEvent.newPosition().getY());
                nodeLayoutData.put(moveEvent.nodeId(), this.toNodeLayoutData(newBox, moveEvent.nodeId()));
            }
        });

        optionalDiagramEvent.filter(ResizeEvent.class::isInstance).map(ResizeEvent.class::cast).ifPresent(resizeEvent -> {
            if (nodeLayoutData.containsKey(resizeEvent.nodeId())) {
                Rectangle newBox = this.toRectangle(nodeLayoutData.get(resizeEvent.nodeId())).translate(-resizeEvent.positionDelta().getX(), -resizeEvent.positionDelta().getY())
                        .resize(resizeEvent.newSize().getWidth(), resizeEvent.newSize().getHeight());
                nodeLayoutData.put(resizeEvent.nodeId(), this.toNodeLayoutData(newBox, resizeEvent.nodeId()));
            }
        });

    }
    public NodeLayoutData toNodeLayoutData(Rectangle rect, String nodeId) {
        return new NodeLayoutData(nodeId, rect.topLeft(), rect.size());
    }

    private Rectangle toRectangle(NodeLayoutData nodeLayoutData) {
        Position position = nodeLayoutData.position();
        Size size = nodeLayoutData.size();
        return new Rectangle(position.x(), position.y(), size.width(), size.height());
    }

    private void layoutNodes(List<Node> nodes, DiagramLayoutData previousLayoutData, Map<String, NodeLayoutData> nodeLayoutData) {
        // Distribute the top-level nodes horizontally from (0,0)
        Position nextChildPosition = new Position(0.0, 0.0);
        for (Node child : nodes) {
            if (child.getState() == ViewModifier.Hidden) {
                continue;
            }
            NodeBox childBox = this.layoutContents(child).moveTo(nextChildPosition);
            nextChildPosition = childBox.getFootprint().topRight();
            nextChildPosition = new Position(nextChildPosition.x(), nextChildPosition.y() + childBox.getMargin().top());
            this.fillLayoutData(childBox, nodeLayoutData, previousLayoutData);
        }
    }

    private void fillLayoutData(NodeBox box, Map<String, NodeLayoutData> nodeLayoutData, DiagramLayoutData previousLayoutData) {
        NodeLayoutData nodeLayout = previousLayoutData.nodeLayoutData().getOrDefault(box.getNodeId(), box.toNodeLayoutData());
        nodeLayoutData.put(box.getNodeId(), nodeLayout);
        for (NodeBox childBox : box.getSubNodeBoxes().values()) {
            this.fillLayoutData(childBox, nodeLayoutData, previousLayoutData);
        }
    }

    private NodeBox layoutContents(Node node) {
        NodeBox box = new NodeBox(node, new Rectangle(0.0, 0.0, 150.0, 70.0));

        // Distribute the children horizontally inside the children area
        var nextChildPosition = box.getContentArea().topLeft();
        boolean isFirstChild = true;
        for (Node child : node.getChildNodes()) {
            if (child.getState() == ViewModifier.Hidden) {
                continue;
            }
            NodeBox childBox = this.layoutContents(child);
            if (isFirstChild) {
                childBox = childBox.moveTo(nextChildPosition).moveBy(childBox.getMargin().left(), 0);
                isFirstChild = false;
            } else {
                childBox = childBox.moveTo(nextChildPosition);
            }

            box.setSubNodeBox(child.getId(), childBox);
            nextChildPosition = childBox.getFootprint().topRight();
            nextChildPosition = new Position(nextChildPosition.x(), nextChildPosition.y() + childBox.getMargin().top());
        }

        return box.expandToFitContent();
    }

}
