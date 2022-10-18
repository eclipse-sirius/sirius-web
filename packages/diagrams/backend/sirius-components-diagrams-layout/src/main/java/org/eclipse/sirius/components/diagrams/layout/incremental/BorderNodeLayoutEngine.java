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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
import org.eclipse.sirius.components.diagrams.layout.api.Geometry;
import org.eclipse.sirius.components.diagrams.layout.api.PointOnRectangleInfo;
import org.eclipse.sirius.components.diagrams.layout.api.RectangleSide;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.BorderNodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodePositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.springframework.stereotype.Service;

/**
 * The engine used to layout border nodes.
 *
 * @author gcoutable
 */
@Service
public class BorderNodeLayoutEngine implements IBorderNodeLayoutEngine {

    private final NodePositionProvider nodePositionProvider;

    private final NodeSizeProvider nodeSizeProvider;

    private final BorderNodeLabelPositionProvider borderNodeLabelPositionProvider;

    public BorderNodeLayoutEngine(NodeSizeProvider nodeSizeProvider) {
        this.nodeSizeProvider = Objects.requireNonNull(nodeSizeProvider);
        this.borderNodeLabelPositionProvider = new BorderNodeLabelPositionProvider();
        this.nodePositionProvider = new NodePositionProvider();
    }

    @Override
    public List<BorderNodesOnSide> layoutBorderNodes(Optional<IDiagramEvent> optionalDiagramElementEvent, List<NodeLayoutData> borderNodesLayoutData, Bounds initialNodeBounds, Bounds newNodeBounds,
            ISiriusWebLayoutConfigurator layoutConfigurator) {
        List<BorderNodesOnSide> borderNodesPerSide = new ArrayList<>();
        if (!borderNodesLayoutData.isEmpty()) {
            for (NodeLayoutData nodeLayoutData : borderNodesLayoutData) {
                // 1- update the position of the border node if it has been explicitly moved
                this.updateBorderNodePosition(optionalDiagramElementEvent, nodeLayoutData);

                Size size = this.nodeSizeProvider.getSize(optionalDiagramElementEvent, nodeLayoutData, layoutConfigurator);
                if (!this.getRoundedSize(size).equals(this.getRoundedSize(nodeLayoutData.getSize()))) {
                    nodeLayoutData.setSize(size);
                    nodeLayoutData.setChanged(true);
                }
            }

            // 2- recompute the border node
            borderNodesPerSide = this.snapBorderNodes(borderNodesLayoutData, initialNodeBounds.getSize(), layoutConfigurator);

            // 3 - move the border node along the side according to the side change
            this.updateBorderNodeAccordingParentResize(optionalDiagramElementEvent, initialNodeBounds, newNodeBounds, borderNodesPerSide, borderNodesLayoutData.get(0).getParent().getId());

            // 4- set the label position if the border is newly created
            this.updateBorderNodeLabel(optionalDiagramElementEvent, borderNodesPerSide);
        }
        return borderNodesPerSide;
    }

    private void updateBorderNodeLabel(Optional<IDiagramEvent> optionalDiagramElementEvent, List<BorderNodesOnSide> borderNodesPerSideList) {

        for (BorderNodesOnSide borderNodesOnSide : borderNodesPerSideList) {
            RectangleSide side = borderNodesOnSide.getSide();
            List<NodeLayoutData> borderNodes = borderNodesOnSide.getBorderNodes();
            for (NodeLayoutData borderNodeLayoutData : borderNodes) {
                this.borderNodeLabelPositionProvider.updateLabelPosition(optionalDiagramElementEvent, side, borderNodeLayoutData);
            }
        }
    }

