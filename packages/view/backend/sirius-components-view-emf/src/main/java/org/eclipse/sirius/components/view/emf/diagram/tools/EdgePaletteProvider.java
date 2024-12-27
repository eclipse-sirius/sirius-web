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
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.ToolFinder;
import org.eclipse.sirius.components.view.emf.diagram.api.IPaletteToolsProvider;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IEdgePaletteProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IEdgeToolFactory;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.INodeToolFactory;
import org.springframework.stereotype.Service;

/**
 * Used to compute the palette of an edge.
 *
 * @author sbegaudeau
 */
@Service
public class EdgePaletteProvider implements IEdgePaletteProvider {

    private final IURLParser urlParser;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final List<IPaletteToolsProvider> paletteToolsProviders;

    private final INodeToolFactory nodeToolFactory;

    private final IEdgeToolFactory edgeToolFactory;

    public EdgePaletteProvider(IURLParser urlParser, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, List<IPaletteToolsProvider> paletteToolsProviders, INodeToolFactory nodeToolFactory, IEdgeToolFactory edgeToolFactory) {
        this.urlParser = Objects.requireNonNull(urlParser);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.paletteToolsProviders = Objects.requireNonNull(paletteToolsProviders);
        this.nodeToolFactory = Objects.requireNonNull(nodeToolFactory);
        this.edgeToolFactory = Objects.requireNonNull(edgeToolFactory);
    }

    @Override
    public Palette getEdgePalette(IEditingContext editingContext, AQLInterpreter interpreter, DiagramDescription diagramDescription, DiagramContext diagramContext, EdgeDescription edgeDescription, Object diagramElement, VariableManager variableManager) {
        Palette edgePalette = null;
        var toolFinder = new ToolFinder();
        Optional<String> optionalSourceElementId = this.getSourceElementId(edgeDescription.getId());
        if (optionalSourceElementId.isPresent()) {
            var sourceElementId = optionalSourceElementId.get();

            var optionalEdgeDescription = this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, edgeDescription.getId());
            if (optionalEdgeDescription.isPresent()) {
                List<ToolSection> extraToolSections = new ArrayList<>();
                this.paletteToolsProviders.stream().map(paletteToolsProvider -> paletteToolsProvider.createExtraToolSections(diagramContext, edgeDescription, diagramElement)).flatMap(List::stream)
                        .forEach(extraToolSections::add);

                List<ITool> quickAccessTools = new ArrayList<>();
                this.paletteToolsProviders.stream().map(paletteToolsProvider -> paletteToolsProvider.createQuickAccessTools(diagramContext, edgeDescription, diagramElement)).flatMap(List::stream)
                        .forEach(quickAccessTools::add);
                org.eclipse.sirius.components.view.diagram.EdgeDescription viewEdgeDescription = optionalEdgeDescription.get();

                toolFinder.findQuickAccessNodeTools(viewEdgeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.nodeToolFactory.createNodeTool(interpreter, tool, false, variableManager))
                        .forEach(quickAccessTools::add);

                List<IPaletteEntry> paletteEntries = new ArrayList<>();
                toolFinder.findNodeTools(viewEdgeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.nodeToolFactory.createNodeTool(interpreter, tool, false, variableManager))
                        .forEach(paletteEntries::add);
                toolFinder.findEdgeTools(viewEdgeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(viewEdgeTools -> this.edgeToolFactory.createEdgeTool(interpreter, viewEdgeTools, diagramDescription, edgeDescription, variableManager))
                        .forEach(paletteEntries::add);
                toolFinder.findToolSections(viewEdgeDescription).stream()
                        .map(edgeToolSection -> this.createToolSection(edgeToolSection, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                paletteEntries.add(new PaletteDivider(UUID.randomUUID().toString()));
                paletteEntries.addAll(extraToolSections);

                String edgePaletteId = "siriusComponents://edgePalette?edgeId=" + sourceElementId;
                edgePalette = Palette.newPalette(edgePaletteId)
                        .quickAccessTools(quickAccessTools)
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

    private ToolSection createToolSection(EdgeToolSection toolSection, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(toolSection).toString().getBytes()).toString();

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(toolSection.getNodeTools().stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.nodeToolFactory.createNodeTool(interpreter, tool, false, variableManager))
                        .toList())
                .build();
    }
}
