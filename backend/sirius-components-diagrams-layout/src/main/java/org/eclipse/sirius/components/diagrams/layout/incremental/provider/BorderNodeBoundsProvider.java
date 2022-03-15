/*******************************************************************************
 * Copyright (c) 2022 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.diagrams.layout.incremental.provider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.utils.Bounds;
import org.eclipse.sirius.components.diagrams.layout.incremental.utils.Geometry;
import org.eclipse.sirius.components.diagrams.layout.incremental.utils.PointOnRectangleInfo;
import org.eclipse.sirius.components.diagrams.layout.incremental.utils.RectangleSide;

/**
 * Provides the position to apply to a BorderNode.
 *
 * @author lfasani
 */
public class BorderNodeBoundsProvider {

    private static final Position UNDEFINED_POSITION = Position.at(-1, -1);

    private static final Size UNDEFINED_SIZE = Size.of(-1, -1);

    private NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());

    /**
     * Provides the new position of the given border node.If the position is no need to be updated, the current position
     * is returned.
     *
     * @return
     */
    public List<BorderNodesOnSide> updateBorderNodesBounds(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData parentNode, ISiriusWebLayoutConfigurator layoutConfigurator) {
        List<NodeLayoutData> borderNodesLayoutData = parentNode.getBorderNodes();

        Optional<NodeLayoutData> borderNodeMovedOrResized = this.getBorderNodesMovedOrResized(optionalDiagramElementEvent, borderNodesLayoutData);
        List<NodeLayoutData> borderNodesToCreate = this.getBorderNodesToCreate(borderNodesLayoutData);
        List<NodeLayoutData> borderNodesNotCreated = new ArrayList<>(borderNodesLayoutData);
        borderNodesNotCreated.removeAll(borderNodesToCreate);
        List<NodeLayoutData> borderNodesNotChanged = new ArrayList<>(borderNodesLayoutData);
        if (borderNodeMovedOrResized.isPresent()) {
            borderNodesNotChanged.remove(borderNodeMovedOrResized.get());
        }

        if (borderNodeMovedOrResized.isPresent()) {
            // update the position of the border node if it has been explicitly moved or resized
            this.updateChangedBorderNodeBounds(optionalDiagramElementEvent, borderNodesLayoutData, parentNode, borderNodeMovedOrResized.get(), layoutConfigurator);
        }

        for (NodeLayoutData currentBorderNode : borderNodesNotChanged) {
            Size size = this.nodeSizeProvider.getSize(optionalDiagramElementEvent, currentBorderNode, layoutConfigurator);
            if (!this.getRoundedSize(size).equals(this.getRoundedSize(currentBorderNode.getSize()))) {
                currentBorderNode.setSize(size);
                currentBorderNode.setChanged(true);
            }
        }

        // recompute the border node
        List<BorderNodesOnSide> borderNodesPerSide = this.snapAndOrderBorderNodes(borderNodesNotCreated, parentNode.getSize(), layoutConfigurator);

        // Add the new border nodes
        for (NodeLayoutData borderNodeToCreate : borderNodesToCreate) {
            this.updateCreatedBorderNodePosition(borderNodesPerSide, borderNodeToCreate, parentNode.getSize(), layoutConfigurator);
        }

        return borderNodesPerSide;
    }

    /**
     * Find space for created border nodes from North then close wise.<br/>
     * If not found the border node is added on north, at the right of the latest north border node.
     */
    private void updateCreatedBorderNodePosition(List<BorderNodesOnSide> borderNodesPerSide, NodeLayoutData createdBorderNode, Size parentSize, ISiriusWebLayoutConfigurator layoutConfigurator) {
        double gapBeweenTwoBorderNodes = Optional.ofNullable(layoutConfigurator.configureByType(createdBorderNode.getNodeType()).getProperty(CoreOptions.SPACING_PORT_PORT)).orElse(0.);
        double portOffset = Optional.ofNullable(layoutConfigurator.configureByType(createdBorderNode.getNodeType()).getProperty(CoreOptions.PORT_BORDER_OFFSET)).orElse(0.);

        boolean found = false;
        for (RectangleSide side : RectangleSide.values()) {
            Optional<BorderNodesOnSide> borderNodesOnSide = borderNodesPerSide.stream().filter(b -> side.equals(b.getSide())).findFirst();

            if (RectangleSide.NORTH.equals(side) || RectangleSide.SOUTH.equals(side)) {
                found = this.updateCreatedNorthSouthBorderNodePosition(side, borderNodesPerSide, borderNodesOnSide, createdBorderNode, parentSize, gapBeweenTwoBorderNodes, portOffset);
                if (found) {
                    break;
                }
            } else {
                found = this.updateCreatedEastWestBorderNodePosition(side, borderNodesPerSide, borderNodesOnSide, createdBorderNode, parentSize, gapBeweenTwoBorderNodes, portOffset);
                if (found) {
                    break;
                }
            }
        }

        if (!found) {
            // Add the border on the north
            List<NodeLayoutData> borderNodesOnNorth = new ArrayList<>();
            // @formatter:off
            borderNodesOnNorth = borderNodesPerSide.stream()
                    .filter(b -> RectangleSide.NORTH.equals(b.getSide()))
                    .findFirst()
                    .orElse(new BorderNodesOnSide(RectangleSide.NORTH, borderNodesOnNorth))
                    .getBorderNodes();
            // @formatter:on
            if (borderNodesOnNorth.isEmpty()) {
                createdBorderNode.setPosition(Position.at(gapBeweenTwoBorderNodes, -createdBorderNode.getSize().getHeight() + portOffset));
            } else {
                NodeLayoutData lastBorderNodeOnNorth = borderNodesOnNorth.get(borderNodesOnNorth.size() - 1);
                createdBorderNode.setPosition(Position.at(lastBorderNodeOnNorth.getPosition().getX() + lastBorderNodeOnNorth.getSize().getWidth() + gapBeweenTwoBorderNodes,
                        -createdBorderNode.getSize().getHeight() + portOffset));
            }
        }
    }

    private boolean updateCreatedEastWestBorderNodePosition(RectangleSide side, List<BorderNodesOnSide> borderNodesPerSide, Optional<BorderNodesOnSide> borderNodesOnSide,
            NodeLayoutData createdBorderNode, Size parentSize, double gapBeweenTwoBorderNodes, double portOffset) {
        double y = 0;
        boolean found = false;
        if (borderNodesOnSide.isEmpty() && (parentSize.getHeight() > createdBorderNode.getSize().getHeight())) {
            found = true;
            y = 0.;
        } else if (borderNodesOnSide.isPresent()) {
            List<NodeLayoutData> borderNodes = borderNodesOnSide.get().getBorderNodes();
            int nbBorderNodes = borderNodes.size();
            for (int i = 0; i < nbBorderNodes && !found; i++) {
                NodeLayoutData currentBorderNode = borderNodes.get(i);
                if (i == 0) {
                    double availableSpace = currentBorderNode.getPosition().getY();
                    if (availableSpace >= (createdBorderNode.getSize().getHeight() + gapBeweenTwoBorderNodes)) {
                        found = true;
                        y = 0;
                        break;
                    }
                }
                if (i == nbBorderNodes - 1) {
                    double availableSpace = parentSize.getHeight() - currentBorderNode.getPosition().getY() - currentBorderNode.getSize().getHeight();
                    if (availableSpace >= (createdBorderNode.getSize().getHeight() + gapBeweenTwoBorderNodes)) {
                        found = true;
                        y = currentBorderNode.getPosition().getY() + currentBorderNode.getSize().getHeight() + gapBeweenTwoBorderNodes;
                    }
                } else {
                    NodeLayoutData followingBorderNode = borderNodes.get(i + 1);
                    double availableSpaceBetweenCenterOfTwoNodes = followingBorderNode.getPosition().getY() - currentBorderNode.getPosition().getY() - currentBorderNode.getSize().getHeight();
                    if (availableSpaceBetweenCenterOfTwoNodes >= (createdBorderNode.getSize().getHeight() + 2 * gapBeweenTwoBorderNodes)) {
                        found = true;
                        y = currentBorderNode.getPosition().getY() + currentBorderNode.getSize().getHeight() + gapBeweenTwoBorderNodes;
                    }
                }
            }
        }
        if (found) {
            if (RectangleSide.WEST.equals(side)) {
                createdBorderNode.setPosition(Position.at(-createdBorderNode.getSize().getWidth() - portOffset, y));
            } else if (RectangleSide.EAST.equals(side)) {
                createdBorderNode.setPosition(Position.at(parentSize.getWidth() + portOffset, y));
            }
            if (borderNodesOnSide.isEmpty()) {
                borderNodesPerSide.add(new BorderNodesOnSide(side, Arrays.asList(createdBorderNode)));
            } else {
                borderNodesOnSide.get().getBorderNodes().add(createdBorderNode);
            }
        }

        return found;
    }

    private boolean updateCreatedNorthSouthBorderNodePosition(RectangleSide side, List<BorderNodesOnSide> borderNodesPerSide, Optional<BorderNodesOnSide> borderNodesOnSide,
            NodeLayoutData createdBorderNode, Size parentSize, double gapBeweenTwoBorderNodes, double portOffset) {
        double x = 0;
        boolean found = false;
        if (borderNodesOnSide.isEmpty() && (parentSize.getWidth() > createdBorderNode.getSize().getWidth())) {
            found = true;
            x = 0.;
        } else if (borderNodesOnSide.isPresent()) {
            List<NodeLayoutData> borderNodes = borderNodesOnSide.get().getBorderNodes();
            int nbBorderNodes = borderNodes.size();
            for (int i = 0; i < nbBorderNodes && !found; i++) {
                NodeLayoutData currentBorderNode = borderNodes.get(i);
                if (i == 0) {
                    double availableSpace = currentBorderNode.getPosition().getX();
                    if (availableSpace >= (createdBorderNode.getSize().getWidth() + gapBeweenTwoBorderNodes)) {
                        found = true;
                        x = 0;
                        break;
                    }
                }
                if (i == nbBorderNodes - 1) {
                    double availableSpace = parentSize.getWidth() - currentBorderNode.getPosition().getX() - currentBorderNode.getSize().getWidth();
                    if (availableSpace >= (createdBorderNode.getSize().getWidth() + gapBeweenTwoBorderNodes)) {
                        found = true;
                        x = currentBorderNode.getPosition().getX() + currentBorderNode.getSize().getWidth() + gapBeweenTwoBorderNodes;
                    }
                } else {
                    NodeLayoutData followingBorderNode = borderNodes.get(i + 1);
                    double availableSpaceBetweenCenterOfTwoNodes = followingBorderNode.getPosition().getX() - currentBorderNode.getPosition().getX() - currentBorderNode.getSize().getWidth();
                    if (availableSpaceBetweenCenterOfTwoNodes >= (createdBorderNode.getSize().getWidth() + 2 * gapBeweenTwoBorderNodes)) {
                        found = true;
                        x = currentBorderNode.getPosition().getX() + currentBorderNode.getSize().getWidth() + gapBeweenTwoBorderNodes;
                    }
                }
            }
        }
        if (found) {
            if (RectangleSide.NORTH.equals(side)) {
                createdBorderNode.setPosition(Position.at(x, -createdBorderNode.getSize().getHeight() - portOffset));
            } else if (RectangleSide.SOUTH.equals(side)) {
                createdBorderNode.setPosition(Position.at(x, parentSize.getHeight() + portOffset));
            }
            if (borderNodesOnSide.isEmpty()) {
                borderNodesPerSide.add(new BorderNodesOnSide(side, Arrays.asList(createdBorderNode)));
            } else {
                borderNodesOnSide.get().getBorderNodes().add(createdBorderNode);
            }
        }
        return found;
    }

    /**
     * Update the border node by snapping it to the parentRectangle, that is moving it to the closest point of the
     * parentRectangle.
     *
     * @param borderNodesLayoutData
     *            the border nodes which position is given in the rectangle upper right corner coordinates system
     * @return for each side of the given parentRectangle, the list of the updates border node
     */
    public List<BorderNodesOnSide> snapAndOrderBorderNodes(List<NodeLayoutData> borderNodesLayoutData, Size parentRectangle, ISiriusWebLayoutConfigurator layoutConfigurator) {
        EnumMap<RectangleSide, List<NodeLayoutData>> borderNodesPerSide = new EnumMap<>(RectangleSide.class);

        Geometry geometry = new Geometry();

        for (NodeLayoutData borderNodeLayoutData : borderNodesLayoutData) {
            double portOffset = layoutConfigurator.configureByType(borderNodeLayoutData.getNodeType()).getProperty(CoreOptions.PORT_BORDER_OFFSET).doubleValue();

            Bounds borderNodeRectangle = Bounds.newBounds().position(borderNodeLayoutData.getPosition()).size(borderNodeLayoutData.getSize()).build();
            PointOnRectangleInfo borderNodePositionOnSide = geometry.snapBorderNodeOnRectangle(borderNodeRectangle, parentRectangle, portOffset);
            // update the border node
            borderNodeLayoutData.setPosition(borderNodePositionOnSide.getPosition());

            borderNodesPerSide.computeIfAbsent(borderNodePositionOnSide.getSide(), side -> new ArrayList<>());
            borderNodesPerSide.get(borderNodePositionOnSide.getSide()).add(borderNodeLayoutData);
        }

        // @formatter:off
        return borderNodesPerSide.entrySet().stream()
                .map(entry -> {
                    // reorder from left to right or from top to bottom
                    List<NodeLayoutData> borderNodes = entry.getValue();
                    RectangleSide side = entry.getKey();
                    if (RectangleSide.NORTH.equals(side) || RectangleSide.SOUTH.equals(side)) {
                        Collections.sort(borderNodes, (bn1, bn2) -> Double.compare(bn1.getPosition().getX(), bn2.getPosition().getX()));
                    } else {
                        Collections.sort(borderNodes, (bn1, bn2) -> Double.compare(bn1.getPosition().getY(), bn2.getPosition().getY()));
                    }
                    return new BorderNodesOnSide(entry.getKey(), borderNodes);
                })
                .collect(Collectors.toList());
        // @formatter:on
    }

    private void updateChangedBorderNodeBounds(Optional<IDiagramEvent> optionalDiagramElementEvent, List<NodeLayoutData> borderNodesLayoutData, NodeLayoutData parentNode,
            NodeLayoutData changedBorderNode, ISiriusWebLayoutConfigurator layoutConfigurator) {
        Size newSize = Size.of(changedBorderNode.getSize().getWidth(), changedBorderNode.getSize().getHeight());
        Position newPosition = Position.at(changedBorderNode.getPosition().getX(), changedBorderNode.getPosition().getY());

        // @formatter:off
        Optional<ResizeEvent> resizeEventOpt = optionalDiagramElementEvent.filter(ResizeEvent.class::isInstance)
            .map(ResizeEvent.class::cast)
            .filter(resizeEvent -> changedBorderNode.getId().equals(resizeEvent.getNodeId()));
        // @formatter:on

        if (resizeEventOpt.isPresent()) {
            Position oldPosition = changedBorderNode.getPosition();

            newSize = Size.of(resizeEventOpt.get().getNewSize().getWidth(), resizeEventOpt.get().getNewSize().getHeight());
            double newX = oldPosition.getX() - resizeEventOpt.get().getPositionDelta().getX();
            double newY = oldPosition.getY() - resizeEventOpt.get().getPositionDelta().getY();
            newPosition = Position.at(newX, newY);

            // Limit the resize if the border node goes outside the parent node
            Size parentSize = parentNode.getSize();
            Position parentPosition = parentNode.getPosition();

            // TODO : just written and not tested
            List<BorderNodesOnSide> snapAndOrderBorderNodes = this.snapAndOrderBorderNodes(Arrays.asList(changedBorderNode), parentSize, layoutConfigurator);
            if (snapAndOrderBorderNodes.size() == 1) {
                RectangleSide side = snapAndOrderBorderNodes.get(0).getSide();
                if (RectangleSide.EAST.equals(side) || RectangleSide.WEST.equals(side)) {
                    if (newPosition.getY() + newSize.getHeight() > parentSize.getHeight()) {
                        newSize = Size.of(newPosition.getX(), parentSize.getHeight() - newPosition.getY());
                    } else if (newPosition.getY() < 0) {
                        newPosition = Position.at(newPosition.getX(), 0.);
                        newSize = Size.of(newSize.getWidth(), parentPosition.getY() - parentSize.getHeight());
                    }
                } else if (RectangleSide.NORTH.equals(side) || RectangleSide.SOUTH.equals(side)) {
                    if (newPosition.getX() + newSize.getWidth() > parentSize.getWidth()) {
                        newSize = Size.of(parentSize.getWidth() - newPosition.getX(), newSize.getHeight());
                    } else if (newPosition.getX() < 0) {
                        newPosition = Position.at(0., newPosition.getY());
                        newSize = Size.of(newPosition.getX() - newSize.getWidth(), newSize.getHeight());
                    }
                }
            }
        }

        // @formatter:off
        Optional<MoveEvent> moveEventOpt = optionalDiagramElementEvent.filter(MoveEvent.class::isInstance)
                .map(MoveEvent.class::cast)
                .filter(moveEvent -> changedBorderNode.getId().equals(moveEvent.getNodeId()));
        // @formatter:on
        if (moveEventOpt.isPresent()) {
            double newX = moveEventOpt.get().getNewPosition().getX();
            double newY = moveEventOpt.get().getNewPosition().getY();
            newPosition = Position.at(newX, newY);
        }

        // check that there is no overlapping with other border nodes
        List<NodeLayoutData> otherBorderNodes = new ArrayList<>(borderNodesLayoutData);
        otherBorderNodes.remove(changedBorderNode);
        Geometry geometry = new Geometry();
        Bounds changedBorderNodeBounds = Bounds.newBounds().position(newPosition).size(newSize).build();
        boolean isOverlapping = false;
        for (NodeLayoutData otherBorderNode : otherBorderNodes) {
            Bounds otherBorderNodeBounds = Bounds.newBounds().position(otherBorderNode.getPosition()).size(otherBorderNode.getSize()).build();
            isOverlapping = geometry.isOverlapping(changedBorderNodeBounds, otherBorderNodeBounds);
            if (isOverlapping) {
                break;
            }
        }

        if (!isOverlapping) {
            Position oldPosition = changedBorderNode.getPosition();
            if (!newPosition.equals(oldPosition)) {
                changedBorderNode.setPosition(newPosition);
                changedBorderNode.setChanged(true);
                changedBorderNode.setPinned(true);
            }
            Size oldSize = changedBorderNode.getSize();
            if (!newSize.equals(oldSize)) {
                changedBorderNode.setSize(newSize);
                changedBorderNode.setChanged(true);
                changedBorderNode.setPinned(true);
            }
        }
    }

    private Optional<NodeLayoutData> getBorderNodesMovedOrResized(Optional<IDiagramEvent> optionalDiagramElementEvent, List<NodeLayoutData> borderNodesLayoutData) {
        // @formatter:off
        Optional<NodeLayoutData> borderNodeMovedOrResized = optionalDiagramElementEvent
                .map(event -> {
                        String nodeId = null;
                        if (event instanceof MoveEvent) {
                            nodeId =  ((MoveEvent) event).getNodeId();
                        } else if (event instanceof ResizeEvent) {
                            nodeId =  ((ResizeEvent) event).getNodeId();
                        }
                        return nodeId;
                    })
                .map(nodeId -> {
                        return borderNodesLayoutData.stream()
                                .filter(borderNode -> nodeId.equals(borderNode.getId()))
                                .findFirst().orElse(null);
                    });

        // @formatter:on
        return borderNodeMovedOrResized;
    }

    public List<NodeLayoutData> getBorderNodesToCreate(List<NodeLayoutData> borderNodesLayoutData) {
        List<NodeLayoutData> newBorderNodes = borderNodesLayoutData.stream().filter(this::isNewBorderNode).collect(Collectors.toList());
        return newBorderNodes;
    }

    private boolean isNewBorderNode(NodeLayoutData borderNode) {
        return UNDEFINED_POSITION.equals(borderNode.getPosition()) && UNDEFINED_SIZE.equals(borderNode.getSize());
    }

    /**
     * Round size to 1/1000 of a pixel. It is needed when an image has a width or height with a very big decimal part
     * (e.g: 140.0004672837)
     *
     * @param size
     *            the {@link Size} to round.
     * @return the rounded size.
     */
    private Size getRoundedSize(Size size) {
        BigDecimal roundedWidth = BigDecimal.valueOf(size.getWidth()).setScale(4, RoundingMode.HALF_UP);
        BigDecimal roundedHeight = BigDecimal.valueOf(size.getHeight()).setScale(4, RoundingMode.HALF_UP);
        return Size.of(roundedWidth.doubleValue(), roundedHeight.doubleValue());
    }
}
