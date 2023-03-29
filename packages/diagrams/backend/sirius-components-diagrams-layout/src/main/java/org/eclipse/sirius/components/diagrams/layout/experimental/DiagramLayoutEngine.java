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
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.layout.api.IDiagramLayoutConfigurationProvider;
import org.eclipse.sirius.components.diagrams.layout.api.IDiagramLayoutEngine;
import org.eclipse.sirius.components.diagrams.layout.api.Offsets;
import org.eclipse.sirius.components.diagrams.layout.api.Rectangle;
import org.eclipse.sirius.components.diagrams.layout.api.configuration.NodeLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.springframework.stereotype.Service;

/**
 * The default {@link IDiagramLayoutEngine}.
 *
 * @author pcdavid
 */
@Service
public class DiagramLayoutEngine implements IDiagramLayoutEngine {

    private final IDiagramLayoutConfigurationProvider diagramLayoutConfigurationProvider;

    public DiagramLayoutEngine(IDiagramLayoutConfigurationProvider diagramLayoutConfigurationProvider) {
        this.diagramLayoutConfigurationProvider = Objects.requireNonNull(diagramLayoutConfigurationProvider);
    }

    @Override
    public DiagramLayoutData layout(IEditingContext editingContext, Diagram diagram, DiagramLayoutData previousLayoutData, Optional<IDiagramEvent> optionalDiagramEvent) {
        var optionalDiagramLayoutConfiguration = this.diagramLayoutConfigurationProvider.getDiagramLayoutConfiguration(editingContext, diagram);
        if (optionalDiagramLayoutConfiguration.isPresent()) {
            var input = new DiagramLayoutInput(diagram, previousLayoutData, optionalDiagramLayoutConfiguration.get(), optionalDiagramEvent);
            Map<String, NodeLayoutData> newNodeLayoutData = this.layoutDiagram(diagram, input);
            return new DiagramLayoutData(newNodeLayoutData, Map.of(), Map.of());
        } else {
            return Optional.ofNullable(previousLayoutData).orElse(new DiagramLayoutData(Map.of(), Map.of(), Map.of()));
        }
    }

    private Map<String, NodeLayoutData> layoutDiagram(Diagram diagram, DiagramLayoutInput input) {
        Map<String, Rectangle> nodeBounds = new HashMap<>();
        this.layoutContents(diagram.getId(), input, nodeBounds);
        this.arrangeChildren(diagram.getId(), input.getChildNodeIds(diagram.getId()), input, nodeBounds);
        Map<String, NodeLayoutData> newNodeLayoutData = new HashMap<>();
        nodeBounds.forEach((nodeId, newBounds) -> newNodeLayoutData.put(nodeId, new NodeLayoutData(nodeId, newBounds.topLeft(), newBounds.size())));
        return newNodeLayoutData;
    }

    private void layoutContents(String containerId, DiagramLayoutInput input, Map<String, Rectangle> nodeBounds) {
        // Recursively layout the children's contents
        List<String> children = input.getChildNodeIds(containerId);
        for (String child : children) {
            this.layoutContents(child, input, nodeBounds);
        }

        // At this point the children have their correct *size* but are not correctly positioned.
        // Arrange them properly as if on an unbounded canvas.
        this.arrangeChildren(containerId, children, input, nodeBounds);

        // Shift the children's position if needed so that they stay inside the container's content area.
        Offsets internalOffsets = input.getInternalOffsets(containerId);
        Rectangle childrenFootprint = Rectangle.union(children.stream().map(nodeBounds::get).toList());
        if (!children.isEmpty() && !containerId.equals(input.getDiagramId())) {
            double dx = 0.0;
            double dy = 0.0;
            Position childrenOrigin = childrenFootprint.topLeft();
            if (childrenOrigin.x() < internalOffsets.left()) {
                dx = internalOffsets.left() - childrenOrigin.x();
            }
            if (childrenOrigin.y() < internalOffsets.top()) {
                dy = internalOffsets.top() - childrenOrigin.y();
            }
            for (String child : children) {
                Rectangle childBounds = nodeBounds.get(child).translate(dx, dy);
                nodeBounds.put(child, childBounds);
            }
        }

        // Now that our children are properly sized and placed, we can determine our own size.

        // 1. It should be enough for our actual contents with the border and padding accounted for
        Size contentsSize = new Size(childrenFootprint.bottomRight().x() + internalOffsets.right(), childrenFootprint.bottomRight().y() + internalOffsets.bottom());
        // 2. It can not be less than the minimum requested size. The diagram itself has no minimum size.
        Size minSize = Optional.ofNullable(input.getNodeLayoutConfiguration(containerId)).map(NodeLayoutConfiguration::getMinimumSize).orElse(new Size(Double.MAX_VALUE, Double.MAX_VALUE));
        // 3. If we had a previously set size, it must also be considered
        Size previousSize = input.getPreviousBounds(containerId).map(Rectangle::size).orElse(new Size(0, 0));
        // 4. The end-user may have asked for a new size explicitly
        Size requestedSize = input.getOptionalResizeEvent(containerId).map(ResizeEvent::newSize).map(s -> new Size(s.getWidth(), s.getHeight())).orElse(previousSize);
        double width = Math.max(contentsSize.width(), Math.max(minSize.width(), requestedSize.width()));
        double height = Math.max(contentsSize.height(), Math.max(minSize.height(), requestedSize.height()));
        nodeBounds.put(containerId, new Rectangle(0, 0, width, height));
    }

