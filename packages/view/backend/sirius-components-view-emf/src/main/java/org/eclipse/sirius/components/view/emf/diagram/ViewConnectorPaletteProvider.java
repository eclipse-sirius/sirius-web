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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.ConnectorPaletteResult;
import org.eclipse.sirius.components.collaborative.diagrams.api.DefaultConnectorToolResult;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProviderResult;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.palette.dto.IPaletteEntry;
import org.eclipse.sirius.components.palette.dto.ITool;
import org.eclipse.sirius.components.palette.dto.Palette;
import org.eclipse.sirius.components.palette.dto.PaletteDivider;
import org.eclipse.sirius.components.palette.dto.ToolSection;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IConnectorPaletteVariableManagerProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IEdgeToolConverter;
import org.springframework.stereotype.Service;

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

    private final IEdgeToolConverter edgeToolConverter;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final IConnectorPaletteVariableManagerProvider connectorPaletteVariableManagerProvider;

    public ViewConnectorPaletteProvider(
            IURLParser urlParser,
            IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate,
            IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService,
            IEdgeToolConverter edgeToolConverter,
            IViewAQLInterpreterFactory aqlInterpreterFactory,
            IConnectorPaletteVariableManagerProvider connectorPaletteVariableManagerProvider) {
        this.urlParser = Objects.requireNonNull(urlParser);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.edgeToolConverter = Objects.requireNonNull(edgeToolConverter);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
        this.connectorPaletteVariableManagerProvider = Objects.requireNonNull(connectorPaletteVariableManagerProvider);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public IConnectorToolsProviderResult getConnectorTools(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, Object sourceDiagramElement, Object targetDiagramElement, Object sourceElementDescription, Object targetElementDescription) {
        IConnectorToolsProviderResult result = null;
        var optionalVariableManager = this.connectorPaletteVariableManagerProvider.getVariableManager(editingContext, diagramContext, sourceDiagramElement, targetDiagramElement);
        var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());

        if (optionalDiagramDescription.isPresent() && optionalVariableManager.isPresent()) {
            var variableManager = optionalVariableManager.get();
            org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
            var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewDiagramDescription.eContainer());
            if (sourceElementDescription instanceof IDiagramElementDescription diagramElementDescription) {
                result = this.getResult(editingContext, diagramDescription, diagramElementDescription, variableManager, interpreter);
            }
        }

        return result;
    }

    private IConnectorToolsProviderResult getResult(IEditingContext editingContext, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        Optional<String> optionalSourceElementId = this.getSourceElementId(diagramElementDescription.getId());
        IConnectorToolsProviderResult result = null;

        if (optionalSourceElementId.isPresent()) {
            Optional<EdgeTool> candidateEdgeTool = this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, diagramElementDescription.getId())
                    .flatMap(nodeDescription -> nodeDescription.getEdgeTools().stream()
                            .filter(edgeTool -> this.checkPrecondition(edgeTool, variableManager, interpreter))
                            .findFirst())
                    .or(() -> this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, diagramElementDescription.getId())
                            .flatMap(edgeDescription -> edgeDescription.getEdgeTools().stream()
                                    .filter(edgeTool -> this.checkPrecondition(edgeTool, variableManager, interpreter))
                                    .findFirst()));

            if (candidateEdgeTool.isPresent() && candidateEdgeTool.get().getPalette() == null) {
                var tool = this.edgeToolConverter.createEdgeTool(interpreter, candidateEdgeTool.get(), diagramDescription, diagramElementDescription, variableManager);
                return new DefaultConnectorToolResult(tool);
            }

            List<IPaletteEntry> paletteEntries = new ArrayList<>();
            List<ITool> quickAccessTools = new ArrayList<>();
            if (candidateEdgeTool.isPresent()) {
                var firstEdgeTool = candidateEdgeTool.get();
                var palette = firstEdgeTool.getPalette();
                palette.getNodeTools().stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.edgeToolConverter.createEdgeToolFromNodeTool(tool, firstEdgeTool, diagramDescription, diagramElementDescription, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                palette.getToolSections().stream()
                        .map(nodeToolSection -> this.createToolSection(nodeToolSection, firstEdgeTool, diagramDescription, diagramElementDescription, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                palette.getQuickAccessTools().stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.edgeToolConverter.createEdgeToolFromNodeTool(tool, firstEdgeTool, diagramDescription, diagramElementDescription, variableManager, interpreter))
                        .forEach(quickAccessTools::add);
            }

            paletteEntries.add(new PaletteDivider(UUID.randomUUID().toString()));

            String paletteId = "siriusComponents://connectorPalette?diagramElementId=" + optionalSourceElementId.get();
            var palette = Palette.newPalette(paletteId)
                    .quickAccessTools(quickAccessTools)
                    .paletteEntries(paletteEntries)
                    .build();
            result = new ConnectorPaletteResult(palette);
        }
        return result;
    }

    private ToolSection createToolSection(NodeToolSection toolSection, EdgeTool viewEdgeTool, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(toolSection).toString().getBytes()).toString();
        var tools = new ArrayList<>(toolSection.getNodeTools().stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(tool -> this.edgeToolConverter.createEdgeToolFromNodeTool(tool, viewEdgeTool, diagramDescription, diagramElementDescription, variableManager, interpreter))
                .toList());
        
        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(tools)
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
}
