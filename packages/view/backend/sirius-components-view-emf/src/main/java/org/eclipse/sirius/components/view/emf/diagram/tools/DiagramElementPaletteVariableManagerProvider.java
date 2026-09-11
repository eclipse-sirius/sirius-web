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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramVariables;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.representations.IOperationValidator;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramConversionData;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IDiagramElementPaletteVariableManagerProvider;
import org.eclipse.sirius.components.view.emf.editingcontext.api.IViewEditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to provide the variable manager used to evaluate the precondition of the tools of the palette on a single click on a diagram element.
 *
 * @author mcharfadi
 */
@Service
public class DiagramElementPaletteVariableManagerProvider implements IDiagramElementPaletteVariableManagerProvider {

    private final IOperationValidator operationValidator;

    public DiagramElementPaletteVariableManagerProvider(IOperationValidator operationValidator) {
        this.operationValidator = Objects.requireNonNull(operationValidator);
    }

    @Override
    public Optional<VariableManager> getVariableManager(IEditingContext editingContext, DiagramContext diagramContext, Object diagramElement, Object semanticElement) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(RepresentationVariables.SELF.name(), semanticElement);
        variableManager.put(CoreVariables.EDITING_CONTEXT.name(), editingContext);
        variableManager.put(CoreVariables.ENVIRONMENT.name(), new Environment(Environment.SIRIUS_COMPONENTS));
        variableManager.put(DiagramVariables.DIAGRAM_CONTEXT.name(), diagramContext);
        variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));

        variableManager.put(DiagramVariables.SELECTED_NODE.name(), Optional.ofNullable(diagramElement)
                .filter(Node.class::isInstance)
                .map(Node.class::cast)
                .orElse(null));
        variableManager.put(DiagramVariables.SELECTED_EDGE.name(), Optional.ofNullable(diagramElement)
                .filter(Edge.class::isInstance)
                .map(Edge.class::cast)
                .orElse(null));

        this.getViewDiagramConversionData(editingContext, diagramContext.diagram().getDescriptionId())
                .ifPresent(viewDiagramConversionData -> variableManager.put(ViewDiagramDescriptionConverter.CONVERTED_NODES_VARIABLE, viewDiagramConversionData.convertedNodes()));

        this.operationValidator.validate(DiagramInteractionOperations.EDGE_TOOL, variableManager.getVariables());
        return Optional.of(variableManager);
    }

    private Optional<ViewDiagramConversionData> getViewDiagramConversionData(IEditingContext editingContext, String diagramDescriptionId) {
        return Optional.of(editingContext)
                .filter(IViewEditingContext.class::isInstance)
                .map(IViewEditingContext.class::cast)
                .map(IViewEditingContext::getViewConversionData)
                .map(viewConversionData -> viewConversionData.get(diagramDescriptionId))
                .filter(Objects::nonNull)
                .filter(ViewDiagramConversionData.class::isInstance)
                .map(ViewDiagramConversionData.class::cast);
    }

}
