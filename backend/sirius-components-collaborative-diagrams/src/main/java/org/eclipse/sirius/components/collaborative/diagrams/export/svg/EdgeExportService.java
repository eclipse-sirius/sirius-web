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
package org.eclipse.sirius.components.collaborative.diagrams.export.svg;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.Position;
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

    public StringBuilder export(Edge edge) {
        StringBuilder edgeExport = new StringBuilder();
        EdgeStyle style = edge.getStyle();
        List<Position> lineRootingPoints = edge.getRoutingPoints();

        edgeExport.append("<g>"); //$NON-NLS-1$
        edgeExport.append(this.exportLine(style, lineRootingPoints));
        edgeExport.append(this.exportAdditionals(style, lineRootingPoints));
        edgeExport.append(this.exportEdgeLabels(edge));

        return edgeExport.append("</g>"); //$NON-NLS-1$
    }

    private StringBuilder exportEdgeLabels(Edge edge) {
        StringBuilder labelsExport = new StringBuilder();
        if (edge.getBeginLabel() != null) {
            labelsExport.append(this.elementExport.exportLabel(edge.getBeginLabel()));
        }
        if (edge.getCenterLabel() != null) {
            labelsExport.append(this.elementExport.exportLabel(edge.getCenterLabel()));
        }
        if (edge.getEndLabel() != null) {
            labelsExport.append(this.elementExport.exportLabel(edge.getEndLabel()));
        }
        return labelsExport;
    }

    private StringBuilder exportLine(EdgeStyle style, List<Position> rootingPoints) {
        StringBuilder lineExport = new StringBuilder();
        lineExport.append("<g>"); //$NON-NLS-1$

        lineExport.append("<path "); //$NON-NLS-1$
        lineExport.append("d=\"" + this.exportLinePath(rootingPoints) + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
        lineExport.append("style=\"" + this.exportLineStyle(style) + "\"/>"); //$NON-NLS-1$ //$NON-NLS-2$

        return lineExport.append("</g>"); //$NON-NLS-1$
    }

    private StringBuilder exportStrokeDasharray(EdgeStyle style) {
        StringBuilder dashArrayExport = new StringBuilder();
        String lineStyleName = style.getLineStyle().toString();

        if (lineStyleName.equals("Dash")) { //$NON-NLS-1$
            dashArrayExport.append("stroke-dasharray: 5,5;"); //$NON-NLS-1$
        } else if (lineStyleName.equals("Dot")) { //$NON-NLS-1$
            dashArrayExport.append("stroke-dasharray: 2,2;"); //$NON-NLS-1$
        } else if (lineStyleName.equals("Dash_Dot")) { //$NON-NLS-1$
            dashArrayExport.append("stroke-dasharray: 10,5,2,2,2,5;"); //$NON-NLS-1$
        }

        return dashArrayExport;
    }

    private StringBuilder exportLineStyle(EdgeStyle style) {
        StringBuilder styleExport = new StringBuilder();

        styleExport.append("stroke: " + style.getColor() + "; "); //$NON-NLS-1$ //$NON-NLS-2$
        styleExport.append("stroke-width: " + style.getSize() + "px; "); //$NON-NLS-1$ //$NON-NLS-2$
        styleExport.append("pointer-events: stroke; "); //$NON-NLS-1$
        styleExport.append("fill: none; "); //$NON-NLS-1$

        return styleExport.append(this.exportStrokeDasharray(style));
    }

    private StringBuilder exportLinePath(List<Position> rootingPoints) {
        StringBuilder pathExport = new StringBuilder();

        Position[] positions = rootingPoints.toArray(Position[]::new);
        Position firstPoint = positions[0];
        pathExport.append("M "); //$NON-NLS-1$
        pathExport.append(firstPoint.getX());
        pathExport.append(","); //$NON-NLS-1$
        pathExport.append(firstPoint.getY());
        for (int i = 1; i < positions.length; i++) {
            Position point = positions[i];
            pathExport.append(" L "); //$NON-NLS-1$
            pathExport.append(point.getX());
            pathExport.append(","); //$NON-NLS-1$
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

        if (styleName.equals("OutputArrow")) { //$NON-NLS-1$
            arrowExport = this.exportOutputArrow(p1, p2, style);
        } else if (styleName.equals("InputArrow")) { //$NON-NLS-1$
            arrowExport = this.exportInputArrow(p1, p2, style);
        } else if (styleName.equals("OutputClosedArrow")) { //$NON-NLS-1$
            arrowExport = this.exportOutputClosedArrow(p1, p2, style, false);
        } else if (styleName.equals("InputClosedArrow")) { //$NON-NLS-1$
            arrowExport = this.exportInputClosedArrow(p1, p2, style, false);
        } else if (styleName.equals("OutputFillClosedArrow")) { //$NON-NLS-1$
            arrowExport = this.exportOutputClosedArrow(p1, p2, style, true);
        } else if (styleName.equals("InputFillClosedArrow")) { //$NON-NLS-1$
            arrowExport = this.exportInputClosedArrow(p1, p2, style, true);
        } else if (styleName.equals("Diamond")) { //$NON-NLS-1$
            arrowExport = this.exportDiamondArrow(p1, p2, style, false);
        } else if (styleName.equals("FillDiamond")) { //$NON-NLS-1$
            arrowExport = this.exportDiamondArrow(p1, p2, style, true);
        } else if (styleName.equals("InputArrowWithDiamond")) { //$NON-NLS-1$
            arrowExport = this.exportInputArrowWithDiamond(p1, p2, style, false);
        } else if (styleName.equals("InputArrowWithFillDiamond")) { //$NON-NLS-1$
            arrowExport = this.exportInputArrowWithDiamond(p1, p2, style, true);
        } else if (styleName.equals("Circle")) { //$NON-NLS-1$
            arrowExport = this.exportCircleArrow(p1, p2, style, false);
        } else if (styleName.equals("FillCircle")) { //$NON-NLS-1$
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
        StringBuilder arrowStyle = this.exportArrowStyle(style, "none"); //$NON-NLS-1$
        return arrowExport.append(this.buildArrowPath(path, transformation, arrowStyle));
    }

    private StringBuilder exportOutputClosedArrow(Position p1, Position p2, EdgeStyle style, boolean fill) {
        StringBuilder arrowExport = new StringBuilder();
        String fillColor;
        if (!fill) {
            fillColor = "#ffffff"; //$NON-NLS-1$
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
            fillColor = "#ffffff"; //$NON-NLS-1$
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
            fillColor = "#ffffff"; //$NON-NLS-1$
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
            fillColor = "#ffffff"; //$NON-NLS-1$
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
            fillColor = "#ffffff"; //$NON-NLS-1$
        } else {
            fillColor = styleObject.getColor();
        }
        double rotationAngle = Math.toDegrees(this.angle(p2, p1));
        double rotationX = p2.getX();
        double rotationY = p2.getY();
        double translateX = p2.getX() + 5;
        double translateY = p2.getY();

        StringBuilder arrowTransformation = this.exportArrowTransformation(rotationAngle, rotationX, rotationY, translateX, translateY);
        arrowExport.append("<circle "); //$NON-NLS-1$

        arrowExport.append("transform=\""); //$NON-NLS-1$
        arrowExport.append(arrowTransformation);
        arrowExport.append("\" "); //$NON-NLS-1$

        arrowExport.append("r=\""); //$NON-NLS-1$
        arrowExport.append(4 + styleObject.getSize());
        arrowExport.append("\" "); //$NON-NLS-1$

        arrowExport.append("style=\""); //$NON-NLS-1$
        arrowExport.append(this.exportArrowStyle(styleObject, fillColor));
        arrowExport.append("\" "); //$NON-NLS-1$

        return arrowExport.append("/>"); //$NON-NLS-1$
    }

    private StringBuilder exportBasicClosedArrowPath(int strokeWidth) {
        return this.exportBasicArrowPath(strokeWidth).append("z"); //$NON-NLS-1$
    }

    private StringBuilder exportInputArrowWithDiamondPath(int strokeWidth) {
        return this.exportBasicDiamondPath(strokeWidth).append(this.exportOffsetArrowPath(strokeWidth));
    }

    private StringBuilder exportBasicArrowPath(int strokeWidth) {
        // Path definitions scaled with the stroke-width style attribute
        StringBuilder basicArrowPathExport = new StringBuilder();
        basicArrowPathExport.append("m "); //$NON-NLS-1$
        basicArrowPathExport.append(-5 - strokeWidth);
        basicArrowPathExport.append(" "); //$NON-NLS-1$
        basicArrowPathExport.append(-3.5 - strokeWidth);
        basicArrowPathExport.append(" L 0 0 L "); //$NON-NLS-1$
        basicArrowPathExport.append(-5 - strokeWidth);
        basicArrowPathExport.append(" "); //$NON-NLS-1$
        return basicArrowPathExport.append(3.5 + strokeWidth);
    }

    private StringBuilder exportBasicDiamondPath(int strokeWidth) {
        // Path definitions scaled with the stroke-width style attribute
        StringBuilder basicDiamondPathExport = new StringBuilder();

        basicDiamondPathExport.append("m 0 0 L "); //$NON-NLS-1$
        basicDiamondPathExport.append(5 + strokeWidth);
        basicDiamondPathExport.append(" "); //$NON-NLS-1$
        basicDiamondPathExport.append(-3.5 - strokeWidth);
        basicDiamondPathExport.append(" L "); //$NON-NLS-1$
        basicDiamondPathExport.append(10 + strokeWidth * 2);
        basicDiamondPathExport.append(" 0 L "); //$NON-NLS-1$
        basicDiamondPathExport.append(5 + strokeWidth);
        basicDiamondPathExport.append(" "); //$NON-NLS-1$
        basicDiamondPathExport.append(3.5 + strokeWidth);
        return basicDiamondPathExport.append(" z"); //$NON-NLS-1$
    }

    private StringBuilder exportOffsetArrowPath(int strokeWidth) {
        // Path definitions scaled with the stroke-width style attribute
        StringBuilder offsetArrowPathExport = new StringBuilder();

        offsetArrowPathExport.append("m "); //$NON-NLS-1$
        offsetArrowPathExport.append((5 + strokeWidth) * 2);
        offsetArrowPathExport.append(" 0 L "); //$NON-NLS-1$
        offsetArrowPathExport.append((5 + strokeWidth) * 2 + (5 + strokeWidth));
        offsetArrowPathExport.append(" "); //$NON-NLS-1$
        offsetArrowPathExport.append(-3.5 - strokeWidth);
        offsetArrowPathExport.append(" M "); //$NON-NLS-1$
        offsetArrowPathExport.append((5 + strokeWidth) * 2);
        offsetArrowPathExport.append(" 0 L "); //$NON-NLS-1$
        offsetArrowPathExport.append((5 + strokeWidth) * 2 + (5 + strokeWidth));
        offsetArrowPathExport.append(" "); //$NON-NLS-1$
        return offsetArrowPathExport.append(3.5 + strokeWidth);
    }

    private StringBuilder buildArrowPath(StringBuilder path, StringBuilder transformation, StringBuilder style) {
        StringBuilder arrowPath = new StringBuilder();
        arrowPath.append("<path "); //$NON-NLS-1$
        arrowPath.append("d=\""); //$NON-NLS-1$
        arrowPath.append(path);
        arrowPath.append("\" "); //$NON-NLS-1$

        arrowPath.append("transform=\""); //$NON-NLS-1$
        arrowPath.append(transformation);
        arrowPath.append("\" "); //$NON-NLS-1$

        arrowPath.append("style=\""); //$NON-NLS-1$
        arrowPath.append(style);
        return arrowPath.append("\"/>"); //$NON-NLS-1$
    }

    private StringBuilder exportArrowStyle(EdgeStyle style, String fillColor) {
        StringBuilder arrowStyleExport = new StringBuilder();

        arrowStyleExport.append("stroke:"); //$NON-NLS-1$
        arrowStyleExport.append(style.getColor());
        arrowStyleExport.append("; "); //$NON-NLS-1$

        arrowStyleExport.append("stroke-width:"); //$NON-NLS-1$
        arrowStyleExport.append(style.getSize());
        arrowStyleExport.append("; "); //$NON-NLS-1$

        arrowStyleExport.append("fill:"); //$NON-NLS-1$
        arrowStyleExport.append(fillColor);
        return arrowStyleExport.append(";"); //$NON-NLS-1$
    }

    private StringBuilder exportArrowTransformation(double rotationAngle, double rotationX, double rotationY, double translateX, double translateY) {
        StringBuilder transformation = new StringBuilder();
        transformation.append("rotate("); //$NON-NLS-1$
        transformation.append(rotationAngle);
        transformation.append(" "); //$NON-NLS-1$
        transformation.append(rotationX);
        transformation.append(" "); //$NON-NLS-1$
        transformation.append(rotationY);
        transformation.append(")"); //$NON-NLS-1$

        transformation.append(" translate("); //$NON-NLS-1$
        transformation.append(translateX);
        transformation.append(" "); //$NON-NLS-1$
        transformation.append(translateY);
        return transformation.append(")"); //$NON-NLS-1$
    }

    private double angle(Position a, Position b) {
        return Math.atan2(b.getY() - a.getY(), b.getX() - a.getX());
    }
}
