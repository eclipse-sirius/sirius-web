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

package org.eclipse.sirius.components.collaborative.diagrams.changes;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEventRecorder;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Diagram change event recorder.
 *
 * @author mcharfadi
 */
@Service
public class DiagramChangeEventRecorder implements IRepresentationChangeEventRecorder {

    private final IRepresentationSearchService representationSearchService;

    public DiagramChangeEventRecorder(IRepresentationSearchService representationSearchService) {
        this.representationSearchService = representationSearchService;
    }

    @Override
    public List<IRepresentationChangeEvent> getChanges(IEditingContext editingContext, IInput input) {
        if (input instanceof IDiagramInput diagramInput) {
            var currentDiagram = representationSearchService.findById(editingContext, diagramInput.representationId(), Diagram.class);
            if (currentDiagram.isPresent() && diagramInput instanceof LayoutDiagramInput layoutDiagramInput) {
                var nodeLayoutData = layoutDiagramInput.diagramLayoutData().nodeLayoutData().stream()
                        .collect(Collectors.toMap(
                                NodeLayoutDataInput::id,
                                nodeLayoutDataInput -> new NodeLayoutData(nodeLayoutDataInput.id(), nodeLayoutDataInput.position(), nodeLayoutDataInput.size(), nodeLayoutDataInput.resizedByUser()),
                                (oldValue, newValue) -> newValue
                        ));

                return new ArrayList<>(List.of(new LayoutDiagramRepresentionChange(UUID.fromString(diagramInput.representationId()), currentDiagram.get().getLayoutData().nodeLayoutData(), nodeLayoutData)));
            }
        }

        return new ArrayList<>(List.of());
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof IDiagramInput;
    }
}
