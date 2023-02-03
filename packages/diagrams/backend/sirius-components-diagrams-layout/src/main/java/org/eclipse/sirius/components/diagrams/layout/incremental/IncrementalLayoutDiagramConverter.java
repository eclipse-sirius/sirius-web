/*******************************************************************************
 * Copyright (c) 2021, 2023 THALES GLOBAL SERVICES.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.diagrams.CustomizableProperties;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.TextBounds;
import org.eclipse.sirius.components.diagrams.TextBoundsProvider;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.components.LabelType;
import org.eclipse.sirius.components.diagrams.layout.ELKPropertiesService;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ILayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.springframework.stereotype.Service;

/**
 * Used to convert the diagram into layout data. During the transformation.
 *
 * @author wpiers
 */
@Service
public class IncrementalLayoutDiagramConverter {

    private final ELKPropertiesService elkPropertiesService;

    public IncrementalLayoutDiagramConverter(ELKPropertiesService elkPropertiesService) {
        this.elkPropertiesService = Objects.requireNonNull(elkPropertiesService);
    }

    public IncrementalLayoutConvertedDiagram convert(Diagram diagram, ISiriusWebLayoutConfigurator layoutConfigurator) {
        Map<String, ILayoutData> id2LayoutData = new HashMap<>();

        DiagramLayoutData layoutData = new DiagramLayoutData();
        String id = diagram.getId();
        layoutData.setId(id);
        id2LayoutData.put(id, layoutData);

        layoutData.setPosition(diagram.getPosition());
        layoutData.setSize(diagram.getSize());

        List<NodeLayoutData> nodes = new ArrayList<>();
        for (Node node : diagram.getNodes()) {
            nodes.add(this.convertNode(node, layoutData, id2LayoutData, layoutConfigurator));
        }
        layoutData.setChildrenNodes(nodes);

        List<EdgeLayoutData> edges = new ArrayList<>();
        for (Edge edge : diagram.getEdges()) {
            edges.add(this.convertEdge(edge, id2LayoutData));
        }
        layoutData.setEdges(edges);

        return new IncrementalLayoutConvertedDiagram(layoutData, id2LayoutData);
    }

    private NodeLayoutData convertNode(Node node, IContainerLayoutData parent, Map<String, ILayoutData> id2LayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        NodeLayoutData layoutData = new NodeLayoutData();
        String id = node.getId();
        layoutData.setId(id);
        id2LayoutData.put(id, layoutData);

        layoutData.setParent(parent);
        layoutData.setNodeType(node.getType());
        layoutData.setStyle(node.getStyle());
        layoutData.setChildrenLayoutStrategy(node.getChildrenLayoutStrategy());

        layoutData.setPosition(node.getPosition());
        layoutData.setSize(node.getSize());
        layoutData.setUserResizable(node.isUserResizable());

        List<NodeLayoutData> borderNodes = new ArrayList<>();
        for (Node borderNode : node.getBorderNodes()) {
            borderNodes.add(this.convertNode(borderNode, layoutData, id2LayoutData, layoutConfigurator));
        }
        layoutData.setBorderNodes(borderNodes);

        List<NodeLayoutData> childNodes = new ArrayList<>();
        for (Node childNode : node.getChildNodes()) {
            childNodes.add(this.convertNode(childNode, layoutData, id2LayoutData, layoutConfigurator));
        }
        layoutData.setChildrenNodes(childNodes);

        LabelLayoutData labelLayoutData = this.convertNodeLabel(node, id2LayoutData, layoutConfigurator);
        layoutData.setLabel(labelLayoutData);

        layoutData.setResizedByUser(node.getCustomizedProperties().contains(CustomizableProperties.Size));
        layoutData.setBorderNode(node.isBorderNode());

        if (node.getState() == ViewModifier.Hidden) {
            layoutData.setPinned(true);
            layoutData.setExcludedFromLayoutComputation(true);
        }

        return layoutData;
    }

    private EdgeLayoutData convertEdge(Edge edge, Map<String, ILayoutData> id2LayoutData) {
        EdgeLayoutData layoutData = new EdgeLayoutData();
        String id = edge.getId();
        layoutData.setId(id);
        id2LayoutData.put(id, layoutData);

        if (edge.getBeginLabel() != null) {
            layoutData.setBeginLabel(this.convertEdgeLabel(edge.getBeginLabel(), LabelType.EDGE_BEGIN, id2LayoutData));
        }
        if (edge.getCenterLabel() != null) {
            layoutData.setCenterLabel(this.convertEdgeLabel(edge.getCenterLabel(), LabelType.EDGE_CENTER, id2LayoutData));
        }
        if (edge.getEndLabel() != null) {
            layoutData.setEndLabel(this.convertEdgeLabel(edge.getEndLabel(), LabelType.EDGE_END, id2LayoutData));
        }

        layoutData.setRoutingPoints(edge.getRoutingPoints());
        layoutData.setSource((NodeLayoutData) id2LayoutData.get(edge.getSourceId()));
        layoutData.setSourceAnchorRelativePosition(edge.getSourceAnchorRelativePosition());
        layoutData.setTarget((NodeLayoutData) id2LayoutData.get(edge.getTargetId()));
        layoutData.setTargetAnchorRelativePosition(edge.getTargetAnchorRelativePosition());

        return layoutData;
    }

    private LabelLayoutData convertEdgeLabel(Label label, LabelType labelType, Map<String, ILayoutData> id2LayoutData) {
        LabelLayoutData layoutData = new LabelLayoutData();
        String id = label.getId();
        layoutData.setId(id);
        id2LayoutData.put(id, layoutData);

        layoutData.setPosition(label.getPosition());
        layoutData.setLabelType(labelType.getValue());

        TextBounds textBounds = new TextBoundsProvider().computeBounds(label.getStyle(), label.getText());
        layoutData.setTextBounds(textBounds);

        return layoutData;
    }

    private LabelLayoutData convertNodeLabel(Node node, Map<String, ILayoutData> id2LayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        LabelLayoutData layoutData = new LabelLayoutData();
        Label label = node.getLabel();
        String id = label.getId();
        layoutData.setId(id);
        id2LayoutData.put(id, layoutData);

        layoutData.setPosition(label.getPosition());
        String labelType = this.elkPropertiesService.getNodeLabelType(node, layoutConfigurator);
        layoutData.setLabelType(labelType);

        TextBounds textBounds = null;
        if (labelType.startsWith("label:inside-v")) {
            textBounds = new TextBoundsProvider().computeAutoWrapBounds(label.getStyle(), label.getText(), node.getSize().getWidth());
        } else {
            textBounds = new TextBoundsProvider().computeBounds(label.getStyle(), label.getText());
        }
        layoutData.setTextBounds(textBounds);

        return layoutData;
    }
}
