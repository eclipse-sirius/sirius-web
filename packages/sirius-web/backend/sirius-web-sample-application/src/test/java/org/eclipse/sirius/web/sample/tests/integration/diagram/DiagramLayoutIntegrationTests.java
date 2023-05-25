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
package org.eclipse.sirius.web.sample.tests.integration.diagram;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.export.api.IImageRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.export.api.ISVGDiagramExportService;
import org.eclipse.sirius.components.collaborative.diagrams.export.svg.DiagramElementExportService;
import org.eclipse.sirius.components.collaborative.diagrams.export.svg.DiagramExportService;
import org.eclipse.sirius.components.collaborative.diagrams.export.svg.EdgeExportService;
import org.eclipse.sirius.components.collaborative.diagrams.export.svg.NodeExportService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.DiagramLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.experimental.DiagramLayoutConfigurationProvider;
import org.eclipse.sirius.components.diagrams.layout.experimental.DiagramLayoutEngine;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Integration tests for diagram layout engine.
 *
 * @author gcoutable
 */
public class DiagramLayoutIntegrationTests {

    private final Logger logger = LoggerFactory.getLogger(DiagramLayoutIntegrationTests.class);

    @Test
    @DisplayName("Given a diagram, when the layout is performed, then valid layout data are computed")
    public void givenDiagramWhenLayoutIsPerformedThenValidLayoutDataAreComputed() {
        TestDiagramBuilder builder = new TestDiagramBuilder();
        var diagram = Diagram.newDiagram(builder.getDiagram("diagram"))
                .nodes(List.of(builder.getNode("node", true)))
                .build();

        DiagramLayoutConfiguration diagramLayoutConfiguration = new DiagramLayoutConfigurationProvider().getDiagramLayoutConfiguration(diagram, new DiagramLayoutData(Map.of(), Map.of(), Map.of()), Optional.empty());

        DiagramLayoutData diagramLayoutData = new DiagramLayoutEngine().layout(diagramLayoutConfiguration);

        Diagram layoutedDiagram = Diagram.newDiagram(diagram)
                .layoutData(diagramLayoutData)
                .build();

        ISVGDiagramExportService svgDiagramExportService = this.getDiagramExportService();
        var svg = svgDiagramExportService.export(layoutedDiagram);
        this.logger.info(svg);
    }

    private DiagramExportService getDiagramExportService() {
        IImageRegistry imageRegistry = new IImageRegistry.NoOp();
        DiagramElementExportService diagramElementExportService = new DiagramElementExportService(imageRegistry);
        EdgeExportService edgeExportService = new EdgeExportService(diagramElementExportService);
        NodeExportService nodeExportService = new NodeExportService(diagramElementExportService);
        return new DiagramExportService(nodeExportService, edgeExportService, imageRegistry);
    }

}
