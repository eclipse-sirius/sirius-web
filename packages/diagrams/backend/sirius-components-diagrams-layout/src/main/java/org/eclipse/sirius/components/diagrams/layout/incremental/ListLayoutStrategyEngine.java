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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
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
    public Size layoutChildren(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        IPropertyHolder childrenLayoutStrategyPropertyHolder = layoutConfigurator.configureByChildrenLayoutStrategy(node.getChildrenLayoutStrategy().getClass());

        double maxWidth = this.childNodeIncrementalLayoutEngine.getNodeMinimalWidth(node, layoutConfigurator).orElse(0d);

        for (NodeLayoutData child : node.getChildrenNodes()) {
            double nodeWidth = this.childNodeIncrementalLayoutEngine.getNodeWidth(optionalDiagramEvent, child, layoutConfigurator).orElse(0d);
            if (maxWidth < nodeWidth) {
                maxWidth = nodeWidth;
            }
        }

        if (maxWidth < LayoutOptionValues.MIN_WIDTH_CONSTRAINT) {
            maxWidth = LayoutOptionValues.MIN_WIDTH_CONSTRAINT;
        }

        double theMaxWidth = maxWidth;
        double nextChildYPosition = 0;
        for (NodeLayoutData child : node.getChildrenNodes()) {
            NodeLayoutData updatedSizeNode = this.childNodeIncrementalLayoutEngine.layout(optionalDiagramEvent, child, layoutConfigurator, theMaxWidth).orElse(child);
            updatedSizeNode.setPosition(Position.at(0, nextChildYPosition));

            double gapBetweenNodes = childrenLayoutStrategyPropertyHolder.getProperty(CoreOptions.SPACING_NODE_NODE);
            nextChildYPosition = nextChildYPosition + updatedSizeNode.getSize().getHeight() + gapBetweenNodes;
        }

        return Size.of(maxWidth, nextChildYPosition);
    }

    @Override
    public Size layoutChildren(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator, double width) {
        IPropertyHolder childrenLayoutStrategyPropertyHolder = layoutConfigurator.configureByChildrenLayoutStrategy(node.getChildrenLayoutStrategy().getClass());

        double nextChildYPosition = 0;
        for (NodeLayoutData child : node.getChildrenNodes()) {
            NodeLayoutData updatedSizeNode = this.childNodeIncrementalLayoutEngine.layout(optionalDiagramEvent, child, layoutConfigurator, width).orElse(child);
            updatedSizeNode.setPosition(Position.at(0, nextChildYPosition));

            double gapBetweenNodes = childrenLayoutStrategyPropertyHolder.getProperty(CoreOptions.SPACING_NODE_NODE);
            nextChildYPosition = nextChildYPosition + updatedSizeNode.getSize().getHeight() + gapBetweenNodes;
        }

        return Size.of(width, nextChildYPosition);
    }
}
