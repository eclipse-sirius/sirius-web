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

package org.eclipse.sirius.web.application.diagram.services;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEventRecorder;
import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * Diagram change event recorder.
 *
 * @author mcharfadi
 */
// TODO: to remove
public class DiagramChangeEventRecorder implements IRepresentationChangeEventRecorder {

    private final IRepresentationSearchService representationSearchService;

    public DiagramChangeEventRecorder(IRepresentationSearchService representationSearchService) {
        this.representationSearchService = representationSearchService;
    }

    @Override
    public void recordChanges(IEditingContext editingContext, IInput input, IRepresentation previousRepresentation, IRepresentation newRepresentation) {
//        var listChanges = new ArrayList<IRepresentationChangeEvent>();
//        if (input instanceof LayoutDiagramInput layoutDiagramInput && previousRepresentation instanceof Diagram previousDiagram) {
//            var nodeLayoutData = layoutDiagramInput.diagramLayoutData().nodeLayoutData().stream()
//                    .collect(Collectors.toMap(
//                            NodeLayoutDataInput::id,
//                            nodeLayoutDataInput -> new NodeLayoutData(nodeLayoutDataInput.id(), nodeLayoutDataInput.position(), nodeLayoutDataInput.size(), nodeLayoutDataInput.resizedByUser()),
//                            (oldValue, newValue) -> newValue
//                    ));
//            var edgeLayoutData = layoutDiagramInput.diagramLayoutData().edgeLayoutData().stream()
//                    .collect(Collectors.toMap(
//                            EdgeLayoutDataInput::id,
//                            edgeLayoutDataInput -> new EdgeLayoutData(edgeLayoutDataInput.id(), edgeLayoutDataInput.bendingPoints()),
//                            (previousLayoutData, newLayoutData) -> newLayoutData
//                    ));
//            new UUIDParser().parse(layoutDiagramInput.representationId())
//                    .map(representationId -> new LayoutDiagramRepresentationChange(representationId, previousDiagram.getLayoutData(), new DiagramLayoutData(nodeLayoutData, edgeLayoutData, Map.of())))
//                    .ifPresent(listChanges::add);
//
//        }
//        editingContext.getRepresentationChangesDescription().computeIfAbsent(input.id().toString(), key -> new ArrayList<>()).addAll(listChanges);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof IDiagramInput;
    }
}
