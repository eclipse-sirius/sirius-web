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
package org.eclipse.sirius.components.collaborative.diagrams.export.svg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
import org.eclipse.sirius.components.diagrams.layout.api.Geometry;
import org.springframework.stereotype.Service;

/**
 * Export an {@link Edge} to an SVG format.
 *
 * @author rpage
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class EdgeExportService {
    private final DiagramElementExportService elementExport;

    public EdgeExportService(DiagramElementExportService elementExport) {
        this.elementExport = Objects.requireNonNull(elementExport);
    }

    public StringBuilder export(Edge edge, Map<String, NodeAndContainerId> id2NodeHierarchy) {
        StringBuilder edgeExport = new StringBuilder();

        if (edge.getState() != ViewModifier.Hidden) {
            float opacity = 1;
            if (edge.getState() == ViewModifier.Faded) {
                opacity = 0.25f;
            }

            EdgeStyle style = edge.getStyle();
            List<Position> lineRoutingPoints = this.addEdgeEndPositions(edge, id2NodeHierarchy);

            // Should add source and target intersection in routingPoints.
            if (lineRoutingPoints.size() > 1) {
                edgeExport.append("<g style=\"opacity:" + opacity + "\">");
                edgeExport.append(this.exportLine(style, lineRoutingPoints));
                edgeExport.append(this.exportAdditionals(style, lineRoutingPoints));
                edgeExport.append(this.exportEdgeLabels(edge));
                edgeExport.append("</g>");
            }
        }

        return edgeExport;
    }

    private List<Position> addEdgeEndPositions(Edge edge, Map<String, NodeAndContainerId> id2NodeHierarchy) {
        List<Position> routedPoint = new ArrayList<>();
        String sourceId = edge.getSourceId();
        String targetId = edge.getTargetId();

        Optional<NodeAndContainerId> optionalSource = Optional.ofNullable(id2NodeHierarchy.get(sourceId));
        Optional<NodeAndContainerId> optionalTarget = Optional.ofNullable(id2NodeHierarchy.get(targetId));

        if (optionalSource.isPresent() && optionalTarget.isPresent()) {
            NodeAndContainerId sourceNodeAndContainerId = optionalSource.get();
            NodeAndContainerId targetNodeAndContainerId = optionalTarget.get();
            Size sourceNodeSize = sourceNodeAndContainerId.getNode().getSize();
            Size targetNodeSize = targetNodeAndContainerId.getNode().getSize();

            Position sourceAbsolutePosition = this.getAbsolutePosition(sourceNodeAndContainerId, id2NodeHierarchy);
            Position targetAbsolutePosition = this.getAbsolutePosition(targetNodeAndContainerId, id2NodeHierarchy);

            Position sourceEdgeAnchorAbsolutePosition = this.getEdgeAnchorAbsolutePosition(edge.getSourceAnchorRelativePosition(), sourceAbsolutePosition, sourceNodeSize);
            Position targetEdgeAnchorAbsolutePosition = this.getEdgeAnchorAbsolutePosition(edge.getTargetAnchorRelativePosition(), targetAbsolutePosition, targetNodeSize);

            Position sourceDirectionPoint = sourceEdgeAnchorAbsolutePosition;
            Position targetDirectionPoint = targetEdgeAnchorAbsolutePosition;
            List<Position> routingPoints = edge.getRoutingPoints();
            if (routingPoints.size() > 0) {
                targetDirectionPoint = routingPoints.get(0);
                sourceDirectionPoint = routingPoints.get(routingPoints.size() - 1);
            }

            Geometry geometry = new Geometry();
            Bounds sourceBounds = Bounds.newBounds().position(sourceAbsolutePosition).size(sourceNodeSize).build();
            Bounds targetBounds = Bounds.newBounds().position(targetAbsolutePosition).size(targetNodeSize).build();
            Optional<Position> optionalSourceIntersection = geometry.getIntersection(targetDirectionPoint, sourceEdgeAnchorAbsolutePosition, sourceBounds);
            Optional<Position> optionalTargetIntersection = geometry.getIntersection(sourceDirectionPoint, targetEdgeAnchorAbsolutePosition, targetBounds);

            if (optionalSourceIntersection.isPresent() && optionalTargetIntersection.isPresent()) {
                routedPoint.add(optionalSourceIntersection.get());
                routedPoint.addAll(routingPoints);
                routedPoint.add(optionalTargetIntersection.get());
            }
        }

        return routedPoint;
    }

    private Position getEdgeAnchorAbsolutePosition(Ratio edgeEndAnchorRelativePosition, Position edgeEndAbsolutePosition, Size edgeEndSize) {
        double edgeAnchorAbsoluteX = edgeEndAbsolutePosition.getX() + edgeEndSize.getWidth() * edgeEndAnchorRelativePosition.getX();
        double edgeAnchorAbsoluteY = edgeEndAbsolutePosition.getY() + edgeEndSize.getHeight() * edgeEndAnchorRelativePosition.getY();
        return Position.at(edgeAnchorAbsoluteX, edgeAnchorAbsoluteY);
    }

    private Position getAbsolutePosition(NodeAndContainerId nodeAndContainerId, Map<String, NodeAndContainerId> id2NodeHierarchy) {
        Position absolutePosition = nodeAndContainerId.getNode().getPosition();
        Optional<NodeAndContainerId> optionalContainer = Optional.ofNullable(id2NodeHierarchy.get(nodeAndContainerId.getContainerId()));

        while (optionalContainer.isPresent()) {
            NodeAndContainerId container = optionalContainer.get();
            absolutePosition = Position.at(absolutePosition.getX() + container.getNode().getPosition().getX(), absolutePosition.getY() + container.getNode().getPosition().getY());
            optionalContainer = Optional.ofNullable(id2NodeHierarchy.get(container.getContainerId()));
        }

        return absolutePosition;
    }

    private StringBuilder exportEdgeLabels(Edge edge) {
        StringBuilder labelsExport = new StringBuilder();
        if (edge.getBeginLabel() != null) {
            labelsExport.append(this.elementExport.exportLabelAsText(edge.getBeginLabel(), 1));
        }
        if (edge.getCenterLabel() != null) {
            labelsExport.append(this.elementExport.exportLabelAsText(edge.getCenterLabel(), 1));
        }
        if (edge.getEndLabel() != null) {
            labelsExport.append(this.elementExport.exportLabelAsText(edge.getEndLabel(), 1));
        }
        return labelsExport;
    }

    private StringBuilder exportLine(EdgeStyle style, List<Position> rootingPoints) {
        StringBuilder lineExport = new StringBuilder();
        lineExport.append("<g>");

        lineExport.append("<path ");
        lineExport.append("d=\"" + this.exportLinePath(rootingPoints) + "\" ");
        lineExport.append("style=\"" + this.exportLineStyle(style) + "\"/>");

        return lineExport.append("</g>");
    }

    private StringBuilder exportStrokeDasharray(EdgeStyle style) {
        StringBuilder dashArrayExport = new StringBuilder();
        String lineStyleName = style.getLineStyle().toString();

        if (lineStyleName.equals("Dash")) {
            dashArrayExport.append("stroke-dasharray: 5,5;");
        } else if (lineStyleName.equals("Dot")) {
            dashArrayExport.append("stroke-dasharray: 2,2;");
        } else if (lineStyleName.equals("Dash_Dot")) {
            dashArrayExport.append("stroke-dasharray: 10,5,2,2,2,5;");
        }

        return dashArrayExport;
    }

    private StringBuilder exportLineStyle(EdgeStyle style) {
        StringBuilder styleExport = new StringBuilder();

        styleExport.append("stroke: " + style.getColor() + "; ");
        styleExport.append("stroke-width: " + style.getSize() + "px; ");
        styleExport.append("pointer-events: stroke; ");
        styleExport.append("fill: none; ");

        return styleExport.append(this.exportStrokeDasharray(style));
    }

    private StringBuilder exportLinePath(List<Position> routingPoints) {
        StringBuilder pathExport = new StringBuilder();

        Position[] positions = routingPoints.toArray(Position[]::new);
        Position firstPoint = positions[0];
        pathExport.append("M ");
        pathExport.append(firstPoint.getX());
        pathExport.append(",");
        pathExport.append(firstPoint.getY());
        for (int i = 1; i < positions.length; i++) {
            Position point = positions[i];
            pathExport.append(" L ");
            pathExport.append(point.getX());
            pathExport.append(",");
            pathExport.append(point.getY());
        }
        return pathExport;
    }

    private StringBuilder exportAdditionals(EdgeStyle style, List<Position> rootingPoints) {
        StringBuilder additionalsExport = new StringBuilder();

        additionalsExport.append(this.exportArrow(rootingPoints.get(1), rootingPoints.get(0), style.getSourceArrow().toString(), style));
        return additionalsExport.append(this.exportArrow(rootingPoints.get(rootingPoints.size() - 2), rootingPoints.get(rootingPoints.size() - 1), style.getTargetArrow().toString(), style));
    }

    private StringBuilder exportArrow(Position p1, Position p2, String arrowStyle, EdgeStyle style) {
        StringBuilder arrowExport;

        String styleName = arrowStyle.toString();

        if (styleName.equals("OutputArrow")) {
            arrowExport = this.exportOutputArrow(p1, p2, style);
        } else if (styleName.equals("InputArrow")) {
            arrowExport = this.exportInputArrow(p1, p2, style);
        } else if (styleName.equals("OutputClosedArrow")) {
            arrowExport = this.exportOutputClosedArrow(p1, p2, style, false);
        } else if (styleName.equals("InputClosedArrow")) {
            arrowExport = this.exportInputClosedArrow(p1, p2, style, false);
        } else if (styleName.equals("OutputFillClosedArrow")) {
            arrowExport = this.exportOutputClosedArrow(p1, p2, style, true);
        } else if (styleName.equals("InputFillClosedArrow")) {
            arrowExport = this.exportInputClosedArrow(p1, p2, style, true);
        } else if (styleName.equals("Diamond")) {
            arrowExport = this.exportDiamondArrow(p1, p2, style, false);
        } else if (styleName.equals("FillDiamond")) {
            arrowExport = this.exportDiamondArrow(p1, p2, style, true);
        } else if (styleName.equals("InputArrowWithDiamond")) {
            arrowExport = this.exportInputArrowWithDiamond(p1, p2, style, false);
        } else if (styleName.equals("InputArrowWithFillDiamond")) {
            arrowExport = this.exportInputArrowWithDiamond(p1, p2, style, true);
        } else if (styleName.equals("Circle")) {
            arrowExport = this.exportCircleArrow(p1, p2, style, false);
        } else if (styleName.equals("FillCircle")) {
            arrowExport = this.exportCircleArrow(p1, p2, style, true);
        } else {
            arrowExport = new StringBuilder();
        }

        return arrowExport;
    }

    private StringBuilder exportOutputArrow(Position p1, Position p2, EdgeStyle style) {
        StringBuilder arrowExport = new StringBuilder();
        double offsetX = p2.getX() + style.getSize();
        double offsetY = p2.getY();
        double rotationAngle = Math.toDegrees(this.angle(p2, p1));
        double rotationX = p2.getX();
        double rotationY = p2.getY();
        StringBuilder path = this.exportBasicArrowPath(style.getSize());
        StringBuilder transformation = this.exportArrowTransformation(rotationAngle, rotationX, rotationY, offsetX, offsetY);
        StringBuilder arrowStyle = this.exportArrowStyle(style, style.getColor());
        return arrowExport.append(this.buildArrowPath(path, transformation, arrowStyle));
    }

    private StringBuilder exportInputArrow(Position p1, Position p2, EdgeStyle style) {
        StringBuilder arrowExport = new StringBuilder();
        double offsetX = p2.getX();
        double offsetY = p2.getY();
        double rotationAngle = Math.toDegrees(this.angle(p1, p2));
        double rotationX = p2.getX();
        double rotationY = p2.getY();
        StringBuilder path = this.exportBasicArrowPath(style.getSize());
        StringBuilder transformation = this.exportArrowTransformation(rotationAngle, rotationX, rotationY, offsetX, offsetY);
        StringBuilder arrowStyle = this.exportArrowStyle(style, "none");
        return arrowExport.append(this.buildArrowPath(path, transformation, arrowStyle));
    }

    private StringBuilder exportOutputClosedArrow(Position p1, Position p2, EdgeStyle style, boolean fill) {
        StringBuilder arrowExport = new StringBuilder();
        String fillColor;
        if (!fill) {
            fillColor = "#ffffff";
        } else {
            fillColor = style.getColor();
        }
        double offsetX = p2.getX() + 5.5 + style.getSize();
        double offsetY = p2.getY();
        double rotationAngle = Math.toDegrees(this.angle(p2, p1));
        double rotationX = p2.getX();
        double rotationY = p2.getY();
        StringBuilder path = this.exportBasicClosedArrowPath(style.getSize());
        StringBuilder transformation = this.exportArrowTransformation(rotationAngle, rotationX, rotationY, offsetX, offsetY);
        StringBuilder arrowStyle = this.exportArrowStyle(style, fillColor);
        return arrowExport.append(this.buildArrowPath(path, transformation, arrowStyle));
    }

    private StringBuilder exportInputClosedArrow(Position p1, Position p2, EdgeStyle style, boolean fill) {
        StringBuilder arrowExport = new StringBuilder();
        String fillColor;
        if (!fill) {
            fillColor = "#ffffff";
        } else {
            fillColor = style.getColor();
        }
        double offsetX = p2.getX();
        double offsetY = p2.getY();
        double rotationAngle = Math.toDegrees(this.angle(p1, p2));
        double rotationX = p2.getX();
        double rotationY = p2.getY();
        StringBuilder path = this.exportBasicClosedArrowPath(style.getSize());
        StringBuilder transformation = this.exportArrowTransformation(rotationAngle, rotationX, rotationY, offsetX, offsetY);
        StringBuilder arrowStyle = this.exportArrowStyle(style, fillColor);
        arrowExport.append(this.buildArrowPath(path, transformation, arrowStyle));
        return arrowExport;
    }

    private StringBuilder exportDiamondArrow(Position p1, Position p2, EdgeStyle style, boolean fill) {
        StringBuilder arrowExport = new StringBuilder();
        String fillColor;
        if (!fill) {
            fillColor = "#ffffff";
        } else {
            fillColor = style.getColor();
        }
        double offsetX = p2.getX();
        double offsetY = p2.getY();
        double rotationAngle = Math.toDegrees(this.angle(p2, p1));
        double rotationX = p2.getX();
        double rotationY = p2.getY();
        StringBuilder path = this.exportBasicDiamondPath(style.getSize());
        StringBuilder transformation = this.exportArrowTransformation(rotationAngle, rotationX, rotationY, offsetX, offsetY);
        StringBuilder arrowStyle = this.exportArrowStyle(style, fillColor);
        arrowExport.append(this.buildArrowPath(path, transformation, arrowStyle));
        return arrowExport;
    }

    private StringBuilder exportInputArrowWithDiamond(Position p1, Position p2, EdgeStyle style, boolean fill) {
        StringBuilder arrowExport = new StringBuilder();
        String fillColor;
        if (!fill) {
            fillColor = "#ffffff";
        } else {
            fillColor = style.getColor();
        }
        double offsetX = p2.getX() + style.getSize();
        double offsetY = p2.getY();
        double rotationAngle = Math.toDegrees(this.angle(p2, p1));
        double rotationX = p2.getX();
        double rotationY = p2.getY();
        StringBuilder path = this.exportInputArrowWithDiamondPath(style.getSize());
        StringBuilder transformation = this.exportArrowTransformation(rotationAngle, rotationX, rotationY, offsetX, offsetY);
        StringBuilder arrowStyle = this.exportArrowStyle(style, fillColor);
        return arrowExport.append(this.buildArrowPath(path, transformation, arrowStyle));
    }

    private StringBuilder exportCircleArrow(Position p1, Position p2, EdgeStyle styleObject, boolean fill) {
        StringBuilder arrowExport = new StringBuilder();
        String fillColor;
        if (!fill) {
            fillColor = "#ffffff";
        } else {
            fillColor = styleObject.getColor();
        }
        double rotationAngle = Math.toDegrees(this.angle(p2, p1));
        double rotationX = p2.getX();
        double rotationY = p2.getY();
        double translateX = p2.getX() + 5;
        double translateY = p2.getY();

        StringBuilder arrowTransformation = this.exportArrowTransformation(rotationAngle, rotationX, rotationY, translateX, translateY);
        arrowExport.append("<circle ");

        arrowExport.append("transform=\"");
        arrowExport.append(arrowTransformation);
        arrowExport.append("\" ");

        arrowExport.append("r=\"");
        arrowExport.append(4 + styleObject.getSize());
        arrowExport.append("\" ");

        arrowExport.append("style=\"");
        arrowExport.append(this.exportArrowStyle(styleObject, fillColor));
        arrowExport.append("\" ");

        return arrowExport.append("/>");
    }

    private StringBuilder exportBasicClosedArrowPath(int strokeWidth) {
        return this.exportBasicArrowPath(strokeWidth).append("z");
    }

    private StringBuilder exportInputArrowWithDiamondPath(int strokeWidth) {
        return this.exportBasicDiamondPath(strokeWidth).append(this.exportOffsetArrowPath(strokeWidth));
    }

    private StringBuilder exportBasicArrowPath(int strokeWidth) {
        // Path definitions scaled with the stroke-width style attribute
        StringBuilder basicArrowPathExport = new StringBuilder();
        basicArrowPathExport.append("m ");
        basicArrowPathExport.append(-5 - strokeWidth);
        basicArrowPathExport.append(" ");
        basicArrowPathExport.append(-3.5 - strokeWidth);
        basicArrowPathExport.append(" L 0 0 L ");
        basicArrowPathExport.append(-5 - strokeWidth);
        basicArrowPathExport.append(" ");
        return basicArrowPathExport.append(3.5 + strokeWidth);
    }

    private StringBuilder exportBasicDiamondPath(int strokeWidth) {
        // Path definitions scaled with the stroke-width style attribute
        StringBuilder basicDiamondPathExport = new StringBuilder();

        basicDiamondPathExport.append("m 0 0 L ");
        basicDiamondPathExport.append(5 + strokeWidth);
        basicDiamondPathExport.append(" ");
        basicDiamondPathExport.append(-3.5 - strokeWidth);
        basicDiamondPathExport.append(" L ");
        basicDiamondPathExport.append(10 + strokeWidth * 2);
        basicDiamondPathExport.append(" 0 L ");
        basicDiamondPathExport.append(5 + strokeWidth);
        basicDiamondPathExport.append(" ");
        basicDiamondPathExport.append(3.5 + strokeWidth);
        return basicDiamondPathExport.append(" z");
    }

    private StringBuilder exportOffsetArrowPath(int strokeWidth) {
        // Path definitions scaled with the stroke-width style attribute
        StringBuilder offsetArrowPathExport = new StringBuilder();

        offsetArrowPathExport.append("m ");
        offsetArrowPathExport.append((5 + strokeWidth) * 2);
        offsetArrowPathExport.append(" 0 L ");
        offsetArrowPathExport.append((5 + strokeWidth) * 2 + (5 + strokeWidth));
        offsetArrowPathExport.append(" ");
        offsetArrowPathExport.append(-3.5 - strokeWidth);
        offsetArrowPathExport.append(" M ");
        offsetArrowPathExport.append((5 + strokeWidth) * 2);
        offsetArrowPathExport.append(" 0 L ");
        offsetArrowPathExport.append((5 + strokeWidth) * 2 + (5 + strokeWidth));
        offsetArrowPathExport.append(" ");
        return offsetArrowPathExport.append(3.5 + strokeWidth);
    }

    private StringBuilder buildArrowPath(StringBuilder path, StringBuilder transformation, StringBuilder style) {
        StringBuilder arrowPath = new StringBuilder();
        arrowPath.append("<path ");
        arrowPath.append("d=\"");
        arrowPath.append(path);
        arrowPath.append("\" ");

        arrowPath.append("transform=\"");
        arrowPath.append(transformation);
        arrowPath.append("\" ");

        arrowPath.append("style=\"");
        arrowPath.append(style);
        return arrowPath.append("\"/>");
    }

    private StringBuilder exportArrowStyle(EdgeStyle style, String fillColor) {
        StringBuilder arrowStyleExport = new StringBuilder();

        arrowStyleExport.append("stroke:");
        arrowStyleExport.append(style.getColor());
        arrowStyleExport.append("; ");

        arrowStyleExport.append("stroke-width:");
        arrowStyleExport.append(style.getSize());
        arrowStyleExport.append("; ");

        arrowStyleExport.append("fill:");
        arrowStyleExport.append(fillColor);
        return arrowStyleExport.append(";");
    }

    private StringBuilder exportArrowTransformation(double rotationAngle, double rotationX, double rotationY, double translateX, double translateY) {
        StringBuilder transformation = new StringBuilder();
        transformation.append("rotate(");
        transformation.append(rotationAngle);
        transformation.append(" ");
        transformation.append(rotationX);
        transformation.append(" ");
        transformation.append(rotationY);
        transformation.append(")");

        transformation.append(" translate(");
        transformation.append(translateX);
        transformation.append(" ");
        transformation.append(translateY);
        return transformation.append(")");
    }

    private double angle(Position a, Position b) {
        return Math.atan2(b.getY() - a.getY(), b.getX() - a.getX());
    }
}
