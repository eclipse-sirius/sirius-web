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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.SingleClickOnTwoDiagramElementToolHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolHandlerProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.emf.api.IViewInterpreterProvider;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.ToolFinder;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramConversionData;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverterContext;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IToolExecutor;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.studio.services.representations.api.IHandlerPostExecutionToolProvider;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * Use to get SingleClickOnDiagramElementToolHandler from view dsl.
 *
 * @author mcharfadi
 */
@Service
public class ViewSingleClickOnTwoDiagramElementToolHandlerProvider implements IToolHandlerProvider {

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IDiagramIdProvider diagramIdProvider;

    private final IToolExecutor toolExecutor;

    private final IViewInterpreterProvider interpreter;

    private final List<IHandlerPostExecutionToolProvider> handlerPostExecutionProviders;

    private final Function<EObject, UUID> idProvider = (eObject) -> {
        // DiagramElementDescription should have a proper id.
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());
    };

    public ViewSingleClickOnTwoDiagramElementToolHandlerProvider(IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IDiagramIdProvider diagramIdProvider, IToolExecutor toolExecutor, IViewInterpreterProvider interpreter, List<IHandlerPostExecutionToolProvider> handlerPostExecutionProviders) {
        this.viewDiagramDescriptionSearchService = viewDiagramDescriptionSearchService;
        this.diagramIdProvider = diagramIdProvider;
        this.toolExecutor = toolExecutor;
        this.interpreter = interpreter;
        this.handlerPostExecutionProviders = handlerPostExecutionProviders;
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramDescription diagramDescription, String diagramElementDescriptionId, String toolId) {
        return this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, diagramElementDescriptionId)
                .flatMap(viewNodeDescription -> this.getEdgeToolByIdFromNodeDescription(viewNodeDescription, toolId))
            .or(() -> this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, diagramElementDescriptionId)
                    .flatMap(viewNodeDescription -> this.getEdgeToolByIdFromEdgeDescription(viewNodeDescription, toolId)))
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

            return this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, diagramElementDescriptionId)
                    .flatMap(viewNodeDescription -> {
                        var optionalEdgeTool = this.getEdgeToolByIdFromNodeDescription(viewNodeDescription, toolId);
                        return optionalEdgeTool.map(edgeTool -> this.getSingleClickOnTwoDiagramElementToolHandler(edgeTool, viewNodeDescription, converterContext));
                    })
                    .or(() -> this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, diagramElementDescriptionId)
                        .flatMap(viewEdgeDescription -> {
                            var optionalEdgeTool = this.getEdgeToolByIdFromEdgeDescription(viewEdgeDescription, toolId);
                            return optionalEdgeTool.map(edgeTool -> this.getSingleClickOnTwoDiagramElementToolHandler(edgeTool, viewEdgeDescription, converterContext));
                        }));
        }
        return toolHandler;
    }

    private Optional<EdgeTool> getEdgeToolByIdFromNodeDescription(NodeDescription viewNodeDescription, String toolId) {
        return new ToolFinder().getEdgeToolByIdFromNodeDescription(viewNodeDescription, toolId);
    }

    private Optional<EdgeTool> getEdgeToolByIdFromEdgeDescription(EdgeDescription viewEdgeDescription, String toolId) {
        return new ToolFinder().getEdgeToolByIdFromEdgeDescription(viewEdgeDescription, toolId);
    }

    private IToolHandler getSingleClickOnTwoDiagramElementToolHandler(EdgeTool edgeTool, DiagramElementDescription elementDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var convertedElements = Collections.unmodifiableMap(converterContext.getConvertedElements());
        Function<VariableManager, IStatus> handler = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(ViewDiagramDescriptionConverter.CONVERTED_NODES_VARIABLE, convertedElements);
            childVariableManager.put("diagramElementDescription", elementDescription);

            var result = this.toolExecutor.executeTool(edgeTool, converterContext.getInterpreter(), childVariableManager);

            for (IHandlerPostExecutionToolProvider toolPostExecutionProvider : this.handlerPostExecutionProviders) {
                if (toolPostExecutionProvider.canHandle(result, converterContext, variableManager, edgeTool)) {
                    result = toolPostExecutionProvider.handle(result, converterContext, variableManager, edgeTool);
                }
            }

            return result;
        };
        var optionalDialogDescriptionId = Optional.ofNullable(edgeTool.getDialogDescription()).map(this.diagramIdProvider::getId);
        var targetCandidatesId = edgeTool.getTargetElementDescriptions().stream().map(this.diagramIdProvider::getId).toList();
        return new SingleClickOnTwoDiagramElementToolHandler(handler, optionalDialogDescriptionId, targetCandidatesId);
    }

}