    /**
     * Move the border node along the side according to the parent Size changes.
     */
    private void updateBorderNodeAccordingParentResize(Optional<IDiagramEvent> optionalDiagramElementEvent, Bounds initialNodeBounds, Bounds newNodeBounds,
            List<BorderNodesOnSide> borderNodesPerSideList, String parentId) {
        // @formatter:off
        boolean isParentRectangleResized = optionalDiagramElementEvent
            .filter(ResizeEvent.class::isInstance)
            .map(ResizeEvent.class::cast)
            .map(ResizeEvent::getNodeId)
            .filter(parentId::equals)
            .isPresent();
        // @formatter:on

        if (isParentRectangleResized && !initialNodeBounds.equals(newNodeBounds)) {
            EnumMap<RectangleSide, Double> sideHomotheticRatio = this.getHomotheticRatio(initialNodeBounds.getSize(), newNodeBounds.getSize());

            Size initialSize = initialNodeBounds.getSize();
            Size newSize = newNodeBounds.getSize();

            for (BorderNodesOnSide borderNodesOnSide : borderNodesPerSideList) {
                RectangleSide side = borderNodesOnSide.getSide();
                List<NodeLayoutData> borderNodes = borderNodesOnSide.getBorderNodes();
                double homotheticRatio = sideHomotheticRatio.get(side);
                for (NodeLayoutData borderNodeLayoutData : borderNodes) {
                    // The border node position is done in the parent node coordinate system
                    Position position = borderNodeLayoutData.getPosition();
                    Size size = borderNodeLayoutData.getSize();
                    if (RectangleSide.NORTH.equals(side)) {
                        borderNodeLayoutData.setPosition(Position.at((position.getX() + size.getWidth() / 2) * homotheticRatio - size.getWidth() / 2, position.getY()));
                    } else if (RectangleSide.SOUTH.equals(side)) {
                        double dySouthShift = newSize.getHeight() - initialSize.getHeight();
                        borderNodeLayoutData.setPosition(Position.at((position.getX() + size.getWidth() / 2) * homotheticRatio - size.getWidth() / 2, position.getY() + dySouthShift));
                    } else if (RectangleSide.WEST.equals(side)) {
                        borderNodeLayoutData.setPosition(Position.at(position.getX(), (position.getY() + size.getHeight() / 2) * homotheticRatio - size.getHeight() / 2));
                    } else if (RectangleSide.EAST.equals(side)) {
                        double dxEastShift = newSize.getWidth() - initialSize.getWidth();
                        borderNodeLayoutData.setPosition(Position.at(position.getX() + dxEastShift, (position.getY() + size.getHeight() / 2) * homotheticRatio - size.getHeight() / 2));
                    }
                }
            }
        }
    }

    private void updateBorderNodePosition(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData nodeLayoutData) {
        // @formatter:off
        optionalDiagramElementEvent.filter(MoveEvent.class::isInstance)
            .map(MoveEvent.class::cast)
            .map(MoveEvent::getNodeId)
            .filter(nodeLayoutData.getId()::equals)
            .ifPresent(nodeId  -> {
                Position position = this.nodePositionProvider.getPosition(optionalDiagramElementEvent, nodeLayoutData);
                if (!position.equals(nodeLayoutData.getPosition())) {
                    nodeLayoutData.setPosition(position);
                    nodeLayoutData.setChanged(true);
                    nodeLayoutData.setPinned(true);
                }
        });
        // @formatter:on
    }

    /**
     * Update the border node by snapping it to the parentRectangle, that is moving it to the closest point of the
     * parentRectangle.
     *
     * @param borderNodesLayoutData
     *            the border nodes which position is given in the rectangle upper right corner coordinates system
     * @return for each side of the given parentRectangle, the list of the updates border node
     */
    private List<BorderNodesOnSide> snapBorderNodes(List<NodeLayoutData> borderNodesLayoutData, Size parentRectangle, ISiriusWebLayoutConfigurator layoutConfigurator) {
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
                .map(entry -> new BorderNodesOnSide(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        // @formatter:on
    }

    /**
     * This method compares two rectangles and for each side return the homothetic ratio.
     */
    private EnumMap<RectangleSide, Double> getHomotheticRatio(Size rectangle1, Size rectangle2) {
        EnumMap<RectangleSide, Double> sideToHomotheticRatio = new EnumMap<>(RectangleSide.class);
        double initialHeight = rectangle1.getHeight();
        double initialWidth = rectangle1.getWidth();
        double newHeight = rectangle2.getHeight();
        double newWidth = rectangle2.getWidth();

        double verticalRatio = 0;
        if (initialHeight != 0) {
            verticalRatio = newHeight / initialHeight;
        }
        double horizontalRatio = 0;
        if (initialWidth != 0) {
            horizontalRatio = newWidth / initialWidth;
        }
        sideToHomotheticRatio.put(RectangleSide.NORTH, horizontalRatio);
        sideToHomotheticRatio.put(RectangleSide.SOUTH, horizontalRatio);
        sideToHomotheticRatio.put(RectangleSide.EAST, verticalRatio);
        sideToHomotheticRatio.put(RectangleSide.WEST, verticalRatio);

        return sideToHomotheticRatio;
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
