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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.elk.graph.properties.MapPropertyHolder;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ChildLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ChildrenAreaLaidOutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * The layout strategy engine that layout the children of the given node like a list.
 *
 * <ul>
 * <li>Rectangle and Image node will be considered as compartment.</li>
 * <li>Icon label node will be considered as list item.</li>
 * </ul>
 *
 * <p>
 * The compartment
 * </p>
 *
 * @author gcoutable
 */
public final class ListLayoutStrategyEngine implements ILayoutStrategyEngine {

    private final ChildNodeIncrementalLayoutEngineHandler childNodeIncrementalLayoutEngine;

    public ListLayoutStrategyEngine(ChildNodeIncrementalLayoutEngineHandler childNodeIncrementalLayoutEngine) {
        this.childNodeIncrementalLayoutEngine = Objects.requireNonNull(childNodeIncrementalLayoutEngine);
    }

    @Override
    public ChildrenAreaLaidOutData layoutChildrenArea(ChildrenAreaLayoutContext childrenAreaLayoutContext, ISiriusWebLayoutConfigurator layoutConfigurator) {
        // 1- Adapts the diagram event to each child.
        Map<String, IDiagramEvent> nodeIdToEvent = this.handleDiagramEvent(childrenAreaLayoutContext);

        // 2- Asks to each children, the size they need to be correctly laid out and get the max between the values
        double maxWidth = childrenAreaLayoutContext.getOptionalChildrenAreaWidth().orElseGet(() -> this.getMaxChildWith(nodeIdToEvent, childrenAreaLayoutContext, layoutConfigurator));

        // @formatter:off
        double gapBetweenNodes = Optional.of(childrenAreaLayoutContext.getChildrenLayoutStrategy()).map(ILayoutStrategy::getClass)
                .map(layoutConfigurator::configureByChildrenLayoutStrategy)
                .map(propertyHolder -> propertyHolder.getProperty(CoreOptions.SPACING_NODE_NODE))
                .orElse(0d);
        // @formatter:on

        // 3- Lays out children with the max size
        Map<String, ChildLayoutData> nodeIdToChildLayoutData = new HashMap<>();
        List<NodeLayoutData> nodesLaidOutData = new ArrayList<>();
        double minDeltaX = 0;
        double minDeltaY = 0;
        for (ChildLayoutData child : childrenAreaLayoutContext.getChildrenLayoutData()) {
            double xPosition = child.getPosition().getX();
            double yPosition = child.getPosition().getY();
            nodeIdToChildLayoutData.put(child.getId(), child);
            Optional<IDiagramEvent> optionalEvent = Optional.ofNullable(nodeIdToEvent.get(child.getId()));
            Optional<NodeLayoutData> optionalNodeLayoutData = this.childNodeIncrementalLayoutEngine.layout(optionalEvent, child, layoutConfigurator, Optional.of(maxWidth));
            if (optionalNodeLayoutData.isPresent()) {
                NodeLayoutData nodeLaidOutData = optionalNodeLayoutData.get();
                nodesLaidOutData.add(nodeLaidOutData);
                Position positionInArea = nodeLaidOutData.getPosition();
                // If the laid out child has moved because of anything that happened deeper, updates the delta position
                // of the children area
                minDeltaX = Math.min(minDeltaX, positionInArea.getX() - xPosition);
                minDeltaY = Math.min(minDeltaY, positionInArea.getY() - yPosition);
            }
        }

        double nextChildYPosition = 0;
        for (NodeLayoutData nodeLaidOutData : nodesLaidOutData) {
            nodeLaidOutData.setPosition(Position.at(minDeltaX, nextChildYPosition + minDeltaY));
            nextChildYPosition = nextChildYPosition + nodeLaidOutData.getSize().getHeight() + gapBetweenNodes;
        }

        // 4- return the children area layout data build from that
        // @formatter:off
        return ChildrenAreaLaidOutData.newChildrenAreaLaidOutData()
                .deltaPosition(Position.at(minDeltaX, minDeltaY))
                .size(Size.of(maxWidth, nextChildYPosition))
                .nodeIdToChildLayoutData(nodeIdToChildLayoutData)
                .build();
        // @formatter:on
    }

