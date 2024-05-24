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
        return switch (type) {
            case DiagramElementProps.TYPE -> this.instantiateDiagram(props, children);
            case NodeElementProps.TYPE -> this.instantiateNode(props, children);
            case EdgeElementProps.TYPE -> this.instantiateEdge(props, children);
            case LabelElementProps.TYPE -> this.instantiateLabel(props);
            case InsideLabelElementProps.TYPE -> this.instantiateInsideLabel(props);
            case OutsideLabelElementProps.TYPE -> this.instantiateOutsideLabel(props);
            default -> null;
        };
    }

    private Diagram instantiateDiagram(IProps props, List<Object> children) {
        if (props instanceof DiagramElementProps diagramElementProps) {
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

            return Diagram.newDiagram(diagramElementProps.getId())
                    .targetObjectId(diagramElementProps.getTargetObjectId())
                    .descriptionId(diagramElementProps.getDescriptionId())
                    .label(diagramElementProps.getLabel())
                    .position(diagramElementProps.getPosition())
                    .size(diagramElementProps.getSize())
                    .nodes(nodes)
                    .edges(edges)
                    .build();
        }
        return null;
    }

    private Node instantiateNode(IProps props, List<Object> children) {
        if (props instanceof NodeElementProps nodeElementProps) {
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

            Builder nodeBuilder = Node.newNode(nodeElementProps.getId())
                    .type(nodeElementProps.getType())
                    .targetObjectId(nodeElementProps.getTargetObjectId())
                    .targetObjectKind(nodeElementProps.getTargetObjectKind())
                    .targetObjectLabel(nodeElementProps.getTargetObjectLabel())
                    .descriptionId(nodeElementProps.getDescriptionId())
                    .borderNode(nodeElementProps.isBorderNode())
                    .style(nodeElementProps.getStyle())
                    .position(nodeElementProps.getPosition())
                    .size(nodeElementProps.getSize())
                    .userResizable(nodeElementProps.isUserResizable())
                    .borderNodes(borderNodes)
                    .childNodes(childNodes)
                    .customizedProperties(nodeElementProps.getCustomizableProperties())
                    .state(nodeElementProps.getState())
                    .pinned(nodeElementProps.isPinned())
                    .modifiers(nodeElementProps.getModifiers())
                    .collapsingState(nodeElementProps.getCollapsingState())
                    .outsideLabels(outsideLabels)
                    .labelEditable(nodeElementProps.isLabelEditable());

            if (insideLabel != null) {
                nodeBuilder.insideLabel(insideLabel);
            }

            if (nodeElementProps.getDefaultWidth() != null) {
                nodeBuilder.defaultWidth(nodeElementProps.getDefaultWidth());
            }

            if (nodeElementProps.getDefaultHeight() != null) {
                nodeBuilder.defaultHeight(nodeElementProps.getDefaultHeight());
            }

            if (nodeElementProps.getChildrenLayoutStrategy() != null) {
                nodeBuilder.childrenLayoutStrategy(nodeElementProps.getChildrenLayoutStrategy());
            }

            return nodeBuilder.build();
        }
        return null;
    }

    private Edge instantiateEdge(IProps props, List<Object> children) {
        if (props instanceof EdgeElementProps edgeElementProps) {
            Label beginLabel = this.getLabel(children, LabelType.EDGE_BEGIN);
            Label centerLabel = this.getLabel(children, LabelType.EDGE_CENTER);
            Label endLabel = this.getLabel(children, LabelType.EDGE_END);
            return Edge.newEdge(edgeElementProps.getId())
                    .type(edgeElementProps.getType())
                    .targetObjectId(edgeElementProps.getTargetObjectId())
                    .targetObjectKind(edgeElementProps.getTargetObjectKind())
                    .targetObjectLabel(edgeElementProps.getTargetObjectLabel())
                    .descriptionId(edgeElementProps.getDescriptionId())
                    .beginLabel(beginLabel)
                    .centerLabel(centerLabel)
                    .endLabel(endLabel)
                    .sourceId(edgeElementProps.getSourceId())
                    .targetId(edgeElementProps.getTargetId())
                    .style(edgeElementProps.getStyle())
                    .routingPoints(edgeElementProps.getRoutingPoints())
                    .sourceAnchorRelativePosition(edgeElementProps.getSourceAnchorRelativePosition())
                    .targetAnchorRelativePosition(edgeElementProps.getTargetAnchorRelativePosition())
                    .state(edgeElementProps.getState())
                    .modifiers(edgeElementProps.getModifiers())
                    .centerLabelEditable(edgeElementProps.isCenterLabelEditable())
                    .build();
        }
        return null;
    }

    private Label getLabel(List<Object> children, LabelType labelType) {
        return children.stream()
                .filter(Label.class::isInstance)
                .map(Label.class::cast)
                .filter(label -> label.getType().equals(labelType.getValue()))
                .findFirst()
                .orElse(null);

    }

    private Label instantiateLabel(IProps props) {
        if (props instanceof LabelElementProps labelElementProps) {
            return Label.newLabel(labelElementProps.getId())
                    .type(labelElementProps.getType())
                    .text(labelElementProps.getText())
                    .position(labelElementProps.getPosition())
                    .size(labelElementProps.getSize())
                    .alignment(labelElementProps.getAlignment())
                    .style(labelElementProps.getStyle())
                    .build();
        }
        return null;
    }

    private InsideLabel instantiateInsideLabel(IProps props) {
        if (props instanceof InsideLabelElementProps insideLabelElementProps) {
            return InsideLabel.newLabel(insideLabelElementProps.getId())
                    .text(insideLabelElementProps.getText())
                    .insideLabelLocation(insideLabelElementProps.getInsideLabelLocation())
                    .style(insideLabelElementProps.getStyle())
                    .isHeader(insideLabelElementProps.isIsHeader())
                    .displayHeaderSeparator(insideLabelElementProps.isDisplayHeaderSeparator())
                    .overflowStrategy(insideLabelElementProps.getOverflowStrategy())
                    .textAlign(insideLabelElementProps.getTextAlign())
                    .build();
        }
        return null;
    }

    private OutsideLabel instantiateOutsideLabel(IProps props) {
        if (props instanceof OutsideLabelElementProps outsideLabelElementProps) {
            return new OutsideLabel(outsideLabelElementProps.getId(), outsideLabelElementProps.getText(), outsideLabelElementProps.getOutsideLabelLocation(), outsideLabelElementProps.getStyle(),
                    outsideLabelElementProps.getOverflowStrategy(), outsideLabelElementProps.getTextAlign());
        }
        return null;
    }

}
