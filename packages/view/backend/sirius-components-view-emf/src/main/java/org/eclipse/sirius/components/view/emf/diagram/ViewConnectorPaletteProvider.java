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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.IPaletteEntry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.collaborative.diagrams.dto.PaletteDivider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * Used to provide the connector tools of the palette for diagram created from a view description.
 *
 * @author mcharfadi
 */
@Service
public class ViewConnectorPaletteProvider implements IConnectorPaletteProvider {

    private final IURLParser urlParser;

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IDiagramIdProvider diagramIdProvider;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final Function<EObject, UUID> idProvider = (eObject) -> UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());

    public ViewConnectorPaletteProvider(IURLParser urlParser, IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate,
                                        IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IDiagramDescriptionService diagramDescriptionService, IDiagramIdProvider diagramIdProvider,
                                        IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.urlParser = Objects.requireNonNull(urlParser);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public Palette handle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, Object sourceDiagramElement, Object targetDiagramElement, Object sourceElementDescription, Object targetElementDescription) {
        Palette palette = null;
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, targetDiagramElement);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(DiagramContext.DIAGRAM_CONTEXT, diagramContext);

        var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
        if (optionalDiagramDescription.isPresent()) {
            org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
            var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewDiagramDescription.eContainer());
            if (sourceDiagramElement instanceof Node && sourceElementDescription instanceof NodeDescription nodeDescription) {
                variableManager.put(Node.SELECTED_NODE, sourceDiagramElement);
                palette = this.getNodePalette(editingContext, diagramDescription, nodeDescription, variableManager, interpreter);
            } else if (sourceDiagramElement instanceof Edge && sourceElementDescription instanceof EdgeDescription edgeDescription) {
                variableManager.put(Edge.SELECTED_EDGE, sourceDiagramElement);
                palette = this.getEdgePalette(editingContext, diagramDescription, edgeDescription, variableManager, interpreter);
            }
        }

        return palette;
    }

    private Palette getNodePalette(IEditingContext editingContext, DiagramDescription diagramDescription, NodeDescription nodeDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        Optional<String> sourceElementId = this.getSourceElementId(nodeDescription.getId());
        Palette nodePalette = null;
        var toolFinder = new ToolFinder();
        if (sourceElementId.isPresent()) {
            var optionalNodeDescription = this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, nodeDescription.getId());

            if (optionalNodeDescription.isPresent()) {

                org.eclipse.sirius.components.view.diagram.NodeDescription viewNodeDescription = optionalNodeDescription.get();

                var paletteEntries = new ArrayList<IPaletteEntry>();

                toolFinder.findEdgeTools(viewNodeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(viewEdgeTools -> this.createEdgeTool(viewEdgeTools, diagramDescription, nodeDescription, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                toolFinder.findToolSections(viewNodeDescription).stream()
                        .map(nodeToolSection -> this.createToolSection(nodeToolSection, diagramDescription, nodeDescription, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                paletteEntries.add(new PaletteDivider(UUID.randomUUID().toString()));

                String nodePaletteId = "siriusComponents://connectorPalette?nodeId=" + sourceElementId.get();
                nodePalette = Palette.newPalette(nodePaletteId)
                        .quickAccessTools(List.of())
                        .paletteEntries(paletteEntries)
                        .build();
            }
        }
        return nodePalette;
    }

    private ToolSection createToolSection(NodeToolSection toolSection, DiagramDescription diagramDescription, NodeDescription nodeDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = this.idProvider.apply(toolSection).toString();

        var tools = new ArrayList<ITool>(toolSection.getEdgeTools().stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(viewEdgeTools -> this.createEdgeTool(viewEdgeTools, diagramDescription, nodeDescription, variableManager, interpreter))
                .toList());

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(tools)
                .build();
    }

    private ITool createEdgeTool(EdgeTool viewEdgeTool, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolId = this.idProvider.apply(viewEdgeTool).toString();
        List<String> iconURLProvider = this.edgeToolIconURLProvider(viewEdgeTool, interpreter, variableManager);
        String dialogDescriptionId = "";
        if (viewEdgeTool.getDialogDescription() != null) {
            dialogDescriptionId = this.diagramIdProvider.getId(viewEdgeTool.getDialogDescription());
        }

        List<SingleClickOnTwoDiagramElementsCandidate> candidates = List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                .sources(List.of(diagramElementDescription))
                .targets(viewEdgeTool.getTargetElementDescriptions().stream()
                        .map(viewDiagramElementDescription -> this.diagramDescriptionService.findDiagramElementDescriptionById(diagramDescription, this.diagramIdProvider.getId(viewDiagramElementDescription)))
                        .flatMap(Optional::stream)
                        .toList())
                .build());

        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                .label(viewEdgeTool.getName())
                .iconURL(iconURLProvider)
                .candidates(candidates)
                .dialogDescriptionId(dialogDescriptionId)
                .build();
    }

    private Palette getEdgePalette(IEditingContext editingContext, DiagramDescription diagramDescription, EdgeDescription edgeDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        Palette edgePalette = null;
        var toolFinder = new ToolFinder();
        Optional<String> optionalSourceElementId = this.getSourceElementId(edgeDescription.getId());
        if (optionalSourceElementId.isPresent()) {
            var sourceElementId = optionalSourceElementId.get();

            var optionalEdgeDescription = this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, edgeDescription.getId());
            if (optionalEdgeDescription.isPresent()) {
                org.eclipse.sirius.components.view.diagram.EdgeDescription viewEdgeDescription = optionalEdgeDescription.get();

                List<IPaletteEntry> paletteEntries = new ArrayList<>();

                toolFinder.findEdgeTools(viewEdgeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(viewEdgeTools -> this.createEdgeTool(viewEdgeTools, diagramDescription, edgeDescription, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                paletteEntries.add(new PaletteDivider(UUID.randomUUID().toString()));

                String edgePaletteId = "siriusComponents://connectorPalette?edgeId=" + sourceElementId;
                edgePalette = Palette.newPalette(edgePaletteId)
                        .quickAccessTools(List.of())
                        .paletteEntries(paletteEntries)
                        .build();

            }
        }
        return edgePalette;
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }

    private boolean checkPrecondition(Tool tool, VariableManager variableManager, AQLInterpreter interpreter) {
        String precondition = tool.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), precondition);
            return result.getStatus().compareTo(Status.WARNING) <= 0 && result.asBoolean().orElse(Boolean.FALSE);
        }
        return true;
    }

    private List<String> edgeToolIconURLProvider(EdgeTool edgeTool, AQLInterpreter interpreter, VariableManager variableManager) {
        List<String> iconURL = null;
        String iconURLsExpression = edgeTool.getIconURLsExpression();
        if (iconURLsExpression == null || iconURLsExpression.isBlank()) {
            iconURL = List.of(ViewToolImageProvider.EDGE_CREATION_TOOL_ICON);
        } else {
            iconURL = this.evaluateListString(interpreter, variableManager, iconURLsExpression);
        }
        return iconURL;
    }

    private List<String> evaluateListString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        List<Object> objects = interpreter.evaluateExpression(variableManager.getVariables(), expression).asObjects().orElse(List.of());
        return objects.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();
    }
}