    private Map<String, IDiagramEvent> handleDiagramEvent(ChildrenAreaLayoutContext childrenAreaLayoutContext) {
        Map<String, IDiagramEvent> nodeIdToEvent = new HashMap<>();
        if (childrenAreaLayoutContext.getOptionalDiagramEvent().isPresent()) {
            IDiagramEvent diagramEvent = childrenAreaLayoutContext.getOptionalDiagramEvent().get();
            if (diagramEvent instanceof SinglePositionEvent singlePositionEvent) {
                nodeIdToEvent.putAll(this.handleSinglePositionEvent(singlePositionEvent, childrenAreaLayoutContext.getChildrenLayoutData()));
            } else if (diagramEvent instanceof ResizeEvent resizeEvent) {
                nodeIdToEvent.putAll(this.handleResizeEvent(resizeEvent, childrenAreaLayoutContext));
            } else {
                for (ChildLayoutData child : childrenAreaLayoutContext.getChildrenLayoutData()) {
                    nodeIdToEvent.put(child.getId(), diagramEvent);
                }
            }
        }
        return nodeIdToEvent;
    }

    /**
     * Returns the map of each child ID to its adapted resize event.
     *
     * <p>
     * From the actual state of the resized node, we calculate the height proportion each children are taking inside the
     * node (we do not consider icon label height since it is invariant). Then we create an internal resize event for
     * each child with a new size and position according to the new parent size and the new position of each previous
     * sibling.
     * </p>
     *
     * <p>
     * Only events that resize the parent or the junction of two compartments are handled.
     * </p>
     *
     * @param resizeEvent
     *            The resize event to handle
     * @param childrenAreaLayoutContext
     *            the context needed to handle the resize event
     * @return a map of resize event associated with the child ID
     */
    private Map<String, IDiagramEvent> handleResizeEvent(ResizeEvent resizeEvent, ChildrenAreaLayoutContext childrenAreaLayoutContext) {
        Map<String, IDiagramEvent> nodeIdToEvent = new HashMap<>();
        if (this.isParentBeingResized(childrenAreaLayoutContext, resizeEvent)) {
            Size newParentSize = resizeEvent.newSize();
            Position initilaPositionDelta = resizeEvent.positionDelta();
            // @formatter:off
            Optional<Double> optionalAdaptativeChildrenHeight = childrenAreaLayoutContext.getChildrenLayoutData().stream()
                    // TODO: We should not reference the icon label here (or any type of node). We do not taking account
                    // the height of icon label has space we can resize because we do not want to resize icon labels
                    // height after is parent has been resize (or never).
                    .filter(child -> !NodeType.NODE_ICON_LABEL.equals(child.getNodeType()))
                    .map(ChildLayoutData::getSize)
                    .map(Size::getHeight)
                    .reduce((a, b) -> a + b);
            // @formatter:on
            double resizedChildWidth = newParentSize.getWidth();
            if (optionalAdaptativeChildrenHeight.isPresent()) {
                double childrenHeight = optionalAdaptativeChildrenHeight.get();
                double invariantHeight = childrenAreaLayoutContext.getParentSize().getHeight() - childrenHeight;
                double newChildrenHeight = newParentSize.getHeight() - invariantHeight;
                for (ChildLayoutData child : childrenAreaLayoutContext.getChildrenLayoutData()) {
                    Position positionDelta = Position.at(0, 0);
                    double resizeChildHeight = 0;
                    // TODO: We should not reference the icon label here (or any type of node). We are doing it to
                    // prevent the list layout to resize direct icon label children when the node parent is vertically
                    // resized. Instead we should have a mechanism that ask any type of node if it can be resize
                    // considering the current context (an icon label can be resized horizontally but not vertically
                    // when it is inside a column list compartment)
                    if (NodeType.NODE_ICON_LABEL.equals(child.getNodeType())) {
                        // Force the height of a icon label to be the same because we don't want the icon label to be
                        // resized after its parent has been resized
                        resizeChildHeight = child.getSize().getHeight();
                        positionDelta = Position.at(0, 0);
                    } else {
                        // Other type of node are resized according the space they were taking is the previous state.
                        double childHeightProportion = child.getSize().getHeight() / childrenHeight;
                        resizeChildHeight = newChildrenHeight * childHeightProportion;
                        // positionDelta will be used to position the child node children depending on how much the
                        // child node height has increased. This does not reflect the real position of the child after
                        // the parent has resize. We may use a moveEvent to correct the position manually but the
                        // position of children is already corrected by the list layout strategy engine once every
                        // children laid out.
                        positionDelta = Position.at(0, initilaPositionDelta.getY() * childHeightProportion);
                    }
                    nodeIdToEvent.put(child.getId(), new ResizeEvent(child.getId(), positionDelta, Size.of(resizedChildWidth, resizeChildHeight)));
                }
            }
        } else if (this.isChildBeingResized(childrenAreaLayoutContext, resizeEvent)) {
            ChildLayoutData compartmentBeingResized = childrenAreaLayoutContext.getChildrenLayoutData().stream().filter(child -> child.getId().equals(resizeEvent.nodeId())).findFirst().get();
            if (this.isCompartmentJunctionBeingResized(childrenAreaLayoutContext, resizeEvent)) {
                int childBeingResizeIndex = childrenAreaLayoutContext.getChildrenLayoutData().indexOf(compartmentBeingResized);
                nodeIdToEvent.put(compartmentBeingResized.getId(), resizeEvent);
                if (resizeEvent.positionDelta().getY() == 0) {
                    ChildLayoutData siblingToResizeToo = childrenAreaLayoutContext.getChildrenLayoutData().get(childBeingResizeIndex + 1);
                    Size siblingSize = siblingToResizeToo.getSize();

                    double deltaY = compartmentBeingResized.getSize().getHeight() - resizeEvent.newSize().getHeight();
                    double newSiblingHeight = siblingSize.getHeight() + deltaY;

                    nodeIdToEvent.put(siblingToResizeToo.getId(), new ResizeEvent(siblingToResizeToo.getId(), Position.at(0, deltaY), Size.of(siblingSize.getWidth(), newSiblingHeight)));
                } else {
                    ChildLayoutData siblingToResizeToo = childrenAreaLayoutContext.getChildrenLayoutData().get(childBeingResizeIndex - 1);
                    Size siblingSize = siblingToResizeToo.getSize();

                    double newSiblingHeight = siblingSize.getHeight() - resizeEvent.positionDelta().getY();

                    nodeIdToEvent.put(siblingToResizeToo.getId(), new ResizeEvent(siblingToResizeToo.getId(), Position.at(0, 0), Size.of(siblingSize.getWidth(), newSiblingHeight)));

                }
            }
        } else {
            // All other resize event are forwarded to children because they can concern them
            for (ChildLayoutData child : childrenAreaLayoutContext.getChildrenLayoutData()) {
                nodeIdToEvent.put(child.getId(), resizeEvent);
            }
        }
        return nodeIdToEvent;
    }

