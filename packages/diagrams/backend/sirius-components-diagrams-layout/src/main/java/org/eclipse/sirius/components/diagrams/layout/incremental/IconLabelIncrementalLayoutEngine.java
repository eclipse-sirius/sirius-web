/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import java.util.Optional;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeLabelSizeProvider;

/**
 * The incremental layout engine to layout icon label nodes.
 *
 * @author gcoutable
 */
public class IconLabelIncrementalLayoutEngine implements INodeIncrementalLayoutEngine {

    private final NodeLabelSizeProvider nodeLabelSizeProvider;

    public IconLabelIncrementalLayoutEngine() {
        this.nodeLabelSizeProvider = new NodeLabelSizeProvider();
    }

    @Override
    public NodeLayoutData layout(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator, Optional<Double> optionalMaxWidth) {
        IPropertyHolder nodeTypePropertyHolder = layoutConfigurator.configureByType(nodeLayoutData.getNodeType());

        // Positions the label inside the node icon label if the icon label is just created.
        ElkPadding nodeLabelPadding = this.nodeLabelSizeProvider.getLabelPadding(nodeLayoutData, layoutConfigurator);
        if (nodeLayoutData.getLabel().getPosition().equals(Position.UNDEFINED)) {
            nodeLayoutData.getLabel().setPosition(Position.at(nodeLabelPadding.left, nodeLabelPadding.top));
        }

        Size newNodeSize = this.getNodeSize(optionalDiagramEvent, nodeLayoutData, nodeTypePropertyHolder, nodeLabelPadding, optionalMaxWidth);

        if (!nodeLayoutData.getSize().equals(newNodeSize)) {
            nodeLayoutData.setSize(newNodeSize);
            nodeLayoutData.setChanged(true);
        }

        return nodeLayoutData;
    }

    private Size getNodeSize(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, IPropertyHolder nodeTypePropertyHolder, ElkPadding nodeLabelPadding,
            Optional<Double> forceWidth) {
        double newNodeWidth = 0;
        double newNodeHeight = 0;

        if (optionalDiagramEvent.isPresent()) {
            IDiagramEvent diagramEvent = optionalDiagramEvent.get();
            if (diagramEvent instanceof ResizeEvent resizeEvent) {
                if (resizeEvent.nodeId().equals(nodeLayoutData.getId())) {
                    Size newSize = resizeEvent.newSize();
                    newNodeWidth = newSize.getWidth();
                    newNodeHeight = newSize.getHeight();
                    nodeLayoutData.setResizedByUser(true);
                }
            }
        }

        if (nodeTypePropertyHolder.hasProperty(CoreOptions.NODE_SIZE_CONSTRAINTS) && nodeTypePropertyHolder.getProperty(CoreOptions.NODE_SIZE_CONSTRAINTS).contains(SizeConstraint.MINIMUM_SIZE)) {
            KVector minSize = nodeTypePropertyHolder.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
            newNodeWidth = minSize.x;
            newNodeHeight = minSize.y;
        }

        Size labelSize = nodeLayoutData.getLabel().getTextBounds().getSize();
        double heightToConsider = nodeLabelPadding.top + labelSize.getHeight() + nodeLabelPadding.bottom;
        double widthToConsider = nodeLabelPadding.left + labelSize.getWidth() + nodeLabelPadding.right;

        if (heightToConsider > newNodeHeight) {
            newNodeHeight = heightToConsider;
        }
        if (widthToConsider > newNodeWidth) {
            newNodeWidth = widthToConsider;
        }

        if (forceWidth.isPresent()) {
            newNodeWidth = forceWidth.get();
        }

        return Size.of(newNodeWidth, newNodeHeight);
    }

    @Override
    public double getNodeWidth(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        IPropertyHolder nodeTypePropertyHolder = layoutConfigurator.configureByType(nodeLayoutData.getNodeType());

        // Positions the label inside the node icon label if the icon label is just created.
        ElkPadding nodeLabelPadding = this.nodeLabelSizeProvider.getLabelPadding(nodeLayoutData, layoutConfigurator);
        if (nodeLayoutData.getLabel().getPosition().equals(Position.UNDEFINED)) {
            nodeLayoutData.getLabel().setPosition(Position.at(nodeLabelPadding.left, nodeLabelPadding.top));
        }
        return this.getNodeSize(optionalDiagramEvent, nodeLayoutData, nodeTypePropertyHolder, nodeLabelPadding, Optional.empty()).getWidth();
    }
}
