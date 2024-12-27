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

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.diagrams.services.ISingleClickOnMultipleDiagramElementHandler;
import org.eclipse.sirius.components.collaborative.diagrams.services.ISingleClickOnOneDiagramElementHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service to handle hide element tool execution.
 *
 * @author mcharfadi
 */
@Service
public class HideElementToolHandler implements ISingleClickOnOneDiagramElementHandler, ISingleClickOnMultipleDiagramElementHandler {

    public static final String HIDE_ELEMENT_TOOL_ID = "hide";

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, String toolId, String diagramElementId) {
        return HideElementToolHandler.HIDE_ELEMENT_TOOL_ID.equals(toolId);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, DiagramContext diagramContext, String toolId, String diagramElementId, List<ToolVariable> variables) {
        diagramContext.diagramEvents().add(new HideDiagramElementEvent(Set.of(diagramElementId), true));
        return new Success();
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, String toolId, List<String> diagramElementIds) {
        return HideElementToolHandler.HIDE_ELEMENT_TOOL_ID.equals(toolId);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, DiagramContext diagramContext, String toolId, List<String> diagramElementIds, List<ToolVariable> variables) {
        Set<String> targetSet = new HashSet<>(diagramElementIds);
        diagramContext.diagramEvents().add(new HideDiagramElementEvent(targetSet, true));
        return new Success();
    }
}
