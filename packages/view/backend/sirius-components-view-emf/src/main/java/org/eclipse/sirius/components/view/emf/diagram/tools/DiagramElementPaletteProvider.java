/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IDiagramElementPaletteProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IDiagramPaletteProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IEdgePaletteProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.INodePaletteProvider;
import org.springframework.stereotype.Service;

/**
 * Used to compute the palette of a diagram element.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramElementPaletteProvider implements IDiagramElementPaletteProvider {

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IDiagramPaletteProvider diagramPaletteProvider;

    private final INodePaletteProvider nodePaletteProvider;

    private final IEdgePaletteProvider edgePaletteProvider;

    public DiagramElementPaletteProvider(IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IDiagramPaletteProvider diagramPaletteProvider, INodePaletteProvider nodePaletteProvider, IEdgePaletteProvider edgePaletteProvider) {
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.diagramPaletteProvider = Objects.requireNonNull(diagramPaletteProvider);
        this.nodePaletteProvider = Objects.requireNonNull(nodePaletteProvider);
        this.edgePaletteProvider = Objects.requireNonNull(edgePaletteProvider);
    }

    @Override
    public Palette getPalette(IEditingContext editingContext, AQLInterpreter interpreter, DiagramDescription diagramDescription, DiagramContext diagramContext, Object diagramElementDescription, Object diagramElement, VariableManager variableManager) {
        Palette palette = null;

        if (diagramElement instanceof Diagram) {
            var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
            if (optionalDiagramDescription.isPresent()) {
                palette = this.diagramPaletteProvider.getDiagramPalette(interpreter, diagramDescription, optionalDiagramDescription.get(), variableManager);
            }
        } else if (diagramElement instanceof Node && diagramElementDescription instanceof NodeDescription nodeDescription) {
            variableManager.put(Node.SELECTED_NODE, diagramElement);
            palette = this.nodePaletteProvider.getNodePalette(editingContext, interpreter, diagramDescription, diagramContext, nodeDescription, diagramElement, variableManager);
        } else if (diagramElement instanceof Edge && diagramElementDescription instanceof EdgeDescription edgeDescription) {
            variableManager.put(Edge.SELECTED_EDGE, diagramElement);
            palette = this.edgePaletteProvider.getEdgePalette(editingContext, interpreter, diagramDescription, diagramContext, edgeDescription, diagramElement, variableManager);
        }

        return palette;
    }
}
