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
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.diagrams.tools.ToolSection;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
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
     * {@link ViewToolSectionsProvider} can then find all the applicable tools, and is free to reorganize them in proper
     * user-facing sections and add the appropriate "extra tools".
     */
    public List<ToolSection> createPaletteBasedToolSections(org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var allToolSections = new ArrayList<ToolSection>();

        // Palette for the diagram itself
        String diagramPaletteId = "siriusComponents://diagramPalette?diagramId=" + this.objectService.getId(viewDiagramDescription);
        // @formatter:off
        var diagramPalette = ToolSection.newToolSection(diagramPaletteId)
                .label(viewDiagramDescription.getName())
                .tools(this.createDiagramPaletteTools(viewDiagramDescription, converterContext))
                .imageURL("")
                .build();
        // @formatter:on
        allToolSections.add(diagramPalette);

        // One palette for each NodeDescription
        for (var nodeDescription : converterContext.getConvertedNodes().keySet()) {
            String nodePaletteId = "siriusComponents://nodePalette?nodeId=" + this.objectService.getId(nodeDescription);
            // @formatter:off
            var nodePalette = ToolSection.newToolSection(nodePaletteId)
                    .label(nodeDescription.getName())
                    .tools(this.createNodePaletteTools(nodeDescription, converterContext))
                    .imageURL("")
                    .build();
            // @formatter:on
            allToolSections.add(nodePalette);
        }

        // One palette for each EdgeDescription
        for (var edgeDescription : converterContext.getConvertedEdges().keySet()) {
            String edgePaletteId = "siriusComponents://edgePalette?edgeId=" + this.objectService.getId(edgeDescription);
            // @formatter:off
            var edgeToolSection = ToolSection.newToolSection(edgePaletteId)
                    .label(edgeDescription.getName())
                    .tools(this.createEdgePaletteTools(edgeDescription, converterContext))
                    .imageURL("")
                    .build();
            // @formatter:on
            allToolSections.add(edgeToolSection);
        }

        return allToolSections;
    }

    private List<ITool> createDiagramPaletteTools(DiagramDescription viewDiagramDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var capturedConvertedNodes = Map.copyOf(converterContext.getConvertedNodes());
        List<ITool> diagramTools = new ArrayList<>();
        for (NodeTool nodeTool : new ToolFinder().findNodeTools(viewDiagramDescription)) {
            // @formatter:off
            String toolId = this.idProvider.apply(nodeTool).toString();
            var tool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                    .label(nodeTool.getName())
                    .imageURL(ViewToolImageProvider.NODE_CREATION_TOOL_ICON)
                    .handler(variableManager -> {
                        VariableManager child = variableManager.createChild();
                        child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                        return this.execute(converterContext, capturedConvertedNodes, nodeTool, child);
                    })
                    .targetDescriptions(List.of())
                    .selectionDescriptionId(this.objectService.getId(nodeTool.getSelectionDescription()))
                    .appliesToDiagramRoot(true)
                    .build();
            // @formatter:on
            diagramTools.add(tool);
        }
        return diagramTools;
    }

    private List<ITool> createNodePaletteTools(NodeDescription nodeDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var capturedConvertedNodes = Map.copyOf(converterContext.getConvertedNodes());
        List<ITool> tools = new ArrayList<>();

        for (NodeTool nodeTool : new ToolFinder().findNodeTools(nodeDescription)) {
            // @formatter:off
            String toolId = this.idProvider.apply(nodeTool).toString();
            var tool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                    .label(nodeTool.getName())
                    .imageURL(ViewToolImageProvider.NODE_CREATION_TOOL_ICON)
                    .handler(variableManager -> {
                        VariableManager child = variableManager.createChild();
                        child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                        return this.execute(converterContext, capturedConvertedNodes, nodeTool, child);
                    })
                    .targetDescriptions(List.of())
                    .selectionDescriptionId(this.objectService.getId(nodeTool.getSelectionDescription()))
                    .appliesToDiagramRoot(false)
                    .build();
            // @formatter:on
            tools.add(tool);
        }

        for (EdgeTool edgeTool : new ToolFinder().findEdgeTools(nodeDescription)) {
            // @formatter:off
            String toolId = this.idProvider.apply(edgeTool).toString();
            var tool = SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                    .label(edgeTool.getName())
                    .imageURL(ViewToolImageProvider.EDGE_CREATION_TOOL_ICON)
                    .candidates(List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                            .sources(List.of(converterContext.getConvertedNodes().get(nodeDescription)))
                            .targets(edgeTool.getTargetElementDescriptions().stream().map(converterContext.getConvertedNodes()::get).toList())
                            .build()))
                    .handler(variableManager -> {
                        VariableManager child = variableManager.createChild();
                        child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                        child.put("nodeDescription", nodeDescription);
                        return this.execute(converterContext, capturedConvertedNodes, edgeTool, child);
                    })
                    .build();
            // @formatter:on
            tools.add(tool);
        }
        return tools;
    }

    private List<ITool> createEdgePaletteTools(EdgeDescription edgeDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var capturedConvertedNodes = Map.copyOf(converterContext.getConvertedNodes());
        List<ITool> tools = new ArrayList<>();

        List<NodeTool> paletteSingleTargetTools = new ToolFinder().findNodeTools(edgeDescription);
        for (NodeTool nodeTool : paletteSingleTargetTools) {
            // @formatter:off
            String toolId = this.idProvider.apply(nodeTool).toString();
            var tool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                    .label(nodeTool.getName())
                    .imageURL(ViewToolImageProvider.NODE_CREATION_TOOL_ICON)
                    .handler(variableManager -> {
                        VariableManager child = variableManager.createChild();
                        child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                        return this.execute(converterContext, capturedConvertedNodes, nodeTool, child);
                    })
                    .targetDescriptions(List.of())
                    .appliesToDiagramRoot(false)
                    .build();
            // @formatter:on
            tools.add(tool);
        }

        return tools;
    }

    private IStatus execute(ViewDiagramDescriptionConverterContext converterContext, Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> capturedConvertedNodes,
            Tool tool, VariableManager variableManager) {
        IDiagramContext diagramContext = variableManager.get(IDiagramContext.DIAGRAM_CONTEXT, IDiagramContext.class).orElse(null);
        var operationInterpreter = new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, diagramContext,
                capturedConvertedNodes, this.feedbackMessageService);
        return operationInterpreter.executeTool(tool, variableManager);
    }
}