    /**
     * Passes the diagram event through all the children.
     *
     * <p>
     * FIXME: Massive workaround (undefined position) : Prevent parent node to move when a child is created. This
     * workaround exists because undefined position are at (-1, -1) instead of being optional! This workaround could be
     * removed once the position of node will have become optional globally. There are many _FIXME: Massive workaround
     * (undefined position)_ around the code.
     * </p>
     *
     * @param diagramEvent
     *            The diagram event
     * @param childrenLayoutData
     *            The list of child layout data
     * @return The map of child node id to the event they will need to handle
     */
    private Map<String, IDiagramEvent> handleSinglePositionEvent(SinglePositionEvent diagramEvent, List<ChildLayoutData> childrenLayoutData) {
        Map<String, IDiagramEvent> nodeIdToEvent = new HashMap<>();
        for (ChildLayoutData child : childrenLayoutData) {

            if (child.getPosition().getX() < 0 && child.getPosition().getY() < 0) {
                child.setPosition(Position.at(0, 0));
            }
            nodeIdToEvent.put(child.getId(), diagramEvent);
        }
        return nodeIdToEvent;
    }

    private boolean isChildBeingResized(ChildrenAreaLayoutContext childrenAreaLayoutContext, ResizeEvent resizeEvent) {
        // @formatter:off
        return childrenAreaLayoutContext.getChildrenLayoutData().stream()
                .map(ChildLayoutData::getId)
                .anyMatch(resizeEvent.nodeId()::equals);
        // @formatter:on
    }

