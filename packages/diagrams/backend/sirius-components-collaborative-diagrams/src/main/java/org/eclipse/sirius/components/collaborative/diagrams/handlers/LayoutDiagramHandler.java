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

package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handler for LayoutDiagramInput.
 *
 * @author mcharfadi
 */
@Service
public class LayoutDiagramHandler implements IDiagramEventHandler {

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof LayoutDiagramInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);
        if (diagramInput instanceof LayoutDiagramInput layoutDiagramInput) {

            var diagram = diagramContext.getDiagram();
            var nodeLayoutData = layoutDiagramInput.diagramLayoutData().nodeLayoutData().stream()
                    .collect(Collectors.toMap(
                            NodeLayoutDataInput::id,
                            nodeLayoutDataInput -> new NodeLayoutData(nodeLayoutDataInput.id(), nodeLayoutDataInput.position(), nodeLayoutDataInput.size(), nodeLayoutDataInput.resizedByUser()),
                            (oldValue, newValue) -> newValue
                    ));

            var layoutData = new DiagramLayoutData(nodeLayoutData, Map.of(), Map.of());
            var laidOutDiagram = Diagram.newDiagram(diagram)
                    .layoutData(layoutData)
                    .build();

            changeDescription = new ChangeDescription(ChangeKind.LAYOUT_DIAGRAM, diagramInput.representationId(), diagramInput);
            changeDescription.getParameters().put(IDiagramEventHandler.NEXT_DIAGRAM_PARAMETER, laidOutDiagram);
            payloadSink.tryEmitValue(new SuccessPayload(layoutDiagramInput.id()));
        }
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
