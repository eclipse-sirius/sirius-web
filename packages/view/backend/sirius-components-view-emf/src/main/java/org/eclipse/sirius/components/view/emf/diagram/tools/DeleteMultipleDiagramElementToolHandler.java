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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.diagrams.services.IDeleteFromDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.services.ISingleClickOnMultipleDiagramElementHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IStatus;
import org.springframework.stereotype.Service;

/**
 * Service to handle delete multiple elements tool execution.
 *
 * @author frouene
 */
@Service
public class DeleteMultipleDiagramElementToolHandler implements ISingleClickOnMultipleDiagramElementHandler {

    public static final String DELETE_ELEMENT_TOOL_ID = "semantic-delete";

    private final IDeleteFromDiagramService deleteFromDiagramService;

    public DeleteMultipleDiagramElementToolHandler(IDeleteFromDiagramService deleteFromDiagramService) {
        this.deleteFromDiagramService = Objects.requireNonNull(deleteFromDiagramService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, String toolId, List<String> diagramElementIds) {
        return DELETE_ELEMENT_TOOL_ID.equals(toolId);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, DiagramContext diagramContext, String toolId, List<String> diagramElementIds, List<ToolVariable> variables) {
        return this.deleteFromDiagramService.deleteFromDiagram(editingContext, diagramContext, diagramElementIds, variables);
    }
}
