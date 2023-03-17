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
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.layout.api.IDiagramLayoutConfigurationProvider;
import org.eclipse.sirius.components.diagrams.layout.api.IDiagramLayoutEngine;
import org.eclipse.sirius.components.diagrams.layout.api.configuration.DiagramLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.LabelLayoutData;
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
        this.layoutDiagram(diagram.getNodes(), previousLayoutData, nodeLayoutData);
        this.applyEvent(optionalDiagramEvent, nodeLayoutData);
        Map<String, LabelLayoutData> labelLayoutData = this.createDefaultLabelLayoutData(diagram);
        return new DiagramLayoutData(nodeLayoutData, previousLayoutData.edgeLayoutData(), labelLayoutData);
    }

    private Map<String, LabelLayoutData> createDefaultLabelLayoutData(Diagram diagram) {
        Map<String, LabelLayoutData> labelLayoutData = new HashMap<>();
        for (Node node : diagram.getNodes()) {
            this.addDefaultLabelLayoutData(node, labelLayoutData);
        }
        for (Edge edge : diagram.getEdges()) {
            this.addDefaultLabelLayoutData(edge, labelLayoutData);
        }
        return labelLayoutData;
    }

    private void addDefaultLabelLayoutData(Node node, Map<String, LabelLayoutData> labelLayoutData) {
        if (this.isVisible(node)) {
            Label label = node.getLabel();
            labelLayoutData.put(label.getId(), new LabelLayoutData(new Position(5, 5), new Size(70, 20), new Position(0, 0)));
            for (Node child : node.getChildNodes()) {
                this.addDefaultLabelLayoutData(child, labelLayoutData);
            }
            for (Node borderNode : node.getBorderNodes()) {
                this.addDefaultLabelLayoutData(borderNode, labelLayoutData);
            }
        }
    }

    private void addDefaultLabelLayoutData(Edge edge, Map<String, LabelLayoutData> labelLayoutData) {
        if (this.isVisible(edge)) {
            Optional.ofNullable(edge.getBeginLabel()).ifPresent(beginLabel -> {
                labelLayoutData.put(beginLabel.getId(), new LabelLayoutData(new Position(5, 5), new Size(70, 20), new Position(0, 0)));
            });
            Optional.ofNullable(edge.getCenterLabel()).ifPresent(centerLabel -> {
                labelLayoutData.put(centerLabel.getId(), new LabelLayoutData(new Position(5, 5), new Size(70, 20), new Position(0, 0)));
            });
            Optional.ofNullable(edge.getEndLabel()).ifPresent(edgeLabel -> {
                labelLayoutData.put(edgeLabel.getId(), new LabelLayoutData(new Position(5, 5), new Size(70, 20), new Position(0, 0)));
            });
        }
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

    private void layoutDiagram(List<Node> nodes, DiagramLayoutData previousLayoutData, Map<String, NodeLayoutData> nodeLayoutData) {
        // Distribute the top-level nodes horizontally from (0,0)
        Position nextChildPosition = new Position(0.0, 0.0);
        for (Node child : nodes) {
            if (!this.isVisible(child)) {
                continue;
            }
            NodeBox childBox = this.layoutContents(child, false).moveTo(nextChildPosition);
            nextChildPosition = childBox.getFootprint().topRight().translate(0, childBox.getMargin().top());
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

    private NodeBox layoutContents(Node node, boolean listItem) {
        NodeBox box;
        if (listItem) {
            box = new NodeBox(node, new Rectangle(0.0, 0.0, 100.0, 20.0), Offsets.empty());
        } else {
            box = new NodeBox(node, new Rectangle(0.0, 0.0, 150.0, 70.0), Offsets.of(10));
        }

        // Distribute the children horizontally inside the children area if freeform, or vertically with no margin if list layout
        boolean listLayout = node.getChildrenLayoutStrategy() instanceof ListLayoutStrategy;
        var nextChildPosition = box.getContentArea().topLeft();
        boolean isFirstChild = true;
        for (Node child : node.getChildNodes()) {
            if (!this.isVisible(child)) {
                continue;
            }
            NodeBox childBox = this.layoutContents(child, listLayout);
            if (isFirstChild) {
                childBox = childBox.moveTo(nextChildPosition).moveBy(childBox.getMargin().left(), 0);
                isFirstChild = false;
            } else {
                childBox = childBox.moveTo(nextChildPosition);
            }

            box.setSubNodeBox(child.getId(), childBox);
            if (listLayout) {
                nextChildPosition = childBox.getFootprint().bottomLeft().translate(childBox.getMargin().left(), 0);
            } else {
                nextChildPosition = childBox.getFootprint().topRight().translate(0, childBox.getMargin().top());
            }
        }

        return box.expandToFitContent();
    }

    private boolean isVisible(Node node) {
        return node.getState() != ViewModifier.Hidden;
    }

    private boolean isVisible(Edge edge) {
        return edge.getState() != ViewModifier.Hidden;
    }

}
