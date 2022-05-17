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
package org.eclipse.sirius.components.collaborative.diagrams.export;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;

import org.eclipse.sirius.components.collaborative.diagrams.export.svg.DiagramElementExportService;
import org.eclipse.sirius.components.collaborative.diagrams.export.svg.DiagramExportService;
import org.eclipse.sirius.components.collaborative.diagrams.export.svg.EdgeExportService;
import org.eclipse.sirius.components.collaborative.diagrams.export.svg.ImageRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.export.svg.NodeExportService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Test class for the diagram svg export.
 *
 * @author gcoutable
 */
public class DiagramExportServiceTests {

    private DiagramExportService getDiagramExportService() throws URISyntaxException {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        ImageRegistry imageRegistry = new ImageRegistry(httpServletRequest, false);

        DiagramElementExportService diagramElementExportService = new DiagramElementExportService(imageRegistry);

        EdgeExportService edgeExportService = new EdgeExportService(diagramElementExportService);

        NodeExportService nodeExportService = new NodeExportService(diagramElementExportService);

        DiagramExportService diagramExportService = new DiagramExportService(nodeExportService, edgeExportService, imageRegistry);

        return diagramExportService;
    }

    @Test
    public void testExportDiagramWithEdge() throws URISyntaxException {
        String firstTargetObjectId = "First"; //$NON-NLS-1$
        String secondTargetObjectId = "Second"; //$NON-NLS-1$

        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram("root") //$NON-NLS-1$
            .nodes()
                .rectangleNode(firstTargetObjectId).at(10, 10).of(20, 20).and()
                .rectangleNode(secondTargetObjectId).at(50, 10).of(20, 20).and()
                .and()
            .edge("one") //$NON-NLS-1$
                .from(firstTargetObjectId).at(0.75, 0.5)
                .to(secondTargetObjectId).at(0.25, 0.5)
                .and()
            .build();
        // @formatter:on

        DiagramExportService diagramExportService = this.getDiagramExportService();
        String export = diagramExportService.export(diagram);
        assertThat(export).contains("<path d=\""); //$NON-NLS-1$
    }

    @Test
    public void testExportEmptyDiagram() throws URISyntaxException {
        Diagram emptyDiagram = TestLayoutDiagramBuilder.diagram("Root").nodes().and().build(); //$NON-NLS-1$
        DiagramExportService diagramExportService = this.getDiagramExportService();
        String export = diagramExportService.export(emptyDiagram);
        assertThat(export).isNotBlank();
    }

}
