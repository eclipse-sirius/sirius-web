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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.DiagramLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.IDiagramLayoutEngine;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.IParentLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.NodeLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.Offsets;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.Rectangle;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The default {@link IDiagramLayoutEngine}.
 *
 * @author pcdavid
 */
@Service
public class DiagramLayoutEngine implements IDiagramLayoutEngine {

    private final Logger logger = LoggerFactory.getLogger(DiagramLayoutEngine.class);

    @Override
    public DiagramLayoutData layout(DiagramLayoutConfiguration diagramLayoutConfiguration) {
        Map<String, Rectangle> nodeBounds = new HashMap<>();

        try {
            this.layoutContents(diagramLayoutConfiguration.id(), diagramLayoutConfiguration, nodeBounds);

            var childNodeIds = diagramLayoutConfiguration.childNodeLayoutConfigurations().stream()
                    .map(NodeLayoutConfiguration::id)
                    .toList();
            this.arrangeChildren(diagramLayoutConfiguration.id(), childNodeIds, diagramLayoutConfiguration, nodeBounds);
        } catch (IllegalArgumentException exception) {
            // Several kind of assertions are performed during the layout, in case of bugs in our code,
            // those assertions could throw some exceptions. We will catch them here in order to return
            // a valid result.
            this.logger.warn(exception.getMessage(), exception);
        }

        Map<String, NodeLayoutData> newNodeLayoutData = new HashMap<>();
        nodeBounds.forEach((nodeId, newBounds) -> newNodeLayoutData.put(nodeId, new NodeLayoutData(nodeId, newBounds.topLeft(), newBounds.size())));

        return new DiagramLayoutData(newNodeLayoutData, Map.of(), Map.of());
    }

    private void layoutContents(String containerId, DiagramLayoutConfiguration diagramLayoutConfiguration, Map<String, Rectangle> nodeBounds) {
        // Recursively layout the children's contents
        IParentLayoutConfiguration containerLayoutConfiguration = Optional.<IParentLayoutConfiguration>ofNullable(diagramLayoutConfiguration.nodeLayoutConfigurationsById().get(containerId))
                .orElse(diagramLayoutConfiguration);
        var childNodeIds = containerLayoutConfiguration.childNodeLayoutConfigurations()
                .stream()
                .map(NodeLayoutConfiguration::id)
                .toList();

        childNodeIds.forEach(childNodeId -> this.layoutContents(childNodeId, diagramLayoutConfiguration, nodeBounds));

        // At this point the children have their correct *size* but are not correctly positioned.
        // Arrange them properly as if on an unbounded canvas.
        this.arrangeChildren(containerId, childNodeIds, diagramLayoutConfiguration, nodeBounds);

        // Shift the children's position if needed so that they stay inside the container's content area.
        var internalOffsets = diagramLayoutConfiguration.optionalNodeLayoutConfiguration(containerId)
                .map(configuration -> configuration.border().combine(configuration.padding()))
                .orElse(Offsets.empty());

        Rectangle childrenFootprint = Rectangle.union(childNodeIds.stream().map(nodeBounds::get).toList());
        if (!childNodeIds.isEmpty() && !containerId.equals(diagramLayoutConfiguration.id())) {
            double dx = 0.0;
            double dy = 0.0;
            Position childrenOrigin = childrenFootprint.topLeft();
            if (childrenOrigin.x() < internalOffsets.left()) {
                dx = internalOffsets.left() - childrenOrigin.x();
            }
            if (childrenOrigin.y() < internalOffsets.top()) {
                dy = internalOffsets.top() - childrenOrigin.y();
            }
            for (String childNodeId : childNodeIds) {
                Rectangle childBounds = nodeBounds.get(childNodeId).translate(dx, dy);
                nodeBounds.put(childNodeId, childBounds);
            }
        }

        // Now that our children are properly sized and placed, we can determine our own size.

        // 1. It should be enough for our actual contents with the border and padding accounted for
        Size contentsSize = new Size(childrenFootprint.bottomRight().x() + internalOffsets.right(), childrenFootprint.bottomRight().y() + internalOffsets.bottom());

        // 2. It can not be less than the minimum requested size. The diagram itself has no minimum size.
        Size minSize = diagramLayoutConfiguration.optionalNodeLayoutConfiguration(containerId)
                .map(NodeLayoutConfiguration::minimumSize)
                .orElse(new Size(Double.MAX_VALUE, Double.MAX_VALUE));

        // 3. If we had a previously set size, it must also be considered
        Size previousSize = diagramLayoutConfiguration.optionalPreviousFootprint(containerId)
                .map(Rectangle::size)
                .orElse(new Size(0, 0));

        // 4. The end-user may have asked for a new size explicitly
        Size requestedSize = diagramLayoutConfiguration.optionalResizeEvent(containerId).map(ResizeEvent::newSize).map(s -> new Size(s.getWidth(), s.getHeight())).orElse(previousSize);
        double width = Math.max(contentsSize.width(), Math.max(minSize.width(), requestedSize.width()));
        double height = Math.max(contentsSize.height(), Math.max(minSize.height(), requestedSize.height()));
        nodeBounds.put(containerId, new Rectangle(0, 0, width, height));
    }

