/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.collaborative.diagrams.services.DeleteFromDiagramService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.RemoveEdgeEvent;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.Success;
import org.junit.jupiter.api.Test;

/**
 * Tests of the delete from diagram event handler.
 *
 * @author sbegaudeau
 */
public class DeleteFromDiagramServiceTests {

    private static final String NODE_ID = "nodeId";

    private static final String EDGE_ID = "edgeId";

    private final IObjectSearchService objectSearchService = new IObjectSearchService.NoOp() {
        @Override
        public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
            return Optional.of(new Object());
        }
    };

    private final IDiagramQueryService diagramQueryService = new IDiagramQueryService.NoOp() {
        @Override
        public Optional<Node> findNodeById(Diagram diagram, String nodeId) {
            if (nodeId.equals(NODE_ID)) {
                return Optional.of(new TestDiagramBuilder().getNode(NODE_ID, true));
            }
            return Optional.empty();
        }

        @Override
        public Optional<Edge> findEdgeById(Diagram diagram, String edgeId) {
            return Optional.of(new TestDiagramBuilder().getEdge(EDGE_ID, NODE_ID, NODE_ID));
        }
    };

    private final IDiagramDescriptionService diagramDescriptionService = new IDiagramDescriptionService.NoOp() {
        @Override
        public Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, String nodeDescriptionId) {
            return Optional.of(new TestDiagramDescriptionBuilder().getNodeDescription(UUID.randomUUID().toString(), variableManager -> List.of()));
        }

        @Override
        public Optional<EdgeDescription> findEdgeDescriptionById(DiagramDescription diagramDescription, String edgeDescriptionId) {
            return Optional.of(new TestDiagramDescriptionBuilder().getEdgeDescription(UUID.randomUUID()
                    .toString(), this.findNodeDescriptionById(diagramDescription, UUID.randomUUID().toString()).get()));
        }
    };

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
        @Override
        public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
            return Optional.of(new TestDiagramDescriptionBuilder().getDiagramDescription(UUID.randomUUID().toString(), List.of(), List.of()));
        }
    };

    @Test
    public void testNodeSemanticDeletionFromDiagram() {
        var deleteFromDiagramService = new DeleteFromDiagramService(this.objectSearchService, this.diagramQueryService, this.diagramDescriptionService, this.representationDescriptionSearchService,
                new ICollaborativeDiagramMessageService.NoOp());

        var nodeIds = List.of(NODE_ID);


        DiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));
        var resultStatus = deleteFromDiagramService.deleteFromDiagram(new IEditingContext.NoOp(), diagramContext, nodeIds, List.of());
        assertThat(resultStatus).isInstanceOf(Success.class);
    }

    @Test
    public void testEdgeSemanticDeletionFromDiagram() {
        var deleteFromDiagramService = new DeleteFromDiagramService(this.objectSearchService, this.diagramQueryService, this.diagramDescriptionService, this.representationDescriptionSearchService,
                new ICollaborativeDiagramMessageService.NoOp());

        var edgeIds = List.of(EDGE_ID);


        DiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));
        var resultStatus = deleteFromDiagramService.deleteFromDiagram(new IEditingContext.NoOp(), diagramContext, edgeIds, List.of());


        assertThat(resultStatus).isInstanceOf(Success.class);

        assertThat(diagramContext.diagramEvents()).hasSize(1);
        IDiagramEvent diagramEvent = diagramContext.diagramEvents().get(0);
        assertThat(diagramEvent).isInstanceOf(RemoveEdgeEvent.class);
        RemoveEdgeEvent removeEdgeEvent = (RemoveEdgeEvent) diagramEvent;
        assertThat(removeEdgeEvent.getEdgeIds()).contains(EDGE_ID);
    }
}
