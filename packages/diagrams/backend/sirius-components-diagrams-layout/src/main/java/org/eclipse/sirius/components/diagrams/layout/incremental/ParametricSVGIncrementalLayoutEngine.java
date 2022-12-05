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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.Size.Builder;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeLabelSizeProvider;

/**
 * The incremental layout engine to layout parametric SVG nodes.
 *
 * @author gcoutable
 */
public class ParametricSVGIncrementalLayoutEngine implements INodeIncrementalLayoutEngine {

    private final ChildLayoutStrategyEngineHandler childLayoutStrategyEngineHandler;

    private final IBorderNodeLayoutEngine borderNodeLayoutEngine;

    private final List<ICustomNodeLabelPositionProvider> customLabelPositionProviders;

    private final NodeLabelSizeProvider nodeLabelSizeProvider;

    public ParametricSVGIncrementalLayoutEngine(ChildLayoutStrategyEngineHandler childLayoutStrategyEngineHandler, IBorderNodeLayoutEngine borderNodeLayoutEngine,
            List<ICustomNodeLabelPositionProvider> customLabelPositionProviders) {
        this.childLayoutStrategyEngineHandler = Objects.requireNonNull(childLayoutStrategyEngineHandler);
        this.borderNodeLayoutEngine = Objects.requireNonNull(borderNodeLayoutEngine);
        this.customLabelPositionProviders = Objects.requireNonNull(customLabelPositionProviders);
        this.nodeLabelSizeProvider = new NodeLabelSizeProvider();
    }

    @Override
    public NodeLayoutData layout(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, Map<String, ElkGraphElement> elementId2ElkElement) {
        NodeLabelPositionProvider nodeLabelPositionProvider = new NodeLabelPositionProvider(elementId2ElkElement);
        Bounds initialNodeBounds = Bounds.newBounds().position(nodeLayoutData.getPosition()).size(nodeLayoutData.getSize()).build();

        Size childrenAreaSize = this.childLayoutStrategyEngineHandler.layoutChildren(optionalDiagramEvent, nodeLayoutData, elementId2ElkElement).orElseGet(() -> Size.of(0, 0));

        IPropertyHolder nodeProperties = elementId2ElkElement.get(nodeLayoutData.getId());
        ElkPadding elkPadding = nodeProperties.getProperty(CoreOptions.PADDING);

        double xOffset = elkPadding.left;
        double yOffset = elkPadding.top;

        Builder newNodeSizeBuilder = Size.newSize().width(elkPadding.left + childrenAreaSize.getWidth() + elkPadding.right);
        double newNodeHeight = elkPadding.top + childrenAreaSize.getHeight() + elkPadding.bottom;

        if (this.shouldConsiderNodeLabel(nodeLayoutData, elementId2ElkElement)) {
            Size labelWithPaddingSize = this.nodeLabelSizeProvider.getLabelWithPaddingSize(nodeLayoutData, elementId2ElkElement);
            newNodeHeight += labelWithPaddingSize.getHeight();
            yOffset += labelWithPaddingSize.getHeight();
        }

        EnumSet<SizeConstraint> sizeConstraints = nodeProperties.getProperty(CoreOptions.NODE_SIZE_CONSTRAINTS);
        if (sizeConstraints.contains(SizeConstraint.MINIMUM_SIZE)) {
            KVector minimumSize = nodeProperties.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
            if (newNodeHeight < minimumSize.y) {
                newNodeHeight = minimumSize.y;
            }
        }

        if (newNodeHeight < LayoutOptionValues.MIN_HEIGHT_CONSTRAINT) {
            newNodeHeight = LayoutOptionValues.MIN_HEIGHT_CONSTRAINT;
        }

        newNodeSizeBuilder.height(newNodeHeight);
        Size newNodeSize = newNodeSizeBuilder.build();

        if (!nodeLayoutData.getSize().equals(newNodeSize)) {
            nodeLayoutData.setSize(newNodeSize);
            nodeLayoutData.setChanged(true);
        }

        // Update children position
        for (NodeLayoutData child : nodeLayoutData.getChildrenNodes()) {
            // @formatter:off
            Position updatedChildNodePosition = Position.newPosition()
                    .x(child.getPosition().getX() + xOffset)
                    .y(child.getPosition().getY() + yOffset)
                    .build();
            // @formatter:on
            if (!updatedChildNodePosition.equals(child.getPosition())) {
                child.setPosition(updatedChildNodePosition);
                child.setChanged(true);
            }
        }

        // update the border node once the current node bounds are updated
        Bounds newBounds = Bounds.newBounds().position(nodeLayoutData.getPosition()).size(nodeLayoutData.getSize()).build();
        List<BorderNodesOnSide> borderNodesOnSide = this.borderNodeLayoutEngine.layoutBorderNodes(optionalDiagramEvent, nodeLayoutData.getBorderNodes(), initialNodeBounds, newBounds,
                elementId2ElkElement);

        // recompute the label
        if (nodeLayoutData.getLabel() != null) {
            // @formatter:off
            Position nodeLabelPosition = this.customLabelPositionProviders.stream()
                    .map(customLabelPositionProvider -> customLabelPositionProvider.getLabelPosition(elementId2ElkElement, nodeLayoutData.getLabel().getTextBounds().getSize(), nodeLayoutData.getSize(),
                            nodeLayoutData.getId(), nodeLayoutData.getStyle()))
                    .flatMap(Optional::stream)
                    .findFirst()
                    .orElseGet(() -> nodeLabelPositionProvider.getPosition(nodeLayoutData, nodeLayoutData.getLabel(), borderNodesOnSide));
            // @formatter:on
            nodeLayoutData.getLabel().setPosition(nodeLabelPosition);
        }

        return nodeLayoutData;
    }