    private boolean isParentBeingResized(ChildrenAreaLayoutContext childrenAreaLayoutContext, IDiagramEvent event) {
        if (event instanceof ResizeEvent resizeEvent) {
            return childrenAreaLayoutContext.getParentId().equals(resizeEvent.nodeId());
        }
        return false;
    }

    private boolean isCompartmentJunctionBeingResized(ChildrenAreaLayoutContext childrenAreaLayoutContext, ResizeEvent resizeEvent) {
        ChildLayoutData compartmentBeingResized = childrenAreaLayoutContext.getChildrenLayoutData().stream().filter(child -> child.getId().equals(resizeEvent.nodeId())).findFirst().get();
        if (resizeEvent.newSize().getWidth() != compartmentBeingResized.getSize().getWidth()) {
            return false;
        }

        boolean isJunctionBeingResized = true;
        int childBeingResizeIndex = childrenAreaLayoutContext.getChildrenLayoutData().indexOf(compartmentBeingResized);
        int lastChildIndex = childrenAreaLayoutContext.getChildrenLayoutData().size() - 1;
        if (childBeingResizeIndex == 0 && resizeEvent.positionDelta().getY() != 0) {
            isJunctionBeingResized = false;
        }
        isJunctionBeingResized = isJunctionBeingResized && !(childBeingResizeIndex == 0 && resizeEvent.positionDelta().getY() != 0);
        isJunctionBeingResized = isJunctionBeingResized
                && !(childBeingResizeIndex == lastChildIndex && resizeEvent.positionDelta().getY() == 0 && resizeEvent.newSize().getHeight() != compartmentBeingResized.getSize().getHeight());

        return isJunctionBeingResized;
    }

    private double getMaxChildWith(Map<String, IDiagramEvent> nodeIdToEvent, ChildrenAreaLayoutContext childrenAreaLayoutContext, ISiriusWebLayoutConfigurator layoutConfigurator) {
        double maxWidth = childrenAreaLayoutContext.getParentMinimalSize().getWidth();

        if (childrenAreaLayoutContext.getOptionalDiagramEvent().isPresent() && this.isParentBeingResized(childrenAreaLayoutContext, childrenAreaLayoutContext.getOptionalDiagramEvent().get())) {
            ResizeEvent resizeEvent = (ResizeEvent) childrenAreaLayoutContext.getOptionalDiagramEvent().get();
            Size newParentSize = resizeEvent.newSize();
            if (maxWidth < newParentSize.getWidth()) {
                maxWidth = newParentSize.getWidth();
            }
        } else {
            if (childrenAreaLayoutContext.isParentResizedByUser() && maxWidth < childrenAreaLayoutContext.getParentSize().getWidth()) {
                maxWidth = childrenAreaLayoutContext.getParentSize().getWidth();
            }
        }

        for (ChildLayoutData child : childrenAreaLayoutContext.getChildrenLayoutData()) {
            Optional<IDiagramEvent> optionalEvent = Optional.ofNullable(nodeIdToEvent.get(child.getId()));
            double nodeWidth = this.childNodeIncrementalLayoutEngine.getNodeWidth(optionalEvent, child, layoutConfigurator).orElse(0d);
            if (maxWidth < nodeWidth) {
                maxWidth = nodeWidth;
            }

            // Need a copy of properties to prevent the default value to be added in the default configuration
            IPropertyHolder childProperties = new MapPropertyHolder().copyProperties(layoutConfigurator.configureByType(child.getNodeType()));
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

        return maxWidth;
    }
}
