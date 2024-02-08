/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.renderer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Node.Builder;
import org.eclipse.sirius.components.diagrams.OutsideLabel;
import org.eclipse.sirius.components.diagrams.components.LabelType;
import org.eclipse.sirius.components.diagrams.elements.DiagramElementProps;
import org.eclipse.sirius.components.diagrams.elements.EdgeElementProps;
import org.eclipse.sirius.components.diagrams.elements.InsideLabelElementProps;
import org.eclipse.sirius.components.diagrams.elements.LabelElementProps;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.elements.OutsideLabelElementProps;
import org.eclipse.sirius.components.representations.IElementFactory;
import org.eclipse.sirius.components.representations.IProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to instantiate the elements of the diagram.
 *
 * @author sbegaudeau
 */
public class DiagramElementFactory implements IElementFactory {

    private final Logger logger = LoggerFactory.getLogger(DiagramElementFactory.class);

    @Override
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        Object object = null;
        if (DiagramElementProps.TYPE.equals(type) && props instanceof DiagramElementProps diagramElementProps) {
            object = this.instantiateDiagram(diagramElementProps, children);
        } else if (NodeElementProps.TYPE.equals(type) && props instanceof NodeElementProps nodeElementProps) {
            object = this.instantiateNode(nodeElementProps, children);
        } else if (EdgeElementProps.TYPE.equals(type) && props instanceof EdgeElementProps edgeElementProps) {
            object = this.instantiateEdge(edgeElementProps, children);
        } else if (LabelElementProps.TYPE.equals(type) && props instanceof LabelElementProps labelElementProps) {
            object = this.instantiateLabel(labelElementProps);
        } else if (InsideLabelElementProps.TYPE.equals(type) && props instanceof InsideLabelElementProps insideLabelElementProps) {
            object = this.instantiateInsideLabel(insideLabelElementProps);
        } else if (OutsideLabelElementProps.TYPE.equals(type) && props instanceof OutsideLabelElementProps outsideLabelElementProps) {
            object = this.instantiateOutsideLabel(outsideLabelElementProps);
        }
        return object;
    }

    private Diagram instantiateDiagram(DiagramElementProps props, List<Object> children) {
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        children.forEach(child -> {
            if (child instanceof Node node) {
                nodes.add(node);
            } else if (child instanceof Edge edge) {
                edges.add(edge);
            } else {
                this.logger.warn("Unsupported child {}", child);
            }
        });

        return Diagram.newDiagram(props.getId())
                .targetObjectId(props.getTargetObjectId())
                .descriptionId(props.getDescriptionId())
                .label(props.getLabel())
                .position(props.getPosition())
                .size(props.getSize())
                .nodes(nodes)
                .edges(edges)
                .build();
    }

    private Node instantiateNode(NodeElementProps props, List<Object> children) {
        InsideLabel insideLabel = children.stream()
                .filter(InsideLabel.class::isInstance)
                .map(InsideLabel.class::cast)
                .findFirst()
                .orElse(null);

        List<OutsideLabel> outsideLabels = children.stream()
                .filter(OutsideLabel.class::isInstance)
                .map(OutsideLabel.class::cast)
                .toList();

        List<Node> childNodes = new ArrayList<>();
        List<Node> borderNodes = new ArrayList<>();

        children.stream().filter(Node.class::isInstance).map(Node.class::cast).forEach(node -> {
            if (node.isBorderNode()) {
                borderNodes.add(node);
            } else {
                childNodes.add(node);
            }
        });

        Builder nodeBuilder = Node.newNode(props.getId())
                .type(props.getType())
                .targetObjectId(props.getTargetObjectId())
                .targetObjectKind(props.getTargetObjectKind())
                .targetObjectLabel(props.getTargetObjectLabel())
                .descriptionId(props.getDescriptionId())
                .borderNode(props.isBorderNode())
                .style(props.getStyle())
                .position(props.getPosition())
                .size(props.getSize())
                .userResizable(props.isUserResizable())
                .borderNodes(borderNodes)
                .childNodes(childNodes)
                .customizedProperties(props.getCustomizableProperties())
                .state(props.getState())
                .pinned(props.isPinned())
                .modifiers(props.getModifiers())
                .collapsingState(props.getCollapsingState())
                .outsideLabels(outsideLabels)
                .labelEditable(props.isLabelEditable());

        if (insideLabel != null) {
            nodeBuilder.insideLabel(insideLabel);
        }

        if (props.getDefaultWidth() != null) {
            nodeBuilder.defaultWidth(props.getDefaultWidth());
        }

        if (props.getDefaultHeight() != null) {
            nodeBuilder.defaultHeight(props.getDefaultHeight());
        }

        if (props.getChildrenLayoutStrategy() != null) {
            nodeBuilder.childrenLayoutStrategy(props.getChildrenLayoutStrategy());
        }

        return nodeBuilder.build();
    }

    private Edge instantiateEdge(EdgeElementProps props, List<Object> children) {
        Label beginLabel = this.getLabel(children, LabelType.EDGE_BEGIN);
        Label centerLabel = this.getLabel(children, LabelType.EDGE_CENTER);
        Label endLabel = this.getLabel(children, LabelType.EDGE_END);
        return Edge.newEdge(props.getId())
                .type(props.getType())
                .targetObjectId(props.getTargetObjectId())
                .targetObjectKind(props.getTargetObjectKind())
                .targetObjectLabel(props.getTargetObjectLabel())
                .descriptionId(props.getDescriptionId())
                .beginLabel(beginLabel)
                .centerLabel(centerLabel)
                .endLabel(endLabel)
                .sourceId(props.getSourceId())
                .targetId(props.getTargetId())
                .style(props.getStyle())
                .routingPoints(props.getRoutingPoints())
                .sourceAnchorRelativePosition(props.getSourceAnchorRelativePosition())
                .targetAnchorRelativePosition(props.getTargetAnchorRelativePosition())
                .state(props.getState())
                .modifiers(props.getModifiers())
                .centerLabelEditable(props.isCenterLabelEditable())
                .build();
    }

    private Label getLabel(List<Object> children, LabelType labelType) {
        return children.stream()
                .filter(Label.class::isInstance)
                .map(Label.class::cast)
                .filter(label -> label.getType().equals(labelType.getValue()))
                .findFirst()
                .orElse(null);

    }

    private Label instantiateLabel(LabelElementProps props) {
        return Label.newLabel(props.getId())
                .type(props.getType())
                .text(props.getText())
                .position(props.getPosition())
                .size(props.getSize())
                .alignment(props.getAlignment())
                .style(props.getStyle())
                .build();
    }

    private InsideLabel instantiateInsideLabel(InsideLabelElementProps props) {
        return InsideLabel.newLabel(props.getId())
                .text(props.getText())
                .insideLabelLocation(props.getInsideLabelLocation())
                .style(props.getStyle())
                .isHeader(props.isIsHeader())
                .displayHeaderSeparator(props.isDisplayHeaderSeparator())
                .build();
    }

    private OutsideLabel instantiateOutsideLabel(OutsideLabelElementProps props) {
        return new OutsideLabel(props.getId(), props.getText(), props.getOutsideLabelLocation(), props.getStyle());
    }

}
