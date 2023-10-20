/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.Palette;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.diagrams.tools.ToolSection;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.diagram.providers.api.IViewToolImageProvider;

/**
 * Convert View-based tool definitions into ITools.
 *
 * @author pcdavid
 */
public class ToolConverter {

    private static final String CONVERTED_NODES_VARIABLE = "convertedNodes";

    private final IObjectService objectService;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    private final Function<EObject, UUID> idProvider = (eObject) -> {
        // DiagramElementDescription should have a proper id.
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());
    };

    public ToolConverter(IObjectService objectService, IEditService editService, IViewToolImageProvider viewToolImageProvider, IFeedbackMessageService feedbackMessageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = feedbackMessageService;
    }

    /**
     * Create tool sections to represent the contextual palettes of: the diagram, each node type, each edge type. Each
     * tool section's id is structured to identify the element whose contextual palette it represents:
     * <code>siriusComponents://(diagram|node|edge)Palette?(diagram|node|edge)Id=${palette.eContainer().id}</code>.
     * Given a specific element and the full list of these tool sections (stored on the top-level DiagramDescription),
     * {@link ViewPaletteProvider} can then find all the applicable tools, and is free to reorganize them in proper
     * user-facing sections and add the appropriate "extra tools".
     */
    public List<Palette> createPaletteBasedToolSections(org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription,
            ViewDiagramDescriptionConverterContext converterContext) {
        var allPalettes = new ArrayList<Palette>();
        var toolFinder = new ToolFinder();

        // Palette for the diagram itself
        String diagramPaletteId = "siriusComponents://diagramPalette?diagramId=" + this.objectService.getId(viewDiagramDescription);
        var diagramPalette = Palette.newPalette(diagramPaletteId)
                .toolSections(toolFinder.findToolSections(viewDiagramDescription).stream()
                        .map(toolSection -> this.createToolSection(toolSection, converterContext))
                        .toList())
                .tools(toolFinder.findNodeTools(viewDiagramDescription).stream()
                        .map(nodeTool -> this.createNodeTool(nodeTool, converterContext, true))
                        .toList())
                .build();

        allPalettes.add(diagramPalette);

        // One palette for each NodeDescription
        for (var nodeDescription : converterContext.getConvertedNodes().keySet()) {
            String nodePaletteId = "siriusComponents://nodePalette?nodeId=" + this.objectService.getId(nodeDescription);
            var tools = new ArrayList<ITool>();
            tools.addAll(toolFinder.findNodeTools(nodeDescription).stream()
                    .map(nodeTool -> this.createNodeTool(nodeTool, converterContext, false))
                    .toList());
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
            String edgePaletteId = "siriusComponents://edgePalette?edgeId=" + this.objectService.getId(edgeDescription);
            var edgePalette = Palette.newPalette(edgePaletteId)
                    .tools(toolFinder.findNodeTools(edgeDescription).stream()
                            .map(nodeTool -> this.createNodeTool(nodeTool, converterContext, false))
                            .toList())
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
                .imageURL("")
                .build();
    }

    private ToolSection createToolSection(NodeToolSection toolSection, NodeDescription nodeDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var tools = new ArrayList<ITool>();
        tools.addAll(toolSection.getNodeTools().stream()
                .map(nodeTool -> this.createNodeTool(nodeTool, converterContext, false))
                .toList());
        tools.addAll(toolSection.getEdgeTools().stream()
                .map(edgeTool -> this.createEdgeTool(edgeTool, nodeDescription, converterContext))
                .toList());
        String toolSectionId = this.idProvider.apply(toolSection).toString();
        return ToolSection.newToolSection(toolSectionId)
                .label(toolSection.getName())
                .tools(tools)
                .imageURL("")
                .build();
    }

    private ToolSection createToolSection(EdgeToolSection toolSection, ViewDiagramDescriptionConverterContext converterContext) {
        String toolSectionId = this.idProvider.apply(toolSection).toString();
        return ToolSection.newToolSection(toolSectionId)
                .label(toolSection.getName())
                .tools(toolSection.getNodeTools().stream().map(nodeTool -> this.createNodeTool(nodeTool, converterContext, true)).toList())
                .imageURL("")
                .build();
    }

    private ITool createNodeTool(NodeTool nodeTool, ViewDiagramDescriptionConverterContext converterContext, boolean appliesToDiagramRoot) {
        var convertedNodes = Collections.unmodifiableMap(converterContext.getConvertedNodes());
        String toolId = this.idProvider.apply(nodeTool).toString();
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                .label(nodeTool.getName())
                .imageURL(ViewToolImageProvider.NODE_CREATION_TOOL_ICON)
                .handler(variableManager -> {
                    VariableManager child = variableManager.createChild();
                    child.put(CONVERTED_NODES_VARIABLE, convertedNodes);
                    return this.execute(converterContext, convertedNodes, nodeTool, child);
                })
                .targetDescriptions(List.of())
                .selectionDescriptionId(this.objectService.getId(nodeTool.getSelectionDescription()))
                .appliesToDiagramRoot(appliesToDiagramRoot)
                .build();
    }

    private ITool createEdgeTool(EdgeTool edgeTool, NodeDescription nodeDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var convertedNodes = Collections.unmodifiableMap(converterContext.getConvertedNodes());
        String toolId = this.idProvider.apply(edgeTool).toString();
        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                .label(edgeTool.getName())
                .imageURL(ViewToolImageProvider.EDGE_CREATION_TOOL_ICON)
                .candidates(List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                        .sources(List.of(convertedNodes.get(nodeDescription)))
                        .targets(edgeTool.getTargetElementDescriptions().stream().map(convertedNodes::get).toList())
                        .build()))
                .handler(variableManager -> {
                    VariableManager child = variableManager.createChild();
                    child.put(CONVERTED_NODES_VARIABLE, convertedNodes);
                    child.put("nodeDescription", nodeDescription);
                    return this.execute(converterContext, convertedNodes, edgeTool, child);
                })
                .build();
    }

    private IStatus execute(ViewDiagramDescriptionConverterContext converterContext, Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes, Tool tool,
            VariableManager variableManager) {
        IDiagramContext diagramContext = variableManager.get(IDiagramContext.DIAGRAM_CONTEXT, IDiagramContext.class).orElse(null);
        var operationInterpreter = new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, diagramContext, convertedNodes,
                this.feedbackMessageService);
        return operationInterpreter.executeTool(tool, variableManager);
    }
}
