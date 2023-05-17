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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.export.api.IImageRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.export.api.ISVGDiagramExportService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.springframework.stereotype.Service;

/**
 * Used to export an experimental {@link Diagram} to SVG.
 *
 * @author gcoutable
 */
@Service
public class SVGDiagramExportService implements ISVGDiagramExportService {

    private final SVGNodeExportService nodeExport;

    private final SVGEdgeExportService edgeExport;

    private final IImageRegistry imageRegistry;

    public SVGDiagramExportService(SVGNodeExportService nodeExport, SVGEdgeExportService edgeExport, IImageRegistry imageRegistry) {
        this.nodeExport = Objects.requireNonNull(nodeExport);
        this.edgeExport = Objects.requireNonNull(edgeExport);
        this.imageRegistry = Objects.requireNonNull(imageRegistry);
    }

    @Override
    public String export(Diagram diagram) {
        return """
                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="#elementsCoordinates" tabindex="0" style="cursor: pointer; outline: transparent solid 1px;">
                  <g transform="scale(1) translate(0,0)">
                    #nodes
                    #edges
                  </g>
                  #imageSymbols
                </svg>
                """
            .replace("#elementsCoordinates", this.computeElementsCoordinates())
            .replace("#nodes", this.nodeExport.exportNodes(diagram.getNodes(), diagram.getLayoutData()))
            .replace("#edges", this.edgeExport.exportEdges(diagram.getEdges(), diagram.getLayoutData()))
            .replace("#imageSymbols", this.imageRegistry.getReferencedImageSymbols());
    }


    private String computeElementsCoordinates() {
        return null;
    }

}
