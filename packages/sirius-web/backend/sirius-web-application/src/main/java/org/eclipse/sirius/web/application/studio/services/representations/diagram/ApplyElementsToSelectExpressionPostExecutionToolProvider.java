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
package org.eclipse.sirius.web.application.studio.services.representations.diagram;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverterContext;
import org.eclipse.sirius.components.view.emf.diagram.tools.ToolExecutor;
import org.eclipse.sirius.web.application.studio.services.representations.api.IHandlerPostExecutionToolProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Used to inject selection data in the result of a tool handler.
 *
 * @author mcharfadi
 */
@Service
public class ApplyElementsToSelectExpressionPostExecutionToolProvider implements IHandlerPostExecutionToolProvider {

    private final IIdentityService identityService;

    private final IObjectSearchService objectSearchService;

    public ApplyElementsToSelectExpressionPostExecutionToolProvider(IIdentityService identityService, IObjectSearchService objectSearchService) {
        this.identityService = identityService;
        this.objectSearchService = objectSearchService;
    }

    @Override
    public boolean canHandle(IStatus result, ViewDiagramDescriptionConverterContext converterContext, VariableManager variableManager, Tool tool) {
        return tool instanceof EdgeTool || tool instanceof NodeTool;
    }

    @Override
    public IStatus handle(IStatus result, ViewDiagramDescriptionConverterContext converterContext, VariableManager variableManager, Tool tool) {
        var elementsToSelectExpression = Optional.of(tool).filter(EdgeTool.class::isInstance)
                .map(EdgeTool.class::cast)
                .map(EdgeTool::getElementsToSelectExpression)
                .or(() -> Optional.of(tool).filter(NodeTool.class::isInstance)
                        .map(NodeTool.class::cast)
                        .map(NodeTool::getElementsToSelectExpression));

        if (result instanceof Success success && elementsToSelectExpression.isPresent() && !elementsToSelectExpression.get().isBlank()) {
            // "Resolve" any explicitly returned selection entries into the actual semantic elements; AQL expressions can't do much with just WorkbenSelectionEntries
            List<Object> newSelection = List.of();
            if (success.getParameters().get(Success.NEW_SELECTION) instanceof WorkbenchSelection workbenchSelection && !workbenchSelection.getEntries().isEmpty()) {
                var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
                if (optionalEditingContext.isPresent()) {
                    newSelection = workbenchSelection.getEntries().stream()
                            .filter(entry -> entry.getKind().startsWith(SemanticKindConstants.PREFIX + "?"))
                            .map(entry -> this.objectSearchService.getObject(optionalEditingContext.get(), entry.getId()))
                            .flatMap(Optional::stream)
                            .toList();
                }
            }

            // Evaluate the expression
            Map<String, Object> variables = new HashMap<>(variableManager.getVariables());
            variables.put(Success.NEW_SELECTION, newSelection);

            Map<String, Object> newInstances = Map.of();
            if (success.getParameters().get(ToolExecutor.NAMED_INSTANCES) instanceof Map<?, ?>) {
                newInstances = (Map<String, Object>) success.getParameters().get(ToolExecutor.NAMED_INSTANCES);
            }
            variables.putAll(newInstances);

            var optionalComputedNewSelection = converterContext.getInterpreter().evaluateExpression(variables, elementsToSelectExpression.get()).asObjects();
            if (optionalComputedNewSelection.isPresent()) {
                // Convert back the result into a WorkbenchSelection
                var entries = optionalComputedNewSelection.get().stream()
                        .map(element -> new WorkbenchSelectionEntry(this.identityService.getId(element), this.identityService.getKind(element)))
                        .toList();
                success.getParameters().put(Success.NEW_SELECTION, new WorkbenchSelection(entries));
            }
        }
        return result;
    }
}