    /**
     * Assuming all the children have their proper size, arrange them (only changing their positions) to their final
     * position (relative to their parent).
     */
    private void arrangeChildren(String parentElementId, List<String> childNodeIds, DiagramLayoutConfiguration diagramLayoutConfiguration, Map<String, Rectangle> layout) {
        Canvas canvas = new Canvas();

        // First, place the node(s) which has been directly interacted with by the end-user if there are any.
        for (String childNodeId : childNodeIds) {
            Optional<MoveEvent> optionalMoveEvent = diagramLayoutConfiguration.optionalMoveEvent(childNodeId);
            if (optionalMoveEvent.isPresent()) {
                Position newPosition = new Position(optionalMoveEvent.get().newPosition().getX(), optionalMoveEvent.get().newPosition().getY());
                canvas.setBounds(childNodeId, layout.get(childNodeId).moveTo(newPosition));
            }
            // A resize can also change the position if it moves the top-left corner.
            Optional<ResizeEvent> optionalResizeEvent = diagramLayoutConfiguration.optionalResizeEvent(childNodeId);
            if (optionalResizeEvent.isPresent()) {
                ResizeEvent resizeEvent = optionalResizeEvent.get();
                diagramLayoutConfiguration.optionalPreviousFootprint(childNodeId).ifPresent(bounds -> {
                    double dx = -resizeEvent.positionDelta().getX();
                    double dy = -resizeEvent.positionDelta().getY();
                    Position newPosition = bounds.topLeft().translate(dx, dy);
                    canvas.setBounds(childNodeId, layout.get(childNodeId).moveTo(newPosition));
                });
            }
        }

        // Next, split the remaining nodes into:
        // - nodes which existed before and have a previous location
        // - newly appearing nodes which do not have a previous location.

        Set<String> childrenWithPreviousLocation = childNodeIds.stream()
                .filter(diagramLayoutConfiguration::hasPreviousFootprint)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        childrenWithPreviousLocation.removeIf(canvas::hasBounds);

        Set<String> childrenWithoutPreviousLocation = childNodeIds.stream()
                .filter(Predicate.not(diagramLayoutConfiguration::hasPreviousFootprint))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        childrenWithoutPreviousLocation.removeIf(canvas::hasBounds);

        // If we have at least one newly appearing element and a position for it, place it first
        if (!childrenWithoutPreviousLocation.isEmpty()) {
            diagramLayoutConfiguration.optionalSinglePositionEvent(parentElementId).ifPresent(singlePositionEvent -> {
                String newChildId = childrenWithoutPreviousLocation.iterator().next();
                childrenWithoutPreviousLocation.remove(newChildId);
                var position = singlePositionEvent.position();
                canvas.place(newChildId, this.relativePosition(diagramLayoutConfiguration, parentElementId, new Position(position.getX(), position.getY())), layout.get(newChildId).size());
            });
        }

        // Next, try to keep the position for all the other nodes which have previous positions.
        for (String childId : childrenWithPreviousLocation) {
            diagramLayoutConfiguration.optionalPreviousFootprint(childId).ifPresent(bounds -> {
                canvas.place(childId, bounds.topLeft(), layout.get(childId).size());
            });
        }

        // Finally, place the newly created node which do not have a previous location while avoiding the ones already
        // placed above.
        Function<String, Offsets> marginProvider = nodeId -> diagramLayoutConfiguration.optionalNodeLayoutConfiguration(nodeId)
                .map(NodeLayoutConfiguration::margin)
                .orElse(Offsets.empty());

        Function<String, Size> sizeProvider = nodeId -> layout.get(nodeId).size();

        var newNodesLayout = new CanvasLayoutEngine(childrenWithoutPreviousLocation, sizeProvider, marginProvider)
                .getLeftToRightLayout(canvas.getOccupiedFootprints(marginProvider));
        newNodesLayout.forEach(canvas::setBounds);

        // "Commit" the result into the global layout
        layout.putAll(canvas.getAllBounds());
    }

    public Position relativePosition(DiagramLayoutConfiguration diagramLayoutConfiguration, String nodeId, Position absolutePosition) {
        var optionalParentElementId = diagramLayoutConfiguration.optionalNodeLayoutConfiguration(nodeId)
                .map(nodeLayoutConfiguration -> nodeLayoutConfiguration.parentLayoutConfiguration().id());

        var optionalOrigin = diagramLayoutConfiguration.optionalPreviousFootprint(nodeId).map(Rectangle::topLeft);

        if (optionalParentElementId.isPresent() && optionalOrigin.isPresent()) {
            var parentElementId = optionalParentElementId.get();
            var origin = optionalOrigin.get();

            var positionRelativeToParent = this.relativePosition(diagramLayoutConfiguration, parentElementId, absolutePosition);
            return new Position(positionRelativeToParent.x() - origin.x(), positionRelativeToParent.y() - origin.y());
        }
        return absolutePosition;
    }
}
