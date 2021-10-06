/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.layout.incremental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.web.diagrams.CustomizableProperties;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.TextBounds;
import org.eclipse.sirius.web.diagrams.TextBoundsProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.ILayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;
import org.springframework.stereotype.Service;

/**
 * Used to convert the diagram into layout data. During the transformation.
 *
 * @author wpiers
 */
@Service
public class IncrementalLayoutDiagramConverter {

    public IncrementalLayoutConvertedDiagram convert(Diagram diagram) {
        Map<UUID, ILayoutData> id2LayoutData = new HashMap<>();

        DiagramLayoutData layoutData = new DiagramLayoutData();
        String id = diagram.getId();
        UUID uuid = UUID.nameUUIDFromBytes(id.getBytes());
        layoutData.setId(uuid);
        id2LayoutData.put(uuid, layoutData);

        layoutData.setPosition(diagram.getPosition());
        layoutData.setSize(diagram.getSize());

        List<NodeLayoutData> nodes = new ArrayList<>();
        for (Node node : diagram.getNodes()) {
            nodes.add(this.convertNode(node, layoutData, id2LayoutData));
        }
        layoutData.setChildrenNodes(nodes);

        List<EdgeLayoutData> edges = new ArrayList<>();
        for (Edge edge : diagram.getEdges()) {
            edges.add(this.convertEdge(edge, id2LayoutData));
        }
        layoutData.setEdges(edges);

        return new IncrementalLayoutConvertedDiagram(layoutData, id2LayoutData);
    }

    private NodeLayoutData convertNode(Node node, IContainerLayoutData parent, Map<UUID, ILayoutData> id2LayoutData) {
        NodeLayoutData layoutData = new NodeLayoutData();
        UUID id = node.getId();
        layoutData.setId(id);
        id2LayoutData.put(id, layoutData);

        layoutData.setParent(parent);
        layoutData.setNodeType(node.getType());
        layoutData.setStyle(node.getStyle());

        layoutData.setPosition(node.getPosition());
        layoutData.setSize(node.getSize());

        List<NodeLayoutData> borderNodes = new ArrayList<>();
        for (Node borderNode : node.getBorderNodes()) {
            borderNodes.add(this.convertNode(borderNode, layoutData, id2LayoutData));
        }
        layoutData.setBorderNodes(borderNodes);

        List<NodeLayoutData> childNodes = new ArrayList<>();
        for (Node childNode : node.getChildNodes()) {
            childNodes.add(this.convertNode(childNode, layoutData, id2LayoutData));
        }
        layoutData.setChildrenNodes(childNodes);

        LabelLayoutData labelLayoutData = this.convertLabel(node.getLabel(), id2LayoutData);
        layoutData.setLabel(labelLayoutData);

        layoutData.setResizedByUser(node.getCustomizedProperties().contains(CustomizableProperties.Size));

        return layoutData;
    }

    private EdgeLayoutData convertEdge(Edge edge, Map<UUID, ILayoutData> id2LayoutData) {
        EdgeLayoutData layoutData = new EdgeLayoutData();
        UUID id = edge.getId();
        layoutData.setId(id);
        id2LayoutData.put(id, layoutData);

        if (edge.getBeginLabel() != null) {
            layoutData.setBeginLabel(this.convertLabel(edge.getBeginLabel(), id2LayoutData));
        }
        if (edge.getCenterLabel() != null) {
            layoutData.setCenterLabel(this.convertLabel(edge.getCenterLabel(), id2LayoutData));
        }
        if (edge.getEndLabel() != null) {
            layoutData.setEndLabel(this.convertLabel(edge.getEndLabel(), id2LayoutData));
        }

        layoutData.setRoutingPoints(edge.getRoutingPoints());
        layoutData.setSource((NodeLayoutData) id2LayoutData.get(edge.getSourceId()));
        layoutData.setTarget((NodeLayoutData) id2LayoutData.get(edge.getTargetId()));

        return layoutData;
    }

    private LabelLayoutData convertLabel(Label label, Map<UUID, ILayoutData> id2LayoutData) {
        LabelLayoutData layoutData = new LabelLayoutData();
        UUID id = label.getId();
        layoutData.setId(id);
        id2LayoutData.put(id, layoutData);

        layoutData.setPosition(label.getPosition());
        layoutData.setLabelType(label.getType());

        TextBounds textBounds = new TextBoundsProvider().computeBounds(label.getStyle(), label.getText());
        layoutData.setTextBounds(textBounds);

        return layoutData;
    }

}
