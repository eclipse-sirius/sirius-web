/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.ToolFinder;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramConversionData;
import org.eclipse.sirius.components.view.emf.diagram.ViewToolImageProvider;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.editingcontext.api.IViewEditingContext;
import org.eclipse.sirius.components.view.emf.form.converters.MultiValueProvider;
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

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    public ViewConnectorToolsProvider(IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IDiagramIdProvider diagramIdProvider, IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public List<ITool> getConnectorTools(IEditingContext editingContext, Diagram diagram, Object sourceDiagramElement) {
        List<ITool> tools = new ArrayList<>();

        var optionalViewDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagram.getDescriptionId());
        if (editingContext instanceof IViewEditingContext viewEditingContext && optionalViewDiagramDescription.isPresent() && optionalViewDiagramDescription.get().eContainer() instanceof View view) {
            var optionalViewDiagramConversionData = Optional.ofNullable(viewEditingContext.getViewConversionData().get(diagram.getDescriptionId()))
                    .filter(ViewDiagramConversionData.class::isInstance)
                    .map(ViewDiagramConversionData.class::cast);

            var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, view);

            if (optionalViewDiagramConversionData.isPresent()) {
                var viewDiagramConversionData = optionalViewDiagramConversionData.get();

                var edgeTools =  this.getSingleClickOnTwoDiagramElementsTool(editingContext, interpreter, viewDiagramConversionData, sourceDiagramElement);

                tools = edgeTools.stream()
                        .filter(Objects::nonNull)
                        .map(ITool.class::cast)
                        .toList();
            }
        }
        return tools;
    }

    private List<SingleClickOnTwoDiagramElementsTool> getSingleClickOnTwoDiagramElementsTool(IEditingContext editingContext, AQLInterpreter interpreter, ViewDiagramConversionData viewDiagramConversionData, Object sourceDiagramElement) {
        List<SingleClickOnTwoDiagramElementsTool> tools = new ArrayList<>();

        var optionalSourceDiagramElementDescriptionId = this.getDiagramElementDescriptionId(sourceDiagramElement);
        if (optionalSourceDiagramElementDescriptionId.isPresent()) {
            var sourceDiagramElementDescriptionId = optionalSourceDiagramElementDescriptionId.get();

            Optional<DiagramElementDescription> optionalDiagramElementDescription = this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, sourceDiagramElementDescriptionId)
                    .filter(DiagramElementDescription.class::isInstance)
                    .map(DiagramElementDescription.class::cast)
                    .or(() -> this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, sourceDiagramElementDescriptionId));

            if (optionalDiagramElementDescription.isPresent()) {
                var diagramElementDescription = optionalDiagramElementDescription.get();

                tools = new ToolFinder().findEdgeTools(diagramElementDescription).stream()
                        .map(edgeTool -> this.createEdgeTool(interpreter, edgeTool, diagramElementDescription, viewDiagramConversionData))
                        .toList();
            }
        }

        return tools;
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

    private SingleClickOnTwoDiagramElementsTool createEdgeTool(AQLInterpreter interpreter, EdgeTool edgeTool, DiagramElementDescription elementDescription, ViewDiagramConversionData viewDiagramConversionData) {
        Map<DiagramElementDescription, IDiagramElementDescription> convertedElements = new HashMap<>();

        convertedElements.putAll(viewDiagramConversionData.convertedEdges());
        convertedElements.putAll(viewDiagramConversionData.convertedNodes());

        String toolId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(edgeTool).toString().getBytes()).toString();

        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                .label(edgeTool.getName())
                .iconURL(this.getToolIconURLs(interpreter, edgeTool.getIconURLsExpression()))
                .candidates(
                        List.of(
                                SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                                        .sources(List.of(convertedElements.get(elementDescription)))
                                        .targets(edgeTool.getTargetElementDescriptions().stream().map(convertedElements::get).toList())
                                        .build()
                        )
                )
                .dialogDescriptionId(this.diagramIdProvider.getId(edgeTool.getDialogDescription()))
                .build();
    }

    private List<String> getToolIconURLs(AQLInterpreter interpreter, String iconURLsExpression) {
        List<String> iconURL;
        if (iconURLsExpression == null || iconURLsExpression.isBlank()) {
            iconURL = List.of(ViewToolImageProvider.EDGE_CREATION_TOOL_ICON);
        } else {
            iconURL = new MultiValueProvider<>(interpreter, iconURLsExpression, String.class).apply(new VariableManager());
        }
        return iconURL;
    }
}
