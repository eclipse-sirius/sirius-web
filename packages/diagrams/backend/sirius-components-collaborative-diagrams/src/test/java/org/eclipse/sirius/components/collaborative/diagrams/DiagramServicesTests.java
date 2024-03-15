/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.handlers.TestDiagramBuilder;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.events.UpdateCollapsingStateEvent;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the diagram services.
 *
 * @author gdaniel
 */
public class DiagramServicesTests {

    @Test
    public void testCollapse() {
        var diagramServices = new DiagramServices();
        var diagramServicesContext = new DiagramService(new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString())));
        var nodeId = UUID.randomUUID().toString();
        var nodesToCollapse = List.of(new TestDiagramBuilder().getNode(nodeId, false));
        diagramServices.collapse(diagramServicesContext, nodesToCollapse);

        assertThat(diagramServicesContext.getDiagramContext().getDiagramEvents()).hasSize(1);
        assertThat(diagramServicesContext.getDiagramContext().getDiagramEvents().get(0)).isInstanceOf(UpdateCollapsingStateEvent.class);
        var updateCollapsingStateEvent = (UpdateCollapsingStateEvent) diagramServicesContext.getDiagramContext().getDiagramEvents().get(0);
        assertThat(updateCollapsingStateEvent.collapsingState()).isEqualTo(CollapsingState.COLLAPSED);
        assertThat(updateCollapsingStateEvent.diagramElementId()).isEqualTo(nodeId);
    }

    @Test
    public void testExpand() {
        var diagramServices = new DiagramServices();
        var diagramServicesContext = new DiagramService(new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString())));
        var nodeId = UUID.randomUUID().toString();
        var nodesToCollapse = List.of(new TestDiagramBuilder().getNode(nodeId, false));
        diagramServices.expand(diagramServicesContext, nodesToCollapse);

        assertThat(diagramServicesContext.getDiagramContext().getDiagramEvents()).hasSize(1);
        assertThat(diagramServicesContext.getDiagramContext().getDiagramEvents().get(0)).isInstanceOf(UpdateCollapsingStateEvent.class);
        var updateCollapsingStateEvent = (UpdateCollapsingStateEvent) diagramServicesContext.getDiagramContext().getDiagramEvents().get(0);
        assertThat(updateCollapsingStateEvent.collapsingState()).isEqualTo(CollapsingState.EXPANDED);
        assertThat(updateCollapsingStateEvent.diagramElementId()).isEqualTo(nodeId);
    }

}
