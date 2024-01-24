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
package org.eclipse.sirius.components.collaborative.diagrams.providers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodeInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropOnDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReferencePosition;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramBuilder;
import org.junit.jupiter.api.Test;

/**
 * Used to test the GenericDiagramToolReferencePositionProvider.
 *
 * @author frouene
 */
class GenericDiagramToolReferencePositionProviderTests {

    public static final String CONTAINER_ID = "containerId";

    @Test
    void canHandle() {
        var diagramToolReferencePositionProvider = new GenericDiagramToolReferencePositionProvider();

        InvokeSingleClickOnDiagramElementToolInput inputInvokeSingleClick = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), "", "",
                "", "", 0, 0, "");
        assertThat(diagramToolReferencePositionProvider.canHandle(inputInvokeSingleClick)).isTrue();
        DropNodeInput inputDropNode = new DropNodeInput(UUID.randomUUID(), "", "", "", "", 0, 0);
        assertThat(diagramToolReferencePositionProvider.canHandle(inputDropNode)).isTrue();
        DropOnDiagramInput inputDropOnDiagram = new DropOnDiagramInput(UUID.randomUUID(), "", "", "", List.of(), 0, 0);
        assertThat(diagramToolReferencePositionProvider.canHandle(inputDropOnDiagram)).isTrue();

        DeleteFromDiagramInput inputDelete = new DeleteFromDiagramInput(UUID.randomUUID(), "", "", List.of(), List.of(), null);
        assertThat(diagramToolReferencePositionProvider.canHandle(inputDelete)).isFalse();
    }

    @Test
    void getReferencePositionInvokeSingleClickOnDiagramElementTool() {
        var diagramToolReferencePositionProvider = new GenericDiagramToolReferencePositionProvider();
        var diagramId = UUID.randomUUID().toString();
        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(diagramId));
        // Test click on diagram
        InvokeSingleClickOnDiagramElementToolInput inputInvokeSingleClickOnDiagram = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), "", "",
                diagramId, "", 3, 2, "");
        var result = diagramToolReferencePositionProvider.getReferencePosition(inputInvokeSingleClickOnDiagram, diagramContext);
        this.assertResult(result, null, new Position(3, 2));
        // Test click on container
        InvokeSingleClickOnDiagramElementToolInput inputInvokeSingleClickOnContainer = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), "", "",
                CONTAINER_ID, "", 3, 2, "");
        result = diagramToolReferencePositionProvider.getReferencePosition(inputInvokeSingleClickOnContainer, diagramContext);
        this.assertResult(result, CONTAINER_ID, new Position(3, 2));
    }

    @Test
    void getReferencePositionDropOnDiagramElementTool() {
        var diagramToolReferencePositionProvider = new GenericDiagramToolReferencePositionProvider();
        var diagramId = UUID.randomUUID().toString();
        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(diagramId));
        // Test drop on diagram
        DropOnDiagramInput dropOnDiagramInput = new DropOnDiagramInput(UUID.randomUUID(), "", "",
                diagramId, List.of(), 3, 2);
        var result = diagramToolReferencePositionProvider.getReferencePosition(dropOnDiagramInput, diagramContext);
        this.assertResult(result, null, new Position(3, 2));
        // Test drop on container
        DropOnDiagramInput dropOnContainerInput = new DropOnDiagramInput(UUID.randomUUID(), "", "",
                CONTAINER_ID, List.of(), 3, 2);
        result = diagramToolReferencePositionProvider.getReferencePosition(dropOnContainerInput, diagramContext);
        this.assertResult(result, CONTAINER_ID, new Position(3, 2));
    }

    @Test
    void getReferencePositionDropOnNodeTool() {
        var diagramToolReferencePositionProvider = new GenericDiagramToolReferencePositionProvider();
        var diagramId = UUID.randomUUID().toString();
        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(diagramId));
        DropNodeInput dropNodeInput = new DropNodeInput(UUID.randomUUID(), "", "",
                "", CONTAINER_ID, 3, 2);
        var result = diagramToolReferencePositionProvider.getReferencePosition(dropNodeInput, diagramContext);
        this.assertResult(result, CONTAINER_ID, new Position(3, 2));
    }

    private void assertResult(ReferencePosition result, String parentId, Position position) {
        assertThat(result).isNotNull();
        assertThat(result.parentId()).isEqualTo(parentId);
        assertThat(result.position()).isNotNull();
        assertThat(result.position().x()).isEqualTo(position.x());
        assertThat(result.position().y()).isEqualTo(position.y());
    }
}
