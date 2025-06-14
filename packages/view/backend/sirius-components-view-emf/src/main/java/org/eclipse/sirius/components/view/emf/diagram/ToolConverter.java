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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.Palette;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.diagrams.tools.ToolSection;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.emf.diagram.api.IToolConverter;
import org.eclipse.sirius.components.view.emf.diagram.tools.ToolExecutor;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IToolExecutor;
import org.springframework.stereotype.Service;

/**
 * Convert View-based tool definitions into ITools.
 *
 * @author pcdavid
 */
@Service
public class ToolConverter implements IToolConverter {

    private final IIdentityService identityService;

    private final IObjectSearchService objectSearchService;

    private final IToolExecutor toolExecutor;

    private final IDiagramIdProvider diagramIdProvider;

    private final Function<EObject, UUID> idProvider = (eObject) -> {
        // DiagramElementDescription should have a proper id.
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());
    };

    public ToolConverter(IIdentityService identityService, IObjectSearchService objectSearchService, IToolExecutor toolExecutor, IDiagramIdProvider diagramIdProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.toolExecutor = Objects.requireNonNull(toolExecutor);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
    }

    /**
     * Create tool sections to represent the contextual palettes of: the diagram, each node type, each edge type. Each
     * tool section's id is structured to identify the element whose contextual palette it represents:
     * <code>siriusComponents://(diagram|node|edge)Palette?(diagram|node|edge)Id=${palette.eContainer().id}</code>.
     * Given a specific element and the full list of these tool sections (stored on the top-level DiagramDescription),
     * {@link ViewPaletteProvider} can then find all the applicable tools, and is free to reorganize them in proper
     * user-facing sections and add the appropriate "extra tools".
     */
    @Override
    public List<Palette> createPaletteBasedToolSections(org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription,
            ViewDiagramDescriptionConverterContext converterContext) {
        var allPalettes = new ArrayList<Palette>();
        var toolFinder = new ToolFinder();

        // Palette for the diagram itself
        String diagramPaletteId = "siriusComponents://diagramPalette?diagramId=" + this.identityService.getId(viewDiagramDescription);
        var diagramNodeTools = new ArrayList<ITool>();
        toolFinder.findNodeTools(viewDiagramDescription).stream()
                .map(nodeTool -> this.createNodeTool(nodeTool, converterContext, true))
                .forEach(diagramNodeTools::add);
        toolFinder.findQuickAccessDiagramTools(viewDiagramDescription).stream()
                .map(nodeTool -> this.createNodeTool(nodeTool, converterContext, true))
                .forEach(diagramNodeTools::add);

        var diagramPalette = Palette.newPalette(diagramPaletteId)
                .toolSections(toolFinder.findToolSections(viewDiagramDescription).stream()
                        .map(toolSection -> this.createToolSection(toolSection, converterContext))
                        .toList())
                .tools(diagramNodeTools)
                .build();

        allPalettes.add(diagramPalette);

        // One palette for each NodeDescription
        for (var nodeDescription : converterContext.getConvertedNodes().keySet()) {
            String nodePaletteId = "siriusComponents://nodePalette?nodeId=" + this.identityService.getId(nodeDescription);
            var tools = new ArrayList<ITool>();
            var nodeTools = new ArrayList<ITool>();
            toolFinder.findNodeTools(nodeDescription).stream()
                    .map(nodeTool -> this.createNodeTool(nodeTool, converterContext, false))
                    .forEach(nodeTools::add);
            toolFinder.findQuickAccessNodeTools(nodeDescription).stream()
                    .map(nodeTool -> this.createNodeTool(nodeTool, converterContext, false))
                    .forEach(nodeTools::add);

            tools.addAll(nodeTools);
            tools.addAll(toolFinder.findEdgeTools(nodeDescription).stream()
                    .map(edgeTool -> this.createEdgeTool(edgeTool, nodeDescription, converterContext))
                    .toList());

            var nodePalette = Palette.newPalette(nodePaletteId)
                    .tools(tools)
                    .toolSections(toolFinder.findToolSections(nodeDescription).stream()
                            .map(nodeToolSection -> this.createToolSection(nodeToolSection, nodeDescription, converterContext))
                            .toList())
                    .build();

            allPalettes.add(nodePalette);
        }

        // One palette for each EdgeDescription
        for (var edgeDescription : converterContext.getConvertedEdges().keySet()) {
            String edgePaletteId = "siriusComponents://edgePalette?edgeId=" + this.identityService.getId(edgeDescription);
            var edgeNodeTools = new ArrayList<ITool>();
            toolFinder.findNodeTools(edgeDescription).stream()
                    .map(nodeTool -> this.createNodeTool(nodeTool, converterContext, false))
                    .forEach(edgeNodeTools::add);
            toolFinder.findQuickAccessEdgeTools(edgeDescription).stream()
                    .map(nodeTool -> this.createNodeTool(nodeTool, converterContext, false))
                    .forEach(edgeNodeTools::add);

            toolFinder.findEdgeTools(edgeDescription).stream()
                    .map(edgeTool -> this.createEdgeTool(edgeTool, edgeDescription, converterContext))
                    .forEach(edgeNodeTools::add);

            var edgePalette = Palette.newPalette(edgePaletteId)
                    .tools(edgeNodeTools)
                    .toolSections(toolFinder.findToolSections(edgeDescription).stream()
                            .map(edgeToolSection -> this.createToolSection(edgeToolSection, converterContext))
                            .toList())
                    .build();

            allPalettes.add(edgePalette);
        }

        return allPalettes;
    }

    private ToolSection createToolSection(DiagramToolSection toolSection, ViewDiagramDescriptionConverterContext converterContext) {
        String toolSectionId = this.idProvider.apply(toolSection).toString();
        return ToolSection.newToolSection(toolSectionId)
                .label(toolSection.getName())
                .tools(toolSection.getNodeTools().stream().map(nodeTool -> this.createNodeTool(nodeTool, converterContext, true)).toList())
                .iconURL(List.of())
                .build();
    }

    private ToolSection createToolSection(NodeToolSection toolSection, DiagramElementDescription elementDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var tools = new ArrayList<ITool>();
        tools.addAll(toolSection.getNodeTools().stream()
                .map(nodeTool -> this.createNodeTool(nodeTool, converterContext, false))
                .toList());
        tools.addAll(toolSection.getEdgeTools().stream()
                .map(edgeTool -> this.createEdgeTool(edgeTool, elementDescription, converterContext))
                .toList());
        String toolSectionId = this.idProvider.apply(toolSection).toString();
        return ToolSection.newToolSection(toolSectionId)
                .label(toolSection.getName())
                .tools(tools)
                .iconURL(List.of())
                .build();
    }

    private ToolSection createToolSection(EdgeToolSection toolSection, ViewDiagramDescriptionConverterContext converterContext) {
        String toolSectionId = this.idProvider.apply(toolSection).toString();
        return ToolSection.newToolSection(toolSectionId)
                .label(toolSection.getName())
                .tools(toolSection.getNodeTools().stream().map(nodeTool -> this.createNodeTool(nodeTool, converterContext, true)).toList())
                .iconURL(List.of())
                .build();
    }

    private ITool createNodeTool(NodeTool nodeTool, ViewDiagramDescriptionConverterContext converterContext, boolean appliesToDiagramRoot) {
        var convertedNodes = Collections.unmodifiableMap(converterContext.getConvertedNodes());
        String toolId = this.idProvider.apply(nodeTool).toString();
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                .label(nodeTool.getName())
                .iconURL(this.toolIconURLProvider(nodeTool.getIconURLsExpression(), ViewToolImageProvider.NODE_CREATION_TOOL_ICON, converterContext.getInterpreter()))
                .handler(variableManager -> {
                    VariableManager childVariableManager = variableManager.createChild();
                    childVariableManager.put(ViewDiagramDescriptionConverter.CONVERTED_NODES_VARIABLE, convertedNodes);
                    var result = this.toolExecutor.executeTool(nodeTool, converterContext.getInterpreter(), childVariableManager);
                    this.applyElementsToSelectExpression(result, converterContext, childVariableManager, nodeTool.getElementsToSelectExpression());
                    return result;
                })
                .targetDescriptions(List.of())
                .dialogDescriptionId(this.diagramIdProvider.getId(nodeTool.getDialogDescription()))
                .appliesToDiagramRoot(appliesToDiagramRoot)
                .withImpactAnalysis(nodeTool.isWithImpactAnalysis())
                .build();
    }

    private ITool createEdgeTool(EdgeTool edgeTool, DiagramElementDescription elementDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var convertedElements = Collections.unmodifiableMap(converterContext.getConvertedElements());
        String toolId = this.idProvider.apply(edgeTool).toString();
        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                .label(edgeTool.getName())
                .iconURL(this.toolIconURLProvider(edgeTool.getIconURLsExpression(), ViewToolImageProvider.EDGE_CREATION_TOOL_ICON, converterContext.getInterpreter()))
                .candidates(List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                        .sources(List.of(convertedElements.get(elementDescription)))
                        .targets(edgeTool.getTargetElementDescriptions().stream().map(convertedElements::get).toList())
                        .build()))
                .handler(variableManager -> {
                    VariableManager childVariableManager = variableManager.createChild();
                    childVariableManager.put(ViewDiagramDescriptionConverter.CONVERTED_NODES_VARIABLE, convertedElements);
                    childVariableManager.put("diagramElementDescription", elementDescription);

                    var result = this.toolExecutor.executeTool(edgeTool, converterContext.getInterpreter(), childVariableManager);
                    this.applyElementsToSelectExpression(result, converterContext, childVariableManager, edgeTool.getElementsToSelectExpression());
                    return result;
                })
                .dialogDescriptionId(this.diagramIdProvider.getId(edgeTool.getDialogDescription()))
                .build();
    }

    private void applyElementsToSelectExpression(IStatus result, ViewDiagramDescriptionConverterContext converterContext, VariableManager variableManager, String elementsToSelectExpression) {
        if (result instanceof Success success && elementsToSelectExpression != null && !elementsToSelectExpression.isBlank()) {
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

            var optionalComputedNewSelection = converterContext.getInterpreter().evaluateExpression(variables, elementsToSelectExpression).asObjects();
            if (optionalComputedNewSelection.isPresent()) {
                // Convert back the result into a WorkbenchSelection
                var entries = optionalComputedNewSelection.get().stream()
                        .map(element -> new WorkbenchSelectionEntry(this.identityService.getId(element), this.identityService.getKind(element)))
                        .toList();
                success.getParameters().put(Success.NEW_SELECTION, new WorkbenchSelection(entries));
            }
        }
    }

    private List<String> toolIconURLProvider(String iconURLsExpression, String defaultIconURL, AQLInterpreter interpreter) {
        List<String> iconURL;
        if (iconURLsExpression == null || iconURLsExpression.isBlank()) {
            iconURL = List.of(defaultIconURL);
        } else {
            iconURL = this.evaluateListString(interpreter, new VariableManager(), iconURLsExpression);
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