    /**
     * Assuming all the children have their proper size, arrange them (only changing their positions) to their final
     * position (relative to their parent).
     */
    private void arrangeChildren(String parentElementId, List<String> children, DiagramLayoutInput input, Map<String, Rectangle> layout) {
        Canvas canvas = new Canvas();

        // First, place the node(s) which has been directly interacted with by the end-user if there are any.
        for (String childId : children) {
            Optional<MoveEvent> optionalMoveEvent = input.getOptionalMoveEvent(childId);
            if (optionalMoveEvent.isPresent()) {
                Position newPosition = new Position(optionalMoveEvent.get().newPosition().getX(), optionalMoveEvent.get().newPosition().getY());
                canvas.setBounds(childId, layout.get(childId).moveTo(newPosition));
            }
            // A resize can also change the position if it moves the top-left corner.
            Optional<ResizeEvent> optionalResizeEvent = input.getOptionalResizeEvent(childId);
            if (optionalResizeEvent.isPresent()) {
                ResizeEvent resizeEvent = optionalResizeEvent.get();
                input.getPreviousBounds(childId).ifPresent(bounds -> {
                    double dx = -resizeEvent.positionDelta().getX();
                    double dy = -resizeEvent.positionDelta().getY();
                    Position newPosition = bounds.topLeft().translate(dx, dy);
                    canvas.setBounds(childId, layout.get(childId).moveTo(newPosition));
                });
            }
        }

        // Next, split the remaining nodes into:
        // - nodes which existed before and have a previous location
        // - newly appearing nodes which do not have a previous location.

        Set<String> childrenWithPreviousLocation = children.stream().filter(input::hasPreviousBounds).collect(Collectors.toCollection(LinkedHashSet::new));
        childrenWithPreviousLocation.removeIf(canvas::hasBounds);

        Set<String> childrenWithoutPreviousLocation = children.stream().filter(Predicate.not(input::hasPreviousBounds)).collect(Collectors.toCollection(LinkedHashSet::new));
        childrenWithoutPreviousLocation.removeIf(canvas::hasBounds);

        // If we have at least one newly appearing element and a position for it, place it first
        if (!childrenWithoutPreviousLocation.isEmpty()) {
            input.getOptionalSinglePositionEvent(parentElementId).ifPresent(singlePositionEvent -> {
                String newChildId = childrenWithoutPreviousLocation.iterator().next();
                childrenWithoutPreviousLocation.remove(newChildId);
                var position = singlePositionEvent.position();
                canvas.place(newChildId, input.getRelativePosition(parentElementId, new Position(position.getX(), position.getY())), layout.get(newChildId).size());
            });
        }

        // Next, try to keep the position for all the other nodes which have previous positions.
        for (String childId : childrenWithPreviousLocation) {
            input.getPreviousBounds(childId).ifPresent(bounds -> {
                canvas.place(childId, bounds.topLeft(), layout.get(childId).size());
            });
        }

        // Finally, place the newly created node which do not have a previous location while avoiding the ones already
        // placed above.
        Function<String, Offsets> marginProvider = nodeId -> input.getNodeLayoutConfiguration(nodeId).getMargin();
        Function<String, Size> sizeProvider = nodeId -> layout.get(nodeId).size();
        var newNodesLayout = new CanvasLayoutEngine(childrenWithoutPreviousLocation, sizeProvider, marginProvider)
                .getLeftToRightLayout(canvas.getOccupiedFootprints(nodeId -> input.getNodeLayoutConfiguration(nodeId).getMargin()));
        newNodesLayout.forEach(canvas::setBounds);

        // "Commit" the result into the global layout
        layout.putAll(canvas.getAllBounds());
    }
}