    @Override
    public NodeLayoutData layout(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, Map<String, ElkGraphElement> elementId2ElkElement, double maxWidth) {
        NodeLabelPositionProvider nodeLabelPositionProvider = new NodeLabelPositionProvider(elementId2ElkElement);
        Bounds initialNodeBounds = Bounds.newBounds().position(nodeLayoutData.getPosition()).size(nodeLayoutData.getSize()).build();

        Size childrenAreaSize = this.childLayoutStrategyEngineHandler.layoutChildren(optionalDiagramEvent, nodeLayoutData, elementId2ElkElement).orElseGet(() -> Size.of(0, 0));

        IPropertyHolder nodeProperties = elementId2ElkElement.get(nodeLayoutData.getId());
        ElkPadding elkPadding = nodeProperties.getProperty(CoreOptions.PADDING);

        double xOffset = elkPadding.left;
        double yOffset = elkPadding.top;

        Builder newNodeSizeBuilder = Size.newSize().width(maxWidth);
        double newNodeHeight = elkPadding.top + childrenAreaSize.getHeight() + elkPadding.bottom;

        if (this.shouldConsiderNodeLabel(nodeLayoutData, elementId2ElkElement)) {
            Size labelWithPaddingSize = this.nodeLabelSizeProvider.getLabelWithPaddingSize(nodeLayoutData, elementId2ElkElement);
            newNodeHeight += labelWithPaddingSize.getHeight();
            yOffset += labelWithPaddingSize.getHeight();
        }

        EnumSet<SizeConstraint> sizeConstraints = nodeProperties.getProperty(CoreOptions.NODE_SIZE_CONSTRAINTS);
        if (sizeConstraints.contains(SizeConstraint.MINIMUM_SIZE)) {
            KVector minimumSize = nodeProperties.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
            if (newNodeHeight < minimumSize.y) {
                newNodeHeight = minimumSize.y;
            }
        }

        if (newNodeHeight < LayoutOptionValues.MIN_HEIGHT_CONSTRAINT) {
            newNodeHeight = LayoutOptionValues.MIN_HEIGHT_CONSTRAINT;
        }

        newNodeSizeBuilder.height(newNodeHeight);
        Size newNodeSize = newNodeSizeBuilder.build();

        if (!nodeLayoutData.getSize().equals(newNodeSize)) {
            nodeLayoutData.setSize(newNodeSize);
            nodeLayoutData.setChanged(true);
        }

        // Update children position
        for (NodeLayoutData child : nodeLayoutData.getChildrenNodes()) {
            // @formatter:off
            Position updatedChildNodePosition = Position.newPosition()
                    .x(child.getPosition().getX() + xOffset)
                    .y(child.getPosition().getY() + yOffset)
                    .build();
            // @formatter:on
            if (!updatedChildNodePosition.equals(child.getPosition())) {
                child.setPosition(updatedChildNodePosition);
                child.setChanged(true);
            }
        }

        // update the border node once the current node bounds are updated
        Bounds newBounds = Bounds.newBounds().position(nodeLayoutData.getPosition()).size(nodeLayoutData.getSize()).build();
        List<BorderNodesOnSide> borderNodesOnSide = this.borderNodeLayoutEngine.layoutBorderNodes(optionalDiagramEvent, nodeLayoutData.getBorderNodes(), initialNodeBounds, newBounds,
                elementId2ElkElement);

        // recompute the label
        if (nodeLayoutData.getLabel() != null) {
            // @formatter:off
            Position nodeLabelPosition = this.customLabelPositionProviders.stream()
                    .map(customLabelPositionProvider -> customLabelPositionProvider.getLabelPosition(elementId2ElkElement, nodeLayoutData.getLabel().getTextBounds().getSize(), nodeLayoutData.getSize(),
                            nodeLayoutData.getId(), nodeLayoutData.getStyle()))
                    .flatMap(Optional::stream)
                    .findFirst()
                    .orElseGet(() -> nodeLabelPositionProvider.getPosition(nodeLayoutData, nodeLayoutData.getLabel(), borderNodesOnSide));
            // @formatter:on
            nodeLayoutData.getLabel().setPosition(nodeLabelPosition);
        }

        return nodeLayoutData;
    }

