/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;

/**
 * Mock of the diagram creation service.
 *
 * @author sbegaudeau
 */
public class MockDiagramCreationService implements IDiagramCreationService {

    private int count;

    private Diagram diagram;

    public MockDiagramCreationService(Diagram diagram) {
        this.diagram = Objects.requireNonNull(diagram);
    }

    @Override
    public Diagram create(Object targetObject, DiagramDescription diagramDescription, IEditingContext editingContext) {
        return this.diagram;
    }

    @Override
    public Optional<Diagram> refresh(IEditingContext editingContext, IDiagramContext diagramContext) {
        this.count = this.count + 1;

        var node = this.diagram.getNodes().get(0);
        var insideLabel = InsideLabel.newInsideLabel(node.getInsideLabel())
                .text(String.valueOf(this.count))
                .build();


        this.diagram = Diagram.newDiagram(this.diagram)
                .nodes(List.of(Node.newNode(node).insideLabel(insideLabel).build()))
                .build();
        return Optional.of(this.diagram);
    }

}
