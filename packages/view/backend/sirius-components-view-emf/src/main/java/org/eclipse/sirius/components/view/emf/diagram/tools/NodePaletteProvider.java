/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.IPaletteEntry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.collaborative.diagrams.dto.PaletteDivider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.ToolFinder;
import org.eclipse.sirius.components.view.emf.diagram.api.IPaletteToolsProvider;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IEdgeToolConverter;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.INodePaletteProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.INodeToolConverter;
import org.springframework.stereotype.Service;

/**
 * Used to compute the palette of a node.
 *
 * @author sbegaudeau
 */
@Service
public class NodePaletteProvider implements INodePaletteProvider {

    private final IURLParser urlParser;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final List<IPaletteToolsProvider> paletteToolsProviders;

    private final INodeToolConverter nodeToolConverter;

    private final IEdgeToolConverter edgeToolConverter;

    public NodePaletteProvider(IURLParser urlParser, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, List<IPaletteToolsProvider> paletteToolsProviders, INodeToolConverter nodeToolConverter, IEdgeToolConverter edgeToolConverter) {
        this.urlParser = Objects.requireNonNull(urlParser);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.paletteToolsProviders = Objects.requireNonNull(paletteToolsProviders);
        this.nodeToolConverter = Objects.requireNonNull(nodeToolConverter);
        this.edgeToolConverter = Objects.requireNonNull(edgeToolConverter);
    }

    @Override
    public Palette getNodePalette(IEditingContext editingContext, AQLInterpreter interpreter, DiagramDescription diagramDescription, DiagramContext diagramContext, NodeDescription nodeDescription, Object diagramElement, VariableManager variableManager) {
        Optional<String> sourceElementId = this.getSourceElementId(nodeDescription.getId());
        Palette nodePalette = null;
        var toolFinder = new ToolFinder();
        if (sourceElementId.isPresent()) {
            var optionalNodeDescription = this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, nodeDescription.getId());

            if (optionalNodeDescription.isPresent()) {
                List<ToolSection> extraToolSections = new ArrayList<>();
                this.paletteToolsProviders.stream().map(paletteToolsProvider -> paletteToolsProvider.createExtraToolSections(diagramContext, nodeDescription, diagramElement)).flatMap(List::stream)
                        .forEach(extraToolSections::add);

                List<ITool> quickAccessTools = new ArrayList<>();
                this.paletteToolsProviders.stream().map(paletteToolsProvider -> paletteToolsProvider.createQuickAccessTools(diagramContext, nodeDescription, diagramElement)).flatMap(List::stream)
                        .forEach(quickAccessTools::add);

                org.eclipse.sirius.components.view.diagram.NodeDescription viewNodeDescription = optionalNodeDescription.get();

                toolFinder.findQuickAccessNodeTools(viewNodeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.nodeToolConverter.createNodeTool(interpreter, tool, false, variableManager))
                        .forEach(quickAccessTools::add);

                var paletteEntries = new ArrayList<IPaletteEntry>();
                toolFinder.findNodeTools(viewNodeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.nodeToolConverter.createNodeTool(interpreter, tool, false, variableManager))
                        .forEach(paletteEntries::add);
                toolFinder.findEdgeTools(viewNodeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(viewEdgeTools -> this.edgeToolConverter.createEdgeTool(interpreter, viewEdgeTools, diagramDescription, nodeDescription, variableManager))
                        .forEach(paletteEntries::add);

                toolFinder.findToolSections(viewNodeDescription).stream()
                        .map(nodeToolSection -> this.createToolSection(nodeToolSection, diagramDescription, nodeDescription, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                paletteEntries.add(new PaletteDivider(UUID.randomUUID().toString()));
                paletteEntries.addAll(extraToolSections);

                String nodePaletteId = "siriusComponents://nodePalette?nodeId=" + sourceElementId.get();
                nodePalette = Palette.newPalette(nodePaletteId)
                        .quickAccessTools(quickAccessTools)
                        .paletteEntries(paletteEntries)
                        .build();
            }
        }
        return nodePalette;
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

    private ToolSection createToolSection(NodeToolSection toolSection, DiagramDescription diagramDescription, NodeDescription nodeDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(toolSection).toString().getBytes()).toString();

        var tools = new ArrayList<ITool>();
        tools.addAll(toolSection.getNodeTools().stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(tool -> this.nodeToolConverter.createNodeTool(interpreter, tool, false, variableManager))
                .toList());
        tools.addAll(toolSection.getEdgeTools().stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(viewEdgeTools -> this.edgeToolConverter.createEdgeTool(interpreter, viewEdgeTools, diagramDescription, nodeDescription, variableManager))
                .toList());

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(tools)
                .build();
    }
}
