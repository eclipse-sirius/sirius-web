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
package org.eclipse.sirius.components.collaborative.diagrams.export.svg.experimental;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.springframework.stereotype.Service;

/**
 * Used to export an experimental {@link Edge} to SVG.
 *
 * @author gcoutable
 */
@Service
public class SVGEdgeExportService {

    private final SVGDiagramElementExportService elementExport;

    public SVGEdgeExportService(SVGDiagramElementExportService elementExport) {
        this.elementExport = Objects.requireNonNull(elementExport);
    }

    public String exportEdges(List<Edge> edges, DiagramLayoutData diagramLayoutData) {
        StringBuffer edgesFactory = new StringBuffer();

        for (Edge edge : edges) {

            if (edge.getState() != ViewModifier.Hidden) {
                float opacity = 1;
                if (edge.getState() == ViewModifier.Faded) {
                    opacity = 0.25f;
                }

                EdgeStyle style = edge.getStyle();

                List<Position> lineRoutingPoints = this.addEdgeEndPositions(edge, diagramLayoutData);

                if (lineRoutingPoints.size() > 1) {
                    edgesFactory.append("""
                    <g style="opacity:#opacity;">
                      #line
                      #additionals
                      #edgeLabels
                    </g>
                            """
                            .replace("#opacity", Float.toString(opacity))
                            .replace("#line", this.exportLine(style, lineRoutingPoints))
                            .replace("#additionals", this.exportAdditionals(style, lineRoutingPoints))
                            .replace("#edgeLabels", this.exportEdgeLabels(edge, diagramLayoutData)));
                }
            }

        }

        return edgesFactory.toString();
    }


    private List<Position> addEdgeEndPositions(Edge edge, DiagramLayoutData diagramLayoutData) {
        return null;
    }

    private String exportLine(EdgeStyle style, List<Position> lineRoutingPoints) {
        return null;
    }

    private String exportAdditionals(EdgeStyle style, List<Position> lineRoutingPoints) {
        return null;
    }

    private String exportEdgeLabels(Edge edge, DiagramLayoutData diagramLayoutData) {
        StringBuffer labelsExport = new StringBuffer();
        if (edge.getBeginLabel() != null) {
            labelsExport.append(this.elementExport.exportLabelAsText(edge.getBeginLabel(), 1, diagramLayoutData));
        }
        if (edge.getCenterLabel() != null) {
            labelsExport.append(this.elementExport.exportLabelAsText(edge.getCenterLabel(), 1, diagramLayoutData));
        }
        if (edge.getEndLabel() != null) {
            labelsExport.append(this.elementExport.exportLabelAsText(edge.getEndLabel(), 1, diagramLayoutData));
        }
        return labelsExport.toString();
    }

}
