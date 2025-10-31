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

import org.eclipse.sirius.components.collaborative.diagrams.SingleClickOnDiagramElementToolHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolHandlerProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.emf.api.IViewInterpreterProvider;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.ToolFinder;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramConversionData;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverterContext;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.studio.services.representations.api.IHandlerPostExecutionToolProvider;
import org.springframework.stereotype.Service;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IToolExecutor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Use to get SingleClickOnDiagramElementToolHandler from view dsl.
 *
 * @author mcharfadi
 */
@Service
public class ViewSingleClickOnDiagramElementToolHandlerProvider implements IToolHandlerProvider {

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IDiagramIdProvider diagramIdProvider;

    private final List<IHandlerPostExecutionToolProvider> handlerPostExecutionProviders;

    private final IToolExecutor toolExecutor;

    private final IViewInterpreterProvider interpreter;

    public ViewSingleClickOnDiagramElementToolHandlerProvider(IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IDiagramIdProvider diagramIdProvider, List<IHandlerPostExecutionToolProvider> handlerPostExecutionProviders, IToolExecutor toolExecutor, IViewInterpreterProvider interpreter) {
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.handlerPostExecutionProviders = handlerPostExecutionProviders;
        this.toolExecutor = Objects.requireNonNull(toolExecutor);
        this.interpreter = Objects.requireNonNull(interpreter);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramDescription diagramDescription, String diagramElementDescriptionId, String toolId) {
        return this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId())
            .filter(viewDiagramDescription -> this.diagramIdProvider.getId(viewDiagramDescription).equals(diagramDescription.getId()))
            .flatMap(viewDiagramDescription -> this.getNodeToolByIdFromDiagramDescription(viewDiagramDescription, toolId))
            .or(() -> this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, diagramElementDescriptionId)
                    .flatMap(viewNodeDescription -> this.getNodeToolByIdFromNodeDescription(viewNodeDescription, toolId)))
            .or(() -> this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, diagramElementDescriptionId)
                    .flatMap(viewEdgeDescription -> this.getNodeToolByIdFromEdgeDescription(viewEdgeDescription, toolId)))
            .isPresent();
    }

    @Override
    public Optional<IToolHandler> getToolHandler(IEditingContext editingContext, DiagramDescription diagramDescription, String diagramElementDescriptionId, String toolId) {
        Optional<IToolHandler> toolHandler = Optional.empty();
        var optionalViewDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
        if (optionalViewDiagramDescription.isPresent() && editingContext instanceof EditingContext siriusEditingContext
                && optionalViewDiagramDescription.get().eContainer() instanceof View view) {
            ViewDiagramDescriptionConverterContext converterContext = Optional.ofNullable(siriusEditingContext.getRepresentationDescriptionIdToConverterResult().get(diagramDescription.getId()))
                .filter(ViewDiagramConversionData.class::isInstance)
                .map(ViewDiagramConversionData.class::cast)
                .map(ViewDiagramConversionData::converterContext)
                .orElseGet(() -> {
                    AQLInterpreter aqlInterpreter = this.interpreter.createInterpreter(siriusEditingContext, view);
                    return new ViewDiagramDescriptionConverterContext(aqlInterpreter);
                });

            return this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId())
                .filter(viewDiagramDescription -> this.diagramIdProvider.getId(viewDiagramDescription).equals(diagramDescription.getId()))
                .flatMap(viewDiagramDescription -> this.getNodeToolByIdFromDiagramDescription(viewDiagramDescription, toolId))
                .or(() -> this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, diagramElementDescriptionId)
                        .flatMap(viewNodeDescription -> this.getNodeToolByIdFromNodeDescription(viewNodeDescription, toolId)))
                .or(() -> this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, diagramElementDescriptionId)
                        .flatMap(viewEdgeDescription -> this.getNodeToolByIdFromEdgeDescription(viewEdgeDescription, toolId)))
                .map(tool -> this.getSingleClickOnDiagramElementToolHandler(tool, converterContext));
        }
        return toolHandler;
    }

    private Optional<NodeTool> getNodeToolByIdFromNodeDescription(NodeDescription viewNodeDescription, String toolId) {
        return new ToolFinder().getNodeToolByIdFromNodeDescription(viewNodeDescription, toolId);
    }

    private Optional<NodeTool> getNodeToolByIdFromEdgeDescription(EdgeDescription viewEdgeDescription, String toolId) {
        return new ToolFinder().getNodeToolByIdFromEdgeDescription(viewEdgeDescription, toolId);
    }

    private Optional<NodeTool> getNodeToolByIdFromDiagramDescription(org.eclipse.sirius.components.view.diagram.DiagramDescription diagramDescription, String toolId) {
        return new  ToolFinder().getNodeToolByIdFromDiagramDescription(diagramDescription, toolId);
    }

    private SingleClickOnDiagramElementToolHandler getSingleClickOnDiagramElementToolHandler(NodeTool nodeTool, ViewDiagramDescriptionConverterContext converterContext) {
        var convertedNodes = Collections.unmodifiableMap(converterContext.getConvertedNodes());
        Function<VariableManager, IStatus> handler = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(ViewDiagramDescriptionConverter.CONVERTED_NODES_VARIABLE, convertedNodes);
            var result = this.toolExecutor.executeTool(nodeTool, converterContext.getInterpreter(), childVariableManager);

            for (IHandlerPostExecutionToolProvider toolPostExecutionProvider : this.handlerPostExecutionProviders) {
                if (toolPostExecutionProvider.canHandle(result, converterContext, variableManager, nodeTool)) {
                    result = toolPostExecutionProvider.handle(result, converterContext, variableManager, nodeTool);
                }
            }

            return result;
        };
        var optionalDialogDescriptionId = Optional.ofNullable(nodeTool.getDialogDescription()).map(this.diagramIdProvider::getId);
        return new SingleClickOnDiagramElementToolHandler(handler, optionalDialogDescriptionId);
    }
}
