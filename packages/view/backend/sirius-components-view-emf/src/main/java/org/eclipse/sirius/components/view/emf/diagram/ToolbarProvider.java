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

package org.eclipse.sirius.components.view.emf.diagram;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramToolbar;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolbarProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.springframework.stereotype.Service;

/**
 * Service providing the diagram toolbar.
 *
 * @author tgiraudet
 */
@Service
public class ToolbarProvider implements IToolbarProvider {

    private final IObjectSearchService objectSearchService;
    private final ViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;
    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    public ToolbarProvider(IObjectSearchService objectSearchService, ViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public Optional<DiagramToolbar> createDiagramToolbar(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription) {
        Optional<DiagramToolbar> toolbar = Optional.empty();

        var targetObjectId = diagramContext.diagram().getTargetObjectId();
        var targetElementOpt = this.objectSearchService.getObject(editingContext, targetObjectId);

        if (targetElementOpt.isPresent()) {
            var targetElement = targetElementOpt.get();

            VariableManager variableManager = new VariableManager();
            variableManager.put(VariableManager.SELF, targetElement);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(DiagramContext.DIAGRAM_CONTEXT, diagramContext);

            var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
            if (optionalDiagramDescription.isPresent()) {
                org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
                var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewDiagramDescription.eContainer());
                toolbar = Optional.ofNullable(viewDiagramDescription.getToolbar())
                    .filter(bar -> interpreter.evaluateExpression(variableManager.getVariables(), bar.getPreconditionExpression())
                        .asBoolean()
                        .orElse(true))
                    .map(bar -> new DiagramToolbar(bar.isDefaultExpanded()));
            }
        }
        return toolbar;
    }
}
