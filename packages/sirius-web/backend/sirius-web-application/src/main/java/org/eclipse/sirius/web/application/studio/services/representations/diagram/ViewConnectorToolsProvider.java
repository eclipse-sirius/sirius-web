/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewInterpreterProvider;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.ToolFinder;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramConversionData;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverterContext;
import org.eclipse.sirius.components.view.emf.diagram.ViewToolImageProvider;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

/**
 * Provides candidates connector tools for a given source and target.
 *
 * @author mcharfadi
 */
@Service
public class ViewConnectorToolsProvider implements IConnectorToolsProvider {

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IDiagramIdProvider diagramIdProvider;

    private final IViewInterpreterProvider interpreter;

    private final Function<EObject, UUID> idProvider = (eObject) -> {
        // DiagramElementDescription should have a proper id.
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());
    };

    public ViewConnectorToolsProvider(IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IDiagramIdProvider diagramIdProvider, IViewInterpreterProvider interpreter) {
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.interpreter = Objects.requireNonNull(interpreter);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public List<ITool> getConnectorTools(Object sourceDiagramElement, Object targetDiagramElement, Diagram diagram, IEditingContext editingContext) {
        List<ITool> tools = new ArrayList<>();
        var optionalViewDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagram.getDescriptionId());
        if (editingContext instanceof EditingContext siriusEditingContext && optionalViewDiagramDescription.isPresent() && optionalViewDiagramDescription.get().eContainer() instanceof View view) {
            ViewDiagramDescriptionConverterContext converterContext = Optional.ofNullable(siriusEditingContext.getRepresentationDescriptionIdToConverterResult().get(diagram.getDescriptionId()))
                .filter(ViewDiagramConversionData.class::isInstance)
                .map(ViewDiagramConversionData.class::cast)
                .map(ViewDiagramConversionData::converterContext)
                .orElseGet(() -> {
                    AQLInterpreter aqlInterpreter = this.interpreter.createInterpreter(siriusEditingContext, view);
                    return new ViewDiagramDescriptionConverterContext(aqlInterpreter);
                });

            this.getDiagramElementDescriptionId(sourceDiagramElement)
                .flatMap(diagramElementDescriptionId -> this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, diagramElementDescriptionId))
                .map(sourceViewDiagramElementDescription -> new ToolFinder().findEdgeTools(sourceViewDiagramElementDescription)
                        .stream().map(edgeTool -> this.createEdgeTool(edgeTool, sourceViewDiagramElementDescription, converterContext)).toList())
                .or(() -> this.getDiagramElementDescriptionId(sourceDiagramElement)
                            .flatMap(diagramElementDescriptionId -> this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, diagramElementDescriptionId))
                                .map(sourceViewDiagramElementDescription -> new ToolFinder().findEdgeTools(sourceViewDiagramElementDescription)
                                .stream().map(edgeTool -> this.createEdgeTool(edgeTool, sourceViewDiagramElementDescription, converterContext)).toList()))
                .ifPresent(edgeTools ->
                    edgeTools.stream().filter(edgeTool -> this.isEdgeToolCandidate(edgeTool.candidates(), sourceDiagramElement, targetDiagramElement))
                            .forEach(tools::add)
                );
        }
        return tools;
    }

    private boolean isEdgeToolCandidate(List<SingleClickOnTwoDiagramElementsCandidate> candidates, Object sourceDiagramElement, Object targetDiagramElement) {
        var optionalSourceDiagramElementDescriptionId = getDiagramElementDescriptionId(sourceDiagramElement);
        var optionalTargetDiagramElementDescriptionId = getDiagramElementDescriptionId(targetDiagramElement);
        if (optionalSourceDiagramElementDescriptionId.isPresent() && optionalTargetDiagramElementDescriptionId.isPresent()) {
            return candidates.stream()
                .anyMatch(candidate -> candidate.sources().stream().map(IDiagramElementDescription::getId).anyMatch(id -> id.equals(optionalSourceDiagramElementDescriptionId.get()))
                        && candidate.targets().stream().map(IDiagramElementDescription::getId).anyMatch(id -> id.equals(optionalTargetDiagramElementDescriptionId.get())));
        }
        return false;
    }

    private Optional<String> getDiagramElementDescriptionId(Object object) {
        Optional<String> descriptionId = Optional.empty();
        if (object instanceof Node node) {
            descriptionId = Optional.of(node.getDescriptionId());
        } else if (object instanceof Edge edge) {
            descriptionId = Optional.of(edge.getDescriptionId());
        }
        return descriptionId;
    }

    private SingleClickOnTwoDiagramElementsTool createEdgeTool(EdgeTool edgeTool, DiagramElementDescription elementDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var convertedElements = Collections.unmodifiableMap(converterContext.getConvertedElements());
        String toolId = this.idProvider.apply(edgeTool).toString();
        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                .label(edgeTool.getName())
                .iconURL(this.toolIconURLProvider(edgeTool.getIconURLsExpression(), converterContext.getInterpreter()))
                .candidates(List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                    .sources(List.of(convertedElements.get(elementDescription)))
                    .targets(edgeTool.getTargetElementDescriptions().stream().map(convertedElements::get).toList())
                    .build()))
                .dialogDescriptionId(this.diagramIdProvider.getId(edgeTool.getDialogDescription()))
                .build();
    }

    private List<String> toolIconURLProvider(String iconURLsExpression, AQLInterpreter aqlInterpreter) {
        List<String> iconURL;
        if (iconURLsExpression == null || iconURLsExpression.isBlank()) {
            iconURL = List.of(ViewToolImageProvider.EDGE_CREATION_TOOL_ICON);
        } else {
            iconURL = this.evaluateListString(aqlInterpreter, new VariableManager(), iconURLsExpression);
        }
        return iconURL;
    }

    private List<String> evaluateListString(AQLInterpreter aqlInterpreter, VariableManager variableManager, String expression) {
        List<Object> objects = aqlInterpreter.evaluateExpression(variableManager.getVariables(), expression).asObjects().orElse(List.of());
        return objects.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();
    }
}
