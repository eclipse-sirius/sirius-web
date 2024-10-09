/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.IPaletteEntry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.collaborative.diagrams.dto.PaletteDivider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to provide the tools of the palette for diagram created from a view description.
 * <p>
 * "Node tools" (SingleClickOnDiagramElementTool) and "Edge tools" (SingleClickOnTwoDiagramElementsTool) are obtained
 * from the ToolSection produced by ToolConverter for the target element (diagram, node or edge).
 * <p>
 * For nodes and edges (but not diagrams), the "extra tools" which can be exposed in the palette but are not implemented
 * as ITools but as plain Java handlers (label edit and delete) are added as pseudo-entries if the target element has
 * specified an explicit behavior for them. At runtime their invocation goes through distinct GraphQL mutation
 * operations than plain ITool invocation, so the body we provide here for them is never actually invoked.
 * <p>
 * Drop tools and edge reconnection tools are handled in a separate way, as they do not ever appear in the palette of
 * any element since they can only be triggered by direct gestures/interactions.
 *
 * @author sbegaudeau
 */
@Service
public class ViewPaletteProvider implements IPaletteProvider {

    private final IURLParser urlParser;

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IDiagramIdProvider diagramIdProvider;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final Function<EObject, UUID> idProvider = (eObject) -> UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());

    public ViewPaletteProvider(IURLParser urlParser, IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IDiagramDescriptionService diagramDescriptionService, IDiagramIdProvider diagramIdProvider, IViewAQLInterpreterFactory aqlInterpreterFactory) {
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
    public Palette handle(Object targetElement, Object diagramElement, Object diagramElementDescription, DiagramDescription diagramDescription, IEditingContext editingContext) {
        Palette palette = null;
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, targetElement);
        var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
        if (optionalDiagramDescription.isPresent()) {
            org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
            var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewDiagramDescription.eContainer());
            if (diagramElement instanceof Diagram) {
                palette = this.getDiagramPalette(diagramDescription, viewDiagramDescription, variableManager, interpreter);
            } else if (diagramElement instanceof Node && diagramElementDescription instanceof NodeDescription nodeDescription) {
                variableManager.put(Node.SELECTED_NODE, diagramElement);
                palette = this.getNodePalette(editingContext, diagramDescription, diagramElement, nodeDescription, variableManager, interpreter);
            } else if (diagramElement instanceof Edge && diagramElementDescription instanceof EdgeDescription edgeDescription) {
                variableManager.put(Edge.SELECTED_EDGE, diagramElement);
                palette = this.getEdgePalette(editingContext, edgeDescription, diagramElement, variableManager, interpreter);
            }
        }
        return palette;
    }

    private Palette getDiagramPalette(DiagramDescription diagramDescription, org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        Palette diagramPalette = null;
        var toolFinder = new ToolFinder();
        Optional<String> sourceElementId = this.getSourceElementId(diagramDescription.getId());
        if (sourceElementId.isPresent()) {
            String diagramPaletteId = "siriusComponents://diagramPalette?diagramId=" + sourceElementId.get();

            List<IPaletteEntry> paletteEntries = new ArrayList<>();
            toolFinder.findNodeTools(viewDiagramDescription).stream()
            .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
            .map(tool -> this.createDiagramRootNodeTool(tool, variableManager, interpreter))
            .forEach(paletteEntries::add);

            toolFinder.findToolSections(viewDiagramDescription).stream()
            .map(toolSection -> this.createToolSection(toolSection, variableManager, interpreter))
            .forEach(paletteEntries::add);

            diagramPalette = Palette.newPalette(diagramPaletteId)
                    .quickAccessTools(List.of())
                    .paletteEntries(paletteEntries)
                    .build();
        }
        return diagramPalette;
    }

    private ToolSection createToolSection(DiagramToolSection toolSection, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = this.idProvider.apply(toolSection).toString();

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(toolSection.getNodeTools().stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.createDiagramRootNodeTool(tool, variableManager, interpreter))
                        .toList())
                .build();
    }

    private ITool createDiagramRootNodeTool(NodeTool viewNodeTool, VariableManager variableManager, AQLInterpreter interpreter) {
        return this.createNodeTool(viewNodeTool, true, variableManager, interpreter);
    }

    private ITool createNodeTool(NodeTool viewNodeTool, VariableManager variableManager, AQLInterpreter interpreter) {
        return this.createNodeTool(viewNodeTool, false, variableManager, interpreter);
    }

    private ITool createNodeTool(NodeTool viewNodeTool, boolean appliesToDiagramRoot, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolId = this.idProvider.apply(viewNodeTool).toString();
        List<String> iconURLProvider = this.nodeToolIconURLProvider(viewNodeTool, interpreter, variableManager);
        String dialogDescriptionId = "";
        if (viewNodeTool.getDialogDescription() != null) {
            dialogDescriptionId = this.diagramIdProvider.getId(viewNodeTool.getDialogDescription());
        }

        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                .label(viewNodeTool.getName())
                .iconURL(iconURLProvider)
                .dialogDescriptionId(dialogDescriptionId)
                .targetDescriptions(List.of())
                .appliesToDiagramRoot(appliesToDiagramRoot)
                .build();
    }

    private Palette getNodePalette(IEditingContext editingContext, DiagramDescription diagramDescription, Object diagramElement, NodeDescription nodeDescription, VariableManager variableManager, AQLInterpreter interpreter) {

        Optional<String> sourceElementId = this.getSourceElementId(nodeDescription.getId());
        Palette nodePalette = null;
        var toolFinder = new ToolFinder();
        if (sourceElementId.isPresent()) {
            var optionalNodeDescription = this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, nodeDescription.getId());

            if (optionalNodeDescription.isPresent()) {
                var paletteDefaultToolsProvider = new PaletteDefaultToolsProvider();
                List<ToolSection> extraToolSections = paletteDefaultToolsProvider.createExtraToolSections(nodeDescription, diagramElement);
                List<ITool> extraTools = paletteDefaultToolsProvider.createExtraTools(nodeDescription, diagramElement);
                org.eclipse.sirius.components.view.diagram.NodeDescription viewNodeDescription = optionalNodeDescription.get();

                var paletteEntries = new ArrayList<IPaletteEntry>();
                toolFinder.findNodeTools(viewNodeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.createNodeTool(tool, variableManager, interpreter))
                        .forEach(paletteEntries::add);
                toolFinder.findEdgeTools(viewNodeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(viewEdgeTools -> this.createEdgeTool(viewEdgeTools, diagramDescription, nodeDescription, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                toolFinder.findToolSections(viewNodeDescription).stream()
                        .map(nodeToolSection -> this.createToolSection(nodeToolSection, diagramDescription, nodeDescription, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                paletteEntries.add(new PaletteDivider(UUID.randomUUID().toString()));
                paletteEntries.addAll(extraToolSections);

                String nodePaletteId = "siriusComponents://nodePalette?nodeId=" + sourceElementId.get();
                nodePalette = Palette.newPalette(nodePaletteId)
                        .quickAccessTools(extraTools)
                        .paletteEntries(paletteEntries)
                        .build();
            }
        }
        return nodePalette;
    }

    private ToolSection createToolSection(NodeToolSection toolSection, DiagramDescription diagramDescription, NodeDescription nodeDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = this.idProvider.apply(toolSection).toString();

        var tools = new ArrayList<ITool>();
        tools.addAll(toolSection.getNodeTools().stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(tool -> this.createNodeTool(tool, variableManager, interpreter))
                .toList());
        tools.addAll(toolSection.getEdgeTools().stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(viewEdgeTools -> this.createEdgeTool(viewEdgeTools, diagramDescription, nodeDescription, variableManager, interpreter))
                .toList());

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(tools)
                .build();
    }

    private ITool createEdgeTool(EdgeTool viewEdgeTool, DiagramDescription diagramDescription, NodeDescription nodeDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolId = this.idProvider.apply(viewEdgeTool).toString();
        List<String> iconURLProvider = this.edgeToolIconURLProvider(viewEdgeTool, interpreter, variableManager);

        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                .label(viewEdgeTool.getName())
                .iconURL(iconURLProvider)
                .candidates(List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                        .sources(List.of(nodeDescription))
                        .targets(viewEdgeTool.getTargetElementDescriptions().stream()
                                .filter(org.eclipse.sirius.components.view.diagram.NodeDescription.class::isInstance)
                                .map(viewDiagramElementDescription -> this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, this.diagramIdProvider.getId(viewDiagramElementDescription)))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .toList())
                        .build()))
                .build();
    }

    private Palette getEdgePalette(IEditingContext editingContext, EdgeDescription edgeDescription, Object diagramElement, VariableManager variableManager, AQLInterpreter interpreter) {
        List<ToolSection> extraToolSections = new PaletteDefaultToolsProvider().createExtraToolSections(edgeDescription, diagramElement);
        Palette edgePalette = null;
        var toolFinder = new ToolFinder();
        Optional<String> optionalSourceElementId = this.getSourceElementId(edgeDescription.getId());
        if (optionalSourceElementId.isPresent()) {
            var sourceElementId = optionalSourceElementId.get();

            var optionalEdgeDescription = this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, edgeDescription.getId());
            if (optionalEdgeDescription.isPresent()) {
                org.eclipse.sirius.components.view.diagram.EdgeDescription viewEdgeDescription = optionalEdgeDescription.get();

                List<IPaletteEntry> paletteEntries = new ArrayList<>();
                toolFinder.findToolSections(viewEdgeDescription).stream()
                        .map(edgeToolSection -> this.createToolSection(edgeToolSection, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                toolFinder.findNodeTools(viewEdgeDescription).stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(tool -> this.createNodeTool(tool, variableManager, interpreter))
                .forEach(paletteEntries::add);

                paletteEntries.addAll(extraToolSections);

                String edgePaletteId = "siriusComponents://edgePalette?edgeId=" + sourceElementId;
                edgePalette = Palette.newPalette(edgePaletteId)
                        .quickAccessTools(List.of())
                        .paletteEntries(paletteEntries)
                        .build();

            }
        }
        return edgePalette;
    }

    private ToolSection createToolSection(EdgeToolSection toolSection, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = this.idProvider.apply(toolSection).toString();

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(toolSection.getNodeTools().stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.createNodeTool(tool, variableManager, interpreter))
                        .toList())
                .build();
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

    private List<String> nodeToolIconURLProvider(NodeTool nodeTool, AQLInterpreter interpreter, VariableManager variableManager) {
        List<String> iconURL = null;
        String iconURLsExpression = nodeTool.getIconURLsExpression();
        if (iconURLsExpression == null || iconURLsExpression.isBlank()) {
            iconURL = List.of(ViewToolImageProvider.NODE_CREATION_TOOL_ICON);
        } else {
            iconURL = this.evaluateListString(interpreter, variableManager, iconURLsExpression);
        }
        return iconURL;
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
