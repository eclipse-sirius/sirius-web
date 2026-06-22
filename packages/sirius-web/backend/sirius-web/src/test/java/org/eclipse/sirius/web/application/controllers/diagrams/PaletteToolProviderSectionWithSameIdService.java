/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.controllers.diagrams;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.view.emf.diagram.api.IPaletteToolsProvider;
import org.springframework.stereotype.Service;

/**
 * Service to add another tool section with an existing id.
 * @see PaletteToolProviderControllerTests
 *
 * @author mcharfadi
 */
@Service
public class PaletteToolProviderSectionWithSameIdService implements IPaletteToolsProvider {

    @Override
    public List<ToolSection> createExtraToolSections(IEditingContext editingContext, DiagramContext diagramContext, Object diagramElementDescription, Object diagramElement) {
        var toolSections = new ArrayList<ToolSection>();
        if (diagramElementDescription instanceof IDiagramElementDescription nodeElementDescription) {
            var tool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("edit")
                    .label("extraTool")
                    .iconURL(List.of(DiagramImageConstants.EDIT_SVG))
                    .targetDescriptions(List.of(nodeElementDescription))
                    .withImpactAnalysis(false)
                    .withDeletionConfirmationDialog(false)
                    .keyBindings(List.of())
                    .build();

            toolSections.add(ToolSection.newToolSection("edit-section")
                    .label("Edit")
                    .iconURL(List.of())
                    .tools(List.of(tool))
                    .build());
        }
        return toolSections;
    }

    @Override
    public List<ITool> createQuickAccessTools(IEditingContext editingContext, DiagramContext diagramContext, Object diagramElementDescription, Object diagramElement) {
        return List.of();
    }
}
