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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * The layout strategy engine that layout the children of the given node like a list.
 *
 * <ul>
 * <li>Rectangle and Image node will be considered as compartment.</li>
 * <li>Icon label node will be considered as list item.</li>
 * </ul>
 *
 * @author gcoutable
 */
public final class ListLayoutStrategyEngine implements ILayoutStrategyEngine {

    private final ChildNodeIncrementalLayoutEngineHandler childNodeIncrementalLayoutEngine;

    public ListLayoutStrategyEngine(ChildNodeIncrementalLayoutEngineHandler childNodeIncrementalLayoutEngine) {
        this.childNodeIncrementalLayoutEngine = Objects.requireNonNull(childNodeIncrementalLayoutEngine);

    }

    @Override
    public Size layoutChildren(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData node, Map<String, ElkGraphElement> elementId2ElkElement) {
        IPropertyHolder nodeProperties = elementId2ElkElement.get(node.getId());

        double maxWidth = this.childNodeIncrementalLayoutEngine.getNodeMinimalWidth(node, elementId2ElkElement).orElse(0d);

        for (NodeLayoutData child : node.getChildrenNodes()) {
            double nodeWidth = this.childNodeIncrementalLayoutEngine.getNodeWidth(optionalDiagramEvent, child, elementId2ElkElement).orElse(0d);
            if (maxWidth < nodeWidth) {
                maxWidth = nodeWidth;
            }
            IPropertyHolder childProperties = elementId2ElkElement.get(child.getId());
            if (childProperties.hasProperty(CoreOptions.NODE_SIZE_CONSTRAINTS) && childProperties.getProperty(CoreOptions.NODE_SIZE_CONSTRAINTS).contains(SizeConstraint.MINIMUM_SIZE)) {
                KVector childMinSize = childProperties.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
                if (maxWidth < childMinSize.x) {
                    maxWidth = childMinSize.x;
                }
            }
        }

        if (maxWidth < LayoutOptionValues.MIN_WIDTH_CONSTRAINT) {
            maxWidth = LayoutOptionValues.MIN_WIDTH_CONSTRAINT;
        }

        double theMaxWidth = maxWidth;
        double nextChildYPosition = 0;
        for (NodeLayoutData child : node.getChildrenNodes()) {
            NodeLayoutData updatedSizeNode = this.childNodeIncrementalLayoutEngine.layout(optionalDiagramEvent, child, elementId2ElkElement, theMaxWidth).orElse(child);
            updatedSizeNode.setPosition(Position.at(0, nextChildYPosition));

            double gapBetweenNodes = nodeProperties.getProperty(CoreOptions.SPACING_NODE_NODE);
            nextChildYPosition = nextChildYPosition + updatedSizeNode.getSize().getHeight() + gapBetweenNodes;
        }

        return Size.of(maxWidth, nextChildYPosition);
    }

    @Override
    public Size layoutChildren(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData node, Map<String, ElkGraphElement> elementId2ElkElement, double width) {
        IPropertyHolder nodeProperties = elementId2ElkElement.get(node.getId());

        double nextChildYPosition = 0;
        for (NodeLayoutData child : node.getChildrenNodes()) {
            NodeLayoutData updatedSizeNode = this.childNodeIncrementalLayoutEngine.layout(optionalDiagramEvent, child, elementId2ElkElement, width).orElse(child);
            updatedSizeNode.setPosition(Position.at(0, nextChildYPosition));

            double gapBetweenNodes = nodeProperties.getProperty(CoreOptions.SPACING_NODE_NODE);
            nextChildYPosition = nextChildYPosition + updatedSizeNode.getSize().getHeight() + gapBetweenNodes;
        }

        return Size.of(width, nextChildYPosition);
    }
}