    @Override
    public double getNodeWidth(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, Map<String, ElkGraphElement> elementId2ElkElement) {
        IPropertyHolder nodeProperties = elementId2ElkElement.get(nodeLayoutData.getId());
        ElkPadding elkPadding = nodeProperties.getProperty(CoreOptions.PADDING);

        Size childrenAreaSize = this.childLayoutStrategyEngineHandler.layoutChildren(optionalDiagramEvent, nodeLayoutData, elementId2ElkElement).orElseGet(() -> Size.of(0, 0));

        return elkPadding.left + childrenAreaSize.getWidth() + elkPadding.right;
    }

    @Override
    public double getNodeMinimalWidth(NodeLayoutData node, Map<String, ElkGraphElement> elementId2ElkElement) {
        IPropertyHolder nodeProperties = elementId2ElkElement.get(node.getId());
        double nodeMinimalWidth = 0;
        if (this.isNodeLabelInside(node, elementId2ElkElement)) {
            ElkPadding labelPadding = nodeProperties.getProperty(CoreOptions.NODE_LABELS_PADDING);
            nodeMinimalWidth = node.getLabel().getTextBounds().getSize().getWidth() + labelPadding.right + labelPadding.left;
        }

        return nodeMinimalWidth;
    }

    private boolean shouldConsiderNodeLabel(NodeLayoutData node, Map<String, ElkGraphElement> elementId2ElkElement) {
        boolean shouldConsiderNodeLabel = true;
        LabelLayoutData label = node.getLabel();

        shouldConsiderNodeLabel = label.getTextBounds().getSize().getWidth() != 0;
        shouldConsiderNodeLabel = shouldConsiderNodeLabel && this.isNodeLabelInside(node, elementId2ElkElement);

        return shouldConsiderNodeLabel;
    }

    private boolean isNodeLabelInside(NodeLayoutData node, Map<String, ElkGraphElement> elementId2ElkElement) {
        IPropertyHolder nodeProperties = elementId2ElkElement.get(node.getId());
        EnumSet<NodeLabelPlacement> nodeLabelPlacement = nodeProperties.getProperty(CoreOptions.NODE_LABELS_PLACEMENT);
        return nodeLabelPlacement.contains(NodeLabelPlacement.INSIDE);
    }

}
